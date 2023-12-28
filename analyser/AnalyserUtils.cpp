//
// Created by nettal on 23-11-7.
//

#include "AnalyserUtils.h"
#include "Analyser.h"

#include <utility>
#include <iostream>
#include "clang-c/Documentation.h"

namespace jbindgen {
    bool hasDeclaration(CXType c) {
        auto decl = clang_getTypeDeclaration(c);
        if (decl.kind < CXCursor_FirstInvalid || decl.kind > CXCursor_LastInvalid)
            return true;
        return false;
    }

    std::string toString(const CXString &s) {
        const char *cString = clang_getCString(s);
        assertAppend(cString != nullptr, "");
        std::string str(cString);
        clang_disposeString(s);
        return str;
    }

    std::string toStringIfNullptr(const CXString &s) {
        auto cStr = clang_getCString(s);
        std::string str(cStr == nullptr ? "nullptr" : cStr);
        clang_disposeString(s);
        return str;
    }

    std::string toStringWithoutConst(const CXType &t) {
        auto spelling = clang_getTypeSpelling(t);
        if (clang_isConstQualifiedType(t)) {
            spelling = clang_getTypeSpelling(clang_getUnqualifiedType(t));
        }
        return toStringIfNullptr(spelling);
    }

    CXType removeCXTypeConst(const CXType &t) {
        if (clang_isConstQualifiedType(t)) {
            return clang_getUnqualifiedType(t);
        }
        return t;
    }

    VarDeclare::VarDeclare(std::string name, CXType type, int64_t size, std::string comment,
                           CXCursor cxCursor) : name(std::move(name)), type(type), byteSize(size),
                                                comment(std::move(comment)), cursor(cxCursor), extra() {
    }

    VarDeclare::VarDeclare(std::string name, CXType type, int64_t size, std::string comment, CXCursor cxCursor,
                           std::any extra) : name(std::move(name)), type(type), byteSize(size),
                                             comment(std::move(comment)), cursor(cxCursor), extra(std::move(extra)) {
    }

    std::ostream &operator<<(std::ostream &stream, const jbindgen::VarDeclare &typed) {
        stream << "##Typed name: " << typed.name << " size: " << typed.byteSize;
        return stream;
    }

    std::string getComment(CXCursor cursor) {
        std::string visit;
        clang_visitChildren(cursor, [](auto c, auto p, auto data) {
            CXTranslationUnit tu = clang_Cursor_getTranslationUnit(c);
            CXString spelling = clang_getCursorSpelling(c);
            CXSourceRange extent = clang_getCursorExtent(c);
            CXToken *tokens;
            unsigned numTokens;
            clang_tokenize(tu, extent, &tokens, &numTokens);
            for (unsigned i = 1; i < numTokens; ++i) {
                CXToken token = tokens[i];
                if (clang_getTokenKind(token) == CXToken_Comment) {
                    auto token_spelling = toString(clang_getTokenSpelling(tu, token));
                    *reinterpret_cast<std::string *>(data) += token_spelling;
                }
            }
            return CXChildVisit_Continue;
        }, &visit);
        if (!visit.empty()) {
            return visit;
        }
        auto commentText = clang_Cursor_getRawCommentText(cursor);
        if (clang_getCString(commentText) != nullptr)
            return toString(commentText);
        return {NO_COMMENT};
    }

    std::string makeUnnamedParaNamed(size_t index) {
        return "para" + std::to_string(index);
    }

    std::string makeUnnamedMemberNamed(size_t index) {
        return "member" + std::to_string(index);
    }

    std::string const DeclarationBasic::getName() const {
        assertAppend(0, "DeclarationBasic::getName");
    }

    void DeclarationBasic::addUsage(const std::string &c) {
        //notihing
    }

    CXType const DeclarationBasic::getCXType() const {
        assertAppend(0, "DeclarationBasic::getCXType");
    }

    size_t DeclarationBasic::visitResult() const {
        assertAppend(0, "DeclarationBasic::visitResult");
    }

    const std::string EmptyDeclaration::getName() const {
        assertAppend(0, "empty!");
    }

    void EmptyDeclaration::addUsage(const std::string &c) {
        assertStr(0, "empty!");
    }

    const CXType EmptyDeclaration::getCXType() const {
        assertStr(0, "empty!");
    }

    bool warningOthers(enum CXLinkageKind linkageKind, enum CXLinkageKind target, CXCursor c) {
        if (linkageKind != target)
            if (WARNING)
                std::cerr << "WARNING: ignore CXLinkageKind : " << target << " Linkage: " << clang_getCursorLinkage(c)
                          << ": "
                          << toString(clang_getCursorSpelling(c))
                          << std::endl;
        return linkageKind == target;
    }

    bool defaultAnalyserDeclFilter(const CXCursor &c, const AnalyserConfig &config) {
        unsigned line;
        unsigned column;
        CXFile file;
        unsigned offset;
        clang_getSpellingLocation(clang_getCursorLocation(c), &file, &line, &column, &offset);
        if (!toStringIfNullptr(clang_getFileName(file)).contains(config.acceptedPath)) {
            return false;
        }
        const auto &cursorKind = c.kind;
        const auto &linkage = clang_getCursorLinkage(c);
        if (cursorKind == CXCursor_StructDecl) {
            if (warningOthers(linkage, CXLinkage_External, c)) {
                return false;
            }
        }
        if (cursorKind == CXCursor_UnionDecl) {
            if (warningOthers(linkage, CXLinkage_External, c)) {
                return false;
            }
        }
        if (cursorKind == CXCursor_TypedefDecl) {
            if (linkage == CXLinkage_External || linkage == CXLinkage_NoLinkage) {
                return false;
            }
        }
        if (cursorKind == CXCursor_FunctionDecl) {
            if (warningOthers(linkage, CXLinkage_External, c)) {
                return true;
            }//only process external symbol
        }
        if (cursorKind == CXCursor_ClassDecl || cursorKind == CXCursor_CXXMethod) {
            assertStr(0, "CXCursor_ClassDecl || CXCursor_CXXMethod");
        }
        if (cursorKind == CXCursor_FieldDecl) {
            assertStr(0, "CXCursor_FieldDecl");
        }
        if (cursorKind == CXCursor_VarDecl) {
            if (linkage == CXLinkage_External || linkage == CXLinkage_Internal) {
                return true;
            }
        }
        if (cursorKind == CXCursor_EnumConstantDecl || cursorKind == CXCursor_EnumDecl) {
            if (warningOthers(linkage, CXLinkage_External, c)) {
                return false;
            }
        }
        if (cursorKind == CXCursor_ParmDecl) {
            assertStr(0, "CXCursor_ParmDecl");
        }
        return false;
    }

    int64_t checkResultSize(int64_t size) {
        switch (size) {
            case CXTypeLayoutError_Invalid: assertStr(0, "CXTypeLayoutError_Invalid");
            case CXTypeLayoutError_InvalidFieldName: assertStr(0, "CXTypeLayoutError_InvalidFieldName");
            case CXTypeLayoutError_NotConstantSize: assertStr(0, "CXTypeLayoutError_NotConstantSize");
            case CXTypeLayoutError_Incomplete: assertStr(0, "CXTypeLayoutError_Incomplete");
            case CXTypeLayoutError_Dependent: assertStr(0, "CXTypeLayoutError_Dependent");
            case CXTypeLayoutError_Undeduced: assertStr(0, "CXTypeLayoutError_Undeduced");
            default:
                if (size < 0) assertStr(0, "CXTypeLayoutError_?");
                return size;
        }
    }

    bool defaultAnalyserMacroFilter(const CXCursor &c, const AnalyserConfig &config) {
        unsigned line;
        unsigned column;
        CXFile file;
        unsigned offset;
        clang_getSpellingLocation(clang_getCursorLocation(c), &file, &line, &column, &offset);
        if (!toStringIfNullptr(clang_getFileName(file)).contains(config.acceptedPath)) {
            return false;
        }
        return true;
    }

    bool isValidSize(int64_t size) {
        return size > 0;
    }
}
