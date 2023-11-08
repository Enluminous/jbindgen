//
// Created by nettal on 23-11-8.
//

#ifndef JAVABINDGEN_FUNCTIONTYPEDEFDECLARATION_H
#define JAVABINDGEN_FUNCTIONTYPEDEFDECLARATION_H

#include <string>
#include <vector>
#include "Utils.h"

namespace jbindgen {

    class FunctionTypedefDeclaration {
        std::vector<Typed> parameters{};

    public:
        const std::string name;
        const CXType returnType;

        FunctionTypedefDeclaration(std::string name, CXType returnType);

        static FunctionTypedefDeclaration visit(CXCursor cursor) {
            throw std::runtime_error("not_implemented");
        }
    };

} // jbindgen

#endif //JAVABINDGEN_FUNCTIONTYPEDEFDECLARATION_H
