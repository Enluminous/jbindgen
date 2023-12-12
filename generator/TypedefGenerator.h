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

namespace jbindgen {
    typedef std::function<std::tuple<std::string, std::string, bool>(
            const NormalTypedefDeclaration *declaration)> FN_def_name;

    class TypedefGenerator {
        NormalTypedefDeclaration declaration;
        const std::string defsStructPackageName;
        const std::string defsEnumPackageName;
        const std::string defsValuePackageName;
        const std::string defEnumDir;
        const std::string defStructDir;
        const std::string defValueDir;
        const std::string defsCallbackPackageName;
        const std::string defCallbackDir;
        const std::string nativeFunctionPackageName;
        const std::string sharedValueInterfacePackageName;
        const std::string sharedPointerInterfacePackageName;
        const std::string sharedValuePackageName;
        const std::string sharedVListPackageName;
        const std::string sharedNListPackageName;
        const std::string sharedNativesPackageName;
        const std::string baseSharedPackageName;

        //used for struct generation
        const Analyser &analyser;
        const std::string structsDir;
        const std::string packageName;
        const FN_structMemberName structMemberName;
        const FN_decodeGetter decodeGetter;
        const FN_decodeSetter decodeSetter;

    public:
        TypedefGenerator(NormalTypedefDeclaration declaration, std::string defStructPackageName,
                         std::string defValuePackageName, std::string defEnumPackageName, std::string defEnumDir,
                         std::string defStructDir, std::string defValueDir, std::string defCallbackPackageName,
                         std::string defCallbackDir, std::string nativeFunctionPackageName,
                         std::string sharedValueInterfacePackageName, std::string sharedValuePackageName,
                         std::string sharedVListPackageName,
                         const Analyser &analyser,
                         std::string structsDir, std::string packageName,
                         FN_structMemberName memberRename,
                         FN_decodeGetter decodeGetter, FN_decodeSetter decodeSetter,
                         std::string sharedNListPackageName,
                         const std::string sharedPointerInterfacePackageName, std::string baseSharedPackageName,
                         std::string sharedNativesPackageName);

        void build();

        std::string getValueContent(std::string className, value::jbasic::ValueType type);

        static std::string getFakeClassContent(std::string className);

        void genStruct(const std::string &className, CXType type);

        std::string
        getPrimitiveTypeArrayContent(std::string className, value::jbasic::NativeType type, long elementCount);
    };
} // jbindgen

#endif //JBINDGEN_TYPEDEFGENERATOR_H
