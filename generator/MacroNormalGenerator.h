//
// Created by nettal on 23-11-13.
//

#ifndef JBINDGEN_MACRONORMALGENERATOR_H
#define JBINDGEN_MACRONORMALGENERATOR_H

#include <string>
#include <utility>
#include <vector>
#include <iostream>
#include <format>
#include "../analyser/NormalMacroDeclaration.h"
#include "GenUtils.h"

namespace jbindgen {
    typedef std::string(*PFN_makeMacro)(const jbindgen::NormalMacroDeclaration &declaration, void *pUserdata,
                                        const std::vector<NormalMacroDeclaration> *allDeclaration);

    class MacroNormalGenerator {
        const PFN_makeMacro makeMacro;
        const std::string header;
        const std::string className;
        const std::string tail;
        const std::string dir;
        const std::string packageName;
        const std::vector<NormalMacroDeclaration> macro_declarations;
    public:
        MacroNormalGenerator(PFN_makeMacro makeMacro, std::string header, std::string className, std::string tail,
                             std::string dir, std::string packageName,
                             std::vector<NormalMacroDeclaration> &macro_declarations);

        void build(void *userData) {
            std::string result;
            result += std::vformat("package {};\n"
                                   "\n"
                                   "public class {} {{\n",
                                   std::make_format_args(packageName, className));
            for (auto &item: macro_declarations) {
                if (!item.normalDefines.second.empty()) {
                    std::string toAdd = "    ";
                    auto core = makeMacro(item, userData, &macro_declarations);
                    if (std::equal(core.begin(), core.end(), "IGNORE")) {
                        std::cout << "MacroNormalGenerator: ignore empty dependent definition:"
                                  << item.normalDefines.first << std::endl;
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
    };

} // jbindgen

#endif //JBINDGEN_MACRONORMALGENERATOR_H
