//
// Created by nettal on 23-12-18.
//

#ifndef JBINDGEN_TYPEMANAGER_H
#define JBINDGEN_TYPEMANAGER_H

#include <vector>
#include <unordered_map>
#include <string>
#include "../analyser/AnalyserUtils.h"

namespace jbindgen {
    class GeneratorConfig;

    class TypeManager {
        std::unordered_map<std::string, bool> alreadyGenerated;
        std::vector<std::string> packageNames;
        std::vector<std::string> fullyQualifiedNames;
        std::string imports;

    public:
        explicit TypeManager(GeneratorConfig *previousConfig);

        std::vector<std::string> getPackageNames();

        bool isAlreadyGenerated(const std::string &name);

        template<typename T, typename = std::enable_if_t<std::is_base_of_v<DeclarationBasic, T>>>
        bool isAlreadyGenerated(T &delc) {
            return isAlreadyGenerated(delc.getName());
        }

        std::vector<std::string> getFullyQualifiedNames();

        std::string refreshCurrentImports(const GeneratorConfig *config, bool importShared);

        std::string getPreviousImports();

        std::string getCurrentImports(const GeneratorConfig *config, bool importShared);
    };

} // jbindgen

#endif //JBINDGEN_TYPEMANAGER_H
