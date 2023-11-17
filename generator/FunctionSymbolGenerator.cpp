//
// Created by nettal on 23-11-13.
//

#include "FunctionSymbolGenerator.h"

#include <utility>

namespace jbindgen {
    FunctionSymbolGenerator::FunctionSymbolGenerator(PFN_makeFunction makeFunction,
                                                     std::string functionLoader, std::string header, std::string tail,
                                                     std::string dir,
                                                     std::vector<FunctionDeclaration> function_declarations,
                                                     std::string className) : makeFunction(makeFunction), functionLoader(std::move(functionLoader)), dir(std::move(
            dir)), function_declarations(std::move(function_declarations)), header(std::move(header)), tail(std::move(
            tail)), className(std::move(className)) {
    }
} // jbindgen