//
// Created by nettal on 23-11-13.
//

#ifndef JAVABINDGEN_MACRONORMALGENERATOR_H
#define JAVABINDGEN_MACRONORMALGENERATOR_H

#include <string>
#include <utility>
#include <vector>
#include <iostream>
#include "../analyser/NormalMacroDeclaration.h"

namespace jbindgen {
    typedef std::string(*PFN_makeMacro)(const jbindgen::NormalMacroDeclaration &declaration, void *pUserdata);

    class MacroNormalGenerator {
        const PFN_makeMacro makeMacro;
        const std::string header;
        const std::string className;
        const std::string tail;
        const std::string dir;
        const std::vector<NormalMacroDeclaration> macro_declarations;
    public:
        MacroNormalGenerator(PFN_makeMacro makeMacro, std::string header, std::string className, std::string tail,
                             std::string dir, std::vector<NormalMacroDeclaration> &macro_declarations);

        void build() {
            for (auto &item: macro_declarations) {
                if (!item.normalDefines.second.empty()) {
                    //std::cout << item; todo
                }
            }
        }
    };

} // jbindgen

#endif //JAVABINDGEN_MACRONORMALGENERATOR_H
