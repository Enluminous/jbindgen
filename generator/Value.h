//
// Created by snownf on 23-11-9.
//

#ifndef JAVABINDGEN_VALUE_H
#define JAVABINDGEN_VALUE_H

#include <string>
#include <sstream>
#include "../analyser/StructDeclaration.h"

#define NativeArray std::string("NativeArray")
#define NativePointer std::string("NativePointer")
#define NativeValue std::string("NativeValue")


namespace jbindgen::value {
    namespace jbasic {
        enum basic_j_type {
            j_int,
            j_long,
            j_float,
            j_double,
            j_char,
            j_bool,
            j_byte,
            j_short,
            j_void,
            type_other
        };

        struct FFMType {
            enum basic_j_type type;
            std::string primitive;
            std::string value_layout;
            std::string native_wrapper;
        };

        const FFMType Integer{j_int, "int", "ValueLayout.JAVA_INT", "JInt"};
        const FFMType Long{j_long, "long", "ValueLayout.JAVA_LONG", "JLong"};
        const FFMType Double{j_double, "double", "ValueLayout.JAVA_DOUBLE", "JDouble"};
        const FFMType Float{j_float, "float", "ValueLayout.JAVA_FLOAT", "JFloat"};
        const FFMType Char{j_char, "char", "ValueLayout.JAVA_CHAR", "JChar"};
        const FFMType Byte{j_byte, "byte", "ValueLayout.JAVA_BYTE", "JByte"};
        const FFMType Bool{j_bool, "boolean", "ValueLayout.JAVA_BOOLEAN", "JBoolean"};
        const FFMType Short{j_short, "short", "ValueLayout.JAVA_SHORT", "JShort"};
        const FFMType Void{j_void, "void", "###", "###"};
        const FFMType Not{type_other, "###", "###", "###"};
    }

    namespace jext {
        enum ext_type {
            ext_int128,
            ext_long_double,
            type_other
        };

        struct ExtType {
            enum ext_type type;
            std::string native_wrapper;
        };

        const ExtType EXT_OTHER{type_other, "###"};
        const ExtType EXT_LONG_DOUBLE{type_other, "LongDouble"};
        const ExtType EXT_INT_128{type_other, "Int128"};
    }

    namespace method {
        enum decode_method {
            decode_by_value_call = 1,
            decode_by_primitive,
            decode_by_pointer_call,
            decode_error = INT32_MIN
        };

        enum copy_method {
            copy_by_set_j_int_call = 1,
            copy_by_set_j_long_call,
            copy_by_set_j_float_call,
            copy_by_set_j_double_call,
            copy_by_set_j_char_call,
            copy_by_set_j_short_call,
            copy_by_set_j_byte_call,
            copy_by_set_j_bool_call,
            //translate to Pointer<?>
            copy_by_set_memory_segment_call,
            copy_by_value_j_int_call,
            copy_by_value_j_long_call,
            copy_by_value_j_float_call,
            copy_by_value_j_double_call,
            copy_by_value_j_char_call,
            copy_by_value_j_byte_call,
            copy_by_value_j_bool_call,
            //translate to Pointer<?>
            copy_by_value_memory_segment_call,
            //for array
            copy_by_array_call,
            //for struct
            copy_by_ptr_dest_copy_call,
            //for pointer
            copy_by_ptr_copy_call,
            //ext
            copy_by_ext_int128_call,
            copy_by_ext_long_double_call,
            //error
            copy_error = INT32_MIN,
            copy_void,
            copy_internal_function_proto,
        };

        bool copy_method_is_value(enum copy_method copy_method);

        jbasic::FFMType copy_method_2_ffm_type(enum copy_method copyMethod);
        jext::ExtType copy_method_2_ext_type(enum copy_method copyMethod);

        enum encode_method {
            encode_by_get_j_int_call = 1,
            encode_by_get_j_long_call,
            encode_by_get_j_float_call,
            encode_by_get_j_double_call,
            encode_by_get_j_short_call,
            encode_by_get_j_char_call,
            encode_by_get_j_byte_call,
            encode_by_get_j_bool_call,
            encode_by_get_memory_segment_call,
            //translate to Pointer<?>
            encode_by_object_slice_call,
            encode_by_object_ptr_call,
            encode_by_array_slice_call,
            //ext
            encode_by_ext_int128_call,
            encode_by_ext_long_double_call,
            //error
            encode_error = INT32_MIN,
            encode_by_void,
            //function return void
            encode_internal_function_proto,
        };

        jbasic::FFMType encode_method_2_ffm_type(enum encode_method encodeMethod);
        jext::ExtType encode_method_2_ext_type(enum encode_method encodeMethod);

        enum decode_method typeDecode(const CXType&declare, const CXCursor&cursor);

        enum copy_method typeCopy(const CXType&declare, const CXCursor&cursor);

        enum encode_method typeEncode(const CXType&declare);
    }
}

// jbindgen

#endif //JAVABINDGEN_VALUE_H
