//
// Created by snownf on 23-11-19.
//

#include "TypedefGeneratorUtils.h"
#include "Value.h"

#include <utility>

std::tuple<jbindgen::value::jbasic::ValueType, std::string, bool>
jbindgen::TypedefGeneratorUtils::defaultNameFunction(const jbindgen::NormalTypedefDeclaration *declaration) {
    using namespace value::jbasic;
    std::tuple<ValueType, std::string, bool> a = std::make_tuple(VOther, "", false);
    ValueType ori = VOther;
    std::string extra;
    bool shouldDrop = false;
    auto encode = value::method::typeCopy(declaration->ori);
    switch (encode) {
        case value::method::copy_by_set_memory_segment_call:
            ori = value::jext::VPointer;
            break;
        case value::method::copy_by_set_j_int_call:
        case value::method::copy_by_value_j_int_call:
            ori = value::jbasic::VInteger;
            break;
        case value::method::copy_by_set_j_long_call:
        case value::method::copy_by_value_j_long_call:
            ori = value::jbasic::VLong;
            break;
        case value::method::copy_by_set_j_float_call:
        case value::method::copy_by_value_j_float_call:
            ori = value::jbasic::VFloat;
            break;
        case value::method::copy_by_set_j_double_call:
        case value::method::copy_by_value_j_double_call:
            ori = value::jbasic::VDouble;
            break;
#if NATIVE_UNSUPPORTED
            case value::method::copy_by_set_j_char_call:
            case value::method::copy_by_value_j_char_call:
                ori = value::jbasic::VChar;
                break;
#endif
        case value::method::copy_by_set_j_short_call:
        case value::method::copy_by_value_j_short_call:
            ori = value::jbasic::VShort;
            break;
        case value::method::copy_by_value_j_byte_call:
        case value::method::copy_by_set_j_byte_call:
#if NATIVE_UNSUPPORTED
            case value::method::copy_by_set_j_bool_call:
            case value::method::copy_by_value_j_bool_call:
#endif
            ori = value::jbasic::VByte;
            break;
        case value::method::copy_by_value_memory_segment_call:
            ori = value::jbasic::VOther;
            extra = declaration->oriStr;
            break;
        case value::method::copy_by_array_call:
            ori = value::jbasic::VOther;
            extra = "__ARRAY__" + std::to_string(clang_getArraySize(declaration->ori));
            break;
        case value::method::copy_by_ptr_dest_copy_call:
            shouldDrop = true;
            break;
        case value::method::copy_by_ptr_copy_call: {
            auto c = clang_getTypedefDeclUnderlyingType(declaration->cursor);
            auto c1 = clang_getPointeeType(c);
            if (c1.kind != CXType_FunctionProto) {
                // eg: typedef char *the_ptr;
                ori = value::jbasic::VOther;
                extra = value::jext::Pointer.wrapper();
                break;
            }
            ori = value::jbasic::VOther;
            extra = GEN_FUNCTION;
            break;
        }
        case value::method::copy_by_ext_int128_call:
            ori = value::jbasic::VOther;
            extra = value::jext::EXT_INT_128.native_wrapper;
            break;
        case value::method::copy_by_ext_long_double_call:
            assert(0);
            break;
        case value::method::copy_error:
            assert(0);
            break;
        case value::method::copy_void:
            ori = value::jbasic::VVoid;
            break;
        case value::method::copy_internal_function_proto:
            assert(0);
            break;
    }
    std::get<0>(a) = ori;
    std::get<1>(a) = extra;
    std::get<2>(a) = shouldDrop;
    return a;
}


std::string jbindgen::TypedefGeneratorUtils::GenFuncSym(std::vector<std::string> jParameters,
                                                        std::vector<std::string> functionDescriptors,
                                                        std::string className,
                                                        bool hasResult, std::string resultStr) {
    std::stringstream jPara;
    for (int i = 0; i < jParameters.size(); ++i) {
        std::string &para = jParameters[i];
        jPara << (i == 0 ? "" : " ") << para << ((i == jParameters.size() - 1) ? "" : ",");
    }
    std::stringstream fds;
    for (int i = 0; i < functionDescriptors.size(); ++i) {
        std::string &fd = functionDescriptors[i];
        fds << (i == 0 ? "" : " ") << fd << ((i == functionDescriptors.size() - 1) ? "" : ",");
    }
    std::string returnStr = hasResult ? std::move(resultStr) : "void";
    std::string func = std::vformat(
            "@FunctionalInterface\n"
            "public interface {} {{\n"
            "    {} function({});\n"
            "\n"
            "    default Pointer<?> toPointer(Arena arena) {{\n"
            "        return new Pointer<>() {{\n"
            "            @Override\n"
            "            public MemorySegment pointer() {{\n"
            "                return (FunctionUtils.toMemorySegment(MethodHandles.lookup(), arena, FunctionDescriptor.of({}), this, \"function\"));\n"
            "            }}\n"
            "        }};\n"
            "    }}\n"
            "}}",
            std::make_format_args(className, returnStr, jPara.str(), fds.str()));
    return func;
}

std::string jbindgen::TypedefGeneratorUtils::GenFuncWrapper(std::vector<std::string> jParameters,
                                                            const std::vector<std::string> &toLowerLevel,
                                                            const std::vector<std::string> &toUpperLevel,
                                                            const std::vector<std::string> &parentParameters,
                                                            std::string className,
                                                            std::string parentClassName, bool hasResult,
                                                            std::string resultType,
                                                            std::string parentResultType) {
    std::stringstream jPara;
    for (int i = 0; i < jParameters.size(); ++i) {
        jPara << (i == 0 ? "" : " ") << jParameters[i] << ((i == jParameters.size() - 1) ? "" : ",");
    }
    std::stringstream lowers;
    for (int i = 0; i < toLowerLevel.size(); ++i) {
        lowers << (i == 0 ? "" : " ") << toLowerLevel[i] << ((i == toLowerLevel.size() - 1) ? "" : ",");
    }
    std::stringstream uppers;
    for (int i = 0; i < toUpperLevel.size(); ++i) {
        uppers << (i == 0 ? "" : " ") << toUpperLevel[i] << ((i == toUpperLevel.size() - 1) ? "" : ",");
    }
    std::stringstream parent;
    for (int i = 0; i < parentParameters.size(); ++i) {
        parent << (i == 0 ? "" : " ") << parentParameters[i] << ((i == parentParameters.size() - 1) ? "" : ",");
    }
    std::string returnStr = hasResult ? std::move(resultType) : "void";
    std::string parentReturnStr = hasResult ? std::move(parentResultType) : "void";
    std::string func = std::vformat(
            "@FunctionalInterface\n"
            "public interface {} extends {} {{\n"
            "    {} function({});\n"
            "\n"
            "    @Override\n"
            "    default {} function({}) {{\n"
            "        return function({});\n"
            "    }}\n"
            "}}",
            std::make_format_args(className, parentClassName, returnStr, jPara.str(),
                                  parentReturnStr, parent.str(), lowers.str()));
    return func;
}