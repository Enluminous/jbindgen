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

    class FunctionDeclaration : public DeclarationBasic {
    public:
        FunctionDeclaration(VarDeclare function, jbindgen::VarDeclare ret, std::string canonicalName);

        const VarDeclare function;
        std::string canonicalName;
        VarDeclare ret;
        std::vector<VarDeclare> paras;
        std::shared_ptr<DeclarationBasic> parent;
        std::vector<std::string> usages;
        std::string candidateName;//used when usages are empty or usages are also NO_NAME

        void addPara(VarDeclare typed);

        friend std::ostream &operator<<(std::ostream &stream, const FunctionDeclaration &function);

        void addUsage(const std::string &c) override;

        [[nodiscard]] std::string const getName() const override;

        [[nodiscard]] const CXType getCXType() const override;

        static std::shared_ptr<FunctionDeclaration> visit(CXCursor c, Analyser &analyser);

        static std::shared_ptr<FunctionDeclaration> visitNoCXCursor(const CXType &cxType, Analyser &analyser,
                                                                    const std::shared_ptr<DeclarationBasic> &parent);

        static std::shared_ptr<FunctionDeclaration>
        visitShared(const CXCursor &c, const CXType &type, Analyser &analyser,
                    const std::string &functionName);

    };
    bool isNoCXCursorFunction(CXType cxType);
}
#endif //JBINDGEN_FUNCTIONDECLARATION_H
