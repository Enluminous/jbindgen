//
// Created by nettal on 23-11-13.
//

#include "FunctionProtoTypeGenerator.h"

#include <utility>

namespace jbindgen {
    FunctionProtoTypeGenerator::FunctionProtoTypeGenerator(FunctionTypedefDeclaration declaration, std::string dir,
                                                           std::string defsCallbackPackageName,
                                                           std::string defCallbackDir,
                                                           std::string nativeFunctionPackageName,
                                                           PFN_makeProtoType makeProtoType) :
            declaration(std::move(declaration)),
            dir(std::move(dir)),
            defCallbackDir(std::move(defCallbackDir)),
            defsCallbackPackageName(std::move(defsCallbackPackageName)),
            nativeFunctionPackageName(std::move(nativeFunctionPackageName)),
            makeProtoType(makeProtoType) {}
} // jbindgen