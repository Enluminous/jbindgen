//
// Created by nettal on 23-11-13.
//

#ifndef JBINDGEN_FUNCTIONSYMBOLGENERATOR_H
#define JBINDGEN_FUNCTIONSYMBOLGENERATOR_H

#include <string>
#include <utility>

#include "GenUtils.h"
#include "../analyser/FunctionSymbolDeclaration.h"
#include "StructGeneratorUtils.h"
#include "FunctionGeneratorUtils.h"

namespace jbindgen {
    struct GeneratorConfig;

    class FunctionSymbolGenerator {
        const FN_makeFunction makeFunction;
        const std::string functionLoader;
        const std::string header;
        const std::string functionClassName;
        const std::string symbolClassName;
        const std::string symbolPackageName;
        const std::string functionUtilsPackageName;
        const std::string tail;
        const std::string dir;
        const std::vector<FunctionSymbolDeclaration> function_declarations;
        const Analyser &analyser;

    public:
        FunctionSymbolGenerator(const Analyser &analyser, FN_makeFunction makeFunction, std::string functionLoader,
                                std::string header, std::string tail, std::string dir,
                                std::vector<FunctionSymbolDeclaration> function_declarations,
                                std::string functionClassName,
                                std::string symbolClassName, std::string symbolPackageName,
                                std::string functionUtilsPackageName);

        static std::string
        defaultHead(const std::string &className, const std::string &packageName,
                    std::string valuesPackageName, std::string structPackageName,
                    std::string sharedBasePackageName,
                    std::string sharedValuePackageName);

        static std::string defaultTail();


        static std::string
        makeCore(bool hasResult, const std::string &functionName, const std::string &jrtype,
                 const std::string &resultDescriptor, const std::string &paras,
                 const std::string &paraNames, const std::string &functionDescriptor,
                 std::string symbolClassName);

        static std::string
        makeWrapper(std::vector<std::string> jParameters, const std::vector<std::string> &callParas,
                    std::string parentFuncName,
                    std::string funcName, const std::function<std::string(std::string)> &makeResult,
                    std::string retType,
                    bool hasRet);

        void build();

        std::string makeSymbol();

        static std::string makeCoreWithAllocator(const std::string &functionName, const std::string &jrtype,
                                                 const std::string &resultDescriptor, const std::string &paras,
                                                 const std::string &paraNames, const std::string &functionDescriptor,
                                                 std::string symbolClassName);

        static std::string
        makeWrapperWithAllocator(const std::vector<std::string>& jParameters,
                                 const std::vector<std::string> &callParas,
                                 const std::string& parentFuncName, std::string funcName,
                                 const std::function<std::string(
                                         std::string varName)> &makeResult,
                                 std::string retType);
    };
} // jbindgen

#endif //JBINDGEN_FUNCTIONSYMBOLGENERATOR_H
