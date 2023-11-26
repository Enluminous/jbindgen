//
// Created by nettal on 23-11-26.
//

#include "VarDeclaration.h"

namespace jbindgen {
    std::ostream &operator<<(std::ostream &stream, const VarDeclaration &declaration) {
        stream << "#### Var " << declaration.varDeclare.name << " "
               << toStringWithoutConst(declaration.varDeclare.type) << std::endl;
        return stream;
    }

    VarDeclaration::VarDeclaration(VarDeclare varDeclare1) : varDeclare(std::move(varDeclare1)){

    }

    VarDeclaration VarDeclaration::visit(CXCursor cursor) {
        assert(cursor.kind == CXCursor_VarDecl);
        CXType type = clang_getCursorType(cursor);
        auto declare = VarDeclare(toString(clang_getCursorSpelling(cursor)), type,
                                  clang_Type_getSizeOf(type), getCommit(cursor), cursor);
        return VarDeclaration(VarDeclare(declare));
    }
} // jbindgen