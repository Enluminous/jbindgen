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
        if (parent != nullptr) {
            assert(usages.size() == 1);
            if (std::equal(usages[0].begin(), usages[0].end(), NO_NAME))
                return parent->getName() + "$" + candidateName;
            return parent->getName() + "$" + usages[0];
        }
        return function.name;
    }

    std::shared_ptr<FunctionDeclaration> FunctionDeclaration::visit(CXCursor c, Analyser &analyser) {
        assert(c.kind == CXCursor_FunctionDecl);
        auto type = clang_getCursorType(c);
        assert(type.kind == CXType_FunctionProto || type.kind == CXType_FunctionNoProto);
        return visitShared(c, type, analyser, toString(clang_getCursorSpelling(c)));
    }


    FunctionDeclaration::FunctionDeclaration(VarDeclare function, jbindgen::VarDeclare ret, std::string canonicalName)
            : function(std::move(function)),
              ret(std::move(ret)),
              canonicalName(std::move(canonicalName)) {
        assert(this->function.type.kind == CXType_FunctionProto || this->function.type.kind == CXType_FunctionNoProto);
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

    std::shared_ptr<FunctionDeclaration> FunctionDeclaration::visitNoCXCursor(const CXType &cxType, Analyser &analyser,
                                                                              const std::shared_ptr<DeclarationBasic> &parent) {
        assert(cxType.kind == CXType_Pointer || cxType.kind == CXType_BlockPointer);
        auto type = clang_getPointeeType(cxType);
        auto s = toStringWithoutConst(type);
        assert(type.kind == CXType_FunctionProto || type.kind == CXType_FunctionNoProto);
        CXCursor c = clang_getTypeDeclaration(cxType);//it almost always CXCursor_NoDeclFound
        auto fun = visitShared(c, type, analyser, NO_NAME);
        fun->parent = parent;
        return fun;
    }

    bool isNoCXCursorFunction(CXType cxType) {
        if (cxType.kind == CXType_Pointer || cxType.kind == CXType_BlockPointer) {
            auto type = clang_getPointeeType(cxType);
            if (type.kind == CXType_FunctionProto || type.kind == CXType_FunctionNoProto)
                return true;
        }
        return false;
    }

    std::shared_ptr<FunctionDeclaration>
    FunctionDeclaration::visitShared(const CXCursor &c, const CXType &type, Analyser &analyser,
                                     const std::string &functionName) {
        assert(type.kind == CXType_FunctionProto || type.kind == CXType_FunctionNoProto);

        const auto& funcName = functionName;
        const CXType &resultType = clang_getResultType(type);
        auto size = clang_Type_getSizeOf(type);

        analyser.visitCXType(resultType);
        VarDeclare retType(NO_NAME, resultType, size, NO_COMMIT, clang_getTypeDeclaration(resultType));
        VarDeclare functionType(funcName, type, clang_Type_getSizeOf(type), getCommit(c), c);
        std::shared_ptr<FunctionDeclaration> def = std::make_shared<FunctionDeclaration>
                (FunctionDeclaration(functionType, retType, toStringWithoutConst(clang_getCanonicalType(type))));
        for (int i = 0; i < clang_getNumArgTypes(type); ++i) {
            auto argType = clang_getArgType(type, i);
            auto name = toString(clang_getCursorSpelling(clang_Cursor_getArgument(c, i)));
            name = name.empty() ? NO_NAME : name;
            if (isNoCXCursorFunction(argType)) {
                analyser.visitNoCursorFunction(argType, def,
                                               "para" + std::to_string(i))->addUsage(name);
            }
            analyser.visitCXType(argType);
            VarDeclare par(name, argType, clang_Type_getSizeOf(argType), NO_COMMIT, clang_getTypeDeclaration(argType));
            def->addPara(par);
        }
        return def;
    }

    void FunctionDeclaration::addUsage(const std::string &c) {
        usages.emplace_back(c);
    }
}
