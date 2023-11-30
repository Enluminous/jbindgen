//
// Created by nettal on 23-11-13.
//

#include "FunctionSymbolGenerator.h"

#include <utility>
#include <format>

namespace jbindgen {
    std::string FunctionSymbolGenerator::defaultHead(const std::string &className, const std::string &packageName,
                                                     std::string libName) {
        std::stringstream ss;
        //TODO: gen
        //
        //    private static SymbolLookup GlslangLibSymbols = null;
        //    private static SymbolLookup GlslangStructTableSymbols = null;
        //
        //    public static void registerLibSymbol(SymbolLookup symbol) {
        //        GlslangLibSymbols = symbol;
        //    }
        //
        //    public static void registerStructTableSymbol(SymbolLookup symbol) {
        //        GlslangStructTableSymbols = symbol;
        //    }
        //
        //    public static MethodHandle loadCFunction(String functionName, Class<?> rtype, Class<?>... ptypes) {
        //        return NativeFunction.getCHandle(GlslangLibSymbols, functionName, rtype, ptypes)
        //                .orElseThrow((Supplier<RuntimeException>) () -> new NativeFunction.FunctionNotFound(functionName));
        //    }
        ss <<
           "package " << packageName << ";\n"
                                        "\n"
                                        "\n"
                                        "import infinity.natives.glslang.struct.glslang_input_t;\n"
                                        "import infinity.natives.glslang.struct.glslang_spv_options_t;\n"
                                        "import infinity.natives.shared.NativeList;\n"
                                        "import infinity.natives.shared.Pointer;\n"
                                        "import infinity.natives.shared.types.NativeFunction;\n"
                                        "import infinity.natives.shared.types.NativeInteger;\n"
                                        "import infinity.natives.shared.types.NativeString;\n"
                                        "\n"
                                        "import java.lang.foreign.MemorySegment;\n"
                                        "import java.lang.invoke.MethodHandle;\n"
                                        "\n"
                                        "public final class " << className << " {";
        return ss.str();
    }

    std::string FunctionSymbolGenerator::defaultTail() {
        return "}";
    }

    FunctionSymbolGenerator::FunctionSymbolGenerator(const Analyser &analyser, PFN_makeFunction makeFunction,
                                                     std::string functionLoader, std::string header, std::string tail,
                                                     std::string dir,
                                                     std::vector<FunctionDeclaration> function_declarations,
                                                     std::string className)
            : makeFunction(makeFunction), functionLoader(std::move(functionLoader)), dir(std::move(dir)),
              function_declarations(std::move(function_declarations)), analyser(analyser),
              header(std::move(header)), tail(std::move(tail)), className(std::move(className)) {
    }

    void FunctionSymbolGenerator::build(void *userData) {
        std::stringstream ss;
        ss << header;
        for (const auto &functionDeclaration: function_declarations) {
            auto func = makeFunction(&functionDeclaration, analyser, userData);
            std::stringstream funcTypes;
            for (int i = 0; i < func.parameterDescriptors.size(); ++i) {
                std::string &descriptor = func.parameterDescriptors[i];
                funcTypes << (i == 0 ? "" : " ") << descriptor
                          << ((i == func.parameterDescriptors.size() - 1) ? "" : ",");
            }
            if (func.invokeParameters.empty()) {
                ss << makeCoreWithoutPara(func.hasResult, func.functionName, func.jResult, funcTypes.str()).str();
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
                ss << makeCoreWithPara(func.hasResult, func.functionName, func.jResult, jparas.str(), invpara.str(),
                                       funcTypes.str()).str();
            }
            for (const auto &wrapper: func.wrappers) {
                ss << makeWrapper(wrapper.jParameters, wrapper.decodeParameters, func.functionName, wrapper.wrapperName,
                                  wrapper.wrappedResult, func.hasResult);
            }

        }
        ss << tail;
        overwriteFile(dir + "/" + className + ".java", ss.str());
    }

    std::stringstream makeCoreWithoutPara(bool hasResult, const std::string &functionName, const std::string &jrtype,
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

    std::stringstream makeCoreWithPara(bool hasResult, const std::string &functionName, const std::string &jrtype,
                                       const std::string &paras, const std::string &paraNames,
                                       const std::string &functionDescriptor) {
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