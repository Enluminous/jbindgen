//
// Created by nettal on 23-11-7.
//

#ifndef JAVABINDGEN_STRUCTDECLARATION_H
#define JAVABINDGEN_STRUCTDECLARATION_H

#include <clang-c/Index.h>
#include <string>
#include <utility>
#include <vector>
#include "Member.h"
#include <cstdint>
#include <stdexcept>
#include "Utils.h"

namespace jbindgen {


    class StructDeclaration {

    protected:
        static CXChildVisitResult visitChildren(CXCursor cursor,
                                                CXCursor parent,
                                                CXClientData client_data);

    public:
        static StructDeclaration visit(CXCursor c);

        const Typed structType;
        std::vector<jbendgen::Member> members{};

        explicit StructDeclaration(Typed structType);

        friend std::ostream &operator<<(std::ostream &stream, const StructDeclaration &str) {
            stream << "#### Structure " << str.structType << std::endl;
            for (auto &item: str.members) {
                stream << "  " << item << std::endl;
            }
            stream << "####" << std::endl;
            return stream;
        };
    };
}

#endif //JAVABINDGEN_STRUCTDECLARATION_H
