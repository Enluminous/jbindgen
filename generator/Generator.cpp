//
// Created by nettal on 23-11-9.
//

#include "Generator.h"

namespace jbindgen {
    Generator::Generator(GeneratorConfig config) : config(std::move(config)) {
    }

    void Generator::generateEnum(const std::vector<EnumDeclaration> &enums) {
        EnumGenerator generator(enums, config.enums.enumPackageName, config.enums.enumClassName,
                                config.shared.pointerInterfacePackageName,
                                config.shared.valueInterfacePackageName,
                                config.enums.enumDir,
                                config.enums.enumRename);
        generator.build();
    }

    void Generator::generateStructs(StructDeclaration declaration) {
        StructGenerator generator(std::move(declaration), config.structs.structsDir, config.structs.packageName,
                                  config.structs.memberName,
                                  config.structs.decodeGetter, config.structs.decodeSetter, config.analyser);
        generator.build();
    }

    void Generator::generateFunctionSymbols(std::vector<FunctionSymbolDeclaration> declarations) {
        FunctionSymbolGenerator generator(config.analyser, config.functions.makeFunction,
                                          config.functions.functionLoader,
                                          config.functions.head, config.functions.tail, config.functions.dir,
                                          std::move(declarations), config.functions.className);
        generator.build();
    }

    void Generator::generateTypedef(const NormalTypedefDeclaration &declaration) {
        TypedefGenerator generator(declaration,
                                   config.structs.packageName,
                                   config.typedefs.valuePackageName,
                                   config.enums.enumPackageName,
                                   config.enums.enumDir,
                                   config.structs.structsDir,
                                   config.typedefs.valuesDir,
                                   config.typedefs.callbackPageName,
                                   config.typedefs.callbackDir,
                                   config.shared.functionUtilsPackageName,
                                   config.typedefs.name);
        generator.build();
    }

    void Generator::generateTypedefFunction(const FunctionSymbolDeclaration &declaration) {
        FunctionProtoTypeGenerator generator(declaration, config.analyser, config.typedefFunc.typedefFuncDir,
                                             config.typedefFunc.typedefFuncPackageName,
                                             config.typedefFunc.typedefFuncDir,
                                             config.typedefFunc.typedefFuncPackageName,
                                             config.structs.packageName,
                                             config.typedefs.valuePackageName,
                                             config.shared.functionUtilsPackageName,
                                             config.shared.pointerInterfacePackageName,
                                             config.shared.valueInterfacePackageName,
                                             config.typedefFunc.makeProtoType);
        generator.build();
    }

    void Generator::generateNormalMacro(std::vector<NormalMacroDeclaration> &declaration) {
        MacroNormalGenerator generator(config.normalMacro.makeMacro, config.normalMacro.head,
                                       config.normalMacro.className,
                                       config.normalMacro.tail, config.normalMacro.dir,
                                       config.normalMacro.packageName, declaration);
        generator.build();
    }

    void Generator::generateVarDeclares(std::vector<VarDeclaration> &declaration) {
        VarGenerator generator(config.varDeclares.makeVar, config.varDeclares.head,
                               config.varDeclares.className, config.varDeclares.packageName,
                               config.varDeclares.tail, config.varDeclares.dir, declaration, config.analyser,
                               config.varDeclares.symbolLoader);
        generator.build();
    }

    GeneratorConfig defaultGeneratorConfig(std::string rootDir, std::string libName, std::string nativePackageName,
                                           const Analyser &analyser) {
        GeneratorConfig config{
                .rootDir = std::move(rootDir), .libName = std::move(libName),
                .nativePackageName = std::move(nativePackageName), .analyser = analyser
        };

        config.shared.functionUtilsPackageName = config.nativePackageName + ".shared.FunctionUtils";
        config.shared.pointerInterfacePackageName = config.nativePackageName + ".shared.Pointer";
        config.shared.valueInterfacePackageName = config.nativePackageName + ".shared.Value";
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

        config.functions.className = config.libName + "Symbols";
        config.functions.head = FunctionSymbolGenerator::defaultHead(config.functions.className,
                                                                     config.nativePackageName,
                                                                     config.libName);
        config.functions.tail = FunctionSymbolGenerator::defaultTail();
        config.functions.makeFunction = functiongenerator::defaultMakeFunctionInfo;
        config.functions.dir = config.rootDir;

        config.typedefs.valuePackageName = config.nativePackageName + ".values";
        config.typedefs.valuesDir = config.rootDir + "/values";
        config.typedefs.name = TypedefGeneratorUtils::defaultNameFunction;
        config.typedefs.callbackPageName = config.nativePackageName + ".functions";
        config.typedefs.callbackDir = config.rootDir + "/functions";

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