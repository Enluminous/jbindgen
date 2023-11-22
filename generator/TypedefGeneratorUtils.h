//
// Created by snownf on 23-11-19.
//

#ifndef JAVABINDGEN_TYPEDEFGENERATORUTILS_H
#define JAVABINDGEN_TYPEDEFGENERATORUTILS_H

#include "TypedefGenerator.h"
#include <string>

namespace jbindgen {
    class TypedefGeneratorUtils {

    public:
        static std::tuple<std::string, std::string, bool>
        defaultNameFunction(const jbindgen::NormalTypedefDeclaration *declaration, void *pUserdata);
    };
}

#endif //JAVABINDGEN_TYPEDEFGENERATORUTILS_H
