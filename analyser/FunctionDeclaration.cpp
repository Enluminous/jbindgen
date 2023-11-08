//
// Created by snownf on 23-11-8.
//

#include "FunctionDeclaration.h"

#include <utility>

namespace jbindgen {
    FunctionDeclaration FunctionDeclaration::visit(CXCursor c) {
        auto type = clang_getCursorType(c);
        auto funcName = toString(clang_getCursorSpelling(c));
        auto retName = toString((clang_getTypeSpelling(clang_getResultType(type))));
        auto size = clang_Type_getSizeOf(type);
        Typed retType(retName, type, size);
        FunctionDeclaration def(funcName, retType, toString(clang_getTypeSpelling(clang_getCanonicalType(type))));

        for (int i = 0; i < clang_getNumArgTypes(type); ++i) {
            auto argType = clang_getArgType(type, i);
            auto name = toString(clang_getCursorSpelling(clang_Cursor_getArgument(c, i)));
            name = name.empty() ? "#noName#" : name;
            Typed par(name, argType, clang_Type_getSizeOf(argType));
            def.addPara(par);
        }
        return def;
    }

    FunctionDeclaration::FunctionDeclaration(std::string functionName, jbindgen::Typed ret,
                                             std::string canonicalName) : functionName(std::move(functionName)),
                                                                          ret(std::move(ret)),
                                                                          canonicalName(std::move(canonicalName)) {

    }

    void FunctionDeclaration::addPara(Typed typed) {
        paras.push_back(std::move(typed));
    }
}