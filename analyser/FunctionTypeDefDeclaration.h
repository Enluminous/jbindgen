//
// Created by nettal on 23-11-8.
//

#ifndef JBINDGEN_FUNCTIONTYPEDEFDECLARATION_H
#define JBINDGEN_FUNCTIONTYPEDEFDECLARATION_H

#include <memory>
#include <string>
#include <vector>
#include "AnalyserUtils.h"
#include "FunctionSymbolDeclaration.h"

namespace jbindgen {
    class StructDeclaration;

    class Analyser;

    class FunctionTypedefDeclaration : public FunctionSymbolDeclaration {
        static enum CXChildVisitResult visitChildren(CXCursor cursor,
                                                     CXCursor parent,
                                                     CXClientData client_data);

    public:
        using FunctionSymbolDeclaration::FunctionSymbolDeclaration;

        static std::shared_ptr<FunctionTypedefDeclaration> visit(CXCursor cursor, Analyser &analyser);

        static std::shared_ptr<FunctionTypedefDeclaration>
        visitFunctionUnnamedPointer(CXCursor cursor, const std::shared_ptr<StructDeclaration> &parent,
                                    Analyser &analyser,
                                    const std::string &candidateName);

        static std::shared_ptr<FunctionTypedefDeclaration>
        visitFunctionPointerWithTargetFunctionName(CXCursor cursor, Analyser &analyser, const std::string &name);

        friend std::ostream &operator<<(std::ostream &stream, const FunctionTypedefDeclaration &function);

        static std::shared_ptr<FunctionTypedefDeclaration>
        visitShared(CXCursor cursor, const std::string &functionName, Analyser &analyser,
                    CXType functionType);
    };
} // jbindgen

#endif //JBINDGEN_FUNCTIONTYPEDEFDECLARATION_H
