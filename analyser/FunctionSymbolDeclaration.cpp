//
// Created by snownf on 23-11-8.
//

#include "FunctionSymbolDeclaration.h"
#include "Analyser.h"
#include "../generator/GenUtils.h"

#include <utility>
#include <cassert>
#include <iostream>

namespace jbindgen {
    std::string const FunctionSymbolDeclaration::getName() const {
        if (parent != nullptr) {
            if (usages.empty()) {
                return parent->getName() + "$" + candidateName;
            }
            if (std::equal(usages[0].begin(), usages[0].end(), NO_NAME))
                //usage is NO_NAME
                return parent->getName() + "$" + candidateName;
            return parent->getName() + "$" + usages[0];
        }
        assert(!std::equal(function.name.begin(), function.name.end(), NO_NAME));
        assert(!function.name.empty());
        return function.name;
    }

    std::shared_ptr<FunctionSymbolDeclaration> FunctionSymbolDeclaration::visit(CXCursor c, Analyser &analyser) {
        assert(c.kind == CXCursor_FunctionDecl);
        auto type = clang_getCursorType(c);
        assert(isFunctionProto(type.kind));
        return visitShared(c, type, analyser, toString(clang_getCursorSpelling(c)));
    }


    FunctionSymbolDeclaration::FunctionSymbolDeclaration(VarDeclare function, jbindgen::VarDeclare ret, std::string canonicalName)
            : function(std::move(function)),
              ret(std::move(ret)),
              canonicalName(std::move(canonicalName)) {
        assert(isFunctionProto(this->function.type.kind));
    }

    void FunctionSymbolDeclaration::addPara(VarDeclare typed) {
        paras.push_back(std::move(typed));
    }

    std::ostream &operator<<(std::ostream &stream, const FunctionSymbolDeclaration &function) {
        stream << "#### Function " << std::endl;
        stream << "  " << function.ret << " " << function.function.name << " ";
        for (const auto &item: function.paras) {
            stream << item << " ";
        }
        stream << std::endl;
        return stream;
    }

    const CXType FunctionSymbolDeclaration::getCXType() const {
        return function.type;
    }

    std::shared_ptr<FunctionSymbolDeclaration> FunctionSymbolDeclaration::visitNoCXCursor(const CXType &cxType, Analyser &analyser,
                                                                                          const std::shared_ptr<DeclarationBasic> &parent,
                                                                                          const std::string &candidateName) {
        assert(isPointer(cxType.kind));
        auto type = toDeepPointeeOrArrayType(cxType);
        auto s = toStringWithoutConst(type);
        assert(isFunctionProto(type.kind));
        CXCursor c = clang_getTypeDeclaration(cxType);//it almost always CXCursor_NoDeclFound
        auto fun = visitShared(c, type, analyser, NO_NAME);
        assert(parent.get() != nullptr);
        fun->parent = parent;
        fun->candidateName = candidateName;
        return fun;
    }

    bool isNoCXCursorFunction(CXType cxType) {
        if (isPointer(cxType.kind)) {
            auto type = toDeepPointeeOrArrayType(cxType);
            if (isFunctionProto(type.kind))
                return true;
        }
        return false;
    }

    std::shared_ptr<FunctionSymbolDeclaration>
    FunctionSymbolDeclaration::visitShared(const CXCursor &c, const CXType &type, Analyser &analyser,
                                           const std::string &functionName) {
        assert(isFunctionProto(type.kind));

        const CXType &resultType = clang_getResultType(type);
        auto size = clang_Type_getSizeOf(type);

        analyser.visitCXType(resultType);
        VarDeclare retType(NO_NAME, resultType, size, NO_COMMENT, clang_getTypeDeclaration(resultType));
        VarDeclare functionType(functionName, type, clang_Type_getSizeOf(type), getComment(c), c);
        std::shared_ptr<FunctionSymbolDeclaration> def = std::make_shared<FunctionSymbolDeclaration>
                (FunctionSymbolDeclaration(functionType, retType, toStringWithoutConst(clang_getCanonicalType(type))));
        if (c.kind < CXCursor_FirstInvalid || c.kind > CXCursor_LastInvalid)
            analyser.updateCXCursorMap(c, def);
        for (int i = 0; i < clang_getNumArgTypes(type); ++i) {
            auto argType = clang_getArgType(type, i);
            auto name = toString(clang_getCursorSpelling(clang_Cursor_getArgument(c, i)));
            name = name.empty() ? NO_NAME : name;
            if (isNoCXCursorFunction(argType)) {
                analyser.visitNoCursorFunction(argType, def,
                                               makeUnnamedParaNamed(i))->addUsage(name);
            }
            analyser.visitCXType(argType);
            VarDeclare par(name, argType, clang_Type_getSizeOf(argType), NO_COMMENT, clang_getTypeDeclaration(argType));
            def->addPara(par);
        }
        return def;
    }

    void FunctionSymbolDeclaration::addUsage(const std::string &c) {
        usages.emplace_back(c);
    }

    size_t FunctionSymbolDeclaration::visitResult() const {
        return function.byteSize;
    }
}
