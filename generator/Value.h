//
// Created by snownf on 23-11-9.
//

#ifndef JAVABINDGEN_VALUE_H
#define JAVABINDGEN_VALUE_H

#include <string>
#include <sstream>
#include "../analyser/StructDeclaration.h"

namespace jbindgen {

    typedef std::string (*PFN_ObjCreator_Make)(std::string ptr, Member member);

    struct Getter {
        std::string returnTypeName;
        std::string parameterString;
        PFN_ObjCreator_Make creator_make;
    };
    struct Setter {
        std::string parameterString;
        PFN_ObjCreator_Make creator_make;
    };
} // jbindgen

#endif //JAVABINDGEN_VALUE_H
