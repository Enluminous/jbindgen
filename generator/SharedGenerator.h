//
// Created by snownf on 23-12-4.
//

#ifndef JAVABINDGEN_SHAREDGENERATOR_H
#define JAVABINDGEN_SHAREDGENERATOR_H

#include <string>
#include <format>
#include <utility>
#include "GenUtils.h"
#include "Value.h"

namespace jbindgen {

    std::string getAbstractNativeListContent();

    std::string getPointerContent();

    std::string getFunctionUtilsContent();

    std::string getNListContent();

    std::string getNStringContent(const std::string &vi8ValuePackageName);

    std::string getNPtrListContent();

    std::string getValueContent();

    std::string
    getBasicValueContent(std::string sharedValueInterfacePackageName, std::string sharedPointerPackageName,
                         std::string className,
                         std::string primitiveType, std::string primitiveObjType, std::string valueLayout);

    std::string getSubValueContent(std::string className, std::string basicClassName, std::string specializedList,
                                   std::string specializedListPackageName, std::string sharedValueInterfacePackageName,
                                   std::string sharedPointerPackageName, std::string unused,
                                   std::string basePrimitiveType, std::string baseObjectType);

    std::string
    getSubValueContentSpecialized(std::string className, std::string basicClassName, std::string specializedList,
                                  std::string specializedListPackageName, std::string sharedValueInterfacePackageName,
                                  std::string sharedPointerPackageName, std::string sharedVBasicPackageName,
                                  std::string basePrimitiveType, std::string baseObjectType);

    std::string getNativeContent(std::string className, std::string baseObjectType, std::string valueLayout,
                                 std::string basePrimitiveType, std::string sharedPointerPackageName,
                                 std::string sharedValuePackageName, std::string theValueTypeName,
                                 std::string theValueTypePkgName);

    std::string getVListSpecializedContent(std::string className, std::string valueLayout, std::string byteSize,
                                           std::string primitiveObj);

    class SharedGenerator {
        std::string dir;
        std::string basePackageName;
    public:
        SharedGenerator(std::string dir, std::string basePackageName)
                : dir(std::move(dir)), basePackageName(std::move(basePackageName)) {}

        void makeAbstractNativeList() {
            std::string content = std::vformat("package {};\n", std::make_format_args(basePackageName));
            content += getAbstractNativeListContent();
            overwriteFile(dir + "/" + "AbstractNativeList.java", content);
        }

        void makeFunctionUtils() {
            std::string content = std::vformat("package {};\n", std::make_format_args(basePackageName));
            content += getFunctionUtilsContent();
            overwriteFile(dir + "/" + "FunctionUtils.java", content);
        }

        void makeNList() {
            std::string content = std::vformat("package {};\n", std::make_format_args(basePackageName));
            content += getNListContent();
            overwriteFile(dir + "/" + "NList.java", content);
        }

        void makeNString() {
            std::string content = std::vformat("package {};\n", std::make_format_args(basePackageName));
            content += getNStringContent(basePackageName + ".values.VI8");
            overwriteFile(dir + "/" + "NString.java", content);
        }

        void makeNPtrList() {
            std::string content = std::vformat("package {};\n", std::make_format_args(basePackageName));
            content += getNPtrListContent();
            overwriteFile(dir + "/" + "NPtrList.java", content);
        }

        void makePointer() {
            std::string content = std::vformat("package {};\n", std::make_format_args(basePackageName));
            content += getPointerContent();
            overwriteFile(dir + "/" + "Pointer.java", content);
        }

        void makeValue() {
            std::string content = std::vformat("package {};\n", std::make_format_args(basePackageName));
            content += getValueContent();
            overwriteFile(dir + "/" + "Value.java", content);
        }

        void makeBasicValues() {
            auto maps = {value::jbasic::VDouble, value::jbasic::VFloat,
                         value::jbasic::VLong, value::jbasic::VInteger,
                         value::jbasic::VShort, value::jbasic::VByte, value::jext::VPointer};
            for (const auto &item: maps) {
                std::string className = item.wrapper() + "Basic";
                const auto &valuesPkg =
                        basePackageName + ".values";
                std::string content = std::vformat("package {};\n", std::make_format_args(valuesPkg));
                content += getBasicValueContent(basePackageName + ".Value",
                                                basePackageName + ".Pointer",
                                                className,
                                                item.primitive(),
                                                item.objectPrimitiveName(),
                                                item.value_layout());
                overwriteFile(dir + "/values/" + className + ".java", content);
            }
        }

        void makeValues() {
            auto maps = {value::jbasic::VDouble, value::jbasic::VFloat,
                         value::jbasic::VLong, value::jbasic::VInteger,
                         value::jbasic::VShort, value::jbasic::VByte, value::jext::VPointer};
            for (const auto &item: maps) {
                const auto &valuesPkg =
                        basePackageName + ".values";
                std::string content = std::vformat("package {};\n", std::make_format_args(valuesPkg));
                content += getSubValueContent(item.wrapper(), item.wrapper() + "Basic",
                                              item.list_type(),
                                              basePackageName + "." + item.list_type(),
                                              basePackageName + ".Value",
                                              basePackageName + ".Pointer",
                                              basePackageName + ".VPointerList",
                                              item.primitive(), item.objectPrimitiveName());
                overwriteFile(dir + "/values/" + item.wrapper() + ".java", content);
            }
        }

        void makeNatives() {
            auto maps = {value::jbasic::Double, value::jbasic::Float,
                         value::jbasic::Long, value::jbasic::Integer,
                         value::jbasic::Short, value::jbasic::Byte, value::jext::Pointer};
            for (const auto &item: maps) {
                const auto &nativessPkg =
                        basePackageName + ".natives";
                std::string content = std::vformat("package {};\n",
                                                   std::make_format_args(nativessPkg));
                const std::string &valueTypeName = (value::method::native_type_2_value_type(item).type !=
                                                    value::jbasic::type_other)
                                                   ? value::method::native_type_2_value_type(item).wrapper()
                                                   : value::jext::VPointer.wrapper();
                content += getNativeContent(item.wrapper(), item.objectPrimitiveName(), item.value_layout(),
                                            item.primitive(),
                                            basePackageName + ".Pointer", basePackageName + ".Value",
                                            valueTypeName, basePackageName + ".values." + valueTypeName);
                overwriteFile(dir + "/natives/" + item.wrapper() + ".java", content);
            }
        }

        void makeVListSpecialized() {
            auto maps = {value::jbasic::VDouble, value::jbasic::VFloat,
                         value::jbasic::VLong, value::jbasic::VInteger,
                         value::jbasic::VShort, value::jbasic::VByte, value::jext::VPointer};
            for (const auto &item: maps) {
                std::string content = std::vformat("package {};\n",
                                                   std::make_format_args(basePackageName));
                std::string className = item.list_type();
                content += getVListSpecializedContent(className, item.value_layout(),
                                                      std::to_string(item.byteSize),
                                                      item.objectPrimitiveName());
                overwriteFile(dir + "/" + className + ".java", content);
            }
        }
    };


} // jbindgen

#endif //JAVABINDGEN_SHAREDGENERATOR_H
