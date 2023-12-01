//
// Created by snownf on 23-11-9.
//

#ifndef JBINDGEN_ENUMGENERATOR_H
#define JBINDGEN_ENUMGENERATOR_H

#include <utility>

#include "../analyser/EnumDeclaration.h"
#include "GenUtils.h"

namespace jbindgen {

    typedef std::function<std::string(const EnumDeclaration &declaration)> PFN_enum_name;

    class EnumGenerator {
        const std::vector<EnumDeclaration> enumDeclarations;
        const std::string enumPackageName;
        const std::string enumClassName;
        std::string sharedPointerPackageName;
        std::string sharedValuePackageName;
        const std::string enumDir;
        const PFN_enum_name name;

    public:
        EnumGenerator(const std::vector<EnumDeclaration>& enumDeclarations, std::string enumPackageName,
                      std::string enumClassName,
                      std::string sharedPointerPackageName,
                      std::string sharedValuePackageName,
                      std::string enumDir, PFN_enum_name name)
                : enumDeclarations(enumDeclarations), enumPackageName(std::move(enumPackageName)),
                  enumClassName(std::move(enumClassName)), enumDir(std::move(enumDir)), name(std::move(name)),
                  sharedPointerPackageName(std::move(sharedPointerPackageName)),
                  sharedValuePackageName(std::move(sharedValuePackageName)) {
        }

        void build();
    };

} // jbindgen

#endif //JBINDGEN_ENUMGENERATOR_H
