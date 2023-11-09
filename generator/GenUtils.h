//
// Created by snownf on 23-11-9.
//

#ifndef JAVABINDGEN_GENUTILS_H
#define JAVABINDGEN_GENUTILS_H

#include <string>
#include "../analyser/StructDeclaration.h"
#include "Value.h"

namespace jbindgen {
    typedef std::string(*FPN_structRename)(std::string);

    typedef std::string(*FPN_memberRename)(std::string);

    typedef std::vector<Getter>(*FPN_decodeGetter)(jbindgen::Member);

    typedef std::vector<Setter>(*FPN_decodeSetter)(jbindgen::Member);

    class GenUtils {

    };

} // jbindgen

#endif //JAVABINDGEN_GENUTILS_H
