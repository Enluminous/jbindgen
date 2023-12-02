//
// Created by nettal on 23-11-26.
//

#include "VarGenerator.h"

namespace jbindgen {
    VarGenerator::VarGenerator(const FN_makeVar makeVar, std::string header, std::string className, std::string tail,
                               std::string dir, const std::vector<VarDeclaration> &vars)
            : makeVar(makeVar), header(std::move(header)), className(std::move(className)), tail(std::move(tail)),
              dir(std::move(dir)), vars(vars) {
    }

    void VarGenerator::build() {
        for (auto &item: vars) {
            std::cout << item;
        }
    }
} // jbindgen