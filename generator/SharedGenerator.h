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

    std::string getNPtrListContent();

    std::string getValueContent();

    std::string getVListContent();

    std::string getValueContent(std::string className, std::string baseObjectType, std::string valueLayout,
                                std::string basePrimitiveType, std::string sharedVListPackageName,
                                std::string sharedValuePackageName);

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

        void makeVList() {
            std::string content = std::vformat("package {};\n", std::make_format_args(basePackageName));
            content += getVListContent();
            overwriteFile(dir + "/" + "VList.java", content);
        }

        void makeValues() {
            auto maps = {value::jbasic::VDouble, value::jbasic::VFloat,
                         value::jbasic::VLong, value::jbasic::VInteger,
                         value::jbasic::VShort, value::jbasic::VByte};
            for (const auto &item: maps) {
                std::string content = std::vformat("package {};\n", std::make_format_args(basePackageName + ".value"));
                content += getValueContent(item.wrapper(), item.objectPrimitiveName(), item.value_layout(),
                                           item.primitive(),
                                           basePackageName + ".VList", basePackageName + ".Value");
                overwriteFile(dir + "/value/" + item.wrapper() + ".java", content);
            }
        }

        void makePointers() {

        }
    };


} // jbindgen

#endif //JAVABINDGEN_SHAREDGENERATOR_H
