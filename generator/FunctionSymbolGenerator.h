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
        std::string resultDescriptor;//optional, depend on hasResult
        std::string jResult;//optional, depend on hasResult
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
        bool needAllocator;//todo
        bool critical;
    };

    typedef FunctionSymbolInfo(*PFN_makeFunction)(const jbindgen::FunctionDeclaration *declaration, void *pUserdata);

    static std::stringstream
    makeCoreWithoutPara(bool hasResult, const std::string &functionName, const std::string &jrtype,
                        const std::string &functionDescriptor) {
        std::stringstream ss;
        ss << std::endl << std::endl;
        std::string invokeRet = hasResult ? "return (" + jrtype + ") " : "";
        std::string jFunctionDescriptor = hasResult ? "FunctionDescriptor.of(" + functionDescriptor + ")" :
                                          "FunctionDescriptor.ofVoid(" + functionDescriptor + ")";
        ss << "    private static MethodHandle " << functionName << ";" << END_LINE
           NEXT_LINE
           << "    public static " << (hasResult ? jrtype : "void") << " " << functionName << "() {" << END_LINE
           << "        if (" << functionName << " == null) {" << END_LINE
           << "            " << functionName << " = GlslangSymbols.loadCFunction(" << jFunctionDescriptor << ");\n"
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
    makeCoreWithPara(bool hasResult, const std::string &functionName, const std::string &jrtype,
                     const std::string &paras,
                     const std::string &paraNames, const std::string &functionDescriptor) {
        std::stringstream ss;
        std::string invokeRet = hasResult ? "return (" + jrtype + ") " : "";
        std::string jFunctionDescriptor = hasResult ? "FunctionDescriptor.of(" + functionDescriptor + ")" :
                                          "FunctionDescriptor.ofVoid(" + functionDescriptor + ")";

        ss << "\n"
              "    private static MethodHandle " << functionName << ";\n""\n" <<
           "    public static " << (hasResult ? jrtype : "void") << " " << functionName << "(" << paras << ") {\n" <<
           "        if (" << functionName << " == null) {\n" <<
           "            " << functionName << " = GlslangSymbols.loadCFunction(" << jFunctionDescriptor << ");\n" <<
           "        }\n"
           "        try {\n"
           "            " << invokeRet << "" << functionName << ".invoke(" << paraNames << ");\n" <<
           "        } catch (Throwable e) {\n"
           "            throw new NativeFunction.InvokeException(e);\n"
           "        }\n"
           "    }\n"
           "\n"
           "";
        return ss;
    }

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

        void build(void *userData) {
            std::stringstream ss;
            ss << header;
            for (const auto &functionDeclaration: function_declarations) {
                auto func = makeFunction(&functionDeclaration, userData);
                std::stringstream funcTypes;
                for (int i = 0; i < func.functionDescriptors.size(); ++i) {
                    std::string &descriptor = func.functionDescriptors[i];
                    funcTypes << " " << descriptor << ((i == func.functionDescriptors.size() - 1) ? "" : ",");
                }
                if (func.invokeParameters.empty()) {
                    ss << makeCoreWithoutPara(func.hasResult, func.functionName, func.jResult, funcTypes.str()).str();
                } else {
                    std::stringstream jparas;
                    for (int i = 0; i < func.jParameters.size(); ++i) {
                        std::string &para = func.jParameters[i];
                        jparas << " " << para << ((i == func.jParameters.size() - 1) ? "" : ",");
                    }
                    std::stringstream invpara;
                    for (int i = 0; i < func.invokeParameters.size(); ++i) {
                        std::string &para = func.invokeParameters[i];
                        invpara << " " << para << ((i == func.invokeParameters.size() - 1) ? "" : ",");
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
