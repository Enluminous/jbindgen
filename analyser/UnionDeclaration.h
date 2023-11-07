//
// Created by snownf on 23-11-7.
//

#ifndef JAVABINDGEN_UNIONDECLARATION_H
#define JAVABINDGEN_UNIONDECLARATION_H

#include "StructDeclaration.h"

namespace jbindgen{

    class UnionDeclaration : public StructDeclaration{

        UnionDeclaration(Typed typed);

    public:
        static UnionDeclaration visit(CXCursor c);
    };
}



#endif //JAVABINDGEN_UNIONDECLARATION_H
