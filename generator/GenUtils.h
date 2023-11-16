//
// Created by snownf on 23-11-9.
//

#ifndef JAVABINDGEN_GENUTILS_H
#define JAVABINDGEN_GENUTILS_H

#include <string>
#include "../analyser/StructDeclaration.h"
#include "Value.h"
#include "../analyser/FunctionSymbolDeclaration.h"
#include "../analyser/FunctionProtoTypeDeclaration.h"

namespace jbindgen {
    struct Getter {
        std::string returnTypeName;
        std::string parameterString;
        std::string creator;
    };
    struct Setter {
        std::string parameterString;
        std::string creator;
    };

    typedef std::string(*PFN_rename)(const std::string &name, void *pUserdata);

    typedef std::string(*PFN_structName)(const StructDeclaration &declaration,
                                         void *pUserdata);

    typedef std::string(*PFN_structMemberName)(const StructDeclaration &declaration,
                                               const StructMember &member, void *pUserdata);

    typedef std::vector<Getter>(*PFN_decodeGetter)(const jbindgen::StructMember &structMember,
                                                   const std::string &ptrName, void *pUserdata);

    typedef std::vector<Setter>(*PFN_decodeSetter)(const jbindgen::StructMember &structMember,
                                                   const std::string &ptrName, void *pUserdata);

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
    };

    typedef FunctionSymbolInfo(*PFN_makeFunction)(const jbindgen::FunctionDeclaration* declaration, void *pUserdata);


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

    void overwriteFile(const std::string &file, const std::string &content);

} // jbindgen

#endif //JAVABINDGEN_GENUTILS_H
