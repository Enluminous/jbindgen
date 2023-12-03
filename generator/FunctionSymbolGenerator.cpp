//
// Created by nettal on 23-11-13.
//

#include "FunctionSymbolGenerator.h"

#include <utility>
#include <format>

namespace jbindgen {
    std::string FunctionSymbolGenerator::defaultHead(const std::string &className, const std::string &packageName,
                                                     std::string valuesPackageName, std::string structPackageName,
                                                     std::string functionUtilsPackageName) {
        std::string result = std::vformat(
                "package {};\n"
                "\n"
                "import {}.*;\n"
                "import {}.*;\n"
                "import {};\n"
                "\n"
                "import java.lang.foreign.FunctionDescriptor;\n"
                "import java.lang.foreign.MemorySegment;\n"
                "import java.lang.foreign.ValueLayout;\n"
                "import java.lang.invoke.MethodHandle;\n"
                "\n"
                "public final class {} {{\n",
                std::make_format_args(packageName, valuesPackageName, structPackageName, functionUtilsPackageName,
                                      className));
        return result;
    }

    std::string FunctionSymbolGenerator::makeSymbol() {
        std::string symbol = std::vformat(
                "package {1};\n"
                "\n"
                "import shared.FunctionUtils;\n"
                "\n"
                "import java.lang.foreign.FunctionDescriptor;\n"
                "import java.lang.foreign.MemorySegment;\n"
                "import java.lang.foreign.SymbolLookup;\n"
                "import java.lang.invoke.MethodHandle;\n"
                "import java.util.ArrayList;\n"
                "import java.util.Optional;\n"
                "\n"
                "public class {0} {{\n"
                "    private {0}() {{\n"
                "        throw new UnsupportedOperationException();\n"
                "    }}\n"
                "\n"
                "    private static ArrayList<SymbolLookup> symbolLookups;\n"
                "    private static boolean critical = false;\n"
                "\n"
                "    public void addSymbols(SymbolLookup symbolLookup) {{\n"
                "        symbolLookups.add(symbolLookup);\n"
                "    }}\n"
                "\n"
                "    public void setCritical(boolean critical) {{\n"
                "        {0}.critical = critical;\n"
                "    }}\n"
                "\n"
                "    public static Optional<MethodHandle> toMethodHandle(String functionName, FunctionDescriptor functionDescriptor) {{\n"
                "        return symbolLookups.stream().map(symbolLookup -> FunctionUtils.toMethodHandle(symbolLookup, functionName, functionDescriptor, critical))\n"
                "                .filter(Optional::isPresent).map(Optional::get).findFirst();\n"
                "    }}\n"
                "\n"
                "    public static Optional<MemorySegment> getSymbol(String symbol) {{\n"
                "        return symbolLookups.stream().map(symbolLookup -> symbolLookup.find(symbol))\n"
                "                .filter(Optional::isPresent).map(Optional::get).findFirst();\n"
                "    }}\n"
                "}}\n", std::make_format_args(symbolClassName, symbolPackageName));
        return symbol;
    }

    std::string FunctionSymbolGenerator::defaultTail() {
        return "}";
    }

    FunctionSymbolGenerator::FunctionSymbolGenerator(const Analyser &analyser, FN_makeFunction makeFunction,
                                                     std::string functionLoader, std::string header, std::string tail,
                                                     std::string dir,
                                                     std::vector<FunctionSymbolDeclaration> function_declarations,
                                                     std::string functionClassName, std::string symbolClassName,
                                                     std::string symbolPackageName)
            : makeFunction(std::move(makeFunction)), functionLoader(std::move(functionLoader)), dir(std::move(dir)),
              function_declarations(std::move(function_declarations)), analyser(analyser),
              header(std::move(header)), tail(std::move(tail)), functionClassName(std::move(functionClassName)),
              symbolClassName(std::move(symbolClassName)), symbolPackageName(std::move(symbolPackageName)) {
    }

    void FunctionSymbolGenerator::build() {
        std::stringstream function;
        function << header;
        for (const auto &functionDeclaration: function_declarations) {
            auto func = makeFunction(&functionDeclaration, analyser);
            std::stringstream funcTypes;
            for (int i = 0; i < func.parameterDescriptors.size(); ++i) {
                std::string &descriptor = func.parameterDescriptors[i];
                funcTypes << (i == 0 ? "" : " ") << descriptor
                          << ((i == func.parameterDescriptors.size() - 1) ? "" : ",");
            }
            if (func.invokeParameters.empty()) {
                function << makeCoreWithoutPara(func.hasResult, func.functionName, func.jResult,
                                                funcTypes.str(), symbolClassName);
            } else {
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
                function << makeCoreWithPara(func.hasResult, func.functionName, func.jResult, jparas.str(),
                                             invpara.str(),
                                             funcTypes.str(), symbolClassName);
            }
            for (const auto &wrapper: func.wrappers) {
                function << makeWrapper(wrapper.jParameters, wrapper.decodeParameters, func.functionName,
                                        wrapper.wrapperName,
                                        wrapper.wrappedResult, func.hasResult);
            }

        }
        function << tail;
        overwriteFile(dir + "/" + functionClassName + ".java", function.str());

        overwriteFile(dir + "/" + symbolClassName + ".java", makeSymbol());
    }

    std::string makeCoreWithoutPara(bool hasResult, const std::string &functionName, const std::string &jrtype,
                                    const std::string &functionDescriptor, std::string symbolClassName) {

        std::string invokeRet = hasResult ? "return (" + jrtype + ") " : "";
        std::string jFunctionDescriptor = hasResult ? "FunctionDescriptor.of(" + functionDescriptor + ")" :
                                          "FunctionDescriptor.ofVoid(" + functionDescriptor + ")";
        std::string result = std::vformat(
                "    private static MethodHandle {0};\n"
                "\n"
                "    public static {1} {0}() {{\n"
                "        if ({0} == null) {{\n"
                "            {0} = {3}.toMethodHandle(\"{0}\", FunctionDescriptor.of({2})).orElseThrow();\n"
                "        }}\n"
                "        try {{\n"
                "            {4}{0}.invoke();\n"
                "        } catch (Throwable e) {{\n"
                "            throw new FunctionUtils.InvokeException(e);\n"
                "        }}\n"
                "    }}\n", std::make_format_args(functionName, (hasResult ? jrtype : "void"),
                                                  jFunctionDescriptor, symbolClassName, invokeRet));
        return result;
    }

    std::string makeCoreWithPara(bool hasResult, const std::string &functionName, const std::string &jrtype,
                                 const std::string &paras, const std::string &paraNames,
                                 const std::string &functionDescriptor, std::string symbolClassName) {
        std::string invokeRet = hasResult ? "return (" + jrtype + ") " : "";
        std::string jFunctionDescriptor = hasResult ? "FunctionDescriptor.of(" + functionDescriptor + ")" :
                                          "FunctionDescriptor.ofVoid(" + functionDescriptor + ")";
        std::string result = std::vformat(
                "    private static MethodHandle {0};\n"
                "\n"
                "    public static {1} {0}({6}) {{\n"
                "        if ({0} == null) {{\n"
                "            {0} = {3}.toMethodHandle(\"{0}\", FunctionDescriptor.of({2})).orElseThrow();\n"
                "        }}\n"
                "        try {{\n"
                "            {4}{0}.invoke({5});\n"
                "        }} catch (Throwable e) {{\n"
                "            throw new FunctionUtils.InvokeException(e);\n"
                "        }}\n"
                "    }}\n", std::make_format_args(functionName, (hasResult ? jrtype : "void"),
                                                  jFunctionDescriptor, symbolClassName, invokeRet, paraNames, paras));
        return result;
    }

    std::string makeWrapper(std::vector<std::string> jParameters,
                            const std::vector<std::string> &callParas,
                            std::string parentFuncName,
                            std::string funcName, std::string retName,
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
                                  "        return {}({});\n"
                                  "    }}\n\n",
                                  std::make_format_args(retName, funcName, jPara.str(), parentFuncName, call.str()));
        } else {
            result = std::vformat("    public static void {}({}) {{\n"
                                  "        {}({});\n"
                                  "    }}\n\n",
                                  std::make_format_args(funcName, jPara.str(), parentFuncName, call.str()));
        }
        return result;
    }
} // jbindgen