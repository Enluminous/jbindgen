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
#include "FunctionGeneratorUtils.h"
#include "TypedefGenerator.h"
#include "TypedefGeneratorUtils.h"
#include "FunctionProtoTypeGenerator.h"
#include "MacroNormalGenerator.h"
#include "VarGenerator.h"
#include "TypeManager.h"

namespace jbindgen {
    namespace config {
        struct Enums {
            std::string enumDir;
            std::string enumClassName;
            std::string enumPackageName;
            jbindgen::PFN_enum_name enumRename;
        };
        struct Structs {
            std::string structsDir;
            std::string packageName;
            FN_structMemberName memberName;
            FN_decodeGetter decodeGetter;
            FN_decodeSetter decodeSetter;
        };
        struct FunctionSymbols {
            FN_makeFunction makeFunction;
            std::string functionLoader;
            std::string head;
            std::string tail;
            std::string functionClassName;
            std::string dir;
            bool hideUnWarped;
        };
        struct SymbolLookup {
            std::string symbolClassName;
            std::string symbolPackageName;
            bool accessSymbolLookups;
            bool allowCritical;
            std::string dir;
        };
        struct Typedefs {
            std::string valuePackageName;
            std::string valuesDir;
            std::string callbackPageName;
            std::string callbackDir;
        };
        struct Shared {
            std::string functionUtilsPackageName;
            std::string pointerInterfacePackageName;
            std::string nativesPackageName;
            std::string valuesPackageName;
            std::string valueInterfacePackageName;
            std::string basePackageName;
            std::string sharedDir;
            bool skipGenerate = false;
        };
        struct TypedefFunction {
            std::string typedefFuncDir;
            std::string typedefFuncPackageName;
            FN_makeFunction makeProtoType;
        };
        struct NormalMacro {
            FN_makeMacro makeMacro;
            std::string head;
            std::string tail;
            std::string className;
            std::string dir;
            std::string packageName;
        };
        struct VarDeclares {
            FN_makeVar makeVar;
            std::string head;
            std::string tail;
            std::string className;
            std::string dir;
            std::string packageName;
            std::string symbolLoader;
        };
    }

    struct GeneratorConfig {
        static config::Shared makeSharedConfig(std::string pkg, std::string dir, bool skipGenerate);

        GeneratorConfig changeSharedPackage(std::string pkg, std::string dir);

        const std::string rootDir;
        const std::string libName;
        const std::string libPackageName;
        const Analyser &analyser;
        struct config::Enums enums;
        struct config::Structs structs;
        struct config::FunctionSymbols functionSymbols;
        struct config::SymbolLookup symbols;
        struct config::Typedefs typedefs;
        struct config::Shared shared;
        struct config::TypedefFunction typedefFunc;
        struct config::NormalMacro normalMacro;
        struct config::VarDeclares varDeclares;
        GeneratorConfig *previousConfig;
    };

    GeneratorConfig defaultGeneratorConfig(std::string rootDir, std::string libName,
                                           std::string libPackageName, const Analyser &analyser,
                                           GeneratorConfig *previousConfig);

    GeneratorConfig defaultGeneratorConfig(std::string rootDir, std::string libName,
                                           std::string libPackageName, const Analyser &analyser);

    class Generator {
        const GeneratorConfig config;
        std::shared_ptr<TypeManager> typeManager;

        void generateEnum(const std::vector<EnumDeclaration> &enums);

        void generateStructs(StructDeclaration declaration);

        void generateFunctionSymbols(std::vector<FunctionSymbolDeclaration> declarations);

        void generateTypedef(const NormalTypedefDeclaration &declaration);

        void generateTypedefFunction(const FunctionSymbolDeclaration &declaration);

        void generateNormalMacro(const std::vector<NormalMacroDeclaration> &declaration);

        void generateVarDeclares(const std::vector<VarDeclaration> &declaration);

        void generateSymbols();

    public:
        explicit Generator(GeneratorConfig config);

        void generate();

        static void generateShared(const struct config::Shared &shared);
    };
} // jbindgen

#endif //JBINDGEN_GENERATOR_H
