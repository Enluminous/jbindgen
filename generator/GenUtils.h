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
#include "../shared/CXCursorMap.h"

namespace jbindgen {
    void overwriteFile(const std::string &file, const std::string &content);

    CXType toPointeeType(CXType type, CXCursor c);

    bool isArrayType(CXTypeKind kind);

    CXType toDeepPointeeOrArrayType(const CXType &type, const CXCursor &c);

    int32_t getPointeeOrArrayDepth(CXType type);

    int64_t getArrayLength(CXType type);

    std::string toCXCursorString(const CXCursorMap &cxCursorMap, const CXCursor &c);

    std::string toCXTypeString(const Analyser &analyser, const CXType &c);

    std::string generateFakeValueLayout(int64_t byteSize);
} // jbindgen

#endif //JBINDGEN_GENUTILS_H
