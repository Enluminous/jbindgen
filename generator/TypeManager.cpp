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

    // previous config imports
    std::string TypeManager::getPreviousImports() {
        std::stringstream result;
        for (const auto &item: packageNames)
            result << "import " + item + ".*;\n";
        for (const auto &item: fullyQualifiedNames)
            result << "import " + item + ";\n";
        return result.str();
    }

    // refresh current config imports
    std::string TypeManager::refreshCurrentImports(const GeneratorConfig *config, bool importShared) {
        bool hasValue = false;
        bool hasStruct = false;
        for (const auto &item: config->analyser.typedefs) {
            TypedefGenerator generator(item, *config, std::make_shared<TypeManager>(*this), true);
            generator.build();
            switch (generator.getGeneratingLocation()) {
                case STRUCT:
                    hasStruct = true;
                    break;
                case VALUE:
                    hasValue = true;
                    break;
                case SKIPPED:
                    break;
                case UNDEFINED:
                    assertStr(0, "unexpected for generate location UNDEFINED")
                    break;
            }
        }
        if ((!config->analyser.structs.empty()) || (!config->analyser.unions.empty()) || hasStruct) {
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
        if (hasValue)
            imports += "import " + config->typedefs.valuePackageName + ".*;\n";
        std::stringstream ret;
        ret << imports;
        if (importShared) {
            ret << "import " + config->shared.valuesPackageName + ".*;\n";
            ret << "import " + config->shared.basePackageName + ".*;\n";
            ret << "import " + config->shared.nativesPackageName + ".*;\n";
            ret << "import " + config->shared.valueInterfaceFullyQualifiedName + ";\n";;
            ret << "import " + config->shared.pointerInterfaceFullyQualifiedName + ";\n";;
            ret << "import " + config->shared.functionUtilsFullyQualifiedName + ";\n";;
        }
        return ret.str();
    }

    // current config imports
    std::string TypeManager::getCurrentImports(const GeneratorConfig *config, bool importShared) {
        if (imports.empty())
            return refreshCurrentImports(config, importShared);
        std::stringstream ret;
        ret << imports;
        if (importShared) {
            ret << "import " + config->shared.valuesPackageName + ".*;\n";
            ret << "import " + config->shared.basePackageName + ".*;\n";
            ret << "import " + config->shared.nativesPackageName + ".*;\n";
            ret << "import " + config->shared.valueInterfaceFullyQualifiedName + ";\n";;
            ret << "import " + config->shared.pointerInterfaceFullyQualifiedName + ";\n";;
            ret << "import " + config->shared.functionUtilsFullyQualifiedName + ";\n";;
        }
        return ret.str();
    }

} // jbindgen