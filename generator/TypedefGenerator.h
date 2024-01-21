//
// Created by nettal on 23-11-18.
//

#ifndef JBINDGEN_TYPEDEFGENERATOR_H
#define JBINDGEN_TYPEDEFGENERATOR_H

#include <string>
#include <iostream>
#include <sstream>
#include <format>
#include "../analyser/NormalTypedefDeclaration.h"
#include "GenUtils.h"
#include "FunctionGeneratorUtils.h"
#include "StructGenerator.h"
#include "EnumGenerator.h"

namespace jbindgen {
    enum GeneratingLocation {
        UNDEFINED,
        STRUCT,
        VALUE,
        SKIPPED,
    };

    class TypedefGenerator {

        NormalTypedefDeclaration declaration;
        const std::string baseSharedPackageName;

        const GeneratorConfig &config;
        bool noGeneration;

        //used for struct generation
        const Analyser &analyser;
        std::shared_ptr<TypeManager> typeManager;

        GeneratingLocation location = UNDEFINED;

    public:
        TypedefGenerator(NormalTypedefDeclaration declaration, const GeneratorConfig &config,
                         std::shared_ptr<TypeManager> typeManager, bool noGeneration = false);

        void build();

        std::string getValueContent(std::string className, value::jbasic::ValueType type);

        static std::string getFakeClassContent(std::string className);

        void genStruct(const std::string &className, CXType type);

        std::string
        getPrimitiveTypeArrayContent(std::string className, value::jbasic::NativeType type, long elementCount);

        GeneratingLocation getGeneratingLocation();
    };
} // jbindgen

#endif //JBINDGEN_TYPEDEFGENERATOR_H
