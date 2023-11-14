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
    static std::stringstream makeCoreWithOutPara(std::string functionName,std::string rtype) {
        std::stringstream ss;
        ss << "    private static MethodHandle " << functionName << ";" << END_LINE
           NEXT_LINE
           << "    public static void " << functionName << "() {" << END_LINE
           << "        if (" << functionName << " == null) {" << END_LINE
           << "            " << functionName << " = GlslangSymbols.loadCFunction(" << functionName << ", " << rtype << ".class);\n"
           << "        }\n"
           << "        try {\n"
           << "            " << functionName << ".invoke();\n"
           << "        } catch (Throwable e) {\n"
           << "            throw new NativeFunction.InvokeException(e);\n"
           << "        }\n"
           << "    }";
        return ss;
    }

    static std::stringstream
    makeCoreWithPara(std::string functionName, std::string rtype, std::string paras, std::string paraNames,
                     std::string paraTypes) {
        std::stringstream ss;
        std::string invRet = rtype.find("void") != std::string::npos ? "" : "return (" + rtype + ") ";
        /*
    private static MethodHandle %s;

    public static %s %s(%s) {
        if (%s == null) {
            %s = GlslangSymbols.loadCFunction("%s", %s.class, %s);
        }
        try {
            %s%s.invoke(%s);
        } catch (Throwable e) {
            throw new NativeFunction.InvokeException(e);
        }
    }*/
        ss <<
        "    private static MethodHandle " << functionName << ";\n"
        "\n"
        "    public static " << rtype << " " << functionName << "(" << paras << ") {\n"
        "        if (" << functionName << " == null) {\n"
        "            " << functionName << " = GlslangSymbols.loadCFunction(" << functionName << ", " << rtype << ".class, " << paraTypes << ");\n"
        "        }\n"
        "        try {\n"
        "            " << invRet << "" << functionName << ".invoke(" << paraNames << ");\n"
        "        } catch (Throwable e) {\n"
        "            throw new NativeFunction.InvokeException(e);\n"
        "        }\n"
        "    }\n"
        "\n"
        "";
        return ss;
    }

    class FunctionSymbolGenerator {
        const std::string libName;
        const PFN_makeFunction makeFunction;
        const std::string functionLoader;
        const std::string header;
        const std::string tail;
        const std::string dir;
        const std::vector<FunctionDeclaration> function_declarations;

    public:
        FunctionSymbolGenerator(std::string libName, PFN_makeFunction makeFunction, std::string functionLoader,
                                std::string header, std::string tail, std::string dir,
                                std::vector<FunctionDeclaration> function_declarations);

        void build(void *userData) {
            for (const auto &item: function_declarations) {
                auto func = makeFunction(item, userData);

            }
        }
    };
} // jbindgen

#endif //JAVABINDGEN_FUNCTIONSYMBOLGENERATOR_H
