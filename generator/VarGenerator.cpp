//
// Created by nettal on 23-11-26.
//

#include <format>
#include <utility>
#include "VarGenerator.h"
#include "Value.h"
#include "GenUtils.h"

namespace jbindgen {
    std::string makeCore(const VarDeclaration &varDeclare) {
        if (varDeclare.hasSymbol) {

        }
        auto evaluate = clang_Cursor_Evaluate(varDeclare.varDeclare.cursor);
        std::string kindStr;
        std::string kindValue;
        switch (clang_EvalResult_getKind(evaluate)) {
            case CXEval_Int:
                kindStr = "int";
                kindValue = std::to_string(clang_EvalResult_getAsInt(evaluate));
                break;
            case CXEval_Float:
                kindStr = "float";
                kindValue = std::to_string(clang_EvalResult_getAsDouble(evaluate));
                break;
            case CXEval_ObjCStrLiteral:
                assert(0);
                break;
            case CXEval_StrLiteral:
                assert(0);
                break;
            case CXEval_CFStr:
                assert(0);
                break;
            case CXEval_Other:
                assert(0);
                break;
            case CXEval_UnExposed:
                //the type of the var is UnExposed, ignore it
                return "";
        }

        auto result = std::vformat("    public static final {} {} = {};\n",
                                   std::make_format_args(kindStr, varDeclare.varDeclare.name, kindValue));
        return result;
    }

    VarGenerator::VarGenerator(FN_makeVar makeVar, std::string header, std::string className, std::string packageName,
                               std::string tail, std::string dir, const std::vector<VarDeclaration> &vars,
                               const Analyser &analyser)
            : makeVar(std::move(makeVar)), header(std::move(header)), className(std::move(className)),
              tail(std::move(tail)),
              dir(std::move(dir)), vars(vars), packageName(std::move(packageName)), analyser(analyser) {
    }

    void VarGenerator::build() {
        std::string content;
        content += std::vformat("package {};\n"
                                "\n"
                                "public class {} {{\n", std::make_format_args(packageName, className));

        for (auto &item: vars) {
            content += makeCore(item);
        }
        content += "}\n";
        overwriteFile(dir + "/" + className + ".java", content);
    }

} // jbindgen