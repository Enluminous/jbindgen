//
// Created by snownf on 23-11-9.
//

#ifndef JAVABINDGEN_GENUTILS_H
#define JAVABINDGEN_GENUTILS_H

#include <string>
#include "../analyser/StructDeclaration.h"
#include "Value.h"
#include "../analyser/FunctionSymbolDeclaration.h"
#include "../analyser/FunctionProtoTypeDeclaration.h"

namespace jbindgen {
    void overwriteFile(const std::string &file, const std::string &content);

} // jbindgen

#endif //JAVABINDGEN_GENUTILS_H
