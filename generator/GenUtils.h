//
// Created by snownf on 23-11-9.
//

#ifndef JAVABINDGEN_GENUTILS_H
#define JAVABINDGEN_GENUTILS_H

#include <string>
#include "../analyser/StructDeclaration.h"
#include "Value.h"

namespace jbindgen {
    typedef std::string(*PFN_rename)(const std::string &name, void *pUserdata);

    typedef std::vector<Getter>(*PFN_decodeGetter)(const jbindgen::StructMember &structMember, void *pUserdata);

    typedef std::vector<Setter>(*PFN_decodeSetter)(const jbindgen::StructMember &structMember, void *pUserdata);

    class GenUtils {

    };

} // jbindgen

#endif //JAVABINDGEN_GENUTILS_H
