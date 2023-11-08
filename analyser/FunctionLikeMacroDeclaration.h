//
// Created by snownf on 23-11-7.
//

#ifndef JAVABINDGEN_FUNCTIONLIKEMACRODECLARATION_H
#define JAVABINDGEN_FUNCTIONLIKEMACRODECLARATION_H

namespace jbindgen {

    class FunctionLikeMacroDeclaration {
        FunctionLikeMacroDeclaration(std::string ori, std::string map);

    public:
        static FunctionLikeMacroDeclaration visit(CXCursor c);

    protected:
        std::string ori;
        std::string map;
        friend std::ostream &operator<<(std::ostream &stream, const FunctionLikeMacroDeclaration &normal) {
            stream << "#### FunctionLikeMacro " << std::endl;
            stream << "  " << normal.ori << " "
                   << normal.map << std::endl;
            return stream;
        };
    };



} // jbindgen

#endif //JAVABINDGEN_FUNCTIONLIKEMACRODECLARATION_H
