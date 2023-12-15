//
// Created by nettal on 23-12-15.
//

#ifndef JBINDGEN_SYMBOLGENERATOR_H
#define JBINDGEN_SYMBOLGENERATOR_H

#include "Generator.h"

namespace jbindgen {

    class SymbolGenerator {
        const struct config::SymbolLookup symbolsConfig;
        const std::string functionUtilsPackageName;

    public:
        SymbolGenerator(struct config::SymbolLookup symbolsConfig, std::string functionUtilsPackageName);

        std::string makeSymbol();

        void build();
    };

} // jbindgen

#endif //JBINDGEN_SYMBOLGENERATOR_H
