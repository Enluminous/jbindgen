//
// Created by nettal on 23-11-7.
//

#ifndef JAVABINDGEN_STRUCTDECLARATION_H
#define JAVABINDGEN_STRUCTDECLARATION_H

#include <clang-c/Index.h>
#include <string>
#include <utility>
#include <vector>
#include <cstdint>
#include "Utils.h"

namespace jbindgen {
    class Member {
    public:
        const Typed type;
        const long offsetOfBit;

        explicit Member(Typed type, long offsetOfBit);
    };

    class StructDeclaration {
        static const StructDeclaration visit(CXCursor c) {
            auto name = toString(clang_getCursorSpelling(c));
            auto type = clang_getCursorType(c);
            StructDeclaration declaration(Typed(name, type, clang_Type_getSizeOf(type)));
            if (declaration.structType.size < 0) {
                return declaration;
            }
            std::string prefix;
            intptr_t pUser[] = {reinterpret_cast<intptr_t>(&declaration), (intptr_t) &prefix};
            clang_visitChildren(c, visitChildren, pUser);

        }

        static CXChildVisitResult visitChildren(CXCursor cursor,
                                                CXCursor parent,
                                                CXClientData client_data) {
            auto *this_ptr = (StructDeclaration *) (reinterpret_cast<intptr_t *>(client_data)[0]);
            auto *prefix = (std::string *) (reinterpret_cast<intptr_t *>(client_data)[0]);
            if (clang_getCursorKind(cursor) == CXCursor_FieldDecl) {

                this_ptr->members.emplace_back();
            }
            return CXChildVisit_Continue;
        }

    public:
        const Typed structType;
        const std::vector<Member> members{};

        explicit StructDeclaration(Typed structType);
    };
}


#endif //JAVABINDGEN_STRUCTDECLARATION_H
