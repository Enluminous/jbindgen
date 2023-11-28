//
// Created by nettal on 23-11-8.
//

#ifndef JBINDGEN_FUNCTIONTYPEDEFDECLARATION_H
#define JBINDGEN_FUNCTIONTYPEDEFDECLARATION_H

#include <string>
#include <vector>
#include "AnalyserUtils.h"

namespace jbindgen {
    class Analyser;

    class FunctionTypedefDeclaration : public DeclarationBasic {
        static enum CXChildVisitResult visitChildren(CXCursor cursor,
                                                     CXCursor parent,
                                                     CXClientData client_data);

    public:
        const VarDeclare function;
        const VarDeclare ret;
        const std::string canonicalName;
        std::vector<VarDeclare> paras;

        std::string getName() override;

        FunctionTypedefDeclaration(VarDeclare function, VarDeclare ret, std::string canonicalName);

        static FunctionTypedefDeclaration visit(CXCursor cursor, Analyser&analyser);

        static FunctionTypedefDeclaration visitFunctionUnnamedPointer(CXCursor cursor, const std::string&functionName,
                                                                      Analyser&analyser);

        friend std::ostream& operator<<(std::ostream&stream, const FunctionTypedefDeclaration&function);

        static FunctionTypedefDeclaration
        visitShared(CXCursor cursor, const std::string&functionName, Analyser&analyser, CXType functionType);
    };
} // jbindgen

#endif //JBINDGEN_FUNCTIONTYPEDEFDECLARATION_H
