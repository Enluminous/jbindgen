//
// Created by nettal on 23-11-8.
//

#ifndef JAVABINDGEN_FUNCTIONTYPEDEFDECLARATION_H
#define JAVABINDGEN_FUNCTIONTYPEDEFDECLARATION_H

#include <string>
#include <vector>
#include "AnalyserUtils.h"

namespace jbindgen {
    class FunctionTypedefDeclaration {
        static enum CXChildVisitResult visitChildren(CXCursor cursor,
                                                     CXCursor parent,
                                                     CXClientData client_data);

        const Typed function;
        const Typed ret;
        const std::string canonicalName;
        std::vector<Typed> paras;
    public:

        FunctionTypedefDeclaration(Typed function, Typed ret, std::string canonicalName);

        static FunctionTypedefDeclaration visit(CXCursor cursor);

        friend std::ostream &operator<<(std::ostream &stream, const FunctionTypedefDeclaration &function);
    };

} // jbindgen

#endif //JAVABINDGEN_FUNCTIONTYPEDEFDECLARATION_H
