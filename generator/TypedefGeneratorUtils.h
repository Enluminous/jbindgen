//
// Created by snownf on 23-11-19.
//

#ifndef JBINDGEN_TYPEDEFGENERATORUTILS_H
#define JBINDGEN_TYPEDEFGENERATORUTILS_H

#include "TypedefGenerator.h"
#include <string>

namespace jbindgen {
    class TypedefGeneratorUtils {

    public:

        static std::string
        getFuncSymContent(std::vector<std::string> jParameters, std::vector<std::string> functionDescriptors,
                          std::string className, bool hasResult, const std::string& resultDescriptors,
                          std::string resultStr);

        static std::string
        getFuncWrapperContent(std::vector<std::string> jParameters, const std::vector<std::string> &toLowerLevel,
                              const std::vector<std::string> &toUpperLevel,
                              const std::vector<std::string> &parentParameters, std::string className,
                              std::string parentClassName, bool hasResult, std::string resultType,
                              std::string parentResultType,
                              std::string callFunctionWrapper);

        static std::string
        getOfPointerContent(std::string interfaceName, std::string returnName,
                            const std::vector<std::string> &parentParameters,
                            std::vector<std::string> functionDescriptors, bool hasResult, bool critical,
                            std::string jResultType, std::vector<std::string> invokeParas);
    };
}

#endif //JBINDGEN_TYPEDEFGENERATORUTILS_H
