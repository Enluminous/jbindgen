//
// Created by nettal on 23-11-8.
//

#include "FunctionTypeDefDeclaration.h"
#include "Analyser.h"

#include <utility>
#include <cassert>

namespace jbindgen {
    std::string const FunctionTypedefDeclaration::getName() const {
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

    FunctionTypedefDeclaration::FunctionTypedefDeclaration(VarDeclare function, VarDeclare ret,
                                                           std::string canonicalName)
            : function(std::move(function)),
              ret(std::move(ret)), canonicalName(std::move(canonicalName)) {
        assert(this->function.type.kind == CXType_FunctionProto || this->function.type.kind == CXType_FunctionNoProto);
    }

    std::ostream &operator<<(std::ostream &stream, const FunctionTypedefDeclaration &function) {
        stream << "#### TypedefFunction " << std::endl;
        stream << "  " << function.ret << " " << function.function.name << " ";
        for (const auto &item: function.paras) {
            stream << item << " ";
        }
        stream << std::endl;
        return stream;
    }

    std::shared_ptr<FunctionTypedefDeclaration> FunctionTypedefDeclaration::visit(CXCursor cursor, Analyser &analyser) {
        assert(cursor.kind == CXCursor_TypedefDecl);
        auto functionName = toString(clang_getCursorSpelling(cursor));
        auto empty = std::shared_ptr<StructDeclaration>{};
        return visitShared(cursor, functionName, analyser,
                           clang_getPointeeType(clang_getTypedefDeclUnderlyingType(cursor)));
    }

    std::shared_ptr<FunctionTypedefDeclaration>
    FunctionTypedefDeclaration::visitFunctionUnnamedPointer(CXCursor cursor,
                                                            const std::shared_ptr<StructDeclaration> &parent,
                                                            Analyser &analyser,
                                                            const std::string &candidateName) {
        assert(cursor.kind == CXCursor_FieldDecl);
        assert(parent.get() != nullptr);
        auto fun = visitShared(cursor, NO_NAME, analyser,
                               clang_getPointeeType(clang_getCursorType(cursor)));
        fun->candidateName = candidateName;
        fun->parent = parent;
        return fun;
    }

    std::shared_ptr<FunctionTypedefDeclaration>
    FunctionTypedefDeclaration::visitShared(CXCursor cursor, const std::string &functionName, Analyser &analyser,
                                            CXType functionType) {
        assert(functionType.kind == CXType_FunctionProto || functionType.kind == CXType_FunctionNoProto);
        auto ret = clang_getResultType(functionType);

        VarDeclare function(functionName, functionType, clang_Type_getSizeOf(functionType), getCommit(cursor), cursor);
        std::shared_ptr<FunctionTypedefDeclaration> declaration = std::make_shared<FunctionTypedefDeclaration>
                (function, VarDeclare(NO_NAME, ret, clang_Type_getSizeOf(ret), NO_COMMIT,
                                      clang_getTypeDeclaration(ret)),
                 toStringWithoutConst(clang_getCanonicalType(functionType)));
        analyser.updateCXCursorMap(cursor, declaration);
        intptr_t ptrs[] = {reinterpret_cast<intptr_t>(&declaration), reinterpret_cast<intptr_t>(&analyser)};
        clang_visitChildren(cursor, FunctionTypedefDeclaration::visitChildren, ptrs);
        return declaration;
    }

    void FunctionTypedefDeclaration::addUsage(const std::string &c) {
        usages.emplace_back(c);
    }


    enum CXChildVisitResult
    FunctionTypedefDeclaration::visitChildren(CXCursor cursor, CXCursor parent, CXClientData client_data) {
        auto declaration = reinterpret_cast<std::shared_ptr<FunctionTypedefDeclaration> *>(reinterpret_cast<intptr_t *>(client_data)[0]);
        auto analyser = reinterpret_cast<Analyser *>(reinterpret_cast<intptr_t *>(client_data)[1]);
        if (cursor.kind == CXCursor_ParmDecl) {
            auto name = toString(clang_getCursorSpelling(cursor));
            if (name.empty()) {
                name = NO_NAME;
            }
            auto type = clang_getCursorType(cursor);
            analyser->visitCXCursor(clang_getTypeDeclaration(type));
            VarDeclare typed(name, type, clang_Type_getSizeOf(type), getCommit(cursor), clang_getTypeDeclaration(type));
            (*declaration)->paras
                    .emplace_back(typed);
            if (isNoCXCursorFunction(type)) {
                analyser->visitNoCursorFunction(type, *declaration,
                                                makeUnnamedParaNamed((*declaration)->paras.size()))->addUsage(name);
            }
        }
        return CXChildVisit_Continue;
    }

    const CXType FunctionTypedefDeclaration::getCXType() const {
        return function.type;
    }
} // jbindgen
