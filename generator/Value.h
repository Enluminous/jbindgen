//
// Created by snownf on 23-11-9.
//

#ifndef JAVABINDGEN_VALUE_H
#define JAVABINDGEN_VALUE_H

#include <string>
#include <sstream>
#include "../analyser/StructDeclaration.h"

#define NativeArray std::string("NativeArray")


namespace jbindgen {
    enum basic_j_type {
        j_int,
        j_long,
        j_float,
        j_double,
        j_char,
        j_bool,
        j_byte,
        j_void,
        type_other
    };

    struct FFMType {
        enum basic_j_type type;
        std::string primitive;
        std::string value_layout;
        std::string native_wrapper;
    };
    const FFMType Integer{j_int, "int", "JAVA_INT", "JInt"};
    const FFMType Long{j_long, "long", "JAVA_LONG", "JLong"};
    const FFMType Double{j_double, "double", "JAVA_DOUBLE", "JDouble"};
    const FFMType Float{j_float, "float", "JAVA_FLOAT", "JFloat"};
    const FFMType Char{j_char, "char", "JAVA_CHAR", "JChar"};
    const FFMType Byte{j_byte, "byte", "JAVA_BYTE", "JByte"};
    const FFMType Bool{j_bool, "boolean", "JAVA_BOOLEAN", "JBoolean"};
    const FFMType Function_ptr{type_other, "MemorySegment", "ADDRESS", ""};
    const FFMType Any_ptr{type_other, "MemorySegment", "ADDRESS"};


    const FFMType Not{""};

    enum decode_method {
        decode_by_value_call = 1,
        decode_by_primitive,
        decode_by_pointer_call,
        decode_error = -1
    };
    enum copy_method {
        copy_by_set_j_int_call = 1,
        copy_by_set_j_long_call,
        copy_by_set_j_float_call,
        copy_by_set_j_double_call,
        copy_by_set_j_char_call,
        copy_by_set_j_byte_call,
        copy_by_set_j_bool_call,
        copy_by_set_memory_segment_call,
        copy_by_value_j_int_call,
        copy_by_value_j_long_call,
        copy_by_value_j_float_call,
        copy_by_value_j_double_call,
        copy_by_value_j_char_call,
        copy_by_value_j_byte_call,
        copy_by_value_j_bool_call,
        copy_by_value_memory_segment_call,
        copy_by_ptr_dest_copy_call,
        copy_error = INT32_MIN,
        copy_void,
        copy_internal_function_proto,
    };
    enum encode_method {
        encode_by_get_j_int_call = 1,
        encode_by_get_j_long_call,
        encode_by_get_j_float_call,
        encode_by_get_j_double_call,
        encode_by_get_j_char_call,
        encode_by_get_j_byte_call,
        encode_by_get_j_bool_call,
        encode_by_get_memory_segment_call,
        encode_by_object_slice,//memorysegment.asSlice(,)
        encode_by_object_ptr,//memorysegment.get(ValueLayout.ADDRESS,)
        encode_by_array_ptr,
        encode_error = INT32_MIN,
        encode_by_void,//function return void
        encode_internal_function_proto,
    };

    int TypeDecode(const CXType &declare, const CXCursor &cursor);

    int TypeCopy(const CXType &declare, const CXCursor &cursor);

    int TypeEncode(const CXType &declare);

    int ClassType(const CXType &declare, const CXCursor &cursor);
} // jbindgen

#endif //JAVABINDGEN_VALUE_H
