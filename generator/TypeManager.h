//
// Created by nettal on 23-12-18.
//

#ifndef JBINDGEN_TYPEMANAGER_H
#define JBINDGEN_TYPEMANAGER_H

#include <vector>
#include "Generator.h"

namespace jbindgen {
    class TypeManager {
        std::unordered_map<std::string, bool> alreadyGenerated;
        std::vector<std::string> packageNames;

    public:
        explicit TypeManager(const std::vector<GeneratorConfig> &previousConfigs);

        std::vector<std::string> getPackageNames();

        bool isAlreadyGenerated(const std::string& name);
    };

} // jbindgen

#endif //JBINDGEN_TYPEMANAGER_H
