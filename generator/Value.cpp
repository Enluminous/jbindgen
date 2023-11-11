//
// Created by snownf on 23-11-9.
//

#include <cassert>
#include <iostream>
#include "Value.h"

namespace jbindgen {
    int TypeDecode(const CXType &declare, const CXCursor &cursor) {
        int result = TypeCopy(declare, cursor);
        switch (result) {
            case copy_by_set_j_bool_call:
            case copy_by_set_j_int_call:
            case copy_by_set_j_long_call:
            case copy_by_set_j_byte_call:
            case copy_by_set_j_double_call:
            case copy_by_set_j_float_call:
            case copy_by_set_j_char_call:
                return decode_by_primitive;
            case copy_by_ptr_dest_copy_call:
                return decode_by_pointer_call;
            case copy_by_value_j_int_call:
            case copy_by_value_j_bool_call:
            case copy_by_value_j_float_call:
            case copy_by_value_j_double_call:
            case copy_by_value_j_long_call:
            case copy_by_value_j_char_call:
            case copy_by_value_j_byte_call:
                return decode_by_value_call;
            default:
                return decode_error;
        }
    }

    int basic_j_types(const CXType &declare) {
        auto type_kind = declare.kind;
        //j types
        if (type_kind == CXType_Int || type_kind == CXType_UInt) {
            return j_int;
        }
        if (type_kind == CXType_Long || type_kind == CXType_LongLong) {//todo add support for windows
            return j_long;
        }
        if (type_kind == CXType_Double) {
            return j_double;
        }
        return type_other;
    }

    int TypeEncode(const CXType &declare) {
        auto type_kind = declare.kind;
        if (type_kind == CXType_NullPtr || type_kind == CXType_Unexposed) {
            std::cout << "CXType_Unexposed" << std::endl;
            assert(0);
        }
        //j types
        switch (basic_j_types(declare)) {
            case j_int: {
                return encode_by_get_j_int_call;
            }
            case j_long: {
                return encode_by_get_j_long_call;
            }
            case j_float: {
                return encode_by_get_j_float_call;
            }
            case j_char: {
                return encode_by_get_j_char_call;
            }
            case j_bool: {
                return encode_by_get_j_bool_call;
            }
            case j_byte: {
                return encode_by_get_j_byte_call;
            }
            case j_double: {
                return encode_by_get_j_double_call;
            }
        }
        //void
        if (type_kind == CXType_Void) {
            return encode_by_void;
        }
        //
        if (type_kind == CXType_Pointer || type_kind == CXType_BlockPointer) {
            auto pointer = clang_getPointeeType(declare);
            auto result = TypeEncode(pointer);
            if (result == encode_by_void) {//void*
                return encode_by_get_memory_segment_call;
            } else {
                return encode_by_object_ptr;
            }
        }
        if (type_kind == CXType_FunctionProto || type_kind == CXType_FunctionNoProto) {
            return encode_internal_function_proto;
        }
        if (type_kind == CXType_Elaborated) {
            auto declared = clang_getCursorType(clang_getTypeDeclaration(declare));
            return TypeEncode(declared);
        }
        if (type_kind == CXType_Record) {
            return encode_by_object_slice;
        }
        if (type_kind == CXType_Typedef) {
            return encode_by_object_slice;
        }
        if (type_kind == CXType_ConstantArray || type_kind == CXType_IncompleteArray ||
            type_kind == CXType_VariableArray ||
            type_kind == CXType_DependentSizedArray) {
            return encode_by_array_ptr;
        }
        std::cout << "WARNING: Unhandled CXType: " << toString(declare) << std::endl;
        assert(0);
    }

    int TypeCopy(const CXType &declare, const CXCursor &cursor) {
        auto type_kind = declare.kind;
        if (type_kind == CXType_NullPtr || type_kind == CXType_Unexposed) {
            std::cout << "CXType_Unexposed" << std::endl;
            assert(0);
        }
        //j types
        switch (basic_j_types(declare)) {
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
        }
        if (type_kind == CXType_Pointer || type_kind == CXType_BlockPointer) {
            auto pointer = clang_getPointeeType(declare);
            auto result = TypeEncode(pointer);
            if (result == encode_by_void) {//void*
                return encode_by_get_memory_segment_call;
            } else {
                return encode_by_object_ptr;
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
            return TypeEncode(declared);
        }
        if (type_kind == CXType_Record) {
            return copy_by_ptr_dest_copy_call;
        }
        if (type_kind == CXType_Typedef) {
            auto ori = clang_getTypedefDeclUnderlyingType(cursor);
            auto copy = TypeCopy(ori, clang_getTypeDeclaration(ori));
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
                    return copy;
                }
            }
        }
        if (type_kind == CXType_ConstantArray || type_kind == CXType_IncompleteArray ||
            type_kind == CXType_VariableArray ||
            type_kind == CXType_DependentSizedArray) {
            return copy_by_ptr_dest_copy_call;
        }
        std::cout << "WARNING: Unhandled CXType: " << toString(declare) << std::endl;
        assert(0);
    }

} // jbindgen