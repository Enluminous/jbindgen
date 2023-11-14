//
// Created by nettal on 23-11-13.
//

#include "FunctionProtoTypeGenerator.h"

namespace jbindgen {
    FunctionProtoTypeGenerator::FunctionProtoTypeGenerator(FunctionTypedefDeclaration declaration, std::string dir,
                                                           PFN_makeProtoType makeProtoType) : declaration(std::move(declaration)),
                                                                                              dir(std::move(dir)),
                                                                                              makeProtoType(makeProtoType) {}
} // jbindgen