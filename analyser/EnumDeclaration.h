//
// Created by nettal on 23-11-7.
//

#ifndef JAVABINDGEN_ENUMDECLARATION_H
#define JAVABINDGEN_ENUMDECLARATION_H

#include <string>
#include <clang-c/Index.h>
#include <vector>
#include "Utils.h"

namespace jbindgen {

    class EnumMember {
    public:
        const Typed type;
        const int64_t declValue;
        const std::string declStr;

        explicit EnumMember(Typed type, int64_t declValue, std::string declStr);

        friend std::ostream &operator<<(std::ostream &stream, const EnumMember &member);
    };

    class EnumDeclaration {

        static CXChildVisitResult visitChildren(CXCursor cursor,
                                                CXCursor parent,
                                                CXClientData client_data);

    public:
        static EnumDeclaration visit(CXCursor c);

        std::vector<EnumMember> members{};
        const std::string name;
        const Typed type;

        EnumDeclaration(std::string name, Typed type);

        friend std::ostream &operator<<(std::ostream &stream, const EnumDeclaration &declaration);
    };
}


#endif //JAVABINDGEN_ENUMDECLARATION_H
