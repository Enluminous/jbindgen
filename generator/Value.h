//
// Created by snownf on 23-11-9.
//

#ifndef JAVABINDGEN_VALUE_H
#define JAVABINDGEN_VALUE_H

#include <string>
#include <sstream>
#include "../analyser/StructDeclaration.h"

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
} // jbindgen

#endif //JAVABINDGEN_VALUE_H
