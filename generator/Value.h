//
// Created by snownf on 23-11-9.
//

#ifndef JBINDGEN_VALUE_H
#define JBINDGEN_VALUE_H

#include <string>
#include <sstream>
#include "../analyser/StructDeclaration.h"

#ifndef NATIVE_UNSUPPORTED
#define NATIVE_UNSUPPORTED 0
#endif

#define NList std::string("NList")
#define VList std::string("VList")
#define MEMORY_LAYOUT std::string("MEMORY_LAYOUT")
#define BYTE_SIZE std::string("BYTE_SIZE")

namespace jbindgen::value {
    namespace jbasic {
        enum basic_j_type {
            j_int,
            j_long,
            j_float,
            j_double,
#if NATIVE_UNSUPPORTED
            j_char,
            j_bool,
#endif
            j_byte,
            j_short,
            j_void,
            type_other
        };

        enum basic_j_type convert_2_j_type(const CXType &declare);

        class NativeType {
            const char *primitive_;
            const char *value_layout_;
            const char *native_wrapper_;
            const char *objectPrimitiveName_;

        public:
            enum basic_j_type type;
            int byteSize;

            constexpr NativeType(enum basic_j_type basicJavaType, int byteSize, const char *primitiveName,
                                 const char *objectPrimitiveName,
                                 const char *valueLayoutString,
                                 const char *wrapper) : type(basicJavaType),
                                                        byteSize(byteSize),
                                                        primitive_(primitiveName),
                                                        value_layout_(valueLayoutString),
                                                        native_wrapper_(wrapper),
                                                        objectPrimitiveName_(objectPrimitiveName) {
            }

            [[nodiscard]] std::string primitive() const {
                return primitive_;
            }

            [[nodiscard]] std::string value_layout() const {
                return value_layout_;
            }

            [[nodiscard]] std::string wrapper() const {
                return native_wrapper_;
            }

            [[nodiscard]] std::string objectPrimitiveName() const {
                return objectPrimitiveName_;
            }
        };

        constexpr auto NOT_AVAILABLE = "###";
        constexpr NativeType Integer{j_int, 4, "int", "Integer", "ValueLayout.JAVA_INT", "NI32"};
        constexpr NativeType Long{j_long, 8, "long", "Long", "ValueLayout.JAVA_LONG", "NI64"};
        constexpr NativeType Double{j_double, 8, "double", "Double", "ValueLayout.JAVA_DOUBLE", "NFP64"};
        constexpr NativeType Float{j_float, 4, "float", "Float", "ValueLayout.JAVA_FLOAT", "NFP32"};
#if NATIVE_UNSUPPORTED
        constexpr NativeType Char{j_char, 2, "char", "Character", "ValueLayout.JAVA_CHAR", "NC16"};
        constexpr NativeType Bool{j_bool, 1, "boolean", "Boolean", "ValueLayout.JAVA_BOOLEAN", "NI8"};
#endif
        constexpr NativeType Byte{j_byte, 1, "byte", "Byte", "ValueLayout.JAVA_BYTE", "NI8"};
        constexpr NativeType Short{j_short, 2, "short", "Short", "ValueLayout.JAVA_SHORT", "NI16"};
        constexpr NativeType Void{j_void, 0, "void", NOT_AVAILABLE, NOT_AVAILABLE, NOT_AVAILABLE};
        constexpr NativeType Other{type_other, 0, NOT_AVAILABLE, NOT_AVAILABLE, NOT_AVAILABLE, NOT_AVAILABLE};

        NativeType j_type_2_ffm_type(enum basic_j_type jType);

        enum basic_j_type valueBasedCXType2J(const CXType &cxType);

        class ValueType {
            const char *primitive_;
            const char *objectPrimitiveName_;
            const char *wrapper_;
            const char *value_layout_;

        public:
            enum basic_j_type type;
            int byteSize;

            constexpr ValueType(enum basic_j_type basicJavaType, int byteSize, const char *primitiveName,
                                const char *objectPrimitiveName,
                                const char *valueLayoutString,
                                const char *nativeJavaGlue) : type(basicJavaType),
                                                              byteSize(byteSize),
                                                              primitive_(primitiveName),
                                                              wrapper_(nativeJavaGlue),
                                                              value_layout_(valueLayoutString),
                                                              objectPrimitiveName_(objectPrimitiveName) {
            }

            [[nodiscard]] std::string primitive() const {
                return primitive_;
            }

            [[nodiscard]] std::string wrapper() const {
                return wrapper_;
            }

            [[nodiscard]] std::string value_layout() const {
                return value_layout_;
            }

            [[nodiscard]] std::string objectPrimitiveName() const {
                return objectPrimitiveName_;
            }
        };

        constexpr ValueType VInteger{j_int, 4, "int", "Integer", "ValueLayout.JAVA_INT", "VI32"};
        constexpr ValueType VLong{j_long, 8, "long", "Long", "ValueLayout.JAVA_LONG", "VI64"};
        constexpr ValueType VDouble{j_double, 8, "double", "Double", "ValueLayout.JAVA_DOUBLE", "VFP64"};
        constexpr ValueType VFloat{j_float, 4, "float", "Float", "ValueLayout.JAVA_FLOAT", "VFP32"};
#if NATIVE_UNSUPPORTED
        constexpr ValueType VChar{j_char, 2, "char", "Character", "ValueLayout.JAVA_CHAR", "NC16"};
        constexpr ValueType VBool{j_bool, 1, "boolean", "Boolean", "ValueLayout.JAVA_BOOLEAN", "NI8"};
#endif
        constexpr ValueType VByte{j_byte, 1, "byte", "Byte", "ValueLayout.JAVA_BYTE", "VI8"};
        constexpr ValueType VShort{j_short, 2, "short", "Short", "ValueLayout.JAVA_SHORT", "VI16"};
        constexpr ValueType VVoid{j_void, 0, "void", NOT_AVAILABLE, NOT_AVAILABLE, "VVoid"};
        constexpr ValueType VOther{type_other, 0, NOT_AVAILABLE, NOT_AVAILABLE, NOT_AVAILABLE, NOT_AVAILABLE};
    }

    std::string makeVList(jbasic::ValueType type);

    std::string makeVList(const std::string &valueName, jbasic::ValueType valueType);

    std::string makePointer(const std::string &type);

    namespace jext {
        using jbasic::NOT_AVAILABLE;
        constexpr jbasic::NativeType Pointer{jbasic::type_other, 8, "MemorySegment", NOT_AVAILABLE,
                                             "ValueLayout.ADDRESS",
                                             "NPointer"};
        constexpr jbasic::ValueType VPointer{jbasic::type_other, 8, "MemorySegment", NOT_AVAILABLE,
                                             "ValueLayout.ADDRESS",
                                             "VPointer"};

        constexpr jbasic::NativeType String{jbasic::type_other, 8, "String", NOT_AVAILABLE, NOT_AVAILABLE, "NString"};

        enum ext_type {
            ext_int128,
            ext_long_double,
            type_other
        };

        enum ext_type convert_2_ext(const CXType &declare);

        struct ExtType {
            constexpr ExtType(ext_type t, int b, const char *n) : type(t), byteSize(b), native_wrapper(n) {
            }

            enum ext_type type;
            int byteSize;
            std::string native_wrapper;
        };


        constexpr ExtType EXT_OTHER{type_other, 0, NOT_AVAILABLE};
        constexpr ExtType EXT_LONG_DOUBLE{ext_long_double, 16, "NFP128"};
        constexpr ExtType EXT_INT_128{ext_int128, 16, "NI128"};
    }

    namespace method {
        enum copy_method {
            copy_by_set_j_int_call = 1,
            copy_by_set_j_long_call,
            copy_by_set_j_float_call,
            copy_by_set_j_double_call,
#if NATIVE_UNSUPPORTED
            copy_by_set_j_char_call,
            copy_by_set_j_bool_call,
#endif
            copy_by_set_j_short_call,
            copy_by_set_j_byte_call,
            //translate to Pointer<?>
            copy_by_set_memory_segment_call,
            copy_by_value_j_int_call,
            copy_by_value_j_long_call,
            copy_by_value_j_float_call,
            copy_by_value_j_double_call,
            copy_by_value_j_short_call,
            copy_by_value_j_byte_call,
#if NATIVE_UNSUPPORTED
            copy_by_value_j_char_call,
            copy_by_value_j_bool_call,
#endif
            //translate to Value<MemorySegment>
            copy_by_value_memory_segment_call,
            //for array
            copy_by_array_call,//dest copy like
            //for struct
            copy_by_ptr_dest_copy_call,
            //for pointer
            copy_by_ptr_copy_call,
            //ext
            copy_by_ext_int128_call,
            copy_by_ext_long_double_call,
            //for pfn
            copy_by_ptr_function_proto_type_call,
            //typedef void type etc.
            copy_by_ptr_no_target_type_call,
            //error
            copy_error = INT32_MIN,
            copy_void,
            copy_target_void,
            copy_internal_function_proto,
        };

        jbasic::NativeType copy_method_2_native_type(enum copy_method copyMethod);

        jbasic::ValueType native_type_2_value_type(jbasic::NativeType valueType);

        jbasic::ValueType copy_method_2_value_type(enum copy_method copyMethod);

        jext::ExtType copy_method_2_ext_type(enum copy_method copyMethod);

        enum copy_method typeCopy(const CXType &declare);
    }
}

// jbindgen

#endif //JBINDGEN_VALUE_H
