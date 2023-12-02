//
// Created by nettal on 23-11-7.
//

#include "AnalyserUtils.h"
#include "Analyser.h"

#include <utility>
#include <iostream>

namespace jbindgen {
    bool hasDeclaration(CXType c) {
        auto decl = clang_getTypeDeclaration(c);
        if (decl.kind < CXCursor_FirstInvalid || decl.kind > CXCursor_LastInvalid)
            return true;
        return false;
    }

    std::string toString(const CXString &s) {
        const char *cString = clang_getCString(s);
        assert(cString != nullptr);
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
        return toString(spelling);
    }

    CXType removeCXTypeConst(const CXType &t) {
        if (clang_isConstQualifiedType(t)) {
            return clang_getUnqualifiedType(t);
        }
        return t;
    }

    VarDeclare::VarDeclare(std::string name, CXType type, int64_t size, std::string commit,
                           CXCursor cxCursor) : name(std::move(name)), type(type), byteSize(size),
                                                commit(std::move(commit)), cursor(cxCursor), extra() {
    }

    VarDeclare::VarDeclare(std::string name, CXType type, int64_t size, std::string commit, CXCursor cxCursor,
                           std::any extra) : name(std::move(name)), type(type), byteSize(size),
                                             commit(std::move(commit)), cursor(cxCursor), extra(std::move(extra)) {
    }

    std::ostream &operator<<(std::ostream &stream, const jbindgen::VarDeclare &typed) {
        stream << "##Typed name: " << typed.name << " size: " << typed.byteSize;
        return stream;
    }

    std::string getCommit(CXCursor cursor) {
        auto commit = clang_Cursor_getRawCommentText(cursor);
        if (clang_getCString(commit) != nullptr)
            return toString(commit);
        return {NO_COMMIT};
    }

    std::string makeUnnamedParaNamed(size_t index) {
        return "para" + std::to_string(index);
    }

    std::string makeUnnamedMemberNamed(size_t index) {
        return "member" + std::to_string(index);
    }

    std::string const DeclarationBasic::getName() const {
        assert(0);
    }

    void DeclarationBasic::addUsage(const std::string &c) {
        //notihing
    }

    CXType const DeclarationBasic::getCXType() const {
        assert(0);
    }

    const std::string EmptyDeclaration::getName() const {
        throw std::runtime_error("empty!");
    }

    void EmptyDeclaration::addUsage(const std::string &c) {
        throw std::runtime_error("empty!");
    }

    const CXType EmptyDeclaration::getCXType() const {
        throw std::runtime_error("empty!");
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

    int64_t checkResultSize(int64_t size) {
        switch (size) {
            case CXTypeLayoutError_Invalid:
                throw std::runtime_error("CXTypeLayoutError_Invalid");
            case CXTypeLayoutError_InvalidFieldName:
                throw std::runtime_error("CXTypeLayoutError_InvalidFieldName");
            case CXTypeLayoutError_NotConstantSize:
                throw std::runtime_error("CXTypeLayoutError_NotConstantSize");
            case CXTypeLayoutError_Incomplete:
                throw std::runtime_error("CXTypeLayoutError_Incomplete");
            case CXTypeLayoutError_Dependent:
                throw std::runtime_error("CXTypeLayoutError_Dependent");
            case CXTypeLayoutError_Undeduced:
                throw std::runtime_error("CXTypeLayoutError_Undeduced");
            default:
                if (size < 0)
                    throw std::runtime_error("CXTypeLayoutError_?");
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
