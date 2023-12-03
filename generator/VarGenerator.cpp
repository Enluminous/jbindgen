//
// Created by nettal on 23-11-26.
//

#include <format>
#include <utility>
#include "VarGenerator.h"
#include "Value.h"
#include "GenUtils.h"
#include "FunctionGeneratorUtils.h"

namespace jbindgen {
    std::string makeCore(const VarDeclaration &varDeclare, const Analyser &analyser, const std::string &symbolLoader) {
        if (!clang_isConstQualifiedType(varDeclare.varDeclare.type))
            return "";
        if (varDeclare.hasSymbol) {
            auto var = varDeclare.varDeclare;
            auto name = std::vformat(
                    R"({0}.getSymbol("{1}").orElseThrow(() -> new FunctionUtils.SymbolNotFound("{1}"))",
                    std::make_format_args(symbolLoader, var.name));
            auto wrappers = functiongenerator::processWrapperCallType(
                    {name, var.type, var.byteSize, var.commit, var.cursor}, analyser);
            std::string result;
            for (const auto &item: wrappers) {
                result += std::vformat("    public static final {} {} = {};\n",
                                       std::make_format_args(item.type, varDeclare.varDeclare.name, item.encode));
            }
            return result;
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
                               std::string tail, std::string dir, const std::vector<VarDeclaration>& vars,
                               const Analyser &analyser,
                               std::string symbolLoader)
            : makeVar(std::move(makeVar)), header(std::move(header)), className(std::move(className)),
              tail(std::move(tail)),
              dir(std::move(dir)), vars(vars), packageName(std::move(packageName)), analyser(analyser),
              symbolLoader(std::move(symbolLoader)) {
    }

    void VarGenerator::build() {
        std::string content;
        content += std::vformat("package {};\n"
                                "\n"
                                "public class {} {{\n", std::make_format_args(packageName, className));

        for (auto &item: vars) {
            content += makeCore(item, analyser, symbolLoader);
        }
        content += "}\n";
        overwriteFile(dir + "/" + className + ".java", content);
    }

} // jbindgen