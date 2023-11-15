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

        const VarDeclare function;
        const VarDeclare ret;
        const std::string canonicalName;
        std::vector<VarDeclare> paras;

    public:
        FunctionTypedefDeclaration(VarDeclare function, VarDeclare ret, std::string canonicalName);

        static FunctionTypedefDeclaration visit(CXCursor cursor);

        static FunctionTypedefDeclaration visitStructFunctionUnnamed(CXCursor cursor, const std::string&functionName);

        friend std::ostream& operator<<(std::ostream&stream, const FunctionTypedefDeclaration&function);
    };
} // jbindgen

#endif //JAVABINDGEN_FUNCTIONTYPEDEFDECLARATION_H
