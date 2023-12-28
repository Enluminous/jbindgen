//
// Created by snownf on 23-11-9.
//

#ifndef JBINDGEN_GENUTILS_H
#define JBINDGEN_GENUTILS_H

#include <string>
#include "../analyser/StructDeclaration.h"
#include "Value.h"
#include "../analyser/FunctionSymbolDeclaration.h"
#include "../analyser/FunctionTypeDefDeclaration.h"
#include "../analyser/CXCursorMap.h"
#include <functional>

namespace jbindgen {
    const auto JAVA_KEY_WORDS = {"abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class",
                                 "const", "continue", "default", "do", "double", "else", "enum", "extends", "final",
                                 "finally", "float", "for", "goto", "if", "implements", "import", "instanceof", "int",
                                 "interface", "long", "native", "new", "package", "private", "protected", "public",
                                 "return", "short", "static", "strictfp", "super", "switch", "synchronized", "this",
                                 "throw", "throws", "transient", "try", "void", "volatile", "while",
            //                   other literals
                                 "true", "false", "null"};

    void overwriteFile(const std::string &file, const std::string &content, bool silence = false);

    CXType toPointeeType(CXType type);

    bool isArrayType(CXTypeKind kind);

    bool isFunctionProto(CXTypeKind kind);

    bool isTypedefFunction(CXType type);

    bool isPointer(CXTypeKind kind);

    CXType toDeepPointeeOrArrayType(const CXType &type);

    int32_t getPointeeOrArrayDepth(CXType type);

    int64_t getArrayLength(CXType type);

    std::string toCXCursorString(const CXCursorMap &cxCursorMap, const CXCursor &c);

    std::string toCXTypeDeclName(const Analyser &analyser, const CXType &c);

    std::string toCXTypeName(const CXType &c, const Analyser &analyser);

    std::string toCXTypeFunctionPtrName(const CXType &c, const Analyser &analyser);

    std::string generateFakeValueLayout(int64_t byteSize);
} // jbindgen

#endif //JBINDGEN_GENUTILS_H
