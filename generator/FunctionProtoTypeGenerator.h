//
// Created by nettal on 23-11-13.
//

#ifndef JAVABINDGEN_FUNCTIONPROTOTYPEGENERATOR_H
#define JAVABINDGEN_FUNCTIONPROTOTYPEGENERATOR_H

#include <utility>

#include "../analyser/FunctionProtoTypeDeclaration.h"
#include "GenUtils.h"

namespace jbindgen {
    struct FunctionProtoTypeWrapperInfo {
        std::string wrapperClassName;
        std::string wrapperName;
        std::vector<std::string> jParameters;
        std::vector<std::string> encodeParameters;
        std::vector<std::string> decodeParameters;
        std::string resultDescriptor;//optional, depend on hasResult
        std::string jResult;//optional, depend on hasResult
    };

    struct FunctionProtoTypeInfo {
        std::string functionName;
        std::string className;
        std::vector<std::string> jParameters;
        std::vector<std::string> functionDescriptors;
        std::vector<std::string> invokeParameters;
        std::vector<FunctionProtoTypeWrapperInfo> wrappers;
        std::string resultDescriptor;
        std::string jResult;
        bool hasResult;
    };


    typedef FunctionProtoTypeInfo(*PFN_makeProtoType)(const jbindgen::FunctionTypedefDeclaration declaration,
                                                      void *pUserdata);

    class FunctionProtoTypeGenerator {
        const FunctionTypedefDeclaration declaration;
        const std::string dir;
        const PFN_makeProtoType makeProtoType;
    public:
        FunctionProtoTypeGenerator(FunctionTypedefDeclaration declaration, std::string dir,
                                   PFN_makeProtoType makeProtoType);

        void build(void *userData) {
            auto function = makeProtoType(declaration, userData);
        }
    };

} // jbindgen


#endif //JAVABINDGEN_FUNCTIONPROTOTYPEGENERATOR_H
