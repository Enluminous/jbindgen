//
// Created by nettal on 23-11-13.
//

#include "MacroNormalGenerator.h"

#include <utility>

namespace jbindgen {
    MacroNormalGenerator::MacroNormalGenerator(FN_makeMacro makeMacro, std::string header, std::string className,
                                               std::string tail, std::string dir, std::string packageName,
                                               std::vector<NormalMacroDeclaration> &macro_declarations)
            : makeMacro(makeMacro), header(std::move(header)), className(std::move(className)),
              tail(std::move(tail)), dir(std::move(dir)), packageName(std::move(packageName)),
              macro_declarations(std::move(macro_declarations)) {
    }

    void MacroNormalGenerator::build() {
        std::string result;
        result += std::vformat("package {};\n"
                               "\n"
                               "public class {} {{\n",
                               std::make_format_args(packageName, className));
        for (auto &item: macro_declarations) {
            if (!item.normalDefines.second.empty()) {
                std::string toAdd = "    ";
                auto core = makeMacro(item, &macro_declarations);
                if (core.starts_with("IGNORE")) {
                    std::cout << "MacroNormalGenerator: ignore definition "
                              << item.normalDefines.first << ":" << &core[std::string("IGNORE ").size()]
                              << std::endl;
                    continue;
                }
                toAdd += core;
                toAdd += ";\n";
                result += toAdd;
            } else {
                std::cout << "MacroNormalGenerator: ignore empty definition: " << item.normalDefines.first
                          << std::endl;
            }
        }
        result += "}";
        overwriteFile(dir + "/" + className + ".java", result);
    }
} // jbindgen