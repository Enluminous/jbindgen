//
// Created by nettal on 23-11-13.
//

#ifndef JAVABINDGEN_TYPEDEFVALUEGENERATOR_H
#define JAVABINDGEN_TYPEDEFVALUEGENERATOR_H

#include <string>
#include "../analyser/NormalTypedefDeclaration.h"
#include "TypedefGenerator.h"

namespace jbindgen {

    class TypedefValueGenerator {
        NormalTypedefDeclaration declaration;
        const std::string defsPackageName;
        const std::string defsDir;
        const PFN_def_name name;

    public:
        TypedefValueGenerator(NormalTypedefDeclaration declaration1, std::string defsPackageName, std::string defsDir,
                              PFN_def_name name);
    };

} // jbindgen

#endif //JAVABINDGEN_TYPEDEFVALUEGENERATOR_H
