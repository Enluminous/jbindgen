//
// Created by nettal on 23-11-26.
//

#include "VarDeclaration.h"
#include "Analyser.h"

namespace jbindgen {
    std::ostream &operator<<(std::ostream &stream, const VarDeclaration &declaration) {
        stream << "#### Var " << declaration.varDeclare.name << " "
               << toStringWithoutConst(declaration.varDeclare.type) << std::endl;
        return stream;
    }

    VarDeclaration::VarDeclaration(VarDeclare varDeclare1, bool hasSymbol) :
            varDeclare(std::move(varDeclare1)),
            hasSymbol(hasSymbol) {

    }

    std::string const VarDeclaration::getName() const {
        assertAppend(0, "should not call FunctionLikeMacroDeclaration::getName()")
        return varDeclare.name;
    }

    VarDeclaration VarDeclaration::visit(CXCursor cursor, Analyser &analyser) {
        assertAppend(cursor.kind == CXCursor_VarDecl, "VarDeclaration::visit have wrong cursor, current is :" +
                                                      toStringIfNullptr(clang_getCursorKindSpelling(cursor.kind)));
        CXType type = clang_getCursorType(cursor);
        analyser.visitCXType(type);
        auto declare = VarDeclare(toString(clang_getCursorSpelling(cursor)), type,
                                  clang_Type_getSizeOf(type), getComment(cursor), cursor);
        return VarDeclaration(VarDeclare(declare), clang_getCursorLinkage(cursor) == CXLinkage_External);
    }

    const CXType VarDeclaration::getCXType() const {
        return varDeclare.type;
    }

    size_t VarDeclaration::visitResult() const {
        return varDeclare.byteSize;
    }
} // jbindgen