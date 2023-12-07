//
// Created by nettal on 23-11-18.
//

#include "TypedefGenerator.h"
#include "TypedefGeneratorUtils.h"
#include "SharedGenerator.h"

#include <utility>

namespace jbindgen {
    TypedefGenerator::TypedefGenerator(NormalTypedefDeclaration declaration,
                                       std::string defStructPackageName,
                                       std::string defValuePackageName,
                                       std::string defEnumPackageName,
                                       std::string defEnumDir,
                                       std::string defStructDir,
                                       std::string defValueDir,
                                       std::string defCallbackPackageName,
                                       std::string defCallbackDir,
                                       std::string nativeFunctionPackageName,
                                       std::string sharedValueInterfacePackageName,
                                       std::string sharedValuePackageName,
                                       std::string sharedVListPackageName) :
            declaration(std::move(declaration)),
            defsStructPackageName(std::move(defStructPackageName)),
            defsValuePackageName(std::move(defValuePackageName)),
            defEnumDir(std::move(defEnumDir)),
            defStructDir(std::move(defStructDir)),
            defValueDir(std::move(defValueDir)),
            defsEnumPackageName(std::move(defEnumPackageName)),
            defCallbackDir(std::move(defCallbackDir)),
            defsCallbackPackageName(std::move(defCallbackPackageName)),
            nativeFunctionPackageName(std::move(nativeFunctionPackageName)),
            sharedValuePackageName(std::move(sharedValuePackageName)),
            sharedValueInterfacePackageName(std::move(sharedValueInterfacePackageName)),
            sharedVListPackageName(std::move(sharedVListPackageName)) {
    }

    void TypedefGenerator::build() {
        if (DEBUG_LOG)
            std::cout << declaration.oriStr << " -> " << declaration.mappedStr << std::endl;
        auto result = jbindgen::TypedefGeneratorUtils::defaultNameFunction(&declaration);
        std::string target = declaration.mappedStr;
        value::jbasic::ValueType valueType = std::get<0>(result);
        auto extra = std::get<1>(result);
        bool drop = std::get<2>(result);
        if (drop)
            return;
        std::string s = std::vformat("package {};\n\n", std::make_format_args(defsValuePackageName));
        if (valueType.wrapper() == value::jbasic::VOther.wrapper()) {
            assert(extra.length() != 0);
            if (std::equal(extra.begin(), extra.end(), GEN_FUNCTION)) {
                //todo: check whether analyser#typedefFunctions has
            } else if (extra == value::jext::Pointer.wrapper()) {
                //todo: NPointer
                return;
            } else if (extra.starts_with("__ARRAY__")) {
                //todo: ARRAY
                return;
            } else if (extra == VOID_OR_FUNCTION_PTR) {
                s += std::vformat("public class {0} {{\n"
                                  "    private {0}() {{\n"
                                  "        throw new UnsupportedOperationException();\n"
                                  "    }}\n"
                                  "}}\n", std::make_format_args(target));
            } else {
                //other non-primitive type.
            }
        } else {
            assert(extra.length() == 0);
            s += getValueContent(target, valueType.objectPrimitiveName(),
                                 valueType.value_layout(), valueType.primitive(),
                                 sharedVListPackageName, sharedValueInterfacePackageName);
        }
        overwriteFile(defValueDir + "/" + target + ".java", s);
    }
} // jbindgen