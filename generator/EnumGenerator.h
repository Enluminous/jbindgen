//
// Created by snownf on 23-11-9.
//

#ifndef JAVABINDGEN_ENUMGENERATOR_H
#define JAVABINDGEN_ENUMGENERATOR_H

#include <utility>

#include "../analyser/EnumDeclaration.h"
#include "GenUtils.h"

namespace jbindgen {
    typedef bool (*PFN_EnumGenerationFilter)(EnumDeclaration *enumDeclaration, void *userdata);

    typedef std::string(*PFN_enum_rename)(const EnumDeclaration &declaration, void *pUserdata);

    class EnumGenerator {
        PFN_EnumGenerationFilter filter;
        const std::vector<EnumDeclaration> enumDeclarations;
        const std::string enumPackageName;
        const std::string enumClassName;
        std::string sharedPointerPackageName;
        std::string sharedValuePackageName;
        const std::string enumDir;
        const PFN_enum_rename rename;

    public:
        EnumGenerator(std::vector<EnumDeclaration> enumDeclarations, std::string enumPackageName,
                      std::string enumClassName,
                      std::string sharedPointerPackageName,
                      std::string sharedValuePackageName,
                      std::string enumDir, PFN_enum_rename rename, PFN_EnumGenerationFilter enumGenerationFilter)
                : enumDeclarations(std::move(enumDeclarations)), enumPackageName(std::move(enumPackageName)),
                  enumClassName(std::move(enumClassName)), enumDir(std::move(enumDir)), rename(rename),
                  filter(enumGenerationFilter), sharedPointerPackageName(std::move(sharedPointerPackageName)),
                  sharedValuePackageName(std::move(sharedValuePackageName)) {
        }

        void build(void *pUserdata, void *enumGenerationFilterUserdata);
    };

} // jbindgen

#endif //JAVABINDGEN_ENUMGENERATOR_H
