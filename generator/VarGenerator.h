//
// Created by nettal on 23-11-26.
//

#ifndef JBINDGEN_VARGENERATOR_H
#define JBINDGEN_VARGENERATOR_H

#include <string>
#include <utility>
#include <vector>
#include <iostream>
#include "../analyser/VarDeclaration.h"

namespace jbindgen {
    typedef std::string(*PFN_makeVar)(const jbindgen::VarDeclaration &declaration, void *pUserdata);

    class VarGenerator {
        const PFN_makeVar makeVar;
        const std::string header;
        const std::string className;
        const std::string tail;
        const std::string dir;
        const std::vector<VarDeclaration> vars;
    public:
        VarGenerator(PFN_makeVar makeVar, std::string header, std::string className,
                     std::string tail, std::string dir, const std::vector<VarDeclaration> &vars);

        void build() {
            for (auto &item: vars) {
                std::cout << item;
            }
        }
    };

} // jbindgen

#endif //JBINDGEN_VARGENERATOR_H
