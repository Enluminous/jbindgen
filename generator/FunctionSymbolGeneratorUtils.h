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
        static std::string defaultHead(const std::string &className, const std::string &packageName, std::string libName);

        static std::string defaultTail();

        static FunctionSymbolInfo defaultMakeFunction(const jbindgen::FunctionDeclaration* declaration, void *pUserdata );
    };
    //wrapper type,decode way,encode way
    struct wrapper {
        std::string type;
        std::string decode;
        std::string encode;
    };
    std::vector<FunctionSymbolWrapperInfo> makeWrappers(const FunctionDeclaration &declaration);
    std::vector<wrapper> processWrapperCallType(const VarDeclare &declare);

} // jbindgen

#endif //JAVABINDGEN_FUNCTIONSYMBOLGENERATORUTILS_H
