//
// Created by snownf on 23-11-9.
//

#ifndef JBINDGEN_GENUTILS_H
#define JBINDGEN_GENUTILS_H

#include <string>
#include <cassert>
#include "../analyser/StructDeclaration.h"
#include "Value.h"
#include "../analyser/FunctionSymbolDeclaration.h"
#include "../analyser/FunctionTypeDefDeclaration.h"
#include "../analyser/CXCursorMap.h"
#include <functional>

namespace jbindgen {
    void overwriteFile(const std::string &file, const std::string &content);

    CXType toPointeeType(CXType type);

    bool isArrayType(CXTypeKind kind);

    CXType toDeepPointeeOrArrayType(const CXType &type);

    int32_t getPointeeOrArrayDepth(CXType type);

    int64_t getArrayLength(CXType type);

    std::string toCXCursorString(const CXCursorMap &cxCursorMap, const CXCursor &c);

    std::string toCXTypeDeclName(const Analyser &analyser, const CXType &c);

    std::string toCXTypeName(const CXType &c, const Analyser &analyser);

    std::string generateFakeValueLayout(int64_t byteSize);
} // jbindgen

#endif //JBINDGEN_GENUTILS_H
