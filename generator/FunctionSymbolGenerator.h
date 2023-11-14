//
// Created by nettal on 23-11-13.
//

#ifndef JAVABINDGEN_FUNCTIONSYMBOLGENERATOR_H
#define JAVABINDGEN_FUNCTIONSYMBOLGENERATOR_H

#include <string>
#include <utility>

#include "GenUtils.h"
#include "../analyser/FunctionDeclaration.h"
#include "StructGeneratorUtils.h"

namespace jbindgen {
    static std::string makeCore(std::string functionName) {
        std::stringstream ss;
        ss << "    private static MethodHandle " << functionName << ";" << END_LINE
           NEXT_LINE
           << "    public static void " << functionName << "() {" << END_LINE
           << "        if (" << functionName << " == null) {" << END_LINE
           << "            glslang_finalize_process = GlslangSymbols.loadCFunction(\"glslang_finalize_process\", void.class);\n"
           << "        }\n"
           << "        try {\n"
           << "            glslang_finalize_process.invoke();\n"
           << "        } catch (Throwable e) {\n"
           << "            throw new NativeFunction.InvokeException(e);\n"
           << "        }\n"
           << "    }";
    }

    class FunctionSymbolGenerator {
        const std::string libName;
        const PFN_makeFunction makeFunction;
        const std::string functionLoader;
        const std::string header;
        const std::string tail;
        const std::vector<FunctionDeclaration> function_declarations;

    public:
        FunctionSymbolGenerator(std::string libName, PFN_makeFunction makeFunction, std::string functionLoader,
                                std::string header, std::string tail,
                                std::vector<FunctionDeclaration> function_declarations);

        void build(void *userData) {
            for (const auto &item: function_declarations) {
                auto func = makeFunction(item, userData);

            }
        }
    };
} // jbindgen

#endif //JAVABINDGEN_FUNCTIONSYMBOLGENERATOR_H
