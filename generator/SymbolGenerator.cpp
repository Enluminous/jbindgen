//
// Created by nettal on 23-12-15.
//

#include "SymbolGenerator.h"

#include <utility>

namespace jbindgen {
    SymbolGenerator::SymbolGenerator(struct config::SymbolLookup symbolsConfig, std::string functionUtilsPackageName)
            : symbolsConfig(std::move(symbolsConfig)), functionUtilsPackageName(std::move(functionUtilsPackageName)) {
    }

    std::string SymbolGenerator::makeSymbol() {
        std::string symbol = std::vformat(
                "package {1};\n"
                "\n"
                "import {2};\n"
                "\n"
                "import java.lang.foreign.FunctionDescriptor;\n"
                "import java.lang.foreign.MemorySegment;\n"
                "import java.lang.foreign.SymbolLookup;\n"
                "import java.lang.invoke.MethodHandle;\n"
                "import java.util.ArrayList;\n"
                "import java.util.Optional;\n"
                "\n"
                "public class {0} {{\n"
                "    private {0}() {{\n"
                "        throw new UnsupportedOperationException();\n"
                "    }}\n"
                "\n"
                "    private static final ArrayList<SymbolLookup> symbolLookups = new ArrayList<>();\n"
                "    private static {5}boolean critical = false;\n"
                "\n"
                "    public static void addSymbols(SymbolLookup symbolLookup) {{\n"
                "        symbolLookups.add(symbolLookup);\n"
                "    }}\n"
                "\n{3}{4}"
                "    public static Optional<MethodHandle> toMethodHandle(String functionName, FunctionDescriptor functionDescriptor) {{\n"
                "        return symbolLookups.stream().map(symbolLookup -> FunctionUtils.toMethodHandle(symbolLookup, functionName, functionDescriptor, critical))\n"
                "                .filter(Optional::isPresent).map(Optional::get).findFirst();\n"
                "    }}\n"
                "\n"
                "    public static Optional<MemorySegment> getSymbol(String symbol) {{\n"
                "        return symbolLookups.stream().map(symbolLookup -> symbolLookup.find(symbol))\n"
                "                .filter(Optional::isPresent).map(Optional::get).findFirst();\n"
                "    }}\n"
                "}}\n", std::make_format_args(symbolsConfig.symbolClassName, symbolsConfig.symbolPackageName,
                                              functionUtilsPackageName, symbolsConfig.accessSymbolLookups
                                                                        ? "    public static ArrayList<SymbolLookup> getSymbolLookups() {\n"
                                                                          "        return symbolLookups;\n"
                                                                          "    }\n"
                                                                          "\n" : "",
                                              symbolsConfig.allowCritical ?
                                              "    public static void setCritical(boolean critical) {{\n"
                                              "        " + symbolsConfig.symbolClassName + ".critical = critical;\n" +
                                              "    }}\n"
                                              "\n" : "", symbolsConfig.allowCritical ? "" : "final "));
        return symbol;
    }

    void SymbolGenerator::build() {
        overwriteFile(symbolsConfig.dir + "/" + symbolsConfig.symbolClassName + ".java", makeSymbol());
    }
} // jbindgen