//
// Created by snownf on 23-12-4.
//

#ifndef JAVABINDGEN_SHAREDGENERATOR_H
#define JAVABINDGEN_SHAREDGENERATOR_H

#include <string>
#include <format>
#include <utility>
#include "GenUtils.h"

namespace jbindgen {

    std::string getAbstractNativeListContent();
    std::string getPointerContent();

    class SharedGenerator {
        std::string dir;
        std::string basePackageName;
    public:
        SharedGenerator(std::string dir, std::string basePackageName)
                : dir(std::move(dir)), basePackageName(std::move(basePackageName)) {}

        void makeAbstractNativeList() {
            std::string content = std::vformat("package {};", std::make_format_args(basePackageName));
            content += getAbstractNativeListContent();
            overwriteFile(dir + "/" + "AbstractNativeList.java", content);
        }

        void makeFunctionUtils() {

        }

        void makeNList() {

        }

        void makeNPtrList() {

        }

        void makePointer() {
            std::string content = std::vformat("package {};", std::make_format_args(basePackageName));
            content += getPointerContent();
            overwriteFile(dir + "/" + "Pointer.java", content);
        }

        void makeValue() {

        }

        void makeVList() {

        }

    };


} // jbindgen

#endif //JAVABINDGEN_SHAREDGENERATOR_H
