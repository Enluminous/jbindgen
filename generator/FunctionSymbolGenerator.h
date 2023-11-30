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
    static std::stringstream
    makeCoreWithoutPara(bool hasResult, const std::string &functionName, const std::string &jrtype,
                        const std::string &functionDescriptor);

    static std::stringstream
    makeCoreWithPara(bool hasResult, const std::string &functionName, const std::string &jrtype,
                     const std::string &paras,
                     const std::string &paraNames, const std::string &functionDescriptor);

    std::string makeWrapper(std::vector<std::string> jParameters,
                            const std::vector<std::string> &callParas,
                            std::string parentFuncName,
                            std::string funcName, std::string retName,
                            bool hasRet);

    class FunctionSymbolGenerator {
        const PFN_makeFunction makeFunction;
        const std::string functionLoader;
        const std::string header;
        const std::string className;
        const std::string tail;
        const std::string dir;
        const std::vector<FunctionDeclaration> function_declarations;
        const Analyser &analyser;
    public:
        FunctionSymbolGenerator(const Analyser &analyser, PFN_makeFunction makeFunction,
                                std::string functionLoader, std::string header, std::string tail,
                                std::string dir,
                                std::vector<FunctionDeclaration> function_declarations,
                                std::string className);

        static std::string
        defaultHead(const std::string &className, const std::string &packageName, std::string libName);

        static std::string defaultTail();

        void build(void *userData);
    };
} // jbindgen

#endif //JBINDGEN_FUNCTIONSYMBOLGENERATOR_H
