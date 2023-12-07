//
// Created by nettal on 23-11-18.
//

#ifndef JBINDGEN_TYPEDEFGENERATOR_H
#define JBINDGEN_TYPEDEFGENERATOR_H

#include <string>
#include <iostream>
#include <sstream>
#include <format>
#include "../analyser/NormalTypedefDeclaration.h"
#include "GenUtils.h"
#include "FunctionGeneratorUtils.h"

#define GEN_FUNCTION "GEN_FUNCTION"

namespace jbindgen {
    typedef std::function<std::tuple<std::string, std::string, bool>(
            const NormalTypedefDeclaration *declaration)> FN_def_name;

    class TypedefGenerator {
        NormalTypedefDeclaration declaration;
        const std::string defsStructPackageName;
        const std::string defsEnumPackageName;
        const std::string defsValuePackageName;
        const std::string defEnumDir;
        const std::string defStructDir;
        const std::string defValueDir;
        const std::string defsCallbackPackageName;
        const std::string defCallbackDir;
        const std::string nativeFunctionPackageName;
        const std::string sharedValueInterfacePackageName;
        const std::string sharedValuePackageName;
        const std::string sharedVListPackageName;
    public:
        TypedefGenerator(NormalTypedefDeclaration declaration, std::string defStructPackageName,
                         std::string defValuePackageName, std::string defEnumPackageName, std::string defEnumDir,
                         std::string defStructDir, std::string defValueDir, std::string defCallbackPackageName,
                         std::string defCallbackDir, std::string nativeFunctionPackageName,
                         std::string sharedValueInterfacePackageName, std::string sharedValuePackageName,
                         std::string sharedVListPackageName);

        void build();
    };
} // jbindgen

#endif //JBINDGEN_TYPEDEFGENERATOR_H
