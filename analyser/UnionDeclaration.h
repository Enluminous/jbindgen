//
// Created by snownf on 23-11-7.
//

#ifndef JBINDGEN_UNIONDECLARATION_H
#define JBINDGEN_UNIONDECLARATION_H

#include "StructDeclaration.h"

namespace jbindgen {

    class UnionDeclaration : public StructDeclaration {


        explicit UnionDeclaration(VarDeclare typed);

    public:
        static UnionDeclaration visit(CXCursor c, Analyser &analyser);

        static UnionDeclaration visitStructUnnamed(CXCursor c, const std::string &name, Analyser &analyser);

        friend std::ostream &operator<<(std::ostream &stream, const UnionDeclaration &str);
    };
}


#endif //JBINDGEN_UNIONDECLARATION_H
