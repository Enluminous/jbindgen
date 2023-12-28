//
// Created by nettal on 23-11-26.
//

#ifndef JBINDGEN_VARGENERATOR_H
#define JBINDGEN_VARGENERATOR_H

#include <string>
#include <utility>
#include <vector>
#include <iostream>
#include <functional>
#include <memory>
#include "../analyser/VarDeclaration.h"
#include "TypeManager.h"

namespace jbindgen {
    typedef std::function<std::string(const jbindgen::VarDeclaration &declaration)> FN_makeVar;

    class VarGenerator {
        const FN_makeVar makeVar;
        const std::string header;
        const std::string className;
        const std::string packageName;
        const std::string tail;
        const std::string dir;
        const std::string symbolLoader;
        const std::vector<VarDeclaration> vars;
        const Analyser &analyser;
        const std::shared_ptr<TypeManager> typeManager;
        const GeneratorConfig &config;
    public:
        VarGenerator(FN_makeVar makeVar, std::string header, std::string className, std::string packageName,
                     std::string tail, std::string dir, const std::vector<VarDeclaration> &vars,
                     const Analyser &analyser,
                     std::string symbolLoader, const std::shared_ptr<TypeManager> &typeManager,
                     const GeneratorConfig &config);

        void build();
    };

} // jbindgen

#endif //JBINDGEN_VARGENERATOR_H
