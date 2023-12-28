//
// Created by nettal on 23-11-9.
//

#include "Generator.h"

#include <utility>
#include "SharedGenerator.h"
#include "SymbolGenerator.h"
#include "FunctionSymbolGenerator.h"
#include "MacroNormalGeneratorUtils.h"
#include "TypeManager.h"
#include "FunctionProtoTypeGenerator.h"

namespace jbindgen {
    Generator::Generator(GeneratorConfig config) : config(std::move(config)) {
        this->typeManager = std::make_shared<TypeManager>(TypeManager(this->config.previousConfig));
    }

    void Generator::generateEnum(const std::vector<EnumDeclaration> &enums) {
        EnumGenerator generator(enums, config.enums.enumPackageName, config.enums.enumClassName,
                                config.shared.pointerInterfaceFullyQualifiedName,
                                config.shared.basePackageName,
                                config.shared.valuesPackageName,
                                config.enums.enumDir,
                                config.enums.enumRename, typeManager);
        generator.build();
    }

    void Generator::generateStructs(StructDeclaration declaration) {
        StructGenerator generator(std::move(declaration), typeManager, config);
        generator.build();
    }

    void Generator::generateFunctionSymbols(std::vector<FunctionSymbolDeclaration> declarations) {
        FunctionSymbolGenerator generator(config.analyser, config.functionSymbols, std::move(declarations),
                                          config.symbols.symbolClassName, config.symbols.symbolPackageName);
        generator.build();
    }

    void Generator::generateTypedef(const NormalTypedefDeclaration &declaration) {
        TypedefGenerator generator(declaration, config, typeManager);
        generator.build();
    }

    void Generator::generateTypedefFunction(const FunctionSymbolDeclaration &declaration) {
        FunctionProtoTypeGenerator generator(declaration, typeManager,
                                             config);
        generator.build();
    }

    void Generator::generateNormalMacro(const std::vector<NormalMacroDeclaration> &declaration) {
        MacroNormalGenerator generator(config.normalMacro.makeMacro, config.normalMacro.head,
                                       config.normalMacro.className,
                                       config.normalMacro.tail, config.normalMacro.dir,
                                       config.normalMacro.packageName, declaration);
        generator.build();
    }

    void Generator::generateVarDeclares(const std::vector<VarDeclaration> &declaration) {
        VarGenerator generator(config.varDeclares.makeVar, config.varDeclares.head,
                               config.varDeclares.className, config.varDeclares.packageName,
                               config.varDeclares.tail, config.varDeclares.dir, declaration, config.analyser,
                               config.varDeclares.symbolLoader, typeManager, config);
        generator.build();
    }

    void Generator::generateSymbols() {
        SymbolGenerator generator(config.symbols, config.shared.functionUtilsFullyQualifiedName);
        generator.build();
    }

    void Generator::generateShared(const struct config::Shared &shared) {
        if (shared.skipGenerate)
            return;
        SharedGenerator sharedGenerator(shared.sharedDir, shared.basePackageName);
        sharedGenerator.makeAbstractNativeList();
        sharedGenerator.makePointer();
        sharedGenerator.makeFunctionUtils();
        sharedGenerator.makeNList();
        sharedGenerator.makeNString();
        sharedGenerator.makeNPtrList();
        sharedGenerator.makeValue();
//        sharedGenerator.makeVList(); no longer need
        sharedGenerator.makeBasicValues();
        sharedGenerator.makeValues();
        sharedGenerator.makeNatives();
//        sharedGenerator.makeNPointer(); no longer need
        sharedGenerator.makeVListSpecialized();
    }

    template<class T>
    std::vector<T> removeDuplicate(const std::vector<T> &in) {
        std::unordered_map<std::string, std::any> generated;
        std::vector<T> out;
        for (auto &item: in) {
            if (generated.contains(item.getName())) {
                if (WARNING)
                    std::cerr << "Warning: duplicate " + std::string(typeid(T).name()) + ": " + item.getName()
                              << std::endl;
                continue;
            }
            out.emplace_back(item);
            generated[item.getName()] = item;
        }
        return out;
    }

    template<class T>
    std::pair<std::vector<T>, std::vector<T>> splitByResult(const std::vector<T> &in) {
        std::unordered_map<std::string, std::any> err_m;
        std::vector<T> err_v;
        std::unordered_map<std::string, std::any> ok_m;
        std::vector<T> ok_v;
        for (auto &item: in) {
            if (isValidSize(item.visitResult())) {//ok
                if (ok_m.contains(item.getName())) {
                    if (WARNING)
                        std::cerr << "Warning: duplicate " + std::string(typeid(T).name()) + ": " + item.getName()
                                  << std::endl;
                    continue;
                }
                ok_v.emplace_back(item);
                ok_m[item.getName()] = item;
            } else {
                if (err_m.contains(item.getName()) || ok_m.contains(item.getName())) {
                    if (WARNING)
                        std::cerr << "Warning: duplicate " + std::string(typeid(T).name()) + ": " + item.getName()
                                  << std::endl;
                    continue;
                }
                if (WARNING)
                    std::cerr << "Warning: incomplete " + std::string(typeid(T).name()) + ": " + item.getName()
                              << std::endl;
                err_v.emplace_back(item);
                err_m[item.getName()] = item;
            }
        }
        return {ok_v, err_v};
    }

    void Generator::generate() {
        generateEnum(removeDuplicate(config.analyser.enums));

        generateFunctionSymbols(config.analyser.functionSymbols);

        for (auto &item: config.analyser.typedefs)
            generateTypedef(item);
        for (const auto &item: config.analyser.typedefFunctions)
            generateTypedefFunction(item);
        for (const auto &item: config.analyser.functionPointers)
            generateTypedefFunction(item);
        generateNormalMacro(config.analyser.normalMacro);
        generateVarDeclares(config.analyser.vars);
        {
            auto [ok, err] = splitByResult(config.analyser.structs);
            for (auto &item: ok) {
                generateStructs(item);
            }
            for (auto &item: err) {
                generateStructs(item);
            }
        }
        {
            auto [ok, err] = splitByResult(config.analyser.unions);
            for (auto &item: ok) {
                generateStructs(item);
            }
            for (auto &item: err) {
                generateStructs(item);
            }
        }
        generateShared(config.shared);
        generateSymbols();
    }

    GeneratorConfig defaultGeneratorConfig(std::string rootDir, std::string libName, std::string libPackageName,
                                           const Analyser &analyser,
                                           GeneratorConfig *previousConfig) {
        GeneratorConfig config{
                .rootDir = std::move(rootDir), .libName = std::move(libName),
                .libPackageName = std::move(libPackageName), .analyser = analyser,
                .previousConfig = previousConfig
        };

        config.enums.enumDir = config.rootDir;
        config.enums.enumClassName = config.libName + "Enums";
        config.enums.enumPackageName = config.libPackageName;
        config.enums.enumRename = [](auto declare) { return declare.getName(); };
        config.enums.enumFullyQualifiedName = config.enums.enumPackageName + "." + config.enums.enumClassName;

        config.structs.structsDir = config.rootDir + "/structs";
        config.structs.packageName = config.libPackageName + ".structs";

        config.structs.memberName = StructGeneratorUtils::defaultStructMemberName;
        config.structs.decodeGetter = StructGeneratorUtils::defaultStructDecodeGetter;
        config.structs.decodeSetter = StructGeneratorUtils::defaultStructDecodeSetter;

        config.typedefs.valuePackageName = config.libPackageName + ".values";
        config.typedefs.valuesDir = config.rootDir + "/values";
        config.typedefs.callbackPageName = config.libPackageName + ".functions";
        config.typedefs.callbackDir = config.rootDir + "/functions";

        config.functionSymbols.functionClassName = config.libName + "Functions";
        config.functionSymbols.tail = FunctionSymbolGenerator::defaultTail();
        config.functionSymbols.makeFunction = functiongenerator::defaultMakeFunctionInfo;
        config.functionSymbols.dir = config.rootDir;
        config.functionSymbols.hideUnWarped = true;

        config.symbols.dir = config.rootDir;
        config.symbols.accessSymbolLookups = true;
        config.symbols.allowCritical = false;
        config.symbols.symbolPackageName = config.libPackageName;
        config.symbols.symbolClassName = config.libName + "Symbols";

        config.typedefFunc.typedefFuncDir = config.rootDir + "/functions";
        config.typedefFunc.typedefFuncPackageName = config.libPackageName + ".functions";
        config.typedefFunc.makeProtoType = functiongenerator::defaultMakeFunctionInfo;

        config.normalMacro.className = config.libName + "Macros";
        config.normalMacro.makeMacro = MacroNormalGeneratorUtils::defaultMakeMacro;
        config.normalMacro.dir = config.rootDir;
        config.normalMacro.packageName = config.libPackageName;

        config.varDeclares.className = config.libName + "Vars";
        config.varDeclares.makeVar = nullptr;
        config.varDeclares.dir = config.rootDir;
        config.varDeclares.packageName = config.libPackageName;
        config.varDeclares.symbolLoader = config.libName;

        config.changeSharedPackage(config.libPackageName + ".shared", config.rootDir + "/shared");

        return config;
    }

    GeneratorConfig defaultGeneratorConfig(std::string rootDir, std::string libName, std::string libPackageName,
                                           const Analyser &analyser) {
        return defaultGeneratorConfig(std::move(rootDir), std::move(libName), std::move(libPackageName), analyser,
                                      {});
    }

    GeneratorConfig GeneratorConfig::changeSharedPackage(std::string pkg, std::string dir) {
        this->shared = makeSharedConfig(std::move(pkg), std::move(dir), this->shared.skipGenerate);
        this->functionSymbols.head = FunctionSymbolGenerator::defaultHead(
                this->functionSymbols.functionClassName,
                this->libPackageName,
                *this,
                std::make_shared<TypeManager>(previousConfig));
        return *this;
    }

    config::Shared GeneratorConfig::makeSharedConfig(std::string pkg, std::string dir, bool skipGenerate) {
        struct config::Shared shared;
        shared.basePackageName = std::move(pkg);
        shared.functionUtilsFullyQualifiedName = shared.basePackageName + ".FunctionUtils";
        shared.pointerInterfaceFullyQualifiedName = shared.basePackageName + ".Pointer";
        shared.nativesPackageName = shared.basePackageName + ".natives";
        shared.valuesPackageName = shared.basePackageName + ".values";
        shared.valueInterfaceFullyQualifiedName = shared.basePackageName + ".Value";
        shared.sharedDir = std::move(dir);
        shared.skipGenerate = skipGenerate;
        return shared;
    }
} // jbindgen