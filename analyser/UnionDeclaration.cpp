//
// Created by snownf on 23-11-7.
//

#include <iostream>
#include <utility>
#include <cassert>
#include "UnionDeclaration.h"
#include "Analyser.h"

namespace jbindgen {
    UnionDeclaration UnionDeclaration::visit(CXCursor c, Analyser &analyser) {
        assert(c.kind == CXCursor_UnionDecl);
        auto name = toString(clang_getCursorSpelling(c));
        if (name.starts_with("union ")) {
            name = name.substr(std::string_view("union ").length());
        }
        auto type = clang_getCursorType(c);
        assert(type.kind == CXType_Record);
        UnionDeclaration declaration(VarDeclare(name, type, clang_Type_getSizeOf(type), getCommit(c), c));
        if (declaration.structType.byteSize < 0) {
            return declaration;
        }
        intptr_t pUser[] = {reinterpret_cast<intptr_t>(&declaration), reinterpret_cast<intptr_t>(&analyser)};
        clang_visitChildren(c, UnionDeclaration::visitChildren, pUser);
        if (DEBUG_LOG) {
            std::cout << declaration;
        }
        return declaration;
    }

    UnionDeclaration UnionDeclaration::visitStructUnnamed(CXCursor c, const std::string &name,
                                                          Analyser &analyser) {
        auto type = clang_getCursorType(c);
        UnionDeclaration declaration(VarDeclare(name, type, clang_Type_getSizeOf(type), getCommit(c), c));
        if (declaration.structType.byteSize < 0) {
            return declaration;
        }
        intptr_t pUser[] = {reinterpret_cast<intptr_t>(&declaration), reinterpret_cast<intptr_t>(&analyser)};
        clang_visitChildren(c, UnionDeclaration::visitChildren, pUser);
        if (DEBUG_LOG) {
            std::cout << declaration;
        }
        return declaration;
    }

    UnionDeclaration::UnionDeclaration(VarDeclare typed) : StructDeclaration(std::move(typed)) {

    }

    std::ostream &operator<<(std::ostream &stream, const UnionDeclaration &str) {
        stream << "#### Union " << str.structType << std::endl;
        for (auto &item: str.members) {
            stream << "  " << item << std::endl;
        }
        return stream;
    }
}