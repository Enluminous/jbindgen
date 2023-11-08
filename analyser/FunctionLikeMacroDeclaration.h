//
// Created by snownf on 23-11-7.
//

#ifndef JAVABINDGEN_FUNCTIONLIKEMACRODECLARATION_H
#define JAVABINDGEN_FUNCTIONLIKEMACRODECLARATION_H

#include <clang-c/Index.h>

namespace jbindgen {

    class FunctionLikeMacroDeclaration {
        FunctionLikeMacroDeclaration(std::string ori, std::string map);

    public:
        static FunctionLikeMacroDeclaration visit(CXCursor c);

    protected:
        std::string ori;
        std::string map;
        friend std::ostream &operator<<(std::ostream &stream, const FunctionLikeMacroDeclaration &normal);;
    };



} // jbindgen

#endif //JAVABINDGEN_FUNCTIONLIKEMACRODECLARATION_H
