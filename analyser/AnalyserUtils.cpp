//
// Created by nettal on 23-11-7.
//

#include "AnalyserUtils.h"

#include <utility>

namespace jbindgen {
    bool hasDeclaration(CXType c) {
        auto decl = clang_getTypeDeclaration(c);
        if (decl.kind < CXCursor_FirstInvalid || decl.kind > CXCursor_LastInvalid)
            return true;
        return false;
    }

    std::string toString(const CXString&s) {
        std::string str(clang_getCString(s));
        clang_disposeString(s);
        return str;
    }

    std::string toStringIfNullptr(const CXString&s) {
        auto cStr = clang_getCString(s);
        std::string str(cStr == nullptr ? "nullptr" : cStr);
        clang_disposeString(s);
        return str;
    }

    std::string toStringWithoutConst(const CXType&t) {
        auto spelling = clang_getTypeSpelling(t);
        if (clang_isConstQualifiedType(t)) {
            spelling = clang_getTypeSpelling(clang_getUnqualifiedType(t));
        }
        return toString(spelling);
    }

    VarDeclare::VarDeclare(std::string name, CXType type, int64_t size, std::string commit,
                           CXCursor cxCursor) : name(
                                                    std::move(name)), type(type), byteSize(size),
                                                commit(std::move(commit)), cursor(cxCursor), extra() {
    }

    VarDeclare::VarDeclare(std::string name, CXType type, int64_t size, std::string commit, CXCursor cxCursor,
                           std::any extra) : name(
                                                 std::move(name)), type(type), byteSize(size),
                                             commit(std::move(commit)), cursor(cxCursor), extra(std::move(extra)) {
    }

    std::ostream& operator<<(std::ostream&stream, const jbindgen::VarDeclare&typed) {
        stream << "##Typed name: " << typed.name << " size: " << typed.byteSize;
        return stream;
    }

    std::string getCommit(CXCursor cursor) {
        auto commit = clang_Cursor_getRawCommentText(cursor);
        if (clang_getCString(commit) != nullptr)
            return toString(commit);
        return {NO_COMMIT};
    }

    std::string const DeclarationBasic::getName() const{
        assert(0);
    }

    void DeclarationBasic::addUsage(const std::string&c) {
        //notihing
    }
}
