//
// Created by nettal on 23-11-7.
//

#include "Analyser.h"
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

    Analyser::Analyser(const std::string &path, const char *const *command_line_args, int num_command_line_args) {
        index4declaration = clang_createIndex(0, 0);
        {
            auto err = clang_parseTranslationUnit2(
                    index4declaration,
                    path.c_str(), command_line_args, num_command_line_args,
                    nullptr, 0,
                    CXTranslationUnit_SkipFunctionBodies, &unit4declaration);
            if (err != CXError_Success || unit4declaration == nullptr) {
                cerr << "Unable to parse translation unit (" << err << "). Quitting." << endl;
                exit(-1);
            }
            CXCursor cursor = clang_getTranslationUnitCursor(unit4declaration);
            intptr_t ptrs[] = {
                    reinterpret_cast<intptr_t>(this),
                    reinterpret_cast<intptr_t>(&unit4declaration),
                    (intptr_t) path.c_str()
            };
            clang_visitChildren(
                    cursor,
                    [](CXCursor c, CXCursor parent, CXClientData ptrs) {
                        if (DEBUG_LOG) {
                            char *path = reinterpret_cast<char *>((reinterpret_cast<intptr_t *>(ptrs))[2]);
                            unsigned line;
                            unsigned column;
                            CXFile file;
                            unsigned offset;
                            clang_getSpellingLocation(clang_getCursorLocation(c), &file, &line, &column, &offset);
                            cout << "processing: " << path << ":" << line << ":" << column << endl << std::flush;
                        }
                        CXCursorKind cursorKind = clang_getCursorKind(c);
                        if (cursorKind == CXCursor_UnexposedDecl) {
                            throw std::runtime_error("CXCursor_UnexposedDecl");
                        }
                        if (cursorKind == CXCursor_StructDecl) {
                            reinterpret_cast<Analyser *>((reinterpret_cast<intptr_t *>(ptrs))[0])->visitStruct(c);
                        }
                        if (cursorKind == CXCursor_UnionDecl) {
                            reinterpret_cast<Analyser *>((reinterpret_cast<intptr_t *>(ptrs))[0])->visitUnion(c);
                        }
                        if (cursorKind == CXCursor_TypedefDecl) {
                            reinterpret_cast<Analyser *>((reinterpret_cast<intptr_t *>(ptrs))[0])->visitTypedef(c);
                        }
                        if (cursorKind == CXCursor_FunctionDecl) {
                            reinterpret_cast<Analyser *>((reinterpret_cast<intptr_t *>(ptrs))[0])->visitFunction(c);
                        }
                        if (cursorKind == CXCursor_ClassDecl || cursorKind == CXCursor_CXXMethod) {
                            throw std::runtime_error("CXCursor_ClassDecl || CXCursor_CXXMethod");
                        }
                        if (cursorKind == CXCursor_VarDecl || cursorKind == CXCursor_FieldDecl) {
                            if (DEBUG_LOG)
                                cerr << "WARNING: ignore: VarDecl || FieldDecl: "
                                     << toString(clang_getCursorSpelling(c))
                                     << endl;
                        }
                        if (cursorKind == CXCursor_EnumConstantDecl || cursorKind == CXCursor_EnumDecl) {
                            reinterpret_cast<Analyser *>((reinterpret_cast<intptr_t *>(ptrs))[0])->visitEnum(c);
                        }
                        if (cursorKind == CXCursor_ParmDecl) {
                            throw std::runtime_error("CXCursor_ParmDecl");
                        }
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
                    path.c_str(), args, 1,
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
                    reinterpret_cast<intptr_t>(&unit4macro),
                    (intptr_t) path.c_str()
            };
            clang_visitChildren(
                    cursor,
                    [](CXCursor c, CXCursor parent, CXClientData ptrs) {
                        char *path = reinterpret_cast<char *>((reinterpret_cast<intptr_t *>(ptrs))[2]);

                        unsigned line;
                        unsigned column;
                        CXFile file;
                        unsigned offset;
                        clang_getSpellingLocation(clang_getCursorLocation(c), &file, &line, &column, &offset);
                        if (DEBUG_LOG) {
                            cout << "processing: " << path << ":" << line << ":" << column << endl << std::flush;
                        }
                        //                    clang_Cursor_getCommentRange()
                        CXCursorKind cursorKind = clang_getCursorKind(c);
                        if (clang_Cursor_isMacroFunctionLike(c)) {
                            reinterpret_cast<Analyser *>((reinterpret_cast<intptr_t *>(ptrs))[0])->
                                    visitFunctionLikeMacro(c);
                        }
                        if (cursorKind == CXCursor_MacroDefinition) {
                            reinterpret_cast<Analyser *>((reinterpret_cast<intptr_t *>(ptrs))[0])->visitNormalMacro(c);
                        }
                        if (cursorKind == CXCursor_MacroExpansion) {
                            std::cout << "WARNING: unhandled kind CXCursor_MacroExpansion "
                                      << toString(clang_getCursorDisplayName(c)) << " " << path << ":" << line << ":"
                                      << column
                                      << std::endl;
                            //                            assert(0);
                        }
                        return CXChildVisit_Continue;
                    },
                    ptrs);
        }
    }

    void Analyser::visitStruct(CXCursor param) {
        const StructDeclaration &declaration = StructDeclaration::visit(param, *this);
        if (DEBUG_LOG) {
            cout << declaration;
        }
        structs.emplace_back(declaration);
    }

    void Analyser::visitUnion(CXCursor param) {
        const UnionDeclaration &declaration = UnionDeclaration::visit(param, *this);
        if (DEBUG_LOG) {
            cout << declaration;
        }
        unions.emplace_back(declaration);
    }

    void Analyser::visitEnum(CXCursor param) {
        const EnumDeclaration &declaration = EnumDeclaration::visit(param);
        if (DEBUG_LOG) {
            cout << declaration;
        }
        enums.emplace_back(declaration);
    }

    void Analyser::visitTypedef(CXCursor param) {
        auto declaration = NormalTypedefDeclaration::visit(param, *this);
        if (DEBUG_LOG) {
            cout << declaration;
        }
        typedefs.emplace_back(declaration);
    }

    void Analyser::visitNormalMacro(CXCursor param) {
        const NormalMacroDeclaration &declaration = NormalMacroDeclaration::visit(param);
        if (DEBUG_LOG) {
            cout << declaration;
        }
        normalMacro.emplace_back(declaration);
    }

    void Analyser::visitFunctionLikeMacro(CXCursor param) {
        const FunctionLikeMacroDeclaration &declaration = FunctionLikeMacroDeclaration::visit(param);
        if (DEBUG_LOG) {
            cout << declaration;
        }
        functionLikeMacro.emplace_back(declaration);
    }

    void Analyser::visitFunction(CXCursor param) {
        FunctionDeclaration declaration = FunctionDeclaration::visit(param);
        if (DEBUG_LOG) {
            cout << declaration;
        }
        functions.push_back(std::move(declaration));
    }

    void Analyser::visitTypeDefFunction(CXCursor param) {
        const FunctionTypedefDeclaration &declaration = FunctionTypedefDeclaration::visit(param);
        if (DEBUG_LOG) {
            cout << declaration;
        }
        typedefFunctions.emplace_back(declaration);
    }

    void Analyser::visitStructUnnamedFunction(CXCursor param, const std::string &functionName) {
        const FunctionTypedefDeclaration &declaration = FunctionTypedefDeclaration::visitFunctionUnnamed(
                param, functionName);
        if (DEBUG_LOG) {
            cout << declaration;
        }
        typedefFunctions.emplace_back(declaration);
    }

    void Analyser::visitStructUnnamedStruct(CXCursor param, const std::string &structName) {
        const StructDeclaration &declaration = StructDeclaration::visitStructUnnamed(
                param, structName, *this);
        if (DEBUG_LOG) {
            cout << declaration;
        }
        structs.emplace_back(declaration);
    }
    void Analyser::visitStructUnnamedUnion(CXCursor param, const std::string &structName) {
        const UnionDeclaration &declaration = UnionDeclaration::visitStructUnnamed(
                param, structName, *this);
        if (DEBUG_LOG) {
            cout << declaration;
        }
        unions.emplace_back(declaration);
    }
}
