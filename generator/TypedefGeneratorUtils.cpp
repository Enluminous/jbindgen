//
// Created by snownf on 23-11-19.
//

#include "TypedefGeneratorUtils.h"

#define VI8_T "VI8"
#define VI16_T "VI16"
#define VI32_T "VI32"
#define VI64_T "VI64"
#define VI128_T "VI128"
#define VFP32_T "FP32"
#define VFP64_T "FP64"
#define VFP128_T "FP128"

std::tuple<std::string, std::string, bool>
jbindgen::TypedefGeneratorUtils::defaultNameFunction(const jbindgen::NormalTypedefDeclaration *declaration,
                                                     void *pUserdata) {
    std::tuple<std::string, std::string, bool> a;
    std::string ori;
    std::string mapped;
    bool shouldDrop = false;
    auto encode = value::method::typeCopy(declaration->ori, declaration->cursor);
    switch (encode) {
        case value::method::copy_by_set_memory_segment_call:
            ori = "MemorySegment";
            break;
        case value::method::copy_by_set_j_int_call:
        case value::method::copy_by_value_j_int_call:
            ori = VI32_T;
            break;
        case value::method::copy_by_set_j_long_call:
        case value::method::copy_by_value_j_long_call:
            ori = VI64_T;
            break;
        case value::method::copy_by_set_j_float_call:
        case value::method::copy_by_value_j_float_call:
            ori = VFP32_T;
            break;
        case value::method::copy_by_set_j_double_call:
        case value::method::copy_by_value_j_double_call:
            ori = VFP64_T;
            break;
        case value::method::copy_by_set_j_char_call:
        case value::method::copy_by_value_j_char_call:
            ori = VI8_T;
            break;
        case value::method::copy_by_set_j_short_call:
        case value::method::copy_by_value_j_short_call:
            ori = VI16_T;
            break;
        case value::method::copy_by_value_j_byte_call:
        case value::method::copy_by_set_j_byte_call:
        case value::method::copy_by_set_j_bool_call:
        case value::method::copy_by_value_j_bool_call:
            ori = VI8_T;
            break;
        case value::method::copy_by_value_memory_segment_call:
            ori = declaration->oriStr;
            break;
        case value::method::copy_by_array_call:
            ori = "__ARRAY__" + std::to_string(clang_getArraySize(declaration->ori));
            break;
        case value::method::copy_by_ptr_dest_copy_call:
            shouldDrop = true;
            break;
        case value::method::copy_by_ptr_copy_call: {
            auto c = clang_getTypedefDeclUnderlyingType(declaration->cursor);
            auto c1 = clang_getPointeeType(c);
            if (c1.kind != CXType_FunctionProto) {
                // eg: typedef char *the_ptr;
                ori = "MemorySegment";
                break;
            }
            ori = GEN_FUNCTION;
            break;
        }
        case value::method::copy_by_ext_int128_call:
            ori = VI128_T;
            break;
        case value::method::copy_by_ext_long_double_call:
            assert(0);
            break;
        case value::method::copy_error:
            assert(0);
            break;
        case value::method::copy_void:
            ori = "Vvoid";
            break;
        case value::method::copy_internal_function_proto:
            assert(0);
            break;
    }
    mapped = declaration->mappedStr;
    std::get<0>(a) = mapped;
    std::get<1>(a) = ori;
    std::get<2>(a) = shouldDrop;
    return a;
}