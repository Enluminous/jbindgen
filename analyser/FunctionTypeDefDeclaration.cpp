//
// Created by nettal on 23-11-8.
//

#include "FunctionTypeDefDeclaration.h"
#include "Analyser.h"

#include <utility>
#include <cassert>

namespace jbindgen {

    FunctionTypedefDeclaration::FunctionTypedefDeclaration(VarDeclare function, VarDeclare ret,
                                                           std::string canonicalName)
            : function(std::move(function)),
              ret(std::move(ret)), canonicalName(std::move(canonicalName)) {
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

    FunctionTypedefDeclaration FunctionTypedefDeclaration::visit(CXCursor cursor, Analyser &analyser) {
        assert(cursor.kind == CXCursor_TypedefDecl);
        auto functionName = toString(clang_getCursorSpelling(cursor));

        return visitShared(cursor, functionName, analyser,
                           clang_getPointeeType(clang_getTypedefDeclUnderlyingType(cursor)));
    }

    FunctionTypedefDeclaration FunctionTypedefDeclaration::visitShared(CXCursor cursor, const std::string &functionName,
                                                                       Analyser &analyser, CXType functionType) {
        assert(functionType.kind == CXType_FunctionProto || functionType.kind == CXType_FunctionNoProto);
        auto ret = clang_getResultType(functionType);

        analyser.visitCXCursor(clang_getTypeDeclaration(ret));
        VarDeclare function(functionName, functionType, clang_Type_getSizeOf(functionType), getCommit(cursor), cursor);
        FunctionTypedefDeclaration declaration(function,
                                               VarDeclare(NO_NAME, ret, clang_Type_getSizeOf(ret), NO_COMMIT,
                                                          clang_getTypeDeclaration(ret)),
                                               toStringWithoutConst(clang_getCanonicalType(functionType)));
        intptr_t ptrs[] = {reinterpret_cast<intptr_t>(&declaration), reinterpret_cast<intptr_t>(&analyser)};
        clang_visitChildren(cursor, FunctionTypedefDeclaration::visitChildren, ptrs);
        return declaration;
    }

    FunctionTypedefDeclaration
    FunctionTypedefDeclaration::visitFunctionUnnamedPointer(CXCursor cursor, const std::string &functionName,
                                                            Analyser &analyser) {
        assert(cursor.kind == CXCursor_FieldDecl);
        return visitShared(cursor, functionName, analyser, clang_getPointeeType(clang_getCursorType(cursor)));
    }

    enum CXChildVisitResult
    FunctionTypedefDeclaration::visitChildren(CXCursor cursor, CXCursor parent, CXClientData client_data) {
        auto declaration = reinterpret_cast<FunctionTypedefDeclaration *>(reinterpret_cast<intptr_t *>(client_data)[0]);
        auto analyser = reinterpret_cast<Analyser *>(reinterpret_cast<intptr_t *>(client_data)[1]);
        if (cursor.kind == CXCursor_ParmDecl) {
            auto name = toString(clang_getCursorSpelling(cursor));
            auto type = clang_getCursorType(cursor);
            analyser->visitCXCursor(clang_getTypeDeclaration(type));
            VarDeclare typed(name, type, clang_Type_getSizeOf(type), getCommit(cursor), clang_getTypeDeclaration(type));
            declaration->paras
                    .emplace_back(typed);
        }
        return CXChildVisit_Continue;
    }
} // jbindgen