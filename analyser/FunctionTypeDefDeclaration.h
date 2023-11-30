//
// Created by nettal on 23-11-8.
//

#ifndef JBINDGEN_FUNCTIONTYPEDEFDECLARATION_H
#define JBINDGEN_FUNCTIONTYPEDEFDECLARATION_H

#include <memory>
#include <string>
#include <vector>
#include "AnalyserUtils.h"

namespace jbindgen {
    class StructDeclaration;

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
        std::shared_ptr<StructDeclaration> parent;
        std::vector<std::string> usages;

        [[nodiscard]] std::string const getName() const override;

        [[nodiscard]] const CXType getCXType() const override;

        FunctionTypedefDeclaration(VarDeclare function, VarDeclare ret, std::string canonicalName);

        static FunctionTypedefDeclaration visit(CXCursor cursor, Analyser &analyser);

        static FunctionTypedefDeclaration
        visitFunctionUnnamedPointer(CXCursor cursor, const std::shared_ptr<StructDeclaration> &declaration,
                                    Analyser &analyser);

        friend std::ostream &operator<<(std::ostream &stream, const FunctionTypedefDeclaration &function);

        static FunctionTypedefDeclaration
        visitShared(CXCursor cursor, const std::string &functionName, Analyser &analyser, CXType functionType,
                    std::shared_ptr<StructDeclaration> parent);

        void addUsage(const std::string &c) override;
    };
} // jbindgen

#endif //JBINDGEN_FUNCTIONTYPEDEFDECLARATION_H
