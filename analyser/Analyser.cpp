//
// Created by nettal on 23-11-7.
//

#include "Analyser.h"
#include <iostream>
#include <cstdint>
#include <cassert>
#include <cstring>

using std::ostream;
using std::cout;
using std::cerr;
using std::endl;
using std::flush;

namespace jbindgen {
    Analyser::Analyser(const std::string &path, const char *const *command_line_args, int num_command_line_args) {
        index = clang_createIndex(0, 0);
        {
            auto err = clang_parseTranslationUnit2(
                    index,
                    path.c_str(), command_line_args, num_command_line_args,
                    nullptr, 0,
                    CXTranslationUnit_SkipFunctionBodies, &unit);
            if (err != CXError_Success || unit == nullptr) {
                cerr << "Unable to parse translation unit (" << err << "). Quitting." << endl;
                exit(-1);
            }
            CXCursor cursor = clang_getTranslationUnitCursor(unit);
            intptr_t ptrs[] = {reinterpret_cast<intptr_t>(this),
                               reinterpret_cast<intptr_t>(&unit)};
            clang_visitChildren(
                    cursor,
                    [](CXCursor c, CXCursor parent, CXClientData ptrs) {
                        if (DEBUG_LOG) {
                            CXFile file;
                            unsigned line;
                            unsigned column;
                            unsigned offset;
                            clang_getSpellingLocation(clang_getCursorLocation(c), &file, &line, &column, &offset);
//                            cout << " line: " << line << " column: "
//                                 << column << " offest: " << offset << endl << std::flush;
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
//                        if (cursorKind == CXCursor_TypedefDecl) {
//                            reinterpret_cast<Analyser *>((reinterpret_cast<intptr_t *>(ptrs))[0])->visitTypedef(c);
//                        }
//                        if (cursorKind == CXCursor_FunctionDecl) {
//                            reinterpret_cast<Analyser *>((reinterpret_cast<intptr_t *>(ptrs))[0])->visitFunction(c);
//                        }
//                        if (cursorKind == CXCursor_ClassDecl || cursorKind == CXCursor_CXXMethod) {
//                            throw std::runtime_error("CXCursor_ClassDecl");
//                        }
//                        if (cursorKind == CXCursor_VarDecl || cursorKind == CXCursor_FieldDecl) {
//                            cerr << "VarDecl || FieldDecl: " << cxstring2string(clang_getCursorSpelling(c)) << endl;
//                        }
                        if (cursorKind == CXCursor_EnumConstantDecl || cursorKind == CXCursor_EnumDecl) {
                            reinterpret_cast<Analyser *>((reinterpret_cast<intptr_t *>(ptrs))[0])->visitEnum(c);
                        }
                        if (cursorKind == CXCursor_ParmDecl) {
                            throw std::runtime_error("CXCursor_ParmDecl");
                        }
                        return CXChildVisit_Continue;
                    },
                    ptrs);
            clang_disposeTranslationUnit(unit);
            unit = nullptr;
        }

        {//process macros
            auto const arg = "-nostdinc";
            auto const args = &arg;
            clang_parseTranslationUnit2(
                    index,
                    path.c_str(), args, 1,
                    nullptr, 0,
                    //enable those flags to process macros.
                    CXTranslationUnit_DetailedPreprocessingRecord | CXTranslationUnit_SingleFileParse, &unit);
            if (unit == nullptr) {
                cerr << "Unable to parse translation unit. Quitting." << endl;
                exit(-1);
            }
            CXCursor cursor = clang_getTranslationUnitCursor(unit);
            intptr_t ptrs[] = {reinterpret_cast<intptr_t>(this),
                               reinterpret_cast<intptr_t>(&unit),
                               (intptr_t) path.c_str()};
            clang_visitChildren(
                    cursor,
                    [](CXCursor c, CXCursor parent, CXClientData ptrs) {
                        unsigned line;
                        unsigned column;
                        if (DEBUG_LOG) {
                            CXFile file;
                            unsigned offset;
                            clang_getSpellingLocation(clang_getCursorLocation(c), &file, &line, &column, &offset);
                            cout << " line: " << line << " column: "
                                 << column << " offest: " << offset << endl << std::flush;
                        }
//                    clang_Cursor_getCommentRange()
                        CXCursorKind cursorKind = clang_getCursorKind(c);
                        if (clang_Cursor_isMacroFunctionLike(c)) {
//                            reinterpret_cast<Analyser *>((reinterpret_cast<intptr_t *>(ptrs))[0])->
//                                    visitMacroFunctionLike(c,
//                                                           *reinterpret_cast<CXTranslationUnit *>(reinterpret_cast<intptr_t *>(ptrs)[2]));
                        }
                        if (cursorKind == CXCursor_MacroDefinition) {
                            reinterpret_cast<Analyser *>((reinterpret_cast<intptr_t *>(ptrs))[0])->visitDefinition(c);
                        }
                        if (cursorKind == CXCursor_MacroExpansion) {
                            char *path = reinterpret_cast<char *>((reinterpret_cast<intptr_t *>(ptrs))[2]);
                            std::cerr << "WARNING: unhandled kind CXCursor_MacroExpansion "
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
        const StructDeclaration &declaration = StructDeclaration::visit(param);
        if (DEBUG_LOG) {
            cout << declaration;
        }
        structs.emplace_back(declaration);
    }

    void Analyser::visitUnion(CXCursor param) {
        const UnionDeclaration &declaration = UnionDeclaration::visit(param);
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

    void Analyser::visitDefinition(CXCursor param) {
        CXTranslationUnit tu = clang_Cursor_getTranslationUnit(param);
        const std::string &ori = toString(clang_getCursorSpelling(param));
        std::string mapped;
        CXToken *tokens;
        unsigned numTokens;
        clang_tokenize(tu, clang_getCursorExtent(param), &tokens, &numTokens); // 将宏定义转换为令牌序列
        if (strcmp(toString(clang_getTokenSpelling(clang_Cursor_getTranslationUnit(param), tokens[0])).c_str(),
                   ori.c_str()) == 0) {
            for (unsigned i = 1; i < numTokens; ++i) {
                if (DEBUG_LOG)
                    cout << "token kind: " << clang_getTokenKind(tokens[i]) << std::endl << std::flush;
                mapped += toString(clang_getTokenSpelling(clang_Cursor_getTranslationUnit(param),
                                                          tokens[i]));
            }
        }
        normalDefinitions.emplace_back(ori, mapped);
        clang_disposeTokens(clang_Cursor_getTranslationUnit(param), tokens, numTokens); // 释放令牌序列
    }
}
