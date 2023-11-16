//
// Created by snownf on 23-11-15.
//

#include <sstream>
#include <iostream>
#include <cassert>
#include "FunctionSymbolGeneratorUtils.h"

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

/*  struct FunctionSymbolWrapperInfo {
        std::string wrapperName;
        std::vector<std::string> jParameters;
        std::vector<std::string> targetParameters;
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
        info.hasResult = declaration->ret.type.kind != CXType_Void;
        if (info.hasResult) {

        } else {

        }
        return info;
    }

    std::tuple<std::string, std::string> processType(const VarDeclare &varDeclare) {
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
            auto len = clang_getArraySize(varDeclare.type);
            auto name = toString(clang_getArrayElementType(varDeclare.type));
            if (varDeclare.extra.has_value()) {
                name = std::any_cast<std::string>(varDeclare.extra);
            }
            assert(len != -1);
            return {value::jext::MemorySegment.primitive(),
                    "MemoryLayout.sequenceLayout(" + std::to_string(len) + ","
                    + name + "." + VALUE_LAYOUT + ")"};
        }
        assert(0);
    }
} // jbindgen