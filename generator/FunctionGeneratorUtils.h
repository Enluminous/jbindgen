//
// Created by snownf on 23-11-15.
//

#ifndef JBINDGEN_FUNCTIONGENERATORUTILS_H
#define JBINDGEN_FUNCTIONGENERATORUTILS_H

#include <string>
#include <vector>
#include "../analyser/FunctionSymbolDeclaration.h"
#include <functional>

namespace jbindgen {
    struct FunctionWrapperInfo {
        std::string wrapperName;
        std::vector<std::string> jParameters;
        std::vector<std::string> decodeParameters;
        std::vector<std::string> encodeParameters;
        std::string wrappedResult;//optional, depend on hasResult
        std::function<std::string(std::string varName)> makeResult;
    };

    struct FunctionInfo {
        std::string functionName;
        std::vector<std::string> jParameters;
        std::vector<std::string> parameterDescriptors;
        std::vector<std::string> invokeParameters;
        std::vector<FunctionWrapperInfo> wrappers;
        std::string resultDescriptor;
        std::string jResult;
        bool hasResult;
        bool needAllocator;
        bool critical;
    };

    typedef std::function<FunctionInfo(const jbindgen::FunctionSymbolDeclaration *declaration,
                                       const Analyser &analyser)> FN_makeFunction;
}

namespace jbindgen::functiongenerator {
    FunctionInfo
    defaultMakeFunctionInfo(const jbindgen::FunctionSymbolDeclaration *declaration, const Analyser &analyser);

    std::tuple<std::vector<std::string>, std::vector<std::string>, std::vector<std::string>>
    makeParameter(const jbindgen::FunctionSymbolDeclaration &declare);

    //wrapper type,decode way,encode way
    struct wrapper {
        std::string type;
        std::string decode;
        std::function<std::string(std::string constructorStr)> getEncode;
    };

    std::vector<wrapper> processWrapperCallType(const VarDeclare &declare, const Analyser &analyser);
} // jbindgen

#endif //JBINDGEN_FUNCTIONGENERATORUTILS_H
