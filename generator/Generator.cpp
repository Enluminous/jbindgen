//
// Created by nettal on 23-11-9.
//

#include "Generator.h"
#include "SharedGenerator.h"

namespace jbindgen {
    Generator::Generator(GeneratorConfig config) : config(std::move(config)) {
    }

    void Generator::generateEnum(const std::vector<EnumDeclaration> &enums) {
        EnumGenerator generator(enums, config.enums.enumPackageName, config.enums.enumClassName,
                                config.shared.pointerInterfacePackageName,
                                config.shared.valuesPackageName,
                                config.enums.enumDir,
                                config.enums.enumRename);
        generator.build();
    }

    void Generator::generateStructs(StructDeclaration declaration) {
        StructGenerator generator(std::move(declaration), config.structs.structsDir, config.structs.packageName,
                                  config.structs.memberName,
                                  config.structs.decodeGetter, config.structs.decodeSetter, config.analyser,
                                  config.shared.basePackageName,
                                  config.typedefs.valuePackageName,
                                  config.typedefs.callbackPageName,
                                  config.shared.nativesPackageName,
                                  config.enums.enumPackageName + "." + config.enums.enumClassName);
        generator.build();
    }

    void Generator::generateFunctionSymbols(std::vector<FunctionSymbolDeclaration> declarations) {
        FunctionSymbolGenerator generator(config.analyser, config.functionSymbols.makeFunction,
                                          config.functionSymbols.functionLoader,
                                          config.functionSymbols.head, config.functionSymbols.tail,
                                          config.functionSymbols.dir,
                                          std::move(declarations),
                                          config.functionSymbols.functionClassName,
                                          config.functionSymbols.symbolClassName,
                                          config.functionSymbols.symbolPackageName,
                                          config.shared.functionUtilsPackageName);
        generator.build();
    }

    void Generator::generateTypedef(const NormalTypedefDeclaration &declaration) {
        TypedefGenerator generator(declaration,
                                   config.structs.packageName,
                                   config.typedefs.valuePackageName,
                                   config.enums.enumPackageName + "." + config.enums.enumClassName,
                                   config.enums.enumDir,
                                   config.structs.structsDir,
                                   config.typedefs.valuesDir,
                                   config.typedefs.callbackPageName,
                                   config.typedefs.callbackDir,
                                   config.shared.functionUtilsPackageName,
                                   config.shared.valueInterfacePackageName,
                                   config.shared.valuesPackageName,
                                   config.shared.basePackageName + ".VList",
                                   config.analyser, config.structs.structsDir,
                                   config.structs.packageName,
                                   config.structs.memberName, config.structs.decodeGetter, config.structs.decodeSetter,
                                   config.shared.basePackageName + ".NList",
                                   config.shared.pointerInterfacePackageName,
                                   config.shared.basePackageName,
                                   config.shared.nativesPackageName,
                                   config.enums.enumRename);
        generator.build();
    }

    void Generator::generateTypedefFunction(const FunctionSymbolDeclaration &declaration) {
        FunctionProtoTypeGenerator generator(declaration, config.analyser, config.typedefFunc.typedefFuncDir,
                                             config.typedefFunc.typedefFuncPackageName,
                                             config.typedefFunc.typedefFuncDir,
                                             config.typedefFunc.typedefFuncPackageName,
                                             config.structs.packageName,
                                             config.typedefs.valuePackageName,
                                             config.shared.basePackageName,
                                             config.shared.pointerInterfacePackageName,
                                             config.shared.valueInterfacePackageName,
                                             config.shared.valuesPackageName,
                                             config.enums.enumPackageName + "." + config.enums.enumClassName,
                                             config.typedefFunc.makeProtoType);
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
                               config.varDeclares.symbolLoader);
        generator.build();
    }

    void Generator::generateShared() {
        SharedGenerator sharedGenerator(config.shared.sharedDir, config.shared.basePackageName);
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
        generateShared();
    }

    GeneratorConfig defaultGeneratorConfig(std::string rootDir, std::string libName, std::string nativePackageName,
                                           const Analyser &analyser) {
        GeneratorConfig config{
                .rootDir = std::move(rootDir), .libName = std::move(libName),
                .nativePackageName = std::move(nativePackageName), .analyser = analyser
        };

        config.shared.functionUtilsPackageName = config.nativePackageName + ".shared.FunctionUtils";
        config.shared.pointerInterfacePackageName = config.nativePackageName + ".shared.Pointer";
        config.shared.nativesPackageName = config.nativePackageName + ".shared.natives";
        config.shared.valuesPackageName = config.nativePackageName + ".shared.values";
        config.shared.valueInterfacePackageName = config.nativePackageName + ".shared.Value";
        config.shared.basePackageName = config.nativePackageName + ".shared";
        config.shared.sharedDir = config.rootDir + "/shared";

        config.enums.enumDir = config.rootDir;
        config.enums.enumClassName = config.libName + "Enums";
        config.enums.enumPackageName = config.nativePackageName;
        config.enums.enumRename = [](auto declare) { return declare.getName(); };

        config.structs.structsDir = config.rootDir + "/structs";
        config.structs.packageName = config.nativePackageName + ".structs";

        config.structs.memberName = StructGeneratorUtils::defaultStructMemberName;
        config.structs.decodeGetter = StructGeneratorUtils::defaultStructDecodeGetter;
        config.structs.decodeSetter = StructGeneratorUtils::defaultStructDecodeSetter;

        config.typedefs.valuePackageName = config.nativePackageName + ".values";
        config.typedefs.valuesDir = config.rootDir + "/values";
        config.typedefs.callbackPageName = config.nativePackageName + ".functions";
        config.typedefs.callbackDir = config.rootDir + "/functions";

        config.functionSymbols.functionClassName = config.libName + "Functions";
        config.functionSymbols.head = FunctionSymbolGenerator::defaultHead(
                config.functionSymbols.functionClassName,
                config.nativePackageName,
                config.typedefs.valuePackageName,
                config.structs.packageName,
                config.shared.basePackageName,
                config.enums.enumPackageName + "." + config.enums.enumClassName,
                config.shared.valuesPackageName,
                config.typedefs.callbackPageName);
        config.functionSymbols.tail = FunctionSymbolGenerator::defaultTail();
        config.functionSymbols.makeFunction = functiongenerator::defaultMakeFunctionInfo;
        config.functionSymbols.dir = config.rootDir;
        config.functionSymbols.symbolPackageName = config.libName;
        config.functionSymbols.symbolClassName = config.libName + "Symbols";

        config.typedefFunc.typedefFuncDir = config.rootDir + "/functions";
        config.typedefFunc.typedefFuncPackageName = config.nativePackageName + ".functions";
        config.typedefFunc.makeProtoType = functiongenerator::defaultMakeFunctionInfo;

        config.normalMacro.className = config.libName + "Macros";
        config.normalMacro.makeMacro = MacroNormalGeneratorUtils::defaultMakeMacro;
        config.normalMacro.dir = config.rootDir;
        config.normalMacro.packageName = config.nativePackageName;

        config.varDeclares.className = config.libName + "Vars";
        config.varDeclares.makeVar = nullptr;
        config.varDeclares.dir = config.rootDir;
        config.varDeclares.packageName = config.nativePackageName;
        config.varDeclares.symbolLoader = config.libName;
        return config;
    }
} // jbindgen