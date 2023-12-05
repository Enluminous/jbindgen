//
// Created by nettal on 23-11-7.
//

#include "Analyser.h"

#include <cassert>

#include "FunctionLikeMacroDeclaration.h"
#include "NormalTypedefDeclaration.h"
#include "FunctionSymbolDeclaration.h"
#include "../generator/GenUtils.h"
#include <iostream>
#include <utility>

using std::ostream;
using std::cout;
using std::cerr;
using std::endl;
using std::flush;

namespace jbindgen {
    Analyser::~Analyser() {
        clang_disposeTranslationUnit(unit4declaration);
        clang_disposeIndex(index4declaration);

        clang_disposeTranslationUnit(unit4macro);
        clang_disposeIndex(index4macro);
    }

    template<typename T>
    std::vector<T> deref_vector(const std::vector<std::shared_ptr<T>> &vec) {
        std::vector<T> result;
        result.reserve(vec.size());
        for (const auto &ptr: vec) {
            result.push_back(*ptr);
        }
        return result;
    }

    Analyser::Analyser(const AnalyserConfig &config) {
        index4declaration = clang_createIndex(0, 0);
        {
            auto err = clang_parseTranslationUnit2(
                    index4declaration,
                    config.path.c_str(), config.command_line_args, config.num_command_line_args,
                    nullptr, 0,
                   CXTranslationUnit_None , &unit4declaration);
            if (err != CXError_Success || unit4declaration == nullptr) {
                cerr << "Unable to parse translation unit (" << err << "). Quitting." << endl;
                exit(-1);
            }
            assert(clang_TargetInfo_getPointerWidth(clang_getTranslationUnitTargetInfo(unit4declaration)) == 64);
            CXCursor cursor = clang_getTranslationUnitCursor(unit4declaration);
            intptr_t ptrs[] = {
                    reinterpret_cast<intptr_t>(this),
                    reinterpret_cast<intptr_t>(&unit4declaration),
                    reinterpret_cast<intptr_t>(&config)
            };
            clang_visitChildren(
                    cursor,
                    [](CXCursor c, CXCursor parent, CXClientData ptrs) {
                        auto *pConfig = reinterpret_cast<AnalyserConfig *>(static_cast<intptr_t *>(ptrs)[2]);
                        if (pConfig->declFilter(c, parent, *pConfig)) {
                            Analyser &analyser = *reinterpret_cast<Analyser *>(static_cast<intptr_t *>(ptrs)[0]);
                            return Analyser::visitCXCursorStatic(c, analyser);
                        }
                        return CXChildVisit_Continue;
                    },
                    ptrs);
        }
        {
            //process macros
            index4macro = clang_createIndex(0, 0);
            auto err = clang_parseTranslationUnit2(
                    index4macro,
                    config.path.c_str(), nullptr, 0,
                    nullptr, 0,
                    //enable those flags to process macros.
                    CXTranslationUnit_DetailedPreprocessingRecord,
                    &unit4macro);
            if (err != CXError_Success || unit4macro == nullptr) {
                cerr << "Unable to parse translation unit (" << err << "). Quitting." << endl;
                exit(-1);
            }
            CXCursor cursor = clang_getTranslationUnitCursor(unit4macro);
            intptr_t ptrs[] = {
                    reinterpret_cast<intptr_t>(this),
                    reinterpret_cast<intptr_t>(&unit4macro),
                    reinterpret_cast<intptr_t>(&config)
            };
            clang_visitChildren(
                    cursor,
                    [](CXCursor c, CXCursor parent, CXClientData ptrs) {
                        Analyser *pAnalyser = reinterpret_cast<Analyser *>((reinterpret_cast<intptr_t *>(ptrs))[0]);
                        auto pConfig = reinterpret_cast<AnalyserConfig *>((reinterpret_cast<intptr_t *>(ptrs))[2]);
                        if (!pConfig->macroFilter(c, parent, *pConfig)) {
                            return CXChildVisit_Continue;
                        }
                        unsigned line;
                        unsigned column;
                        CXFile file;
                        unsigned offset;
                        clang_getSpellingLocation(clang_getCursorLocation(c), &file, &line, &column, &offset);
                        if (DEBUG_LOG) {
                            cout << "processing: " << toStringIfNullptr(clang_getFileName(file))
                                 << ":" << line << ":" << column << endl << std::flush;
                        }
                        CXCursorKind cursorKind = clang_getCursorKind(c);
                        if (clang_Cursor_isMacroBuiltin(c)) {
                            if (WARNING)
                                std::cout << "WARNING: unhandled Builtin Macro  "
                                          << toString(clang_getCursorDisplayName(c)) << " "
                                          << toStringIfNullptr(clang_getFileName(file)) << ":"
                                          << line << ":"
                                          << column
                                          << std::endl;
                            return CXChildVisit_Continue;
                        }
                        if (clang_Cursor_isMacroFunctionLike(c)) {
                            pAnalyser->
                                    visitFunctionLikeMacro(c);
                        }
                        if (cursorKind == CXCursor_MacroDefinition) {
                            pAnalyser->visitNormalMacro(c);
                        }
                        if (cursorKind == CXCursor_MacroExpansion) {
                            if (WARNING)
                                std::cout << "WARNING: unhandled kind CXCursor_MacroExpansion "
                                          << toString(clang_getCursorDisplayName(c)) << ": "
                                          << toStringIfNullptr(clang_getFileName(file)) << ":"
                                          << line << ":"
                                          << column
                                          << std::endl;
                        }
                        return CXChildVisit_Continue;
                    },
                    ptrs);
        }

        structs = deref_vector(_structs);
        unions = deref_vector(_unions);
        vars = deref_vector(_vars);
        enums = deref_vector(_enums);
        normalMacro = deref_vector(_normalMacro);
        functionLikeMacro = deref_vector(_functionLikeMacro);
        functionSymbols = deref_vector(_functions);
        functionPointers = deref_vector(_noCXCursorFunctions);
        typedefFunctions = deref_vector(_typedefFunctions);
        typedefs = deref_vector(_typedefs);
    }

    AnalyserConfig
    defaultAnalyserConfig(const std::string &path, const char *const *command_line_args, int num_command_line_args) {
        AnalyserConfig config;
        config.path = path;
        config.acceptedPath = path;
        config.command_line_args = command_line_args;
        config.num_command_line_args = num_command_line_args;
        config.declFilter = std::bind(defaultAnalyserDeclFilter, std::placeholders::_1, std::cref(config));
        config.macroFilter = std::bind(defaultAnalyserMacroFilter, std::placeholders::_1, std::cref(config));
        return config;
    }

    CXChildVisitResult Analyser::visitCXCursorStatic(const CXCursor &c, Analyser &pAnalyser) {
        if (DEBUG_LOG) {
            unsigned line;
            unsigned column;
            CXFile file;
            unsigned offset;
            clang_getSpellingLocation(clang_getCursorLocation(c), &file, &line, &column, &offset);
            auto name = clang_getFileName(file);
            cout << "processing: " << toStringIfNullptr(name)
                 << ":" << line << ":" << column << endl << std::flush;
        }
        CXCursorKind cursorKind = clang_getCursorKind(c);
        const auto &linkage = clang_getCursorLinkage(c);
        if (cursorKind == CXCursor_UnexposedDecl) {
            throw std::runtime_error("CXCursor_UnexposedDecl");
        }
        if (cursorKind == CXCursor_StructDecl) {
            pAnalyser.visitStruct(c);
        }
        if (cursorKind == CXCursor_UnionDecl) {
            pAnalyser.visitUnion(c);
        }
        if (cursorKind == CXCursor_TypedefDecl) {
            if (linkage == CXLinkage_External || linkage == CXLinkage_NoLinkage) {
                pAnalyser.visitTypedef(c);
            } else
                assert(0);
        }
        if (cursorKind == CXCursor_FunctionDecl) {
            if (linkage == CXLinkage_External) {
                pAnalyser.visitFunction(c);
            } else
                assert(0);
        }
        if (cursorKind == CXCursor_ClassDecl || cursorKind == CXCursor_CXXMethod) {
            throw std::runtime_error("CXCursor_ClassDecl || CXCursor_CXXMethod");
        }
        if (cursorKind == CXCursor_FieldDecl) {
            throw std::runtime_error("CXCursor_FieldDecl");
        }
        if (cursorKind == CXCursor_VarDecl) {
            if (linkage == CXLinkage_External || linkage == CXLinkage_Internal) {
                pAnalyser.visitVar(c);
            } else {
                assert(0);
            }
        }
        if (cursorKind == CXCursor_EnumConstantDecl || cursorKind == CXCursor_EnumDecl) {
            if (linkage == CXLinkage_External) {
                pAnalyser.visitEnum(c);
            }
        }
        if (cursorKind == CXCursor_ParmDecl) {
            throw std::runtime_error("CXCursor_ParmDecl");
        }
        return CXChildVisit_Continue;
    }

    void Analyser::visitCXCursor(const CXCursor &c) {
        auto cursorType = clang_getCursorType(c);
        if (cursorType.kind == CXType_Pointer || cursorType.kind == CXType_BlockPointer) {
            visitCXCursor(clang_getTypeDeclaration(clang_getPointeeType(cursorType)));
        }
        if (isArrayType(cursorType.kind)) {
            visitCXCursor(clang_getTypeDeclaration(clang_getArrayElementType(cursorType)));
        }
        if (cursorType.kind == CXType_Elaborated) {
            visitCXCursor(clang_getTypeDeclaration(cursorType));
        }
        Analyser::visitCXCursorStatic(c, *this);
    }

    void Analyser::visitCXType(const CXType &cursorType) {
        if (cursorType.kind == CXType_Pointer || cursorType.kind == CXType_BlockPointer) {
            visitCXType(clang_getPointeeType(cursorType));
        }
        if (isArrayType(cursorType.kind)) {
            visitCXType(clang_getArrayElementType(cursorType));
        }
        if (cursorType.kind == CXType_Elaborated) {
            visitCXType(clang_getCursorType(clang_getTypeDeclaration(cursorType)));
        }
        Analyser::visitCXCursorStatic(clang_getTypeDeclaration(cursorType), *this);
    }

    void Analyser::visitStruct(CXCursor param) {
        param = gotoDeclaration(param);
        if (cxCursorMap.contains(param))
            return;
        //cxCursorMap[param] updated while visit
        auto sharedPtr = StructDeclaration::visit(param, *this);
        if (DEBUG_LOG) {
            cout << *sharedPtr;
        }
        _structs.emplace_back(sharedPtr);
    }

    void Analyser::visitUnion(CXCursor param) {
        param = gotoDeclaration(param);
        if (cxCursorMap.contains(param))
            return;
        //cxCursorMap[param] updated while visit
        auto sharedPtr = UnionDeclaration::visit(param, *this);
        if (DEBUG_LOG) {
            cout << *sharedPtr;
        }
        _unions.emplace_back(sharedPtr);
    }

    void Analyser::visitEnum(CXCursor param) {
        param = gotoDeclaration(param);
        if (cxCursorMap.contains(param))
            return;
        //cxCursorMap[param] updated while visit
        auto sharedPtr = EnumDeclaration::visit(param, *this);
        if (DEBUG_LOG) {
            cout << *sharedPtr;
        }
        _enums.emplace_back(sharedPtr);
    }

    void Analyser::visitTypedef(CXCursor param) {
        param = gotoDeclaration(param);
        if (cxCursorMap.contains(param))
            return;
        //cxCursorMap[param] updated while visit
        auto sharedPtr = NormalTypedefDeclaration::visit(param, *this);
        if (DEBUG_LOG) {
            cout << *sharedPtr;
        }
        _typedefs.emplace_back(sharedPtr);
    }

    void Analyser::visitNormalMacro(CXCursor param) {
        if (cxCursorMap.contains(param))
            return;
        const NormalMacroDeclaration &declaration = NormalMacroDeclaration::visit(param);
        auto shared_ptr = std::make_shared<NormalMacroDeclaration>(declaration);
        cxCursorMap[param] = shared_ptr;
        if (DEBUG_LOG) {
            cout << declaration;
        }
        _normalMacro.emplace_back(shared_ptr);
    }

    void Analyser::visitFunctionLikeMacro(CXCursor param) {
        if (cxCursorMap.contains(param))
            return;
        const FunctionLikeMacroDeclaration &declaration = FunctionLikeMacroDeclaration::visit(param);
        auto shared_ptr = std::make_shared<FunctionLikeMacroDeclaration>(declaration);
        cxCursorMap[param] = shared_ptr;
        if (DEBUG_LOG) {
            cout << declaration;
        }
        _functionLikeMacro.emplace_back(shared_ptr);
    }

    void Analyser::visitFunction(CXCursor param) {
        param = gotoDeclaration(param);
        if (cxCursorMap.contains(param))
            return;
        //cxCursorMap[param] updated while visit
        auto shared_ptr = FunctionSymbolDeclaration::visit(param, *this);
        if (DEBUG_LOG) {
            cout << shared_ptr;
        }
        _functions.emplace_back(shared_ptr);
    }

    void Analyser::visitTypeDefFunction(CXCursor param) {
        param = gotoDeclaration(param);
        if (cxCursorMap.contains(param))
            return;
        //cxCursorMap[param] updated while visit
        auto shared_ptr = FunctionTypedefDeclaration::visit(param, *this);
        if (DEBUG_LOG) {
            cout << shared_ptr;
        }
        _typedefFunctions.emplace_back(shared_ptr);
    }

    void
    Analyser::visitStructInternalFunctionPointer(CXCursor param, std::shared_ptr<StructDeclaration> &parent,
                                                 const std::string &candidateName) {
        param = gotoDeclaration(param);
        if (cxCursorMap.contains(param))
            return;
        assert(parent != nullptr);
        //cxCursorMap[param] updated while visit
        auto shared_ptr = FunctionTypedefDeclaration::visitFunctionUnnamedPointer(
                param, parent, *this, candidateName);
        if (DEBUG_LOG) {
            cout << shared_ptr;
        }
        _typedefFunctions.emplace_back(shared_ptr);
    }

    void Analyser::visitStructInternalStruct(CXCursor param, const std::shared_ptr<StructDeclaration> &parent,
                                             const std::string &candidateName) {
        param = gotoDeclaration(param);
        if (cxCursorMap.contains(param))
            return;
        //cxCursorMap[param] updated while visit
        assert(parent != nullptr);
        auto declaration = StructDeclaration::visitInternalStruct(
                param, parent, *this, candidateName);
        if (DEBUG_LOG) {
            cout << *declaration;
        }
        _structs.emplace_back(declaration);
    }

    void Analyser::visitStructInternalUnion(CXCursor param, const std::shared_ptr<StructDeclaration> &parent,
                                            const std::string &candidateName) {
        param = gotoDeclaration(param);
        if (cxCursorMap.contains(param))
            return;
        //cxCursorMap[param] updated while visit
        assert(parent != nullptr);
        auto sharedPtr = UnionDeclaration::visitInternalUnion(
                param, parent, *this, candidateName);
        if (DEBUG_LOG) {
            cout << sharedPtr;
        }
        _unions.emplace_back(sharedPtr);
    }

    void Analyser::visitVar(CXCursor param) {
        param = gotoDeclaration(param);
        if (cxCursorMap.contains(param))
            return;
        auto shared_ptr = std::make_shared<VarDeclaration>(VarDeclaration::visit(param, *this));
        cxCursorMap[param] = shared_ptr;
        if (DEBUG_LOG) {
            cout << *shared_ptr;
        }
        _vars.emplace_back(shared_ptr);
    }

    CXCursor Analyser::gotoDeclaration(const CXCursor &param) {
        auto def = clang_getCursorDefinition(param);
        if (!clang_isInvalid(def.kind)) {
            return def;
        }
        CXType type = clang_getCursorType(param);
        assert(type.kind != CXType_Invalid);
        const CXCursor &declaration = clang_getTypeDeclaration(type);
        if (!clang_isInvalid(declaration.kind)) {
            return declaration;
        }
        return param;
    }

    std::shared_ptr<FunctionSymbolDeclaration>
    Analyser::visitNoCursorFunction(const CXType &cxType, const std::shared_ptr<DeclarationBasic> &parent,
                                    const std::string &candidateName) {
        for (const auto &item: _noCXCursorFunctions) {
            if (clang_equalTypes(item->getCXType(), cxType))
                return item;
        }
        auto shared_ptr = FunctionSymbolDeclaration::visitNoCXCursor(cxType, *this,
                                                                     parent, candidateName);
        if (DEBUG_LOG) {
            cout << shared_ptr;
        }
        _noCXCursorFunctions.emplace_back(shared_ptr);
        return shared_ptr;
    }

    void Analyser::updateCXCursorMap(const CXCursor &c, const std::shared_ptr<DeclarationBasic> &declarationBasic) {
        assert(c.kind < CXCursor_FirstInvalid || c.kind > CXCursor_LastInvalid);
        cxCursorMap[c] = declarationBasic;
    }

    const CXCursorMap &Analyser::getCXCursorMap() const {
        return cxCursorMap;
    }
}
