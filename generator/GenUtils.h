//
// Created by snownf on 23-11-9.
//

#ifndef JAVABINDGEN_GENUTILS_H
#define JAVABINDGEN_GENUTILS_H

#include <string>
#include "../analyser/StructDeclaration.h"
#include "Value.h"

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

    void overwriteFile(const std::string &file, const std::string &content);

} // jbindgen

#endif //JAVABINDGEN_GENUTILS_H
