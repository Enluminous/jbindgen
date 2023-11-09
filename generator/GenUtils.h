//
// Created by snownf on 23-11-9.
//

#ifndef JAVABINDGEN_GENUTILS_H
#define JAVABINDGEN_GENUTILS_H

#include <string>
#include "../analyser/StructDeclaration.h"
#include "Value.h"

namespace jbindgen {
    typedef std::string(*PFN_rename)(std::string);

    typedef std::vector<Getter>(*PFN_decodeGetter)(jbindgen::Member);

    typedef std::vector<Setter>(*PFN_decodeSetter)(jbindgen::Member);

    class GenUtils {

    };

} // jbindgen

#endif //JAVABINDGEN_GENUTILS_H
