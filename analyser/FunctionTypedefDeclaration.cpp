//
// Created by nettal on 23-11-8.
//

#include "FunctionTypedefDeclaration.h"

#include <utility>

namespace jbindgen {
    FunctionTypedefDeclaration::FunctionTypedefDeclaration(std::string functionName, std::string canonicalName,
                                                           Typed ret) : functionName(std::move(functionName)),
                                                                        canonicalName(std::move(canonicalName)),
                                                                        ret(std::move(ret)) {

    }

    std::ostream &operator<<(std::ostream &stream, const FunctionTypedefDeclaration &function) {
        stream << "#### TypedefFunction " << std::endl;
        stream << "  " << function.ret << " " << function.functionName << " ";
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
        FunctionTypedefDeclaration declaration(functionName, toString(clang_getCanonicalType(functionType)),
                                               Typed(NO_NAME, ret, clang_Type_getSizeOf(ret)));
        clang_visitChildren(cursor, FunctionTypedefDeclaration::visitChildren, &declaration);
        return declaration;
    }
} // jbindgen