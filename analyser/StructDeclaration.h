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
#include <stdexcept>
#include "Utils.h"

namespace jbindgen {
    class Member {
    public:
        const Typed type;
        const int64_t offsetOfBit;

        explicit Member(Typed type, int64_t offsetOfBit);

        friend std::ostream &operator<<(std::ostream &stream, const Member &member) {
            stream << "Member Info:  " << member.type << " offsetOfBit: " << member.offsetOfBit;
            return stream;
        }
    };

    class StructDeclaration {

    protected:
        static CXChildVisitResult visitChildren(CXCursor cursor,
                                                CXCursor parent,
                                                CXClientData client_data);

    public:
        static StructDeclaration visit(CXCursor c);

        const Typed structType;
        std::vector<Member> members{};

        explicit StructDeclaration(Typed structType);

        friend std::ostream &operator<<(std::ostream &stream, const StructDeclaration &str) {
            stream << "Structure Info: " << str.structType;
            for (auto &item: str.members) {
                stream << "  " << item << std::endl;
            }
            return stream;
        };
    };
}

#endif //JAVABINDGEN_STRUCTDECLARATION_H
