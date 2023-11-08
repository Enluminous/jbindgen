//
// Created by nettal on 23-11-8.
//

#include "FunctionTypedefDeclaration.h"

#include <utility>

namespace jbindgen {

    FunctionTypedefDeclaration::FunctionTypedefDeclaration(Typed function, Typed ret, std::string canonicalName)
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

    FunctionTypedefDeclaration FunctionTypedefDeclaration::visit(CXCursor cursor) {
        auto functionName = toString(clang_getCursorSpelling(cursor));
        auto functionType = clang_getPointeeType(clang_getTypedefDeclUnderlyingType(cursor));
        auto ret = clang_getResultType(functionType);
        Typed function(functionName, functionType, clang_Type_getSizeOf(functionType), getCommit(cursor));
        FunctionTypedefDeclaration declaration(function,
                                               Typed(NO_NAME, ret, clang_Type_getSizeOf(ret), NO_COMMIT),
                                               toString(clang_getCanonicalType(functionType)));
        clang_visitChildren(cursor, FunctionTypedefDeclaration::visitChildren, &declaration);
        return declaration;
    }

    enum CXChildVisitResult
    FunctionTypedefDeclaration::visitChildren(CXCursor cursor, CXCursor parent, CXClientData client_data) {
        if (cursor.kind == CXCursor_ParmDecl) {
            auto name = toString(clang_getCursorSpelling(cursor));
            auto type = clang_getCursorType(cursor);
            Typed typed(name, type, clang_Type_getSizeOf(type), getCommit(cursor));
            reinterpret_cast<FunctionTypedefDeclaration *>(client_data)->paras
                    .emplace_back(typed);
        }
        return CXChildVisit_Continue;
    }
} // jbindgen