//
// Created by snownf on 23-11-7.
//

#ifndef JBINDGEN_UNIONDECLARATION_H
#define JBINDGEN_UNIONDECLARATION_H

#include "StructDeclaration.h"

namespace jbindgen {
    class UnionDeclaration : public StructDeclaration {
    public:
        using StructDeclaration::StructDeclaration;

        static std::shared_ptr<UnionDeclaration> visit(CXCursor c, Analyser &analyser);

        static std::shared_ptr<UnionDeclaration>
        visitInternalUnion(CXCursor c, std::shared_ptr<StructDeclaration> parent, Analyser &analyser,
                           const std::string &candidateName);

        friend std::ostream &operator<<(std::ostream &stream, const UnionDeclaration &str);
    };
}


#endif //JBINDGEN_UNIONDECLARATION_H
