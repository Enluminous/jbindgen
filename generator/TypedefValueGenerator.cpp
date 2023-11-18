//
// Created by nettal on 23-11-13.
//

#include "TypedefValueGenerator.h"

#include <utility>

namespace jbindgen {
    TypedefValueGenerator::TypedefValueGenerator(NormalTypedefDeclaration declaration1, std::string defsPackageName,
                                                 std::string defsDir, PFN_def_name name)
            : declaration(std::move(declaration1)), defsPackageName(std::move(defsPackageName)), defsDir(std::move(defsDir)), name(name) {

    }
} // jbindgen