//
// Created by snownf on 23-11-15.
//

#ifndef JBINDGEN_FUNCTIONGENERATORUTILS_H
#define JBINDGEN_FUNCTIONGENERATORUTILS_H

#include <string>
#include <vector>
#include "../analyser/FunctionSymbolDeclaration.h"

namespace jbindgen {
    struct FunctionWrapperInfo {
        std::string wrapperName;
        std::vector<std::string> jParameters;
        std::vector<std::string> decodeParameters;
        std::vector<std::string> encodeParameters;
        std::string wrappedResult;//optional, depend on hasResult
    };

    struct FunctionInfo {
        std::string functionName;
        std::vector<std::string> jParameters;
        std::vector<std::string> functionDescriptors;
        std::vector<std::string> invokeParameters;
        std::vector<FunctionWrapperInfo> wrappers;
        std::string resultDescriptor;
        std::string jResult;
        bool hasResult;
        bool needAllocator;
        bool critical;
    };

    typedef FunctionInfo(*PFN_makeFunction)(const jbindgen::FunctionDeclaration *declaration, void *pUserdata);
}

namespace jbindgen::functiongenerator {
    FunctionInfo
    defaultMakeFunctionInfo(const jbindgen::FunctionDeclaration *declaration, void *pUserdata);

    std::tuple<std::vector<std::string>, std::vector<std::string>, std::vector<std::string>>
    makeParameter(const jbindgen::FunctionDeclaration &declare);
} // jbindgen

#endif //JBINDGEN_FUNCTIONGENERATORUTILS_H
