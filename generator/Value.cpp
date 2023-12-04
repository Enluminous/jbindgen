//
// Created by snownf on 23-11-9.
//

#include <cassert>
#include <iostream>
#include "Value.h"

#include "GenUtils.h"

namespace jbindgen::value {
    namespace method {
        enum decode_method typeDecode(const CXType &declare) {
            auto result = typeCopy(declare);
            switch (result) {
                case copy_by_set_j_bool_call:
                case copy_by_set_j_int_call:
                case copy_by_set_j_long_call:
                case copy_by_set_j_byte_call:
                case copy_by_set_j_double_call:
                case copy_by_set_j_float_call:
                case copy_by_set_j_short_call:
                case copy_by_set_j_char_call:
                    return decode_by_primitive;
                case copy_by_ptr_dest_copy_call:
                case copy_by_ptr_copy_call:
                case copy_by_set_memory_segment_call:
                case copy_by_array_call:
                case copy_by_ext_int128_call:
                case copy_by_ext_long_double_call:
                    return decode_by_pointer_call;
                case copy_by_value_j_int_call:
                case copy_by_value_j_bool_call:
                case copy_by_value_j_float_call:
                case copy_by_value_j_double_call:
                case copy_by_value_j_long_call:
                case copy_by_value_j_char_call:
                case copy_by_value_j_short_call:
                case copy_by_value_j_byte_call:
                case copy_by_value_memory_segment_call:
                    return decode_by_value_call;
                case copy_error:
                case copy_void:
                case copy_internal_function_proto:
                    return decode_error;
            }
            assert(0);
        }
    }

    namespace jbasic {
        enum basic_j_type convert_2_j_type(const CXType &declare) {
            auto type_kind = declare.kind;
            auto sizeOf = clang_Type_getSizeOf(declare);
            //j types
            if (type_kind == CXType_UChar) {
                switch (sizeOf) {
                    case Byte.byteSize:
                        return Byte.type;
                    default:
                        assert(0);
                }
            }
            if (type_kind == CXType_SChar) {
                switch (sizeOf) {
                    case Byte.byteSize:
                        return Byte.type;
                    default:
                        assert(0);
                }
            }

            if (type_kind == CXType_Char_S) {
                switch (sizeOf) {
                    case Byte.byteSize:
                        return Byte.type;
                    default:
                        assert(0);
                }
            }
            if (type_kind == CXType_WChar) {
                switch (sizeOf) {
                    case Byte.byteSize:
                        return Byte.type;
                    case Short.byteSize:
                        return Short.type;
                    case Integer.byteSize:
                        return Integer.type;
                    default:
                        assert(0);
                }
            }
            if (type_kind == CXType_Short || type_kind == CXType_UShort) {
                switch (sizeOf) {
                    case Short.byteSize:
                        return Short.type;
                    default:
                        assert(0);
                }
            }
            if (type_kind == CXType_Int || type_kind == CXType_UInt) {
                switch (sizeOf) {
                    case Integer.byteSize:
                        return Integer.type;
                    case Short.byteSize:
                        return Short.type;
                    case Long.byteSize:
                        return Long.type;
                    default:
                        assert(0);
                }
            }
            if (type_kind == CXType_Long || type_kind == CXType_ULong) {
                switch (sizeOf) {
                    case Integer.byteSize:
                        return Integer.type;
                    case Short.byteSize:
                        return Short.type;
                    case Long.byteSize:
                        return Long.type;
                    case jext::EXT_INT_128.byteSize:
                        return Not.type;
                    default:
                        assert(0);
                }
            }
            if (type_kind == CXType_LongLong || type_kind == CXType_ULongLong) {
                switch (sizeOf) {
                    case Integer.byteSize:
                        return Integer.type;
                    case Short.byteSize:
                        return Short.type;
                    case Long.byteSize:
                        return Long.type;
                    case jext::EXT_INT_128.byteSize:
                        return Not.type;
                    default:
                        assert(0);
                }
            }
            if (type_kind == CXType_Float) {
                switch (sizeOf) {
                    case Float.byteSize:
                        return Float.type;
                    case Double.byteSize:
                        return Double.type;
                    case jext::EXT_LONG_DOUBLE.byteSize:
                        return Not.type;
                    default:
                        assert(0);
                }
            }
            if (type_kind == CXType_Double) {
                switch (sizeOf) {
                    case Float.byteSize:
                        return Float.type;
                    case Double.byteSize:
                        return Double.type;
                    case jext::EXT_LONG_DOUBLE.byteSize:
                        return Not.type;
                    default:
                        assert(0);
                }
            }
            return type_other;
        }

        FFMType j_type_2_ffm_type(enum basic_j_type jType) {
            switch (jType) {
                case j_int:
                    return Integer;
                case j_long:
                    return Long;
                case j_float:
                    return Float;
                case j_double:
                    return Double;
                case j_char:
                    return Char;
                case j_bool:
                    return Bool;
                case j_byte:
                    return Byte;
                case j_short:
                    return Short;
                case j_void:
                    return Void;
                case type_other:
                    return Not;
            }
            assert(0);
        }
    }

    namespace jext {
        enum ext_type convert_2_ext(const CXType &declare) {
            auto type_kind = declare.kind;
            auto sizeOf = clang_Type_getSizeOf(declare);
            if (type_kind == CXType_Long || type_kind == CXType_ULong) {
                switch (sizeOf) {
                    case jbasic::Integer.byteSize:
                    case jbasic::Long.byteSize:
                        return EXT_OTHER.type;
                    case EXT_INT_128.byteSize:
                        return EXT_INT_128.type;
                    default:
                        assert(0);
                }
            }
            if (type_kind == CXType_LongLong || type_kind == CXType_ULongLong) {
                switch (sizeOf) {
                    case jbasic::Integer.byteSize:
                    case jbasic::Long.byteSize:
                        return EXT_OTHER.type;
                    case EXT_INT_128.byteSize:
                        return EXT_INT_128.type;
                    default:
                        assert(0);
                }
            }
            if (type_kind == CXType_Double) {
                switch (sizeOf) {
                    case jbasic::Float.byteSize:
                    case jbasic::Double.byteSize:
                        return EXT_OTHER.type;
                    case EXT_LONG_DOUBLE.byteSize:
                        return EXT_LONG_DOUBLE.type;
                    default:
                        assert(0);
                }
            }
            if (type_kind == CXType_LongDouble) {
                switch (sizeOf) {
                    case jbasic::Float.byteSize:
                    case jbasic::Double.byteSize:
                        return EXT_OTHER.type;
                    case EXT_LONG_DOUBLE.byteSize:
                        return EXT_LONG_DOUBLE.type;
                    default:
                        assert(0);
                }
            }
            return type_other;
        }
    }

    namespace method {
        using namespace jbasic;

        enum encode_method typeEncode(const CXType &declare) {
            switch (typeCopy(declare)) {
                case copy_by_set_j_int_call:
                    return encode_by_get_j_int_call;
                case copy_by_set_j_long_call:
                    return encode_by_get_j_long_call;
                case copy_by_set_j_float_call:
                    return encode_by_get_j_float_call;
                case copy_by_set_j_double_call:
                    return encode_by_get_j_double_call;
                case copy_by_set_j_char_call:
                    return encode_by_get_j_char_call;
                case copy_by_set_j_short_call:
                    return encode_by_get_j_short_call;
                case copy_by_set_j_byte_call:
                    return encode_by_get_j_byte_call;
                case copy_by_set_j_bool_call:
                    return encode_by_get_j_bool_call;
                case copy_by_set_memory_segment_call:
                    return encode_by_get_memory_segment_call;
                case copy_by_value_j_int_call:
                case copy_by_value_j_long_call:
                case copy_by_value_j_float_call:
                case copy_by_value_j_double_call:
                case copy_by_value_j_char_call:
                case copy_by_value_j_short_call:
                case copy_by_value_j_byte_call:
                case copy_by_value_j_bool_call:
                case copy_by_value_memory_segment_call:
                    return encode_by_object_slice_call;
                case copy_by_array_call:
                    return encode_by_array_slice_call;
                case copy_by_ptr_dest_copy_call:
                    return encode_by_object_slice_call;
                case copy_by_ptr_copy_call:
                    return encode_by_object_ptr_call;
                case copy_by_ext_int128_call:
                    return encode_by_ext_int128_call;
                case copy_by_ext_long_double_call:
                    return encode_by_ext_long_double_call;
                case copy_error:
                case copy_void:
                case copy_internal_function_proto:
                    assert(0);
            }
            assert(0);
        }

        enum copy_method typeCopy(const CXType &declare) {
            auto type_kind = declare.kind;
            if (type_kind == CXType_NullPtr || type_kind == CXType_Unexposed) {
                std::cout << "CXType_Unexposed" << std::endl;
                assert(0);
            }
            //j types
            switch (convert_2_j_type(declare)) {
                case j_int: {
                    return copy_by_set_j_int_call;
                }
                case j_long: {
                    return copy_by_set_j_long_call;
                }
                case j_float: {
                    return copy_by_set_j_float_call;
                }
                case j_char: {
                    return copy_by_set_j_char_call;
                }
                case j_bool: {
                    return copy_by_set_j_bool_call;
                }
                case j_byte: {
                    return copy_by_set_j_byte_call;
                }
                case j_double: {
                    return copy_by_set_j_double_call;
                }
                case j_short:
                    return copy_by_set_j_short_call;
                case j_void:
                    assert(0);
                case type_other: {
                    switch (jext::convert_2_ext(declare)) {
                        case jext::ext_int128:
                            return copy_by_ext_int128_call;
                        case jext::ext_long_double:
                            return copy_by_ext_long_double_call;
                            // non-java primitive value will pass to here.
                        case jext::type_other:
                            break;
                    }
                }
            }
            if (type_kind == CXType_Pointer || type_kind == CXType_BlockPointer) {
                auto pointer = clang_getPointeeType(declare);
                auto result = typeCopy(pointer);
                if (result == copy_void) {
                    //void*
                    return copy_by_set_memory_segment_call;
                } else {
                    return copy_by_ptr_copy_call;
                }
            }
            if (type_kind == CXType_Void) {
                return copy_void;
            }
            if (type_kind == CXType_FunctionProto || type_kind == CXType_FunctionNoProto) {
                return copy_internal_function_proto;
            }
            if (type_kind == CXType_Elaborated) {
                auto declared = clang_getCursorType(clang_getTypeDeclaration(declare));
                return typeCopy(declared);
            }
            if (type_kind == CXType_Record) {
                return copy_by_ptr_dest_copy_call;
            }
            if (type_kind == CXType_Enum) {
                return copy_by_value_j_int_call;
            }
            if (type_kind == CXType_Typedef) {
                auto ori = clang_getTypedefDeclUnderlyingType(clang_getTypeDeclaration(declare));
                auto copy = typeCopy(ori);
                switch (copy) {
                    case copy_by_value_j_int_call:
                    case copy_by_set_j_int_call: {
                        return copy_by_value_j_int_call;
                    }
                    case copy_by_value_j_long_call:
                    case copy_by_set_j_long_call: {
                        return copy_by_value_j_long_call;
                    }
                    case copy_by_value_j_float_call:
                    case copy_by_set_j_float_call: {
                        return copy_by_value_j_float_call;
                    }
                    case copy_by_value_j_char_call:
                    case copy_by_set_j_char_call: {
                        return copy_by_value_j_char_call;
                    }
                    case copy_by_value_j_bool_call:
                    case copy_by_set_j_bool_call: {
                        return copy_by_value_j_bool_call;
                    }
                    case copy_by_value_j_byte_call:
                    case copy_by_set_j_byte_call: {
                        return copy_by_value_j_byte_call;
                    }
                    case copy_by_value_j_double_call:
                    case copy_by_set_j_double_call: {
                        return copy_by_value_j_double_call;
                    }
                    case copy_by_value_memory_segment_call:
                    case copy_by_set_memory_segment_call:
                        return copy_by_value_memory_segment_call;
                    default: {
                        return copy_by_ptr_dest_copy_call; //like struct
                    }
                }
            }
            if (isArrayType(declare.kind)) {
                return copy_by_array_call;
            }
            if (WARNING)
                std::cout << "WARNING: Unhandled CXType: " << toStringWithoutConst(declare) << std::endl;
            assert(0);
        }

        FFMType encode_method_2_ffm_type(enum encode_method encodeMethod) {
            switch (encodeMethod) {
                case encode_by_get_j_int_call: {
                    return Integer;
                }
                case encode_by_get_j_long_call: {
                    return Long;
                }
                case encode_by_get_j_float_call: {
                    return Float;
                }
                case encode_by_get_j_double_call: {
                    return Double;
                }
                case encode_by_get_j_char_call: {
                    return Char;
                }
                case encode_by_get_j_byte_call: {
                    return Byte;
                }
                case encode_by_get_j_bool_call: {
                    return Bool;
                }
                case encode_by_get_j_short_call: {
                    return Short;
                }
                default: {
                    return Not;
                }
            }
        }

        jext::ExtType encode_method_2_ext_type(encode_method encodeMethod) {
            switch (encodeMethod) {
                case encode_by_ext_int128_call: {
                    return jext::EXT_INT_128;
                }
                case encode_by_ext_long_double_call: {
                    return jext::EXT_LONG_DOUBLE;
                }
                default: {
                    return jext::EXT_OTHER;
                }
            }
        }

        bool copy_method_is_value(copy_method copy_method) {
            switch (copy_method) {
                case copy_by_value_j_int_call:
                case copy_by_value_j_long_call:
                case copy_by_value_j_float_call:
                case copy_by_value_j_double_call:
                case copy_by_value_j_char_call:
                case copy_by_value_j_byte_call:
                case copy_by_value_j_bool_call:
                case copy_by_value_memory_segment_call:
                    return true;
                default:
                    return false;
            }
        }

        FFMType copy_method_2_ffm_type(enum copy_method copyMethod) {
            switch (copyMethod) {
                case copy_by_set_j_int_call: {
                    return Integer;
                }
                case copy_by_set_j_long_call: {
                    return Long;
                }
                case copy_by_set_j_float_call: {
                    return Float;
                }
                case copy_by_set_j_double_call: {
                    return Double;
                }
                case copy_by_set_j_char_call: {
                    return Char;
                }
                case copy_by_set_j_short_call: {
                    return Short;
                }
                case copy_by_set_j_byte_call: {
                    return Byte;
                }
                case copy_by_set_j_bool_call: {
                    return Bool;
                }
                case copy_by_value_j_int_call: {
                    return Integer;
                }
                case copy_by_value_j_long_call: {
                    return Long;
                }
                case copy_by_value_j_float_call: {
                    return Float;
                }
                case copy_by_value_j_double_call: {
                    return Double;
                }
                case copy_by_value_j_char_call: {
                    return Char;
                }
                case copy_by_value_j_short_call: {
                    return Short;
                }
                case copy_by_value_j_byte_call: {
                    return Byte;
                }
                case copy_by_value_j_bool_call: {
                    return Bool;
                }
                case copy_void: {
                    return Void;
                }
                default: {
                    return Not;
                }
            }
        }

        jext::ExtType copy_method_2_ext_type(copy_method copyMethod) {
            switch (copyMethod) {
                case method::copy_by_ext_int128_call: {
                    return jext::EXT_INT_128;
                }
                case method::copy_by_ext_long_double_call: {
                    return jext::EXT_LONG_DOUBLE;
                }
                default:
                    return jext::EXT_OTHER;
            }
        }
    }
} // jbindgen
