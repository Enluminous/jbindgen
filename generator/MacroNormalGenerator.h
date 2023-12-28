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
    typedef std::function<std::string(const jbindgen::NormalMacroDeclaration &declaration)> FN_makeMacro;

    class MacroNormalGenerator {
        const FN_makeMacro makeMacro;
        const std::string header;
        const std::string className;
        const std::string tail;
        const std::string dir;
        const std::string packageName;
        const std::vector<NormalMacroDeclaration> macro_declarations;
    public:
        MacroNormalGenerator(FN_makeMacro makeMacro, std::string header, std::string className, std::string tail,
                             std::string dir, std::string packageName,
                             const std::vector<NormalMacroDeclaration> &macro_declarations);

        void build();
    };

} // jbindgen

#endif //JBINDGEN_MACRONORMALGENERATOR_H
