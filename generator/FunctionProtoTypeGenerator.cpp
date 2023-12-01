//
// Created by nettal on 23-11-13.
//

#include "FunctionProtoTypeGenerator.h"

#include <utility>

namespace jbindgen {
    FunctionProtoTypeGenerator::FunctionProtoTypeGenerator(FunctionTypedefDeclaration declaration,
                                                           const Analyser &analyser, std::string dir,
                                                           std::string defsCallbackPackageName,
                                                           std::string defCallbackDir,
                                                           std::string nativeFunctionPackageName,
                                                           PFN_makeFunction makeProtoType) :
            declaration(std::move(declaration)),
            dir(std::move(dir)),
            defCallbackDir(std::move(defCallbackDir)),
            defsCallbackPackageName(std::move(defsCallbackPackageName)),
            nativeFunctionPackageName(std::move(nativeFunctionPackageName)),
            makeFunction(makeProtoType),
            analyser(analyser) {}
} // jbindgen