//
// Created by snownf on 23-11-7.
//

#include <iostream>
#include <utility>
#include "UnionDeclaration.h"
#include "Analyser.h"

namespace jbindgen {
    UnionDeclaration UnionDeclaration::visit(CXCursor c) {
        auto name = toString(clang_getCursorSpelling(c));
        if (std::equal(name.begin(), name.end(), "ma_engine")) {
            std::cout << "ma_engine";
        }
        auto type = clang_getCursorType(c);
        UnionDeclaration declaration(Typed(name, type, clang_Type_getSizeOf(type)));
        if (declaration.structType.size < 0) {
            return declaration;
        }
        intptr_t pUser[] = {reinterpret_cast<intptr_t>(&declaration), (intptr_t) ""};
        clang_visitChildren(c, visitChildren, pUser);
        if (DEBUG_LOG) {
            std::cout << declaration;
        }
        return declaration;
    }

    UnionDeclaration::UnionDeclaration(Typed typed) : StructDeclaration(std::move(typed)) {

    }
}