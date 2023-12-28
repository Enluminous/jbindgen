//
// Created by nettal on 23-11-13.
//

#include "MacroNormalGenerator.h"

#include <utility>

namespace jbindgen {
    MacroNormalGenerator::MacroNormalGenerator(FN_makeMacro makeMacro, std::string header, std::string className,
                                               std::string tail, std::string dir, std::string packageName,
                                               const std::vector<NormalMacroDeclaration> &macro_declarations)
            : makeMacro(std::move(makeMacro)), header(std::move(header)), className(std::move(className)),
              tail(std::move(tail)), dir(std::move(dir)), packageName(std::move(packageName)),
              macro_declarations(macro_declarations) {
    }

    bool isBlank(const std::string &test) {
        if (test.empty())
            return true;
        if (test.empty())
            return true;
        int64_t count = 0;
        for (auto &item: test) {
            if (isblank(item))
                count++;
        }
        return test.length() == count;
    }

    void MacroNormalGenerator::build() {
        std::string result;
        result += std::vformat("package {};\n"
                               "\n"
                               "public class {} {{\n",
                               std::make_format_args(packageName, className));
        for (auto &item: macro_declarations) {
            if (!isBlank(item.normalDefines.second)) {
                std::stringstream toAdd;
                toAdd << "    ";
                auto core = makeMacro(item);
                if (core.empty()) {
                    if (WARNING)
                        std::cout << "WARNING: ignore definition " << item.normalDefines.first << std::endl;
                    continue;
                }
                toAdd << core;
                toAdd << ";";
                toAdd << " // ";
                toAdd << item.normalDefines.second;
                toAdd << "\n";
                result += toAdd.str();
            } else {
                std::cout << "MacroNormalGenerator: ignore empty definition: " << item.normalDefines.first
                          << std::endl;
            }
        }
        result += "}";
        overwriteFile(dir + "/" + className + ".java", result);
    }
} // jbindgen