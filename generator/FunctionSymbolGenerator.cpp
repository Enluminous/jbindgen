//
// Created by nettal on 23-11-13.
//

#include "FunctionSymbolGenerator.h"

#include <utility>
#include <format>

namespace jbindgen {
    std::string FunctionSymbolGenerator::defaultHead(const std::string &className, const std::string &packageName,
                                                     const GeneratorConfig &config,
                                                     const std::shared_ptr<TypeManager> &typeManager) {
        std::string result = std::vformat(
                "package {};\n"
                "\n"
                "{}"
                "{}"
                "\n"
                "import java.lang.foreign.*;\n"
                "import java.lang.invoke.MethodHandle;\n"
                "\n"
                "public final class {} {{\n",
                std::make_format_args(packageName, typeManager->getImports(&config, true),
                                      typeManager->getImports(), className));
        return result;
    }

    std::string FunctionSymbolGenerator::defaultTail() {
        return "}";
    }

    FunctionSymbolGenerator::FunctionSymbolGenerator(const Analyser &analyser,
                                                     struct config::FunctionSymbols config,
                                                     std::vector<FunctionSymbolDeclaration> function_declarations,
                                                     std::string symbolClassName, std::string symbolPackageName)
            : function_declarations(std::move(function_declarations)), analyser(analyser), config(std::move(config)),
              symbolClassName(std::move(symbolClassName)), symbolPackageName(std::move(symbolPackageName)) {
    }

    void FunctionSymbolGenerator::build() {
        std::stringstream function;
        function << config.head;
        for (const auto &functionDeclaration: function_declarations) {
            auto func = config.makeFunction(&functionDeclaration, analyser);
            std::stringstream funcTypes;
            for (int i = 0; i < func.parameterDescriptors.size(); ++i) {
                std::string &descriptor = func.parameterDescriptors[i];
                funcTypes << (i == 0 ? "" : " ") << descriptor
                          << ((i == func.parameterDescriptors.size() - 1) ? "" : ",");
            }
            std::stringstream jparas;
            for (int i = 0; i < func.jParameters.size(); ++i) {
                std::string &para = func.jParameters[i];
                jparas << (i == 0 ? "" : " ") << para << ((i == func.jParameters.size() - 1) ? "" : ",");
            }
            std::stringstream invpara;
            for (int i = 0; i < func.invokeParameters.size(); ++i) {
                std::string &para = func.invokeParameters[i];
                invpara << (i == 0 ? "" : " ") << para << ((i == func.invokeParameters.size() - 1) ? "" : ",");
            }

            assertAppend(func.invokeParameters.size() == func.jParameters.size(),
                         "function name: " + func.functionName);
            assertAppend(func.invokeParameters.size() == func.parameterDescriptors.size(),
                         "function name: " + func.functionName);
            assertAppend(func.invokeParameters.size() == functionDeclaration.paras.size(),
                         "function name: " + func.functionName);
            assertAppend(func.invokeParameters.empty() == invpara.str().empty(),
                         "function name: " + func.functionName);

            bool makePrivate = config.hideUnWarped;
            if (func.wrappers.empty())
                makePrivate = false;//if not wrapper,make it public
            if (func.needAllocator) {
                assertAppend(func.hasResult,
                             "function name: " + func.functionName);
                function << makeCoreWithAllocator(func.functionName, func.jResult, func.resultDescriptor,
                                                  jparas.str(), invpara.str(),
                                                  funcTypes.str(), symbolClassName, makePrivate);
                for (const auto &wrapper: func.wrappers) {
                    function << makeWrapperWithAllocator(wrapper.jParameters, wrapper.decodeParameters,
                                                         func.functionName,
                                                         wrapper.wrapperName, wrapper.makeResult,
                                                         wrapper.wrappedResult);
                }
            } else {
                function << makeCore(func.hasResult, func.functionName, func.jResult, func.resultDescriptor,
                                     jparas.str(), invpara.str(),
                                     funcTypes.str(), symbolClassName, makePrivate);
                for (const auto &wrapper: func.wrappers) {
                    function << makeWrapper(wrapper.jParameters, wrapper.decodeParameters, func.functionName,
                                            wrapper.wrapperName, wrapper.makeResult,
                                            wrapper.wrappedResult, func.hasResult);
                }
            }

        }
        function << config.tail;
        overwriteFile(config.dir + "/" + config.functionClassName + ".java", function.str());
    }

    std::string
    FunctionSymbolGenerator::makeCore(bool hasResult, const std::string &functionName, const std::string &jrtype,
                                      const std::string &resultDescriptor, const std::string &paras,
                                      const std::string &paraNames, const std::string &functionDescriptor,
                                      std::string symbolClassName, bool makePrivate) {
        std::string invokeRet = hasResult ? "return (" + jrtype + ") " : "";
        std::string jFunctionDescriptor = hasResult
                                          ? "FunctionDescriptor.of(" + resultDescriptor +
                                            (functionDescriptor.empty() ? ")" : (", " + functionDescriptor) + ")")
                                          : "FunctionDescriptor.ofVoid(" + functionDescriptor + ")";
        std::string result = std::vformat(
                "    private static MethodHandle {0};\n"
                "\n"
                "    {7} static {1} {0}({6}) {{\n"
                "        if ({0} == null) {{\n"
                "            {0} = {3}.toMethodHandle(\"{0}\", {2}).orElseThrow(() -> new FunctionUtils.SymbolNotFound(\"{0}\"));\n"
                "        }}\n"
                "        try {{\n"
                "            {4}{0}.invoke({5});\n"
                "        }} catch (Throwable e) {{\n"
                "            throw new FunctionUtils.InvokeException(e);\n"
                "        }}\n"
                "    }}\n", std::make_format_args(functionName, (hasResult ? jrtype : "void"),
                                                  jFunctionDescriptor, symbolClassName, invokeRet, paraNames, paras,
                                                  makePrivate ? "private" : "public"));
        return result;
    }

    std::string
    FunctionSymbolGenerator::makeCoreWithAllocator(const std::string &functionName, const std::string &jrtype,
                                                   const std::string &resultDescriptor, const std::string &paras,
                                                   const std::string &paraNames, const std::string &functionDescriptor,
                                                   std::string symbolClassName, bool makePrivate) {
        std::string jFunctionDescriptor = functionDescriptor.empty() ? "FunctionDescriptor.of(" + resultDescriptor + ")"
                                                                     : "FunctionDescriptor.of(" + resultDescriptor +
                                                                       ", " + functionDescriptor + ")";
        std::string result = std::vformat(
                "    private static MethodHandle {0};\n"
                "\n"
                "    {7} static {1} {0}(SegmentAllocator allocator{6}) {{\n"
                "        if ({0} == null) {{\n"
                "            {0} = {3}.toMethodHandle(\"{0}\", {2}).orElseThrow(() -> new FunctionUtils.SymbolNotFound(\"{0}\"));\n"
                "        }}\n"
                "        try {{\n"
                "            {4}{0}.invoke(allocator{5});\n"
                "        }} catch (Throwable e) {{\n"
                "            throw new FunctionUtils.InvokeException(e);\n"
                "        }}\n"
                "    }}\n", std::make_format_args(functionName, jrtype, jFunctionDescriptor, symbolClassName,
                                                  "return (" + jrtype + ") ",
                                                  paraNames.empty() ? "" : ", " + paraNames,
                                                  paras.empty() ? "" : ", " + paras,
                                                  makePrivate ? "private" : "public"));
        return result;
    }

    std::string FunctionSymbolGenerator::makeWrapper(std::vector<std::string> jParameters,
                                                     const std::vector<std::string> &callParas,
                                                     std::string parentFuncName, std::string funcName,
                                                     const std::function<std::string(std::string varName)> &makeResult,
                                                     std::string retType,
                                                     bool hasRet) {
        std::stringstream jPara;
        for (int i = 0; i < jParameters.size(); ++i) {
            std::string &para = jParameters[i];
            jPara << (i == 0 ? "" : " ") << para << ((i == jParameters.size() - 1) ? "" : ",");
        }
        std::stringstream call;
        for (int i = 0; i < callParas.size(); ++i) {
            const auto &fd = callParas[i];
            call << (i == 0 ? "" : " ") << fd << ((i == callParas.size() - 1) ? "" : ",");
        }
        std::string result;
        if (hasRet) {
            result = std::vformat("    public static {} {}({}) {{\n"
                                  "        return {};\n"
                                  "    }}\n\n",
                                  std::make_format_args(retType, funcName, jPara.str(),
                                                        makeResult(parentFuncName + "(" + call.str() + ")")));
        } else {
            result = std::vformat("    public static void {}({}) {{\n"
                                  "        {}({});\n"
                                  "    }}\n\n",
                                  std::make_format_args(funcName, jPara.str(), parentFuncName, call.str()));
        }
        return result;
    }

    std::string FunctionSymbolGenerator::makeWrapperWithAllocator(const std::vector<std::string> &jParameters,
                                                                  const std::vector<std::string> &callParas,
                                                                  const std::string &parentFuncName,
                                                                  std::string funcName,
                                                                  const std::function<std::string(
                                                                          std::string varName)> &makeResult,
                                                                  std::string retType) {
        std::stringstream jPara;
        jPara << "SegmentAllocator allocator";
        for (auto &para: jParameters) {
            jPara << "," << para;
        }
        std::stringstream call;
        call << "allocator";
        for (int i = 0; i < callParas.size(); ++i) {
            const auto &fd = callParas[i];
            call << "," << fd;
        }
        std::string result;
        result = std::vformat("    public static {} {}({}) {{\n"
                              "        return {};\n"
                              "    }}\n\n",
                              std::make_format_args(retType, funcName, jPara.str(),
                                                    makeResult(parentFuncName + "(" + call.str() + ")")));
        return result;
    }
} // jbindgen