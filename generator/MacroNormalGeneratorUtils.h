//
// Created by snownf on 23-11-29.
//

#ifndef JAVABINDGEN_MACRONORMALGENERATORUTILS_H
#define JAVABINDGEN_MACRONORMALGENERATORUTILS_H

#include <string>
#include <utility>
#include <vector>
#include <cstring>
#include "../analyser/NormalMacroDeclaration.h"
#include "./Value.h"

namespace jbindgen {
    class JTypeWithValue {

    public:
        JTypeWithValue(std::string jtype, std::string value) : jtype(std::move(jtype)), value(std::move(value)) {}

        std::string jtype;
        std::string value;
    };

    class MacroNormalGeneratorUtils {
    public:
        static JTypeWithValue getJTypeWithValue(const std::string &string);

        static std::string defaultMakeMacro(const NormalMacroDeclaration &declaration);
    };

} // jbindgen

#endif //JAVABINDGEN_MACRONORMALGENERATORUTILS_H
