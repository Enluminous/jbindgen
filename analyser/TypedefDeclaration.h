//
// Created by nettal on 23-11-7.
//

#ifndef JAVABINDGEN_TYPEDEFDECLARATION_H
#define JAVABINDGEN_TYPEDEFDECLARATION_H

#include "Utils.h"

namespace jbindgen {
    class Analyser;//forward declare

    class TypedefDeclaration {
        static CXChildVisitResult visitChildren(CXCursor cursor,
                                                CXCursor parent,
                                                CXClientData client_data);

    public:
        const std::string oriStr;
        const std::string mappedStr;
        const CXType ori;
        const CXType mapped;

        TypedefDeclaration(std::string oriStr, std::string mappedStr,CXType ori,CXType mapped);

        static TypedefDeclaration visit(CXCursor c, Analyser &analyser);

        friend std::ostream &operator<<(std::ostream &stream, const TypedefDeclaration &declaration);
    };

} // jbindgen

#endif //JAVABINDGEN_TYPEDEFDECLARATION_H
