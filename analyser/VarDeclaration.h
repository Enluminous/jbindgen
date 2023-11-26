//
// Created by nettal on 23-11-26.
//

#ifndef JAVABINDGEN_VARDECLARATION_H
#define JAVABINDGEN_VARDECLARATION_H

#include <cassert>
#include <utility>
#include "AnalyserUtils.h"

namespace jbindgen {

    class VarDeclaration {
        const VarDeclare varDeclare;

    public:
        static VarDeclaration visit(CXCursor cursor);

        explicit VarDeclaration(VarDeclare varDeclare1);

        friend std::ostream &operator<<(std::ostream &stream, const VarDeclaration &declaration);
    };

} // jbindgen

#endif //JAVABINDGEN_VARDECLARATION_H
