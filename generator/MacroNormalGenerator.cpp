//
// Created by nettal on 23-11-13.
//

#include "MacroNormalGenerator.h"

#include <utility>

namespace jbindgen {
    MacroNormalGenerator::MacroNormalGenerator(PFN_makeMacro makeMacro, std::string header, std::string className,
                                               std::string tail, std::string dir, std::string packageName,
                                               std::vector<NormalMacroDeclaration> &macro_declarations)
            : makeMacro(makeMacro), header(std::move(header)), className(std::move(className)),
              tail(std::move(tail)), dir(std::move(dir)), packageName(std::move(packageName)),
              macro_declarations(std::move(macro_declarations)) {
    }
} // jbindgen