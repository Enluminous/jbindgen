//
// Created by nettal on 23-11-7.
//

#include "Analyser.h"

#include <cassert>

#include "FunctionLikeMacroDeclaration.h"
#include "NormalTypedefDeclaration.h"
#include "FunctionSymbolDeclaration.h"
#include <iostream>

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

    bool warningOthers(enum CXLinkageKind linkageKind, enum CXLinkageKind target, CXCursor c) {
        if (linkageKind != target)
            if (WARNING)
                cerr << "WARNING: ignore CXLinkageKind : " << target << " Linkage: " << clang_getCursorLinkage(c)
                     << ": "
                     << toString(clang_getCursorSpelling(c))
                     << endl;
        return linkageKind == target;
    }

    Analyser::Analyser(const AnalyserConfig &config) {
        index4declaration = clang_createIndex(0, 0);
        {
            auto err = clang_parseTranslationUnit2(
                    index4declaration,
                    config.path.c_str(), config.command_line_args, config.num_command_line_args,
                    nullptr, 0,
                    CXTranslationUnit_SkipFunctionBodies, &unit4declaration);
            assert(clang_TargetInfo_getPointerWidth(clang_getTranslationUnitTargetInfo(unit4declaration)) == 64);
            if (err != CXError_Success || unit4declaration == nullptr) {
                cerr << "Unable to parse translation unit (" << err << "). Quitting." << endl;
                exit(-1);
            }
            CXCursor cursor = clang_getTranslationUnitCursor(unit4declaration);
            intptr_t ptrs[] = {
                    reinterpret_cast<intptr_t>(this),
                    reinterpret_cast<intptr_t>(&unit4declaration),
                    reinterpret_cast<intptr_t>(config.filter)
            };
            clang_visitChildren(
                    cursor,
                    [](CXCursor c, CXCursor parent, CXClientData ptrs) {
                        if (reinterpret_cast<AnalyserFilter>(static_cast<intptr_t *>(ptrs)[2])(c, parent))
                            return Analyser::visitCXCursorStatic(c,
                                                                 *reinterpret_cast<Analyser *>(static_cast<intptr_t *>(ptrs)[0]));
                        return CXChildVisit_Continue;
                    },
                    ptrs);
        }
        {
            //process macros
            index4macro = clang_createIndex(0, 0);
            auto const arg = "-nostdinc";
            auto const args = &arg;
            auto err = clang_parseTranslationUnit2(
                    index4macro,
                    config.path.c_str(), args, 1,
                    nullptr, 0,
                    //enable those flags to process macros.
                    CXTranslationUnit_DetailedPreprocessingRecord | CXTranslationUnit_SingleFileParse,
                    &unit4macro);
            if (err != CXError_Success || unit4macro == nullptr) {
                cerr << "Unable to parse translation unit (" << err << "). Quitting." << endl;
                exit(-1);
            }
            CXCursor cursor = clang_getTranslationUnitCursor(unit4macro);
            intptr_t ptrs[] = {
                    reinterpret_cast<intptr_t>(this),
                    reinterpret_cast<intptr_t>(&unit4macro)
            };
            clang_visitChildren(
                    cursor,
                    [](CXCursor c, CXCursor parent, CXClientData ptrs) {
                        Analyser *pAnalyser = reinterpret_cast<Analyser *>((reinterpret_cast<intptr_t *>(ptrs))[0]);
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
                            std::cout << "WARNING: unhandled kind CXCursor_MacroExpansion "
                                      << toString(clang_getCursorDisplayName(c)) << " "
                                      << toStringIfNullptr(clang_getFileName(file)) << ":"
                                      << line << ":"
                                      << column
                                      << std::endl;
                        }
                        return CXChildVisit_Continue;
                    },
                    ptrs);
        }
    }

    bool defaultAnalyserFilter(CXCursor c, CXCursor parent) {
        const auto &cursorKind = c.kind;
        const auto &linkage = clang_getCursorLinkage(c);
        if (cursorKind == CXCursor_StructDecl) {
            if (warningOthers(linkage, CXLinkage_External, c)) {
                return false;
            } else
                assert(0);
        }
        if (cursorKind == CXCursor_UnionDecl) {
            if (warningOthers(linkage, CXLinkage_External, c)) {
                return false;
            } else
                assert(0);
        }
        if (cursorKind == CXCursor_TypedefDecl) {
            if (linkage == CXLinkage_External || linkage == CXLinkage_NoLinkage) {
                return false;
            } else
                assert(0);
        }
        if (cursorKind == CXCursor_FunctionDecl) {
            if (warningOthers(linkage, CXLinkage_External, c)) {
                return true;
            }//only process external symbol
        }
        if (cursorKind == CXCursor_ClassDecl || cursorKind == CXCursor_CXXMethod) {
            throw std::runtime_error("CXCursor_ClassDecl || CXCursor_CXXMethod");
        }
        if (cursorKind == CXCursor_FieldDecl) {
            throw std::runtime_error("CXCursor_FieldDecl");
        }
        if (cursorKind == CXCursor_VarDecl) {
            if (linkage == CXLinkage_External || linkage == CXLinkage_Internal) {
                return true;
            } else {
                assert(0);
            }
        }
        if (cursorKind == CXCursor_EnumConstantDecl || cursorKind == CXCursor_EnumDecl) {
            if (warningOthers(linkage, CXLinkage_External, c)) {
                return false;
            }
        }
        if (cursorKind == CXCursor_ParmDecl) {
            throw std::runtime_error("CXCursor_ParmDecl");
        }
        return false;
    }

    AnalyserConfig
    defaultAnalyserConfig(const std::string &path, const char *const *command_line_args, int num_command_line_args) {
        AnalyserConfig config;
        config.path = path;
        config.command_line_args = command_line_args;
        config.num_command_line_args = num_command_line_args;
        config.filter = defaultAnalyserFilter;
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
            if (linkage == CXLinkage_External) {
                pAnalyser.visitStruct(c);
            } else
                assert(0);
        }
        if (cursorKind == CXCursor_UnionDecl) {
            if (linkage == CXLinkage_External) {
                pAnalyser.visitUnion(c);
            } else
                assert(0);
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
        Analyser::visitCXCursorStatic(c, *this);
    }

    bool Analyser::checkVisited(const CXCursor &c) {
        if (cxCursorMap.contains(c)) {
            return true;//visited
        }
        cxCursorMap[c] = 1;
        return false;
    }

    void Analyser::visitStruct(const CXCursor &param) {
        if (checkVisited(param)) {
            return;
        }
        const StructDeclaration &declaration = StructDeclaration::visit(param, *this);
        cxCursorMap[param] = declaration;
        if (DEBUG_LOG) {
            cout << declaration;
        }
        structs.emplace_back(declaration);
    }

    void Analyser::visitUnion(const CXCursor &param) {
        if (checkVisited(param)) {
            return;
        }
        const UnionDeclaration &declaration = UnionDeclaration::visit(param, *this);
        cxCursorMap[param] = declaration;
        if (DEBUG_LOG) {
            cout << declaration;
        }
        unions.emplace_back(declaration);
    }

    void Analyser::visitEnum(const CXCursor &param) {
        if (checkVisited(param)) {
            return;
        }
        const EnumDeclaration &declaration = EnumDeclaration::visit(param);
        cxCursorMap[param] = declaration;
        if (DEBUG_LOG) {
            cout << declaration;
        }
        enums.emplace_back(declaration);
    }

    void Analyser::visitTypedef(const CXCursor &param) {
        if (checkVisited(param)) {
            return;
        }
        auto declaration = NormalTypedefDeclaration::visit(param, *this);
        cxCursorMap[param] = declaration;
        if (DEBUG_LOG) {
            cout << declaration;
        }
        typedefs.emplace_back(declaration);
    }

    void Analyser::visitNormalMacro(const CXCursor &param) {
        if (checkVisited(param)) {
            return;
        }
        const NormalMacroDeclaration &declaration = NormalMacroDeclaration::visit(param);
        cxCursorMap[param] = declaration;
        if (DEBUG_LOG) {
            cout << declaration;
        }
        normalMacro.emplace_back(declaration);
    }

    void Analyser::visitFunctionLikeMacro(const CXCursor &param) {
        if (checkVisited(param)) {
            return;
        }
        const FunctionLikeMacroDeclaration &declaration = FunctionLikeMacroDeclaration::visit(param);
        cxCursorMap[param] = declaration;
        if (DEBUG_LOG) {
            cout << declaration;
        }
        functionLikeMacro.emplace_back(declaration);
    }

    void Analyser::visitFunction(const CXCursor &param) {
        if (checkVisited(param)) {
            return;
        }
        FunctionDeclaration declaration = FunctionDeclaration::visit(param, *this);
        cxCursorMap[param] = declaration;
        if (DEBUG_LOG) {
            cout << declaration;
        }
        functions.push_back(std::move(declaration));
    }

    void Analyser::visitTypeDefFunction(const CXCursor &param) {
//        if (checkVisited(param)) { // let typedef function override origin typedef
//            return;
//        }
        const FunctionTypedefDeclaration &declaration = FunctionTypedefDeclaration::visit(param, *this);
        cxCursorMap[param] = declaration;
        if (DEBUG_LOG) {
            cout << declaration;
        }
        typedefFunctions.emplace_back(declaration);
    }

    void Analyser::visitStructUnnamedFunctionPointer(const CXCursor &param, const std::string &functionName) {
        if (checkVisited(param)) {
            return;
        }
        const FunctionTypedefDeclaration &declaration = FunctionTypedefDeclaration::visitFunctionUnnamedPointer(
                param, functionName, *this);
        cxCursorMap[param] = declaration;
        if (DEBUG_LOG) {
            cout << declaration;
        }
        FunctionTypedefDeclaration dec = {declaration.function, declaration.ret, declaration.canonicalName};
        for (const auto &item: declaration.paras) {
            dec.paras.emplace_back(item);
        }
        typedefFunctions.emplace_back(dec);
    }

    void Analyser::visitStructUnnamedStruct(const CXCursor &param, const std::string &structName) {
        if (checkVisited(param)) {
            return;
        }
        const StructDeclaration &declaration = StructDeclaration::visitStructUnnamed(
                param, structName, *this);
        cxCursorMap[param] = declaration;
        if (DEBUG_LOG) {
            cout << declaration;
        }
        structs.emplace_back(declaration);
    }

    void Analyser::visitStructUnnamedUnion(const CXCursor &param, const std::string &structName) {
        if (checkVisited(param)) {
            return;
        }
        const UnionDeclaration &declaration = UnionDeclaration::visitStructUnnamed(
                param, structName, *this);
        cxCursorMap[param] = declaration;
        if (DEBUG_LOG) {
            cout << declaration;
        }
        unions.emplace_back(declaration);
    }

    void Analyser::visitVar(const CXCursor &param) {
        if (checkVisited(param)) {
            return;
        }
        const VarDeclaration &declaration = VarDeclaration::visit(param, *this);
        cxCursorMap[param] = declaration;
        if (DEBUG_LOG) {
            cout << declaration;
        }
        vars.emplace_back(declaration);
    }
}
