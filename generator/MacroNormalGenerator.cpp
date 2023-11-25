//
// Created by nettal on 23-11-13.
//

#include "MacroNormalGenerator.h"

namespace jbindgen {
    MacroNormalGenerator::MacroNormalGenerator(PFN_makeMacro makeMacro, std::string header, std::string className,
                                               std::string tail, std::string dir,
                                               std::vector<NormalMacroDeclaration> &macro_declarations)
            : makeMacro(makeMacro), header(std::move(header)), className(std::move(className)),
              tail(std::move(tail)), dir(std::move(dir)), macro_declarations(std::move(macro_declarations)) {
    }
} // jbindgen