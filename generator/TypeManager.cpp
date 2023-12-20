//
// Created by nettal on 23-12-18.
//

#include "TypeManager.h"
#include "Generator.h"

namespace jbindgen {

    template<class T>
    void foreachTypes(std::unordered_map<std::string, bool> &map, T t) {
        for (auto &structDecl: t) {
            auto name = structDecl.getName();
            map[name] = true;
        }
    }

    TypeManager::TypeManager(GeneratorConfig *previousConfig) {
        GeneratorConfig *config = previousConfig;
        while (true) {
            if (config == nullptr)
                break;
            packageNames.emplace_back(config->structs.packageName);
            packageNames.emplace_back(config->enums.enumPackageName + "." + config->enums.enumClassName);
            packageNames.emplace_back(config->typedefFunc.typedefFuncPackageName);;
            packageNames.emplace_back(config->typedefs.valuePackageName);;
            foreachTypes(alreadyGenerated, config->analyser.structs);
            foreachTypes(alreadyGenerated, config->analyser.unions);
            foreachTypes(alreadyGenerated, config->analyser.enums);
            foreachTypes(alreadyGenerated, config->analyser.functionPointers);
            foreachTypes(alreadyGenerated, config->analyser.typedefFunctions);
            foreachTypes(alreadyGenerated, config->analyser.typedefs);
            config = config->previousConfig;
        }
    }

    bool TypeManager::isAlreadyGenerated(const std::string &name) {
        return alreadyGenerated.contains(name);
    }

    std::vector<std::string> TypeManager::getPackageNames() {
        return packageNames;
    }

    std::vector<std::string> TypeManager::getFullyQualifiedNames() {
        return fullyQualifiedNames;
    }

    std::string TypeManager::getImports() {
        std::string result;
        for (const auto &item: packageNames)
            result += "import " + item + ".*;\n";
        for (const auto &item: fullyQualifiedNames)
            result += "import " + item + ";\n";
        return result;
    }

} // jbindgen