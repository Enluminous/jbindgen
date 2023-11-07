//
// Created by nettal on 23-11-7.
//

#include "Utils.h"

#include <utility>

std::string jbindgen::toString(const CXString &s) {
    std::string str(clang_getCString(s));
    clang_disposeString(s);
    return str;
}

std::string jbindgen::toString(const CXType &t) {
    auto spelling = clang_getTypeSpelling(t);
    return toString(spelling);
}

jbindgen::Typed::Typed(std::string name, CXType type, int64_t size) : name(std::move(name)), type(type),
                                                                   size(size) {

}
