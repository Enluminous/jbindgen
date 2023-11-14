//
// Created by nettal on 23-11-13.
//

#ifndef JAVABINDGEN_FUNCTIONPROTOTYPEGENERATOR_H
#define JAVABINDGEN_FUNCTIONPROTOTYPEGENERATOR_H

#include <utility>

#include "../analyser/FunctionTypedefDeclaration.h"
#include "GenUtils.h"

namespace jbindgen {

    class FunctionProtoTypeGenerator {
        const FunctionTypedefDeclaration declaration;
        const std::string dir;
        const PFN_makeProtoType makeProtoType;
    public:
        FunctionProtoTypeGenerator(FunctionTypedefDeclaration declaration, std::string dir,
                                   PFN_makeProtoType makeProtoType);

        void build(void *userData) {
            auto function = makeProtoType(declaration, userData);
        }
    };

} // jbindgen


#endif //JAVABINDGEN_FUNCTIONPROTOTYPEGENERATOR_H
