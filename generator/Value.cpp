//
// Created by snownf on 23-11-9.
//

#include <cassert>
#include <iostream>
#include "Value.h"

#include "GenUtils.h"

namespace jbindgen::value {
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
                        return Other.type;
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
                        return Other.type;
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
                        return Other.type;
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
                        return Other.type;
                    default:
                        assert(0);
                }
            }
            return type_other;
        }

        NativeType j_type_2_ffm_type(enum basic_j_type jType) {
            switch (jType) {
                case j_int:
                    return Integer;
                case j_long:
                    return Long;
                case j_float:
                    return Float;
                case j_double:
                    return Double;
#if NATIVE_UNSUPPORTED
                    case j_char:
                        return Char;
                    case j_bool:
                        return Bool;
#endif
                case j_byte:
                    return Byte;
                case j_short:
                    return Short;
                case j_void:
                    return Void;
                case type_other:
                    return Other;
            }
            assert(0);
        }

        enum basic_j_type valueBasedCXType2J(const CXType &cxType) {
            auto can = clang_getCanonicalType(cxType);
            return convert_2_j_type(can);
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
#if NATIVE_UNSUPPORTED
                    case j_char: {
                        return copy_by_set_j_char_call;
                    }
                    case j_bool: {
                        return copy_by_set_j_bool_call;
                    }
#endif
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
                switch (result) {
                    case copy_by_set_j_int_call:
                    case copy_by_set_j_long_call:
                    case copy_by_set_j_float_call:
                    case copy_by_set_j_double_call:
                    case copy_by_set_j_short_call:
                    case copy_by_set_j_byte_call:
                    case copy_by_set_memory_segment_call:
                    case copy_by_value_j_int_call:
                    case copy_by_value_j_long_call:
                    case copy_by_value_j_float_call:
                    case copy_by_value_j_double_call:
                    case copy_by_value_j_short_call:
                    case copy_by_value_j_byte_call:
                    case copy_by_value_memory_segment_call:
                    case copy_by_array_call:
                    case copy_by_ptr_dest_copy_call:
                    case copy_by_ptr_copy_call:
                    case copy_by_ext_int128_call:
                    case copy_by_ext_long_double_call:
                        //void**
                    case copy_by_ptr_no_target_type_call:
                        return copy_by_ptr_copy_call;
                    case copy_error:
                        assert(0);
                    case copy_void:
                        return copy_by_set_memory_segment_call;
                    case copy_target_void:
                    case copy_internal_function_proto:
                        return copy_by_ptr_no_target_type_call;
                }
                assert(0);
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
#if NATIVE_UNSUPPORTED
                        case copy_by_value_j_char_call:
                        case copy_by_set_j_char_call: {
                            return copy_by_value_j_char_call;
                        }
                        case copy_by_value_j_bool_call:
                        case copy_by_set_j_bool_call: {
                            return copy_by_value_j_bool_call;
                        }
#endif
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
                    case copy_void://for typedef void some_type
                        return copy_target_void;
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

        NativeType copy_method_2_native_type(enum copy_method copyMethod) {
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
                case copy_by_set_j_short_call: {
                    return Short;
                }
                case copy_by_set_j_byte_call: {
                    return Byte;
                }
#if NATIVE_UNSUPPORTED
                    case copy_by_set_j_char_call: {
                        return Char;
                    }
                    case copy_by_set_j_bool_call: {
                        return Bool;
                    }
#endif
                default: {
                    return Other;
                }
            }
        }

        ValueType native_type_2_value_type(NativeType valueType) {
            switch (valueType.type) {
                case j_int :
                    return VInteger;
                case j_long:
                    return VLong;
                case j_float:
                    return VFloat;
                case j_double:
                    return VDouble;
#if NATIVE_UNSUPPORTED
                    case  j_char:
                        return VChar;
                    case  j_bool:
                        return VBool;
#endif
                case j_byte:
                    return VByte;
                case j_short:
                    return VShort;
                case j_void:
                    return VVoid;
                case type_other:
                    return VOther;
            }
            assert(0);
        }

        ValueType copy_method_2_value_type(enum copy_method copyMethod) {
            switch (copyMethod) {
                case copy_by_value_j_int_call: {
                    return VInteger;
                }
                case copy_by_value_j_long_call: {
                    return VLong;
                }
                case copy_by_value_j_float_call: {
                    return VFloat;
                }
                case copy_by_value_j_double_call: {
                    return VDouble;
                }
                case copy_by_value_j_short_call: {
                    return VShort;
                }
                case copy_by_value_j_byte_call: {
                    return VByte;
                }
#if NATIVE_UNSUPPORTED
                    case copy_by_value_j_char_call: {
                        return VChar;
                    }
                    case copy_by_value_j_bool_call: {
                        return VBool;
                    }
#endif
                case copy_void: {
                    return VVoid;
                }
                default: {
                    return VOther;
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

    std::string makeVList(const std::string &valueName, jbasic::ValueType valueType) {
        assert(!std::equal(valueType.objectPrimitiveName().begin(), valueType.objectPrimitiveName().end(),
                           jbasic::NOT_AVAILABLE));
        return VList + "<" + valueName + ", " + valueType.objectPrimitiveName() + ">";
    }

    std::string makeVList(jbasic::ValueType type) {
        assert(!std::equal(type.objectPrimitiveName().begin(), type.objectPrimitiveName().end(),
                           jbasic::NOT_AVAILABLE));
        return VList + "<" + type.wrapper() + ", " + type.objectPrimitiveName() + ">";
    }
} // jbindgen
