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
#include "MacroNormalGeneratorUtils.h"

namespace jbindgen {
    struct GeneratorConfig {
        const std::string rootDir;
        const std::string libName;
        const std::string nativePackageName;
        const Analyser &analyser;

        struct {
            std::string enumDir;
            std::string enumClassName;
            std::string enumPackageName;
            jbindgen::PFN_enum_name enumRename;
        } enums;

        struct {
            std::string structsDir;
            std::string packageName;
            FN_structMemberName memberName;
            FN_decodeGetter decodeGetter;
            FN_decodeSetter decodeSetter;
        } structs;

        struct {
            FN_makeFunction makeFunction;
            std::string functionLoader;
            std::string head;
            std::string tail;
            std::string functionClassName;
            std::string dir;
            std::string symbolClassName;
            std::string symbolPackageName;
        } functions;

        struct {
            std::string valuePackageName;
            std::string valuesDir;
            std::string callbackPageName;
            std::string callbackDir;
            FN_def_name name;
        } typedefs;

        struct {
            std::string functionUtilsPackageName;
            std::string pointerInterfacePackageName;
            std::string valueInterfacePackageName;
            std::string sharedDir;
        } shared;

        struct {
            std::string typedefFuncDir;
            std::string typedefFuncPackageName;
            FN_makeFunction makeProtoType;
        } typedefFunc;

        struct {
            FN_makeMacro makeMacro;
            std::string head;
            std::string tail;
            std::string className;
            std::string dir;
            std::string packageName;
        } normalMacro;

        struct {
            FN_makeVar makeVar;
            std::string head;
            std::string tail;
            std::string className;
            std::string dir;
            std::string packageName;
            std::string symbolLoader;
        } varDeclares;
    };

    GeneratorConfig defaultGeneratorConfig(std::string rootDir, std::string libName,
                                           std::string nativePackageName, const Analyser &analyser);

    class Generator {
        const GeneratorConfig config;

        void generateEnum(const std::vector<EnumDeclaration> &enums);

        void generateStructs(StructDeclaration declaration);

        void generateFunctionSymbols(std::vector<FunctionSymbolDeclaration> declarations);

        void generateTypedef(const NormalTypedefDeclaration &declaration);

        void generateTypedefFunction(const FunctionSymbolDeclaration &declaration);

        void generateNormalMacro(const std::vector<NormalMacroDeclaration> &declaration);

        void generateVarDeclares(const std::vector<VarDeclaration> &declaration);

    public:
        explicit Generator(GeneratorConfig config);

        void generate();
    };
} // jbindgen

#endif //JBINDGEN_GENERATOR_H
