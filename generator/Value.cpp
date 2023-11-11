//
// Created by snownf on 23-11-9.
//

#include <cassert>
#include <iostream>
#include "Value.h"

namespace jbindgen {
    FFMType isFFM_RawType(const CXType &declare, const CXCursor &cursor, int *type) {
        auto type_kind = declare.kind;
        if (type_kind == CXType_Enum || type_kind == CXType_Int || type_kind == CXType_UInt) {
            *type = type_integer;
            return Integer;
        }
        if (type_kind == CXType_Pointer || type_kind == CXType_BlockPointer) {
            auto pointer = clang_getPointeeType(declare);
            int pointer_type = 0;
            isFFM_RawType(pointer, clang_getTypeDeclaration(pointer), &pointer_type);
            if (pointer_type == type_is_void) {
                *type = type_any_pointer;
                return Any_ptr;
            }
            *type = type_is_pointer;
            return Not;
        }
        if (type_kind == CXType_Void) {
            *type = type_is_void;
            return Not;
        }
        if (type_kind == CXType_Elaborated) {
            auto declared = clang_getCursorType(clang_getTypeDeclaration(declare));
            return isFFM_RawType(declared, clang_getTypeDeclaration(declare), type);
        }
        if (type_kind == CXType_NullPtr || type_kind == CXType_Unexposed) {
            std::cout << "CXType_Unexposed" << std::endl;
            assert(0);
        }
        if (type_kind == CXType_Record) {
            *type = type_is_struct;
            return Not;
        }
        if (type_kind == CXType_FunctionProto || type_kind == CXType_FunctionNoProto) {
            *type = type_func_ptr;
            return Function_ptr;
        }
        if (type_kind == CXType_Typedef) {
            auto ori = clang_getTypedefDeclUnderlyingType(cursor);
            return isFFM_RawType(ori, clang_getTypeDeclaration(ori), type);
        }

        if (type_kind == CXType_ConstantArray || type_kind == CXType_IncompleteArray ||
            type_kind == CXType_VariableArray ||
            type_kind == CXType_DependentSizedArray) {
            *type = type_is_array;
            return Not;
        }
        if (type_kind == CXType_Long || type_kind == CXType_LongLong) {
            *type = type_long;
            return Long;
        }
        if (type_kind == CXType_Double || type_kind == CXType_LongDouble) {
            *type = type_double;
            return Double;
        }
        std::cout << "WARNING: Unhandled CXType: " << toString(declare) << std::endl;
        *type = 0;
        return Not;
    }

} // jbindgen