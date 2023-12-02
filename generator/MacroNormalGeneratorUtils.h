//
// Created by snownf on 23-11-29.
//

#ifndef JAVABINDGEN_MACRONORMALGENERATORUTILS_H
#define JAVABINDGEN_MACRONORMALGENERATORUTILS_H

#include <string>
#include <vector>
#include <cstring>
#include "../analyser/NormalMacroDeclaration.h"
#include "./Value.h"

namespace jbindgen {
    enum TYPE {
        T_UNKNOWN,
        T_EMPTY,
        T_STRING,
        T_INT,
        T_LONG,
        T_FLOAT,
        T_DOUBLE,
    };

    class TypeWithName {
    public:
        TYPE type;
        std::string jType;

        constexpr TypeWithName(TYPE type, const char *string) : type(type), jType(string) {}
    };

    constexpr TypeWithName Type_UNKNOWN{T_UNKNOWN, "UNKNOWN"};
    constexpr TypeWithName Type_STRING{T_STRING, "String"};
    constexpr TypeWithName Type_INT{T_INT, "int"};
    constexpr TypeWithName Type_Long{T_LONG, "long"};
    constexpr TypeWithName Type_Float{T_FLOAT, "float"};
    constexpr TypeWithName Type_Double{T_DOUBLE, "double"};
    constexpr TypeWithName Type_Empty{T_EMPTY, "empty"};

    class MacroNormalGeneratorUtils {
    public:
        static TypeWithName getType(const std::string &string);

        static TypeWithName
        lookupType(const std::vector<NormalMacroDeclaration> *allDeclaration, const std::string &type);

        static std::string defaultMakeMacro(const jbindgen::NormalMacroDeclaration &declaration,
                                            const std::vector<NormalMacroDeclaration> *allDeclaration);
    };

} // jbindgen

#endif //JAVABINDGEN_MACRONORMALGENERATORUTILS_H
