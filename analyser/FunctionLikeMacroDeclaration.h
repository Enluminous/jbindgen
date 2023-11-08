//
// Created by snownf on 23-11-7.
//

#ifndef JAVABINDGEN_FUNCTIONLIKEMACRODECLARATION_H
#define JAVABINDGEN_FUNCTIONLIKEMACRODECLARATION_H

#include <clang-c/Index.h>

namespace jbindgen {

    class FunctionLikeMacroDeclaration {

        FunctionLikeMacroDeclaration visit(CXCursor c);
    };

} // jbindgen

#endif //JAVABINDGEN_FUNCTIONLIKEMACRODECLARATION_H
