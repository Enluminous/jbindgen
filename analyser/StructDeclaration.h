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
        const jbindgen::Typed type;
        const int64_t offsetOfBit;

        explicit Member(jbindgen::Typed  type, int64_t offsetOfBit);

        friend std::ostream &operator<<(std::ostream &stream, const Member &member);
    };

    class StructDeclaration {

    protected:
        static CXChildVisitResult visitChildren(CXCursor cursor,
                                                CXCursor parent,
                                                CXClientData client_data);

    public:
        static StructDeclaration visit(CXCursor c);

        const Typed structType;
        std::vector<jbindgen::Member> members{};

        explicit StructDeclaration(Typed structType);

        friend std::ostream &operator<<(std::ostream &stream, const StructDeclaration &str);
    };
}

#endif //JAVABINDGEN_STRUCTDECLARATION_H
