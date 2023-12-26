//
// Created by nettal on 23-11-13.
//

#ifndef JBINDGEN_FUNCTIONPROTOTYPEGENERATOR_H
#define JBINDGEN_FUNCTIONPROTOTYPEGENERATOR_H

#include <utility>
#include <format>
#include <iostream>

#include "../analyser/FunctionTypeDefDeclaration.h"
#include "GenUtils.h"
#include "FunctionGeneratorUtils.h"
#include "TypedefGeneratorUtils.h"
#include "TypeManager.h"
#include "Generator.h"

namespace jbindgen {

    class FunctionProtoTypeGenerator {
        const FunctionSymbolDeclaration declaration;
        const Analyser &analyser;
        const std::string dir;
        const FN_makeFunction makeFunction;
        std::shared_ptr<TypeManager> typeManager;
        const GeneratorConfig& config;

        const std::string defsCallbackPackageName;
        const std::string defCallbackDir;

    public:
        FunctionProtoTypeGenerator(FunctionSymbolDeclaration declaration,
                                   std::shared_ptr<TypeManager> typeManager,
                                   const GeneratorConfig& config);

        void build();
    };

} // jbindgen


#endif //JBINDGEN_FUNCTIONPROTOTYPEGENERATOR_H
