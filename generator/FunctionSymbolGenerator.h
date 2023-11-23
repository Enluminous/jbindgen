//
// Created by nettal on 23-11-13.
//

#ifndef JAVABINDGEN_FUNCTIONSYMBOLGENERATOR_H
#define JAVABINDGEN_FUNCTIONSYMBOLGENERATOR_H

#include <string>
#include <utility>

#include "GenUtils.h"
#include "../analyser/FunctionSymbolDeclaration.h"
#include "StructGeneratorUtils.h"

namespace jbindgen {

    struct FunctionSymbolWrapperInfo {
        std::string wrapperName;
        std::vector<std::string> jParameters;
        std::vector<std::string> targetParameters;
        std::string wrappedResult;//optional, depend on hasResult
    };

    struct FunctionSymbolInfo {
        std::string functionName;
        std::vector<std::string> jParameters;
        std::vector<std::string> functionDescriptors;
        std::vector<std::string> invokeParameters;
        std::vector<FunctionSymbolWrapperInfo> wrappers;
        std::string resultDescriptor;
        std::string jResult;
        bool hasResult;
        bool needAllocator;
        bool critical;
    };

    typedef FunctionSymbolInfo(*PFN_makeFunction)(const jbindgen::FunctionDeclaration *declaration, void *pUserdata);

    static std::stringstream
    makeCoreWithoutPara(bool hasResult, const std::string &functionName, const std::string &jrtype,
                        const std::string &functionDescriptor);

    static std::stringstream
    makeCoreWithPara(bool hasResult, const std::string &functionName, const std::string &jrtype,
                     const std::string &paras,
                     const std::string &paraNames, const std::string &functionDescriptor);

    class FunctionSymbolGenerator {
        const PFN_makeFunction makeFunction;
        const std::string functionLoader;
        const std::string header;
        const std::string className;
        const std::string tail;
        const std::string dir;
        const std::vector<FunctionDeclaration> function_declarations;

    public:
        FunctionSymbolGenerator(PFN_makeFunction makeFunction, std::string functionLoader,
                                std::string header, std::string tail, std::string dir,
                                std::vector<FunctionDeclaration> function_declarations, std::string className);

        void build(void *userData);
    };
} // jbindgen

#endif //JAVABINDGEN_FUNCTIONSYMBOLGENERATOR_H
