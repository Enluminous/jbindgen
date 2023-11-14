//
// Created by snownf on 23-11-9.
//

#ifndef JAVABINDGEN_GENUTILS_H
#define JAVABINDGEN_GENUTILS_H

#include <string>
#include "../analyser/StructDeclaration.h"
#include "Value.h"
#include "../analyser/FunctionDeclaration.h"

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

    typedef std::vector<Getter>(*PFN_decodeGetter)(const jbindgen::StructMember &structMember,
                                                   const std::string &ptrName, void *pUserdata);

    typedef std::vector<Setter>(*PFN_decodeSetter)(const jbindgen::StructMember &structMember,
                                                   const std::string &ptrName, void *pUserdata);

    struct FunctionWrapperInfo {
        std::vector<std::string> jParameters;
        std::vector<std::string> targetParameters;
    };

    struct FunctionInfo {
        std::string functionName;
        std::vector<std::string> jParameters;
        std::vector<std::string> functionDescriptors;
        std::vector<std::string> invokeParameters;
        std::vector<FunctionWrapperInfo> wrappers;
        std::string resultDescriptor;
        std::string jResult;
        bool hasResult;
    };

    typedef FunctionInfo(*PFN_makeFunction)(const jbindgen::FunctionDeclaration declaration, void *pUserdata);

    void overwriteFile(const std::string &file, const std::string &content);

} // jbindgen

#endif //JAVABINDGEN_GENUTILS_H
