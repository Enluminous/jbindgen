//
// Created by snownf on 23-11-8.
//

#include "FunctionSymbolDeclaration.h"
#include "Analyser.h"

#include <utility>
#include <cassert>
#include <iostream>

namespace jbindgen {
    std::string const FunctionDeclaration::getName() const {
        return function.name;
    }

    FunctionDeclaration FunctionDeclaration::visit(CXCursor c, Analyser &analyser) {
        assert(c.kind == CXCursor_FunctionDecl);
        auto type = clang_getCursorType(c);
        assert(type.kind == CXType_FunctionProto || type.kind == CXType_FunctionNoProto);
        auto funcName = toString(clang_getCursorSpelling(c));
        const CXType &resultType = clang_getResultType(type);
        auto size = clang_Type_getSizeOf(type);

        analyser.visitCXType(resultType);
        VarDeclare retType(NO_NAME, resultType, size, NO_COMMIT, clang_getTypeDeclaration(resultType));
        VarDeclare functionType(funcName, type, clang_Type_getSizeOf(type), getCommit(c), c);
        FunctionDeclaration def(functionType, retType, toStringWithoutConst(clang_getCanonicalType(type)));

        for (int i = 0; i < clang_getNumArgTypes(type); ++i) {
            auto argType = clang_getArgType(type, i);
            auto name = toString(clang_getCursorSpelling(clang_Cursor_getArgument(c, i)));
            name = name.empty() ? NO_NAME : name;

            analyser.visitCXType(argType);
            VarDeclare par(name, argType, clang_Type_getSizeOf(argType), NO_COMMIT, clang_getTypeDeclaration(argType));
            def.addPara(par);
        }
        return def;
    }


    FunctionDeclaration::FunctionDeclaration(VarDeclare function, jbindgen::VarDeclare ret, std::string canonicalName)
            : function(std::move(function)),
              ret(std::move(ret)),
              canonicalName(std::move(canonicalName)) {
    }

    void FunctionDeclaration::addPara(VarDeclare typed) {
        paras.push_back(std::move(typed));
    }

    std::ostream &operator<<(std::ostream &stream, const FunctionDeclaration &function) {
        stream << "#### Function " << std::endl;
        stream << "  " << function.ret << " " << function.function.name << " ";
        for (const auto &item: function.paras) {
            stream << item << " ";
        }
        stream << std::endl;
        return stream;
    }

    const CXType FunctionDeclaration::getCXType() const {
        return function.type;
    }
}
