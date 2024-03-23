//
// Created by snownf on 23-11-19.
//

#include "TypedefGeneratorUtils.h"
#include "Value.h"

#include <utility>

std::string jbindgen::TypedefGeneratorUtils::getFuncSymContent(std::vector<std::string> jParameters,
                                                               std::vector<std::string> functionDescriptors,
                                                               std::string className, bool hasResult,
                                                               const std::string &resultDescriptors,
                                                               std::string resultStr) {
    std::stringstream jPara;
    for (int i = 0; i < jParameters.size(); ++i) {
        std::string &para = jParameters[i];
        jPara << (i == 0 ? "" : " ") << para << ((i == jParameters.size() - 1) ? "" : ",");
    }
    std::stringstream fds;
    if (hasResult) {
        fds << resultDescriptors;
        if (!functionDescriptors.empty())
            fds << ", ";
    }
    for (int i = 0; i < functionDescriptors.size(); ++i) {
        std::string &fd = functionDescriptors[i];
        fds << (i == 0 ? "" : " ") << fd << ((i == functionDescriptors.size() - 1) ? "" : ",");
    }
    std::string returnStr = hasResult ? std::move(resultStr) : "void";
    const auto &fd = fds.str();
    std::string funcDesc = hasResult ? std::vformat("FunctionDescriptor.of({})", std::make_format_args(fd))
                                     : std::vformat("FunctionDescriptor.ofVoid({})", std::make_format_args(fd));
    const auto &jparaStr = jPara.str();
    std::string func = std::vformat(
            "@FunctionalInterface\n"
            "public interface {0} {{\n"
            "    {1} function({2});\n"
            "\n"
            "    default VPointer<{0}> toVPointer(Arena arena) {{\n"
            "        FunctionDescriptor functionDescriptor = {3};\n"
            "        try {{\n"
            "            return new VPointer<>(FunctionUtils.toMemorySegment(arena, MethodHandles.lookup().findVirtual({0}.class, \"function\", functionDescriptor.toMethodType()).bindTo(this) , functionDescriptor));\n"
            "        }} catch (NoSuchMethodException | IllegalAccessException e) {{\n"
            "            throw new FunctionUtils.SymbolNotFound(e);\n"
            "        }}\n"
            "   }}"
            "\n"
            "\n",
            std::make_format_args(className, returnStr, jparaStr, funcDesc));
    return func;
}

std::string jbindgen::TypedefGeneratorUtils::getOfPointerContent(std::string interfaceName, std::string returnName,
                                                                 const std::vector<std::string> &parentParameters,
                                                                 std::vector<std::string> functionDescriptors,
                                                                 bool hasResult, bool critical,
                                                                 std::string jResultType,
                                                                 std::vector<std::string> invokeParas) {
    std::stringstream parent;
    for (int i = 0; i < parentParameters.size(); ++i) {
        parent << (i == 0 ? "" : " ") << parentParameters[i] << ((i == parentParameters.size() - 1) ? "" : ",");
    }
    std::stringstream fds;
    for (int i = 0; i < functionDescriptors.size(); ++i) {
        std::string &fd = functionDescriptors[i];
        fds << (i == 0 ? "" : " ") << fd << ((i == functionDescriptors.size() - 1) ? "" : ",");
    }

    std::stringstream invokePara;
    for (int i = 0; i < invokeParas.size(); ++i) {
        std::string &para = invokeParas[i];
        invokePara << (i == 0 ? "" : " ") << para << ((i == invokeParas.size() - 1) ? "" : ",");
    }
    const auto &fd = fds.str();
    std::string funcDesc = hasResult ? std::vformat("FunctionDescriptor.of({})", std::make_format_args(fd))
                                     : std::vformat("FunctionDescriptor.ofVoid({})", std::make_format_args(fd));
    std::string allReturn = hasResult ? std::vformat("return ({}) ", std::make_format_args(jResultType)) : "";
    const auto &haveReturn = hasResult ? std::move(returnName) : "void";
    const auto &parentName = parent.str();
    const auto &criticalStr = critical ? "true" : "false";
    const auto &invokeStr = invokePara.str();
    return std::vformat("\n"
                        "    static {0} ofVPointer(VPointer<{0}> p) {{\n"
                        "        MethodHandle methodHandle = FunctionUtils.toMethodHandle(p.value(), FunctionDescriptor.ofVoid(), {4}).orElseThrow();\n"
                        "        return new {0}() {{\n"
                        "            @Override\n"
                        "            public {1} function({2}) {{\n"
                        "                try {{\n"
                        "                    {5}methodHandle.invokeExact({6});\n"
                        "                }} catch (Throwable e) {{\n"
                        "                    throw new FunctionUtils.InvokeException(e);\n"
                        "                }}\n"
                        "            }}\n"
                        "\n"
                        "            @Override\n"
                        "            public VPointer<{0}> toVPointer(Arena arena) {{\n"
                        "                return p;\n"
                        "            }}\n"
                        "        }};\n"
                        "    }}\n",
                        std::make_format_args(interfaceName, haveReturn,
                                              parentName, funcDesc,
                                              criticalStr, allReturn, invokeStr));
}

std::string jbindgen::TypedefGeneratorUtils::getFuncWrapperContent(std::vector<std::string> jParameters,
                                                                   const std::vector<std::string> &toLowerLevel,
                                                                   const std::vector<std::string> &toUpperLevel,
                                                                   const std::vector<std::string> &parentParameters,
                                                                   std::string className,
                                                                   std::string parentClassName, bool hasResult,
                                                                   std::string resultType,
                                                                   std::string parentResultType,
                                                                   std::string callFunctionWrapper) {
    std::stringstream jPara;
    for (int i = 0; i < jParameters.size(); ++i) {
        jPara << (i == 0 ? "" : " ") << jParameters[i] << ((i == jParameters.size() - 1) ? "" : ",");
    }
    std::stringstream lowers;
    for (int i = 0; i < toLowerLevel.size(); ++i) {
        lowers << (i == 0 ? "" : " ") << toLowerLevel[i] << ((i == toLowerLevel.size() - 1) ? "" : ",");
    }
    std::stringstream uppers;
    for (int i = 0; i < toUpperLevel.size(); ++i) {
        uppers << (i == 0 ? "" : " ") << toUpperLevel[i] << ((i == toUpperLevel.size() - 1) ? "" : ",");
    }
    std::stringstream parent;
    for (int i = 0; i < parentParameters.size(); ++i) {
        parent << (i == 0 ? "" : " ") << parentParameters[i] << ((i == parentParameters.size() - 1) ? "" : ",");
    }
    std::string returnStr = hasResult ? std::move(resultType) : "void";
    std::string parentReturnStr = hasResult ? std::move(parentResultType) : "void";
    const auto &jParaStr = jPara.str();
    const auto &parentName = parent.str();
    const auto &resultStr = hasResult ? "return " : "";
    const auto &lowerStr = lowers.str();
    const auto &shouldCallFunctionWrapper = hasResult ? std::move(callFunctionWrapper) : "";
    std::string func = std::vformat(
            "    @FunctionalInterface\n"
            "    interface {} extends {} {{\n"
            "        {} function({});\n"
            "\n"
            "        @Override\n"
            "        default {} function({}) {{\n"
            "            {}function({}){};\n"
            "        }}\n"
            "    }}\n",
            std::make_format_args(className, parentClassName, returnStr, jParaStr,
                                  parentReturnStr, parentName, resultStr,
                                  lowerStr, shouldCallFunctionWrapper));
    return func;
}