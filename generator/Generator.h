//
// Created by nettal on 23-11-9.
//

#ifndef JAVABINDGEN_GENERATOR_H
#define JAVABINDGEN_GENERATOR_H

#include <string>
#include <utility>
#include "GenUtils.h"
#include "../analyser/EnumDeclaration.h"

namespace jbindgen {
    struct GeneratorConfig {
        //root
        const std::string rootDir;
        const std::string nativeName;
        const std::string nativePackageName;

        //enum
        std::string enumDir;
        std::string enumClassName;
        std::string enumPackageName;
        jbindgen::PFN_rename enumRename;
    };

    inline GeneratorConfig defaultConfig(std::string rootDir, std::string nativeName, std::string nativePackageName) {
        GeneratorConfig config{.rootDir = std::move(rootDir), .nativeName=std::move(
                nativeName), .nativePackageName=std::move(nativePackageName)};
        config.enumDir = config.rootDir;
        config.enumClassName = config.nativeName + "Enums";
        config.enumPackageName = config.nativePackageName;
        config.enumRename = [](std::string s) { return s; };
        return config;
    }

    class Generator {
        const GeneratorConfig values;

    public:
        explicit Generator(GeneratorConfig values);

        void generateEnum(const std::vector<EnumDeclaration>& enums) {

        }
    };

} // jbindgen

#endif //JAVABINDGEN_GENERATOR_H
