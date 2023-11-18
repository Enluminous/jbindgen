//
// Created by nettal on 23-11-13.
//

#ifndef JAVABINDGEN_TYPEDEFVALUEGENERATOR_H
#define JAVABINDGEN_TYPEDEFVALUEGENERATOR_H

#include <vector>
#include <string>
#include "../analyser/NormalTypedefDeclaration.h"

namespace jbindgen {
    typedef std::string(*PFN_def_rename)(const std::string &name, const NormalTypedefDeclaration& declaration,void *pUserdata);

    class TypedefValueGenerator {
        const std::vector<NormalTypedefDeclaration> enumDeclarations;
        const std::string defsPackageName;
        const std::string defsDir;
        const PFN_def_rename rename;
    };

} // jbindgen

#endif //JAVABINDGEN_TYPEDEFVALUEGENERATOR_H
