//
// Created by nettal on 23-11-8.
//

#include "FunctionTypedefDeclaration.h"

#include <utility>

namespace jbindgen {
    FunctionTypedefDeclaration::FunctionTypedefDeclaration(std::string name, CXType returnType) : name(std::move(name)),
                                                                                                  returnType(
                                                                                                          returnType) {

    }
} // jbindgen