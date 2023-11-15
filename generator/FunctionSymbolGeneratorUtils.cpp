//
// Created by snownf on 23-11-15.
//

#include <sstream>
#include <iostream>
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

    FunctionSymbolInfo
    FunctionSymbolGeneratorUtils::defaultMakeFunction(const jbindgen::FunctionDeclaration *declaration,
                                                      void *pUserdata) {
        std::cout << declaration->function << declaration->ret << declaration->canonicalName
                  << std::endl;
        for (const auto &para: declaration->paras) {
            std::cout << para << std::endl;
        }
        FunctionSymbolInfo info;
        info.functionName = "funcName";
        return info;
    }
} // jbindgen