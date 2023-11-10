//
// Created by snownf on 23-11-9.
//

#ifndef JAVABINDGEN_ENUMGENERATOR_H
#define JAVABINDGEN_ENUMGENERATOR_H

#include <utility>

#include "../analyser/EnumDeclaration.h"
#include "GenUtils.h"

namespace jbindgen {

    class EnumGenerator {
        const std::vector<EnumDeclaration> enumDeclarations;
        const std::string enumPackageName;
        const std::string enumClassName;
        const std::string enumDir;
        const PFN_rename rename;

    public:
        EnumGenerator(std::vector<EnumDeclaration> enumDeclarations, std::string enumPackageName,
                      std::string enumClassName, std::string enumDir, PFN_rename rename)
                : enumDeclarations(std::move(enumDeclarations)), enumPackageName(std::move(enumPackageName)),
                  enumClassName(std::move(enumClassName)), enumDir(std::move(enumDir)), rename(rename) {
        }

        void build();
    };

} // jbindgen

#endif //JAVABINDGEN_ENUMGENERATOR_H
