//
// Created by snownf on 23-11-7.
//

#ifndef JBINDGEN_FUNCTIONLIKEMACRODECLARATION_H
#define JBINDGEN_FUNCTIONLIKEMACRODECLARATION_H

#include <clang-c/Index.h>

#include "AnalyserUtils.h"

namespace jbindgen {
    class FunctionLikeMacroDeclaration : public DeclarationBasic {
        FunctionLikeMacroDeclaration(std::string ori, std::string map, CXCursor cursor);

    public:
        static FunctionLikeMacroDeclaration visit(CXCursor c);

        const std::string ori;
        const std::string map;
        const CXCursor cursor;

        std::string getName() override;

    protected:
        friend std::ostream& operator<<(std::ostream&stream, const FunctionLikeMacroDeclaration&normal);
    };
} // jbindgen

#endif //JBINDGEN_FUNCTIONLIKEMACRODECLARATION_H
