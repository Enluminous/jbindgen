//
// Created by nettal on 23-11-26.
//

#ifndef JBINDGEN_VARDECLARATION_H
#define JBINDGEN_VARDECLARATION_H

#include <cassert>
#include <utility>
#include "AnalyserUtils.h"

namespace jbindgen {

    class VarDeclaration {
        const VarDeclare varDeclare;
        const bool hasSymbol;
    public:
        static VarDeclaration visit(CXCursor cursor);

        explicit VarDeclaration(VarDeclare varDeclare1,bool hasSymbol);

        friend std::ostream &operator<<(std::ostream &stream, const VarDeclaration &declaration);
    };

} // jbindgen

#endif //JBINDGEN_VARDECLARATION_H
