//
// Created by snownf on 23-11-9.
//

#ifndef JBINDGEN_ENUMGENERATOR_H
#define JBINDGEN_ENUMGENERATOR_H

#include <utility>

#include "../analyser/EnumDeclaration.h"
#include "GenUtils.h"
#include "TypeManager.h"

namespace jbindgen {

    typedef std::function<std::string(const EnumDeclaration &declaration)> PFN_enum_name;

    class EnumGenerator {
        const std::vector<EnumDeclaration> enumDeclarations;
        const std::string enumPackageName;
        const std::string enumClassName;
        std::string sharedPointerPackageName;
        std::string sharedBasePackageName;
        std::string sharedValuesPackageName;
        const std::string enumDir;
        const PFN_enum_name name;
        std::shared_ptr<TypeManager> typeManager;

    public:
        EnumGenerator(const std::vector<EnumDeclaration> &enumDeclarations, std::string enumPackageName,
                      std::string enumClassName,
                      std::string sharedPointerPackageName,
                      std::string sharedBasePackageName,
                      std::string sharedValuesPackageName,
                      std::string enumDir, PFN_enum_name name,
                      std::shared_ptr<TypeManager> typeManager);

        void build();
    };

} // jbindgen

#endif //JBINDGEN_ENUMGENERATOR_H
