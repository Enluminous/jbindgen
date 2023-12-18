//
// Created by nettal on 23-12-18.
//

#include "TypeManager.h"

namespace jbindgen {

    template<class T>
    void foreachTypes(std::unordered_map<std::string, bool> &map, T t) {
        for (auto &structDecl: t) {
            auto name = structDecl.getName();
            map[name] = true;
        }
    }

    TypeManager::TypeManager(const std::vector<GeneratorConfig> &previousConfigs) {
        for (auto &config: previousConfigs) {
            packageNames.emplace_back(config.structs.packageName);
            packageNames.emplace_back(config.enums.enumPackageName);
            packageNames.emplace_back(config.typedefFunc.typedefFuncPackageName);;
            foreachTypes(alreadyGenerated, config.analyser.structs);
            foreachTypes(alreadyGenerated, config.analyser.unions);
            foreachTypes(alreadyGenerated, config.analyser.enums);
            foreachTypes(alreadyGenerated, config.analyser.functionPointers);
            foreachTypes(alreadyGenerated, config.analyser.typedefFunctions);
            foreachTypes(alreadyGenerated, config.analyser.typedefs);
        }
    }

    bool TypeManager::isAlreadyGenerated(const std::string &name) {
        return alreadyGenerated.contains(name);
    }

    std::vector<std::string> TypeManager::getPackageNames() {
        return packageNames;
    }
} // jbindgen