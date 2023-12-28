//
// Created by nettal on 23-11-26.
//

#include <format>
#include <utility>
#include "VarGenerator.h"
#include "Value.h"
#include "GenUtils.h"
#include "FunctionGeneratorUtils.h"
#include "StructGeneratorUtils.h"

namespace jbindgen {
    std::string makeCore(const VarDeclaration &varDeclare, const Analyser &analyser, const std::string &symbolLoader) {
        if (!clang_isConstQualifiedType(varDeclare.varDeclare.type))
            return "";
        if (varDeclare.hasSymbol) {
            auto var = varDeclare.varDeclare;
            auto name = std::vformat(
                    R"({0}.getSymbol("{1}").orElseThrow(() -> new FunctionUtils.SymbolNotFound("{1}"))",
                    std::make_format_args(symbolLoader, var.name));
            auto wrappers = StructGeneratorUtils::defaultStructDecodeGetter(
                    StructMember(varDeclare.varDeclare, 0), analyser, name);
            std::string result;
            int index = 0;
            for (const auto &item: wrappers) {
                if (!item.parameterString.empty())
                    continue;
                auto append = (index == 0 ? "" : "$" + std::to_string(index));
                index++;
                result += std::vformat("    public static final {} {} = {};\n",
                                       std::make_format_args(item.returnTypeName, varDeclare.varDeclare.name + append,
                                                             item.creator));
            }
            return result;
        }
        auto wrappers = functiongenerator::processWrapperCallType(varDeclare.varDeclare, analyser);
        auto evaluate = clang_Cursor_Evaluate(varDeclare.varDeclare.cursor);
        std::string kindValue;
        switch (clang_EvalResult_getKind(evaluate)) {
            case CXEval_Int:
                switch (varDeclare.varDeclare.byteSize) {
                    case value::jbasic::Byte.byteSize:
                    case value::jbasic::Integer.byteSize:
                    case value::jbasic::Short.byteSize:
                        kindValue = std::to_string(clang_EvalResult_getAsInt(evaluate));
                        break;
                    case value::jbasic::Long.byteSize:
                        kindValue = std::to_string(clang_EvalResult_getAsLongLong(evaluate)) + "L";
                        break;
                    default:
                        assert(0);
                }
                break;
            case CXEval_Float:
                switch (varDeclare.varDeclare.byteSize) {
                    case value::jbasic::Float.byteSize:
                        kindValue = std::to_string(clang_EvalResult_getAsDouble(evaluate)) + "f";
                        break;
                    case value::jbasic::Double.byteSize:
                        kindValue = std::to_string(clang_EvalResult_getAsDouble(evaluate));
                        break;
                    default:
                        assert(0);
                }
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
        std::string result;
        int index = 0;
        for (const auto &item: wrappers) {
            auto append = (index == 0 ? "" : "$" + std::to_string(index));
            index++;
            result += std::vformat("    public static final {} {} = {};\n",
                                   std::make_format_args(item.type, varDeclare.varDeclare.name + append,
                                                         item.getEncode(kindValue)));
        }
        return result;
    }

    VarGenerator::VarGenerator(FN_makeVar makeVar, std::string header, std::string className, std::string packageName,
                               std::string tail, std::string dir, const std::vector<VarDeclaration> &vars,
                               const Analyser &analyser,
                               std::string symbolLoader, const std::shared_ptr<TypeManager> &typeManager,
                               const GeneratorConfig &config)
            : makeVar(std::move(makeVar)), header(std::move(header)), className(std::move(className)),
              tail(std::move(tail)),
              dir(std::move(dir)), vars(vars), packageName(std::move(packageName)), analyser(analyser),
              symbolLoader(std::move(symbolLoader)), config(config), typeManager(typeManager) {
    }

    void VarGenerator::build() {
        std::string content;
        content += std::vformat("package {};\n"
                                "\n"
                                "{}"
                                "{}"
                                "\n"
                                "public class {} {{\n",
                                std::make_format_args(packageName, typeManager->getImports(&config, true),
                                                      typeManager->getImports(), className));

        for (auto &item: vars) {
            content += makeCore(item, analyser, symbolLoader);
        }
        content += "}\n";
        overwriteFile(dir + "/" + className + ".java", content);
    }

} // jbindgen