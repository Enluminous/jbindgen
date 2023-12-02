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

namespace jbindgen {

    class FunctionProtoTypeGenerator {
        const FunctionSymbolDeclaration declaration;
        const Analyser &analyser;
        const std::string dir;
        const FN_makeFunction makeFunction;

        const std::string defsCallbackPackageName;
        const std::string defCallbackDir;
        const std::string nativeFunctionPackageName;
        const std::string nativeStructsPackageName;
        const std::string nativeValuesPackageName;
    public:
        FunctionProtoTypeGenerator(FunctionSymbolDeclaration declaration, const Analyser &analyser,
                                   std::string dir,
                                   std::string defsCallbackPackageName, std::string defCallbackDir,
                                   std::string nativeFunctionPackageName,
                                   std::string nativeStructsPackageName,
                                   std::string nativeValuesPackageName, FN_makeFunction makeFunction);

        void build();
    };

} // jbindgen


#endif //JBINDGEN_FUNCTIONPROTOTYPEGENERATOR_H
