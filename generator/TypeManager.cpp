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
            if ((!config->analyser.structs.empty()) || (!config->analyser.unions.empty()))
                packageNames.emplace_back(config->structs.packageName);
            if (!config->analyser.enums.empty())
                packageNames.emplace_back(config->enums.enumPackageName + "." + config->enums.enumClassName);
            if (!config->analyser.typedefFunctions.empty())
                packageNames.emplace_back(config->typedefFunc.typedefFuncPackageName);;
            if (!config->analyser.typedefs.empty())
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

    std::string
    TypeManager::getImports(const GeneratorConfig *config, bool importShared) {
        std::string imports;
        if ((!config->analyser.structs.empty()) || (!config->analyser.unions.empty())) {
            std::vector<StructDeclaration> structs;
            for (const auto &item: config->analyser.structs) {
                if (isAlreadyGenerated(item.getName()))
                    continue;
                structs.emplace_back(item);
            }
            std::vector<UnionDeclaration> unions = config->analyser.unions;
            for (const auto &item: config->analyser.unions) {
                if (isAlreadyGenerated(item.getName()))
                    continue;
                unions.emplace_back(item);
            }
            if ((!structs.empty()) || (!unions.empty()))
                imports += "import " + config->structs.packageName + ".*;\n";
        }
        if (!config->analyser.enums.empty())
            imports += "import " + config->enums.enumFullyQualifiedName + ".*;\n";
        if (!config->analyser.typedefFunctions.empty())
            imports += "import " + config->typedefFunc.typedefFuncPackageName + ".*;\n";
        if (!config->analyser.typedefs.empty())
            imports += "import " + config->typedefs.valuePackageName + ".*;\n";
        if (importShared) {
            imports += "import " + config->shared.valuesPackageName + ".*;\n";
            imports += "import " + config->shared.basePackageName + ".*;\n";
            imports += "import " + config->shared.nativesPackageName + ".*;\n";
            imports += "import " + config->shared.valueInterfaceFullyQualifiedName + ";\n";;
            imports += "import " + config->shared.pointerInterfaceFullyQualifiedName + ";\n";;
            imports += "import " + config->shared.functionUtilsFullyQualifiedName + ";\n";;
        }
        return imports;
    }

} // jbindgen