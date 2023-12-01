//
// Created by snownf on 23-11-8.
//

#ifndef JBINDGEN_FUNCTIONDECLARATION_H
#define JBINDGEN_FUNCTIONDECLARATION_H


#include <string>
#include <vector>
#include <memory>
#include "AnalyserUtils.h"

namespace jbindgen {
    class Analyser;

    class FunctionSymbolDeclaration : public DeclarationBasic {
    public:
        FunctionSymbolDeclaration(VarDeclare function, jbindgen::VarDeclare ret, std::string canonicalName);

        const VarDeclare function;
        const std::string canonicalName;
        VarDeclare ret;
        std::vector<VarDeclare> paras;
        std::shared_ptr<DeclarationBasic> parent;
        std::vector<std::string> usages;
        std::string candidateName;//used when usages are empty or usages are also NO_NAME

        void addPara(VarDeclare typed);

        friend std::ostream &operator<<(std::ostream &stream, const FunctionSymbolDeclaration &function);

        void addUsage(const std::string &c) override;

        [[nodiscard]] std::string const getName() const override;

        [[nodiscard]] const CXType getCXType() const override;

        static std::shared_ptr<FunctionSymbolDeclaration> visit(CXCursor c, Analyser &analyser);

        static std::shared_ptr<FunctionSymbolDeclaration>
        visitNoCXCursor(const CXType &cxType, Analyser &analyser, const std::shared_ptr<DeclarationBasic> &parent,
                        const std::string &candidateName);

        static std::shared_ptr<FunctionSymbolDeclaration>
        visitShared(const CXCursor &c, const CXType &type, Analyser &analyser,
                    const std::string &functionName);

    };
    bool isNoCXCursorFunction(CXType cxType);
}
#endif //JBINDGEN_FUNCTIONDECLARATION_H
