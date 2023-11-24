//
// Created by snownf on 23-11-15.
//

#ifndef JAVABINDGEN_FUNCTIONSYMBOLGENERATORUTILS_H
#define JAVABINDGEN_FUNCTIONSYMBOLGENERATORUTILS_H

#include <string>
#include "GenUtils.h"
#include "FunctionSymbolGenerator.h"

namespace jbindgen {

    class FunctionSymbolGeneratorUtils {
    public:
        static std::string
        defaultHead(const std::string &className, const std::string &packageName, std::string libName);

        static std::string defaultTail();
    };

    //wrapper type,decode way,encode way
    struct wrapper {
        std::string type;
        std::string decode;
        std::string encode;
    };

    FunctionInfo
    defaultMakeFunctionInfo(const jbindgen::FunctionDeclaration *declaration, void *pUserdata);

    std::tuple<std::vector<std::string>, std::vector<std::string>, std::vector<std::string>>
    makeParameter(const jbindgen::FunctionDeclaration &declare);
} // jbindgen

#endif //JAVABINDGEN_FUNCTIONSYMBOLGENERATORUTILS_H
