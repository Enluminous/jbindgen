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
#include "Generator.h"

namespace jbindgen {
    struct GeneratorConfig;

    class FunctionSymbolGenerator {
        struct config::FunctionSymbols config;
        const std::string symbolClassName;
        const std::string symbolPackageName;
        const std::vector<FunctionSymbolDeclaration> function_declarations;
        const Analyser &analyser;

    public:
        FunctionSymbolGenerator(const Analyser &analyser, struct config::FunctionSymbols config,
                                std::vector<FunctionSymbolDeclaration> function_declarations,
                                std::string symbolClassName, std::string symbolPackageName);

        static std::string
        defaultHead(const std::string &className, const std::string &packageName,
                    const GeneratorConfig &config,
                    const std::shared_ptr<TypeManager> &typeManager);

        static std::string defaultTail();


        static std::string
        makeCore(bool hasResult, const std::string &functionName, const std::string &jrtype,
                 const std::string &resultDescriptor, const std::string &paras,
                 const std::string &paraNames, const std::string &functionDescriptor,
                 std::string symbolClassName, bool makePrivate);

        static std::string
        makeWrapper(std::vector<std::string> jParameters, const std::vector<std::string> &callParas,
                    std::string parentFuncName,
                    std::string funcName, const std::function<std::string(std::string)> &makeResult,
                    std::string retType,
                    bool hasRet);

        void build();

        static std::string makeCoreWithAllocator(const std::string &functionName, const std::string &jrtype,
                                                 const std::string &resultDescriptor, const std::string &paras,
                                                 const std::string &paraNames, const std::string &functionDescriptor,
                                                 std::string symbolClassName, bool makePrivate);

        static std::string
        makeWrapperWithAllocator(const std::vector<std::string> &jParameters,
                                 const std::vector<std::string> &callParas,
                                 const std::string &parentFuncName, std::string funcName,
                                 const std::function<std::string(
                                         std::string varName)> &makeResult,
                                 std::string retType);
    };
} // jbindgen

#endif //JBINDGEN_FUNCTIONSYMBOLGENERATOR_H
