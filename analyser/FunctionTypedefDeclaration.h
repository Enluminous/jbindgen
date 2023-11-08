//
// Created by nettal on 23-11-8.
//

#ifndef JAVABINDGEN_FUNCTIONTYPEDEFDECLARATION_H
#define JAVABINDGEN_FUNCTIONTYPEDEFDECLARATION_H

#include <string>
#include <vector>
#include "Utils.h"

namespace jbindgen {
    class FunctionTypedefDeclaration {
        static enum CXChildVisitResult visitChildren(CXCursor cursor,
                                                     CXCursor parent,
                                                     CXClientData client_data);

        const Typed ret;
        const std::string functionName;
        const std::string canonicalName;
        std::vector<Typed> paras;
        const std::string commit;
    public:

        FunctionTypedefDeclaration(std::string functionName, std::string canonicalName, Typed ret,std::string commit);

        static FunctionTypedefDeclaration visit(CXCursor cursor);

        friend std::ostream &operator<<(std::ostream &stream, const FunctionTypedefDeclaration &function);
    };

} // jbindgen

#endif //JAVABINDGEN_FUNCTIONTYPEDEFDECLARATION_H
