//
// Created by nettal on 23-11-9.
//

#ifndef JBINDGEN_GENERATOR_H
#define JBINDGEN_GENERATOR_H

#include <string>
#include <utility>
#include "GenUtils.h"
#include "../analyser/EnumDeclaration.h"
#include "EnumGenerator.h"
#include "StructGenerator.h"
#include "StructGeneratorUtils.h"
#include "FunctionSymbolGenerator.h"
#include "FunctionGeneratorUtils.h"
#include "TypedefGenerator.h"
#include "TypedefGeneratorUtils.h"
#include "FunctionProtoTypeGenerator.h"
#include "MacroNormalGenerator.h"
#include "VarGenerator.h"

namespace jbindgen {
    struct GeneratorConfig {
        const std::string rootDir;
        const std::string libName;
        const std::string nativePackageName;
        const CXCursorMap&cx_cursor_map;

        struct {
            std::string enumDir;
            std::string enumClassName;
            std::string enumPackageName;
            jbindgen::PFN_enum_rename enumRename;
        } enums;

        struct {
            std::string structsDir;
            std::string packageName;
            PFN_structName structName;
            PFN_structMemberName memberName;
            PFN_decodeGetter decodeGetter;
            PFN_decodeSetter decodeSetter;
        } structs;

        struct {
            PFN_makeFunction makeFunction;
            std::string functionLoader;
            std::string head;
            std::string tail;
            std::string className;
            std::string dir;
        } functions;

        struct {
            std::string valuePackageName;
            std::string valuesDir;
            std::string callbackPageName;
            std::string callbackDir;
            PFN_def_name name;
        } typedefs;

        struct {
            std::string nativeFunctionPackageName;
            std::string pointerPackageName;
            std::string valuePackageName;
            std::string sharedDir;
        } shared;

        struct {
            std::string typedefFuncDir;
            std::string typedefFuncPackageName;
            PFN_makeFunction makeProtoType;
        } typedefFunc;

        struct {
            PFN_makeMacro makeMacro;
            std::string head;
            std::string tail;
            std::string className;
            std::string dir;
        } normalMacro;

        struct {
            PFN_makeVar makeVar;
            std::string head;
            std::string tail;
            std::string className;
            std::string dir;
        } varDeclares;
    };

    inline GeneratorConfig defaultGeneratorConfig(std::string rootDir, std::string libName,
                                                  std::string nativePackageName, const CXCursorMap&cx_cursor_map) {
        GeneratorConfig config{
            .rootDir = std::move(rootDir), .libName = std::move(libName),
            .nativePackageName = std::move(nativePackageName), .cx_cursor_map = cx_cursor_map
        };

        config.shared.nativeFunctionPackageName = config.nativePackageName + ".shared.NativeFunction";
        config.shared.pointerPackageName = config.nativePackageName + ".shared.Pointer";
        config.shared.valuePackageName = config.nativePackageName + ".shared.Value";
        config.shared.sharedDir = config.rootDir + "/shared";

        config.enums.enumDir = config.rootDir;
        config.enums.enumClassName = config.libName + "Enums";
        config.enums.enumPackageName = config.nativePackageName;
        config.enums.enumRename = [](auto declare, void*) { return declare.name; };

        config.structs.structsDir = config.rootDir + "/structs";
        config.structs.packageName = config.nativePackageName + ".structs";
        config.structs.structName = [](auto&s, void*) { return s.structType.name; };
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
        config.typedefFunc.typedefFuncPackageName = config.nativePackageName + ".callbacks";
        config.typedefFunc.makeProtoType = functiongenerator::defaultMakeFunctionInfo;

        config.normalMacro.className = config.libName + "Macros";
        config.normalMacro.makeMacro = nullptr;
        config.normalMacro.dir = config.rootDir;

        config.varDeclares.className = config.libName + "Vars";
        config.varDeclares.makeVar = nullptr;
        config.varDeclares.dir = config.rootDir;
        return config;
    }

    class Generator {
        const GeneratorConfig config;

    public:
        explicit Generator(GeneratorConfig config);

        void generateEnum(const std::vector<EnumDeclaration>&enums, void* enumRenameUserdata) {
            EnumGenerator generator(enums, config.enums.enumPackageName, config.enums.enumClassName,
                                    config.shared.pointerPackageName,
                                    config.shared.valuePackageName,
                                    config.enums.enumDir,
                                    config.enums.enumRename);
            generator.build(enumRenameUserdata);
        }

        void generateStructs(StructDeclaration declaration, void* structRenameUserData, void* memberRenameUserData,
                             void* decodeGetterUserData, void* decodeSetterUserData,
                             void* structGenerationFilterUserdata = nullptr) {
            StructGenerator generator(std::move(declaration), config.structs.structsDir, config.structs.packageName,
                                      config.structs.structName, config.structs.memberName,
                                      config.structs.decodeGetter, config.structs.decodeSetter);
            generator.build(structRenameUserData, memberRenameUserData,
                            decodeGetterUserData, decodeSetterUserData, structGenerationFilterUserdata);
        }

        void generateFunctions(std::vector<FunctionDeclaration> declarations) {
            FunctionSymbolGenerator generator(config.functions.makeFunction,
                                              config.functions.functionLoader,
                                              config.functions.head, config.functions.tail, config.functions.dir,
                                              std::move(declarations), config.functions.className);
            generator.build(nullptr);
        }

        void generateTypedef(const NormalTypedefDeclaration&declaration, void* userdata) {
            TypedefGenerator generator(declaration,
                                       config.structs.packageName,
                                       config.typedefs.valuePackageName,
                                       config.enums.enumPackageName,
                                       config.enums.enumDir,
                                       config.structs.structsDir,
                                       config.typedefs.valuesDir,
                                       config.typedefs.callbackPageName,
                                       config.typedefs.callbackDir,
                                       config.shared.nativeFunctionPackageName,
                                       config.typedefs.name);
            generator.build(userdata);
        }

        void generateTypedefFunction(const FunctionTypedefDeclaration&declaration, void* userData) {
            FunctionProtoTypeGenerator generator(declaration, config.typedefFunc.typedefFuncDir,
                                                 config.typedefFunc.typedefFuncPackageName,
                                                 config.typedefFunc.typedefFuncDir,
                                                 config.typedefFunc.typedefFuncPackageName,
                                                 config.typedefFunc.makeProtoType);
            generator.build(userData);
        }

        void generateNormalMacro(std::vector<NormalMacroDeclaration>&declaration) {
            MacroNormalGenerator generator(config.normalMacro.makeMacro, config.normalMacro.head,
                                           config.normalMacro.className,
                                           config.normalMacro.tail, config.normalMacro.dir, declaration);
            generator.build();
        }

        void generateVarDeclares(std::vector<VarDeclaration>&declaration) {
            VarGenerator generator(config.varDeclares.makeVar, config.normalMacro.head,
                                   config.normalMacro.className,
                                   config.normalMacro.tail, config.normalMacro.dir, declaration);
            generator.build();
        }
    };
} // jbindgen

#endif //JBINDGEN_GENERATOR_H
