//
// Created by snownf on 23-11-15.
//

#include <sstream>
#include <iostream>
#include <cassert>
#include "FunctionSymbolGeneratorUtils.h"
#include "StructGeneratorUtils.h"
#include "Value.h"

namespace jbindgen {
    std::string FunctionSymbolGeneratorUtils::defaultHead(const std::string &className, const std::string &packageName,
                                                          std::string libName) {
        std::stringstream ss;
        //TODO: gen
        //
        //    private static SymbolLookup GlslangLibSymbols = null;
        //    private static SymbolLookup GlslangStructTableSymbols = null;
        //
        //    public static void registerLibSymbol(SymbolLookup symbol) {
        //        GlslangLibSymbols = symbol;
        //    }
        //
        //    public static void registerStructTableSymbol(SymbolLookup symbol) {
        //        GlslangStructTableSymbols = symbol;
        //    }
        //
        //    public static MethodHandle loadCFunction(String functionName, Class<?> rtype, Class<?>... ptypes) {
        //        return NativeFunction.getCHandle(GlslangLibSymbols, functionName, rtype, ptypes)
        //                .orElseThrow((Supplier<RuntimeException>) () -> new NativeFunction.FunctionNotFound(functionName));
        //    }
        ss <<
           "package " << packageName << ";\n"
                                        "\n"
                                        "\n"
                                        "import infinity.natives.glslang.struct.glslang_input_t;\n"
                                        "import infinity.natives.glslang.struct.glslang_spv_options_t;\n"
                                        "import infinity.natives.shared.NativeList;\n"
                                        "import infinity.natives.shared.Pointer;\n"
                                        "import infinity.natives.shared.types.NativeFunction;\n"
                                        "import infinity.natives.shared.types.NativeInteger;\n"
                                        "import infinity.natives.shared.types.NativeString;\n"
                                        "\n"
                                        "import java.lang.foreign.MemorySegment;\n"
                                        "import java.lang.invoke.MethodHandle;\n"
                                        "\n"
                                        "public final class " << className << " {";
        return ss.str();
    }

    std::string FunctionSymbolGeneratorUtils::defaultTail() {
        return "}";
    }

    //  j_type , fd
    std::tuple<std::string, std::string> processDirectCallType(const VarDeclare &varDeclare) {
        auto copyMethod = value::method::typeCopy(varDeclare.type, varDeclare.cursor);
        auto ffm = value::method::copy_method_2_ffm_type(copyMethod);
        if (ffm.type != value::jbasic::type_other && !value::method::copy_method_is_value(copyMethod)) {
            assert(copyMethod != value::method::copy_error);
            assert(copyMethod != value::method::copy_void);
            assert(copyMethod != value::method::copy_internal_function_proto);
            return {ffm.primitive(), ffm.value_layout()};
        }
        if (value::method::copy_method_is_value(copyMethod)) {
            return {value::jext::MemorySegment.primitive(), value::jext::MemorySegment.value_layout()};
        }
        if (value::method::copy_by_set_memory_segment_call == copyMethod ||
            value::method::copy_by_ext_int128_call == copyMethod ||
            value::method::copy_by_ext_long_double_call == copyMethod ||
            value::method::copy_by_ptr_copy_call == copyMethod) {
            return {value::jext::MemorySegment.primitive(), value::jext::MemorySegment.value_layout()};
        }
        if (value::method::copy_by_ptr_dest_copy_call == copyMethod) {
            return {value::jext::MemorySegment.primitive(), varDeclare.name + "." + VALUE_LAYOUT};
        }
        if (value::method::copy_by_array_call == copyMethod) {
            auto len = getArrayLength(varDeclare.type);
            auto name = toString(clang_getArrayElementType(varDeclare.type));
            if (varDeclare.extra.has_value()) {
                name = std::any_cast<std::string>(varDeclare.extra);
            }
            if (len == -1) {
                return {value::jext::MemorySegment.primitive(), value::jext::MemorySegment.value_layout()};
            }
            return {value::jext::MemorySegment.primitive(),
                    "MemoryLayout.sequenceLayout(" + std::to_string(len) + ","
                    + name + "." + VALUE_LAYOUT + ")"};
        }
        assert(0);
    }

    //  j parameters , fds , invokes
    std::tuple<std::vector<std::string>, std::vector<std::string>, std::vector<std::string>>
    makeParameter(const jbindgen::FunctionDeclaration &declare) {
        std::vector<std::string> jParameters;
        std::vector<std::string> fds;
        std::vector<std::string> invokes;
        int i = 0;
        for (const auto &item: declare.paras) {
            auto pre = processDirectCallType(item);
            std::string paraName = item.name;
            if (std::equal(item.name.begin(), item.name.end(), NO_NAME)) {
                paraName = "para" + std::to_string(i);
            }
            jParameters.emplace_back(get<0>(pre) + " " + paraName);
            fds.emplace_back(get<1>(pre));
            invokes.emplace_back(paraName);
            i++;
        }
        return {jParameters, fds, invokes};
    }

/*  struct FunctionSymbolWrapperInfo {
        std::string wrapperName;
        std::vector<std::string> jParameters;
        std::vector<std::string> targetParameters;
        std::string resultDescriptor;//optional
        std::string jResult;//optional
    };

    struct FunctionSymbolInfo {
        std::string functionName;
        std::vector<std::string> jParameters;
        std::vector<std::string> functionDescriptors;
        std::vector<std::string> invokeParameters;
        std::vector<FunctionSymbolWrapperInfo> wrappers;
        std::string resultDescriptor;
        std::string jResult;
        bool hasResult;
    };*/

    FunctionSymbolInfo
    FunctionSymbolGeneratorUtils::defaultMakeFunction(const jbindgen::FunctionDeclaration *declaration,
                                                      void *pUserdata) {
        std::cout << declaration->function << declaration->ret << declaration->canonicalName
                  << std::endl;
        for (const auto &para: declaration->paras) {
            std::cout << para << std::endl;
        }
        FunctionSymbolInfo info;
        info.functionName = declaration->function.name;
        //parameter
        auto parameters = makeParameter(*declaration);
        info.jParameters = get<0>(parameters);
        info.functionDescriptors = get<1>(parameters);
        info.invokeParameters = get<2>(parameters);
        //result
        info.hasResult = declaration->ret.type.kind != CXType_Void;
        if (info.hasResult) {
            auto ret = processDirectCallType(declaration->ret);
            info.jResult = get<0>(ret);
            info.resultDescriptor = get<1>(ret);
        }
        return info;
    }

    //wrapper type,decode way
    std::vector<std::tuple<std::string, std::string>> processWrapperCallType(const VarDeclare &declare) {
        std::vector<std::tuple<std::string, std::string>> optional;
        auto encode = value::method::typeEncode(declare.type);
        {
            const value::jbasic::FFMType &ffmType = encode_method_2_ffm_type(encode);
            if (ffmType.type != value::jbasic::type_other) {
                if (ffmType.type == value::jbasic::j_void) {
                    assert(0);
                }
                optional.emplace_back(std::tuple{ffmType.primitive(), ""});
            }
        }
        {
            auto ext = value::method::encode_method_2_ext_type(encode);
            if (ext.type != value::jext::type_other) {
                optional.emplace_back(std::tuple{ext.native_wrapper, ".pointer()"});
            }
        }
        auto typeName = toString(declare.type);
        if (declare.extra.has_value()) {
            typeName = std::any_cast<std::string>(declare.extra);
        }
        switch (encode) {
            case value::method::encode_by_get_memory_segment_call:
                optional.emplace_back(std::tuple{"Pointer<?>", ".pointer()"});
                break;
            case value::method::encode_by_object_slice_call:
            case value::method::encode_by_object_ptr_call:
                optional.emplace_back(std::tuple{"Pointer<" + typeName + ">", ".pointer()"});
                optional.emplace_back(std::tuple{typeName, ".pointer()"});
                break;
            case value::method::encode_by_array_slice_call:
                optional.emplace_back(std::tuple{"Pointer<" + toArrayName(declare) + ">", ".pointer()"});
                optional.emplace_back(std::tuple{NativeArray + "<" + toArrayName(declare) + ">", ".pointer()"});
                break;
            default:
                assert(0);
        }
        return {};
    }

    std::vector<FunctionSymbolWrapperInfo> makeWrappers(const FunctionDeclaration &declaration) {
        //todo
        return {};
    }
} // jbindgen