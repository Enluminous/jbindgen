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
    static std::stringstream makeCoreWithoutPara(bool hasResult, const std::string& functionName, const std::string& jrtype) {
        std::stringstream ss;
        ss << std::endl << std::endl;
        std::string invokeRet = hasResult ? "return (" + jrtype + ") " : "";
        ss << "    private static MethodHandle " << functionName << ";" << END_LINE
           NEXT_LINE
           << "    public static void " << functionName << "() {" << END_LINE
           << "        if (" << functionName << " == null) {" << END_LINE
           << "            " << functionName << " = GlslangSymbols.loadCFunction(" << functionName << ", " << jrtype << ".class);\n"
           << "        }\n"
           << "        try {\n"
           << "            " << invokeRet << "" << functionName << ".invoke();\n"
           << "        } catch (Throwable e) {\n"
           << "            throw new NativeFunction.InvokeException(e);\n"
           << "        }\n"
           << "    }";
        return ss;
    }

    static std::stringstream
    makeCoreWithPara(bool hasResult, const std::string& functionName, const std::string& jrtype, const std::string& paras,
                     const std::string& paraNames,
                     const std::string& paraTypes) {
        std::stringstream ss;
        std::string invokeRet = hasResult ? "return (" + jrtype + ") " : "";
        ss << "\n"
        "    private static MethodHandle " << functionName << ";\n"
        "\n"
        "    public static " << jrtype << " " << functionName << "(" << paras << ") {\n"
        "        if (" << functionName << " == null) {\n"
        "            " << functionName << " = GlslangSymbols.loadCFunction(" << functionName << ", " << jrtype << ".class, " << paraTypes << ");\n"
        "        }\n"
        "        try {\n"
        "            " << invokeRet << "" << functionName << ".invoke(" << paraNames << ");\n"
        "        } catch (Throwable e) {\n"
        "            throw new NativeFunction.InvokeException(e);\n"
        "        }\n"
        "    }\n"
        "\n"
        "";
        return ss;
    }

    class FunctionSymbolGenerator {
        const std::string libName;
        const PFN_makeFunction makeFunction;
        const std::string functionLoader;
        const std::string header;
        const std::string className;
        const std::string tail;
        const std::string dir;
        const std::vector<FunctionDeclaration> function_declarations;

    public:
        FunctionSymbolGenerator(std::string libName, PFN_makeFunction makeFunction, std::string functionLoader,
                                std::string header, std::string tail, std::string dir,
                                std::vector<FunctionDeclaration> function_declarations, std::string className);

        void build(void *userData) {
            std::stringstream ss;
            ss << header;
            for (const auto &functionDeclaration: function_declarations) {
                auto func = makeFunction(&functionDeclaration, userData);
                if (func.invokeParameters.empty()) {
                    ss << makeCoreWithoutPara(func.hasResult, func.functionName, func.jResult).str();
                } else {
                    std::stringstream jparas;
                    for (const auto &pars: func.jParameters) {
                        jparas << " " << pars;
                    }
                    std::stringstream invpara;
                    for (const auto &para: func.invokeParameters) {
                        invpara << " " << para;
                    }
                    std::stringstream funcTypes;
                    for (const auto &descriptor: func.functionDescriptors) {
                        //TODO:
                        funcTypes << " " << descriptor;
                    }
                    ss << makeCoreWithPara(func.hasResult, func.functionName, func.jResult, jparas.str(), invpara.str(),
                                           funcTypes.str()).str();
                }

            }
            ss << tail;
            overwriteFile(dir + "/" + className + ".java", ss.str());
        }
    };
} // jbindgen

#endif //JAVABINDGEN_FUNCTIONSYMBOLGENERATOR_H
