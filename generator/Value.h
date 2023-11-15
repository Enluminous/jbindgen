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
#define JString std::string("JString")

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

        class FFMType {
            const char* primitive_;
            const char* value_layout_;
            const char* native_wrapper_;

        public:
            enum basic_j_type type;
            int byteSize;

            constexpr FFMType(enum basic_j_type type, int s, const char* n, const char* v, const char* t) : type(type),
                byteSize(s),
                primitive_(n), value_layout_(v), native_wrapper_(t) {
            }

            [[nodiscard]] std::string primitive() const {
                return primitive_;
            }
            [[nodiscard]] std::string value_layout() const {
                return value_layout_;
            }

            [[nodiscard]] std::string native_wrapper() const {
                return native_wrapper_;
            }
        };

        constexpr FFMType Integer{j_int, 4, "int", "ValueLayout.JAVA_INT", "JInt"};
        constexpr FFMType Long{j_long, 8, "long", "ValueLayout.JAVA_LONG", "JLong"};
        constexpr FFMType Double{j_double, 8, "double", "ValueLayout.JAVA_DOUBLE", "JDouble"};
        constexpr FFMType Float{j_float, 4, "float", "ValueLayout.JAVA_FLOAT", "JFloat"};
        constexpr FFMType Char{j_char, 4, "char", "ValueLayout.JAVA_CHAR", "JChar"};
        constexpr FFMType Byte{j_byte, 1, "byte", "ValueLayout.JAVA_BYTE", "JByte"};
        constexpr FFMType Bool{j_bool, 1, "boolean", "ValueLayout.JAVA_BOOLEAN", "JBoolean"};
        constexpr FFMType Short{j_short, 2, "short", "ValueLayout.JAVA_SHORT", "JShort"};
        constexpr FFMType Void{j_void, 0, "void", "###", "###"};
        constexpr FFMType Not{type_other, 0, "###", "###", "###"};
    }

    namespace jext {
        enum ext_type {
            ext_int128,
            ext_long_double,
            type_other
        };

        struct ExtType {
            constexpr ExtType(ext_type t, int b, const char* n) : type(t), byteSize(b), native_wrapper(n) {
            }
            enum ext_type type;
            int byteSize;
            std::string native_wrapper;
        };


        constexpr ExtType EXT_OTHER{type_other, 0, "###"};
        constexpr ExtType EXT_LONG_DOUBLE{ext_long_double, 16, "JLongDouble"};
        constexpr ExtType EXT_INT_128{ext_int128, 16, "JInt128"};
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
            copy_by_value_j_short_call,
            copy_by_value_j_byte_call,
            copy_by_value_j_bool_call,
            //translate to Value<MemorySegment>
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
            //translate to Pointer<?>
            encode_by_get_memory_segment_call,
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
