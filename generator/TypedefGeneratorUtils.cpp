//
// Created by snownf on 23-11-19.
//

#include "TypedefGeneratorUtils.h"
#include "Value.h"

#include <utility>

std::string jbindgen::TypedefGeneratorUtils::GenFuncSym(std::vector<std::string> jParameters,
                                                        std::vector<std::string> functionDescriptors,
                                                        std::string className,
                                                        bool hasResult, std::string resultStr) {
    std::stringstream jPara;
    for (int i = 0; i < jParameters.size(); ++i) {
        std::string &para = jParameters[i];
        jPara << (i == 0 ? "" : " ") << para << ((i == jParameters.size() - 1) ? "" : ",");
    }
    std::stringstream fds;
    for (int i = 0; i < functionDescriptors.size(); ++i) {
        std::string &fd = functionDescriptors[i];
        fds << (i == 0 ? "" : " ") << fd << ((i == functionDescriptors.size() - 1) ? "" : ",");
    }
    std::string returnStr = hasResult ? std::move(resultStr) : "void";
    std::string func = std::vformat(
            "@FunctionalInterface\n"
            "public interface {} {{\n"
            "    {} function({});\n"
            "\n"
            "    default Pointer<{}> toPointer(Arena arena) {{\n"
            "        return new Pointer<>() {{\n"
            "            @Override\n"
            "            public MemorySegment pointer() {{\n"
            "                return (FunctionUtils.toMemorySegment(MethodHandles.lookup(), arena, FunctionDescriptor.of({}), this, \"function\"));\n"
            "            }}\n"
            "        }};\n"
            "    }}\n"
            "\n",
            std::make_format_args(className, returnStr, jPara.str(), className, fds.str()));
    return func;
}

std::string jbindgen::TypedefGeneratorUtils::GenFuncWrapper(std::vector<std::string> jParameters,
                                                            const std::vector<std::string> &toLowerLevel,
                                                            const std::vector<std::string> &toUpperLevel,
                                                            const std::vector<std::string> &parentParameters,
                                                            std::string className,
                                                            std::string parentClassName, bool hasResult,
                                                            std::string resultType,
                                                            std::string parentResultType) {
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
    std::string func = std::vformat(
            "    @FunctionalInterface\n"
            "    interface {} extends {} {{\n"
            "        {} function({});\n"
            "\n"
            "        @Override\n"
            "        default {} function({}) {{\n"
            "            {}function({});\n"
            "        }}\n"
            "    }}\n",
            std::make_format_args(className, parentClassName, returnStr, jPara.str(),
                                  parentReturnStr, parent.str(), hasResult ? "return " : "", lowers.str()));
    return func;
}