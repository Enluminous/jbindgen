//
// Created by nettal on 23-11-8.
//

#ifndef JAVABINDGEN_FUNCTIONTYPEDEFDECLARATION_H
#define JAVABINDGEN_FUNCTIONTYPEDEFDECLARATION_H

#include <string>
#include <vector>
#include "Utils.h"

namespace jbindgen {
    class FunctionTypedefDeclaration {
        static enum CXChildVisitResult visitChildren(CXCursor cursor,
                                                     CXCursor parent,
                                                     CXClientData client_data) {
            if (cursor.kind == CXCursor_ParmDecl) {
                auto name = toString(clang_getCursorSpelling(cursor));
                auto type = clang_getCursorType(cursor);
                Typed typed(name, type, clang_Type_getSizeOf(type));
                reinterpret_cast<FunctionTypedefDeclaration *>(client_data)->paras
                        .emplace_back(typed);
            }
            return CXChildVisit_Continue;
        }

        const Typed ret;
        const std::string functionName;
        const std::string canonicalName;
        std::vector<Typed> paras;

    public:

        FunctionTypedefDeclaration(std::string functionName, std::string canonicalName, Typed ret);

        static FunctionTypedefDeclaration visit(CXCursor cursor) {
            auto functionName = toString(clang_getCursorSpelling(cursor));
            auto functionType = clang_getPointeeType(clang_getTypedefDeclUnderlyingType(cursor));
            auto ret = clang_getResultType(functionType);
            FunctionTypedefDeclaration declaration(functionName, toString(clang_getCanonicalType(functionType)),
                                                   Typed(NO_NAME, ret, clang_Type_getSizeOf(ret)));
            clang_visitChildren(cursor, FunctionTypedefDeclaration::visitChildren, &declaration);
            return declaration;
        }

        friend std::ostream &operator<<(std::ostream &stream, const FunctionTypedefDeclaration &function);
    };

} // jbindgen

#endif //JAVABINDGEN_FUNCTIONTYPEDEFDECLARATION_H
