//
// Created by nettal on 23-11-7.
//

#include "AnalyserUtils.h"

#include <utility>

std::string jbindgen::toString(const CXString&s) {
    std::string str(clang_getCString(s));
    clang_disposeString(s);
    return str;
}

std::string jbindgen::toString(const CXType&t) {
    auto spelling = clang_getTypeSpelling(t);
    return toString(spelling);
}

jbindgen::VarDeclare::VarDeclare(std::string name, CXType type, int64_t size, std::string commit,
                                 CXCursor cxCursor) : name(
                                                          std::move(name)), type(type), size(size),
                                                      commit(std::move(commit)), cursor(cxCursor), extra() {
}

jbindgen::VarDeclare::VarDeclare(std::string name, CXType type, int64_t size, std::string commit, CXCursor cxCursor,
                                 std::any extra) : name(
                                                          std::move(name)), type(type), size(size),
                                                      commit(std::move(commit)), cursor(cxCursor), extra(std::move(extra)) {
}

std::ostream& jbindgen::operator<<(std::ostream&stream, const jbindgen::VarDeclare&typed) {
    stream << "##Typed name: " << typed.name << " size: " << typed.size;
    return stream;
}

std::string jbindgen::getCommit(CXCursor cursor) {
    auto commit = clang_Cursor_getRawCommentText(cursor);
    if (clang_getCString(commit) != nullptr)
        return toString(commit);
    return {NO_COMMIT};
}
