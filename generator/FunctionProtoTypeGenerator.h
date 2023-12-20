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

namespace jbindgen {

    class FunctionProtoTypeGenerator {
        const FunctionSymbolDeclaration declaration;
        const Analyser &analyser;
        const std::string dir;
        const FN_makeFunction makeFunction;
        std::shared_ptr<TypeManager> typeManager;

        const std::string defsCallbackPackageName;
        const std::string defCallbackDir;
        const std::string nativeFunctionPackageName;
        const std::string nativeStructsPackageName;
        const std::string nativeValuesPackageName;
        const std::string sharedBasePackageName;
        const std::string pointerInterfacePackageName;
        const std::string valueInterfacePackageName;
        const std::string sharedValuePackageName;
        const std::string enumFullyQualifiedName;

    public:
        FunctionProtoTypeGenerator(FunctionSymbolDeclaration declaration,
                                   const Analyser &analyser, std::shared_ptr<TypeManager> typeManager,
                                   std::string dir, std::string defsCallbackPackageName,
                                   std::string defCallbackDir,
                                   std::string nativeFunctionPackageName,
                                   std::string nativeStructsPackageName,
                                   std::string nativeValuesPackageName,
                                   std::string sharedBasePackageName,
                                   std::string pointerInterfacePackageName,
                                   std::string valueInterfacePackageName,
                                   std::string sharedValuePackageName,
                                   std::string enumFullyQualifiedName,
                                   FN_makeFunction makeFunction);

        void build();
    };

} // jbindgen


#endif //JBINDGEN_FUNCTIONPROTOTYPEGENERATOR_H
