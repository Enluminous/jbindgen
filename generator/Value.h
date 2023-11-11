//
// Created by snownf on 23-11-9.
//

#ifndef JAVABINDGEN_VALUE_H
#define JAVABINDGEN_VALUE_H

#include <string>
#include <sstream>
#include "../analyser/StructDeclaration.h"

namespace jbindgen {
    struct FFMType {
        std::string primitive;
        std::string value_layout;
        std::string native_wrapper;
    };
    constexpr int type_func_ptr = 3;
    constexpr int type_integer = 2;
    constexpr int type_any_pointer = 1;
    constexpr int type_is_void = 0;
    constexpr int type_is_pointer = -2;
    constexpr int type_is_struct = -3;
    constexpr int type_is_array = -4;
    const FFMType Integer{"int", "JAVA_INT", "JInteger"};
    const FFMType Function_ptr{"MemorySegment"};
    const FFMType Any_ptr{"MemorySegment"};
    const FFMType Not{""};

    FFMType isFFM_RawType(const CXType &declare, const CXCursor &cursor, int *type);
} // jbindgen

#endif //JAVABINDGEN_VALUE_H
