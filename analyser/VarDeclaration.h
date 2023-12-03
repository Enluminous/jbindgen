//
// Created by nettal on 23-11-26.
//

#ifndef JBINDGEN_VARDECLARATION_H
#define JBINDGEN_VARDECLARATION_H

#include <cassert>
#include <utility>
#include "AnalyserUtils.h"

namespace jbindgen {
    class Analyser;

    class VarDeclaration : public DeclarationBasic {
    public:
        const bool hasSymbol;
        const VarDeclare varDeclare;

        static VarDeclaration visit(CXCursor cursor, Analyser&analyser);

        explicit VarDeclaration(VarDeclare varDeclare1, bool hasSymbol);

        friend std::ostream& operator<<(std::ostream&stream, const VarDeclaration&declaration);

        [[nodiscard]] std::string const getName() const override;

        [[nodiscard]] const CXType getCXType() const override;
    };
} // jbindgen

#endif //JBINDGEN_VARDECLARATION_H
