//
// Created by snownf on 23-11-8.
//

#ifndef JAVABINDGEN_FUNCTIONDECLARATION_H
#define JAVABINDGEN_FUNCTIONDECLARATION_H


#include <string>
#include <vector>
#include "Utils.h"

namespace jbindgen {
    class FunctionDeclaration {
        FunctionDeclaration(Typed function, jbindgen::Typed ret, std::string canonicalName);

        const Typed function;
        std::string canonicalName;
        Typed ret;
        std::vector<Typed> paras;
        void addPara(Typed typed);

        friend std::ostream &operator<<(std::ostream &stream, const FunctionDeclaration &function);
    public:
        static FunctionDeclaration visit(CXCursor c);
    };

}
#endif //JAVABINDGEN_FUNCTIONDECLARATION_H
