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
    static std::string
    makeCoreWithoutPara(bool hasResult, const std::string &functionName, const std::string &jrtype,
                        const std::string &functionDescriptor, std::string symbolClassName);

    static std::string
    makeCoreWithPara(bool hasResult, const std::string &functionName, const std::string &jrtype,
                     const std::string &paras,
                     const std::string &paraNames, const std::string &functionDescriptor, std::string symbolClassName);

    std::string makeWrapper(std::vector<std::string> jParameters,
                            const std::vector<std::string> &callParas,
                            std::string parentFuncName,
                            std::string funcName, std::string retName,
                            bool hasRet);

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
                    std::string nativeInterfacePackageName);

        static std::string defaultTail();

        void build();

        std::string makeSymbol();
    };
} // jbindgen

#endif //JBINDGEN_FUNCTIONSYMBOLGENERATOR_H
