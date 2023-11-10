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
        auto type = clang_getCursorType(c);
        UnionDeclaration declaration(Typed(name, type, clang_Type_getSizeOf(type), getCommit(c), c));
        if (declaration.structType.size < 0) {
            return declaration;
        }
        intptr_t pUser[] = {reinterpret_cast<intptr_t>(&declaration), (intptr_t) ""};
        clang_visitChildren(c, UnionDeclaration::visitChildren, pUser);
        if (DEBUG_LOG) {
            std::cout << declaration;
        }
        return declaration;
    }

    UnionDeclaration::UnionDeclaration(Typed typed) : StructDeclaration(std::move(typed)) {

    }

    std::ostream &operator<<(std::ostream &stream, const UnionDeclaration &str) {
        stream << "#### Union " << str.structType << std::endl;
        for (auto &item: str.members) {
            stream << "  " << item << std::endl;
        }
        return stream;
    }
}