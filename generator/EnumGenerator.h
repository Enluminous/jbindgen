//
// Created by snownf on 23-11-9.
//

#ifndef JAVABINDGEN_ENUMGENERATOR_H
#define JAVABINDGEN_ENUMGENERATOR_H

#include <utility>

#include "../analyser/EnumDeclaration.h"
#include "GenUtils.h"

namespace jbindgen {
    typedef bool (*PFN_EnumGenerationFilter)(EnumDeclaration* enumDeclaration);

    class EnumGenerator {
        PFN_EnumGenerationFilter filter;
        const std::vector<EnumDeclaration> enumDeclarations;
        const std::string enumPackageName;
        const std::string enumClassName;
        const std::string enumDir;
        const PFN_rename rename;

    public:
        EnumGenerator(std::vector<EnumDeclaration> enumDeclarations, std::string enumPackageName,
                      std::string enumClassName,
                      std::string enumDir, PFN_rename rename, PFN_EnumGenerationFilter enumGenerationFilter)
                : enumDeclarations(std::move(enumDeclarations)), enumPackageName(std::move(enumPackageName)),
                  enumClassName(std::move(enumClassName)), enumDir(std::move(enumDir)), rename(rename),
                  filter(enumGenerationFilter) {
        }

        void build(void *pUserdata);
    };

} // jbindgen

#endif //JAVABINDGEN_ENUMGENERATOR_H
