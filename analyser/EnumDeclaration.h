//
// Created by nettal on 23-11-7.
//

#ifndef JBINDGEN_ENUMDECLARATION_H
#define JBINDGEN_ENUMDECLARATION_H

#include <string>
#include <clang-c/Index.h>
#include <vector>
#include "AnalyserUtils.h"

namespace jbindgen {
    class EnumMember {
    public:
        const VarDeclare type;
        const int64_t declValue;
        const std::string declStr;
        explicit EnumMember(VarDeclare type, int64_t declValue, std::string declStr);

        friend std::ostream& operator<<(std::ostream&stream, const EnumMember&member);
    };

    class EnumDeclaration : public DeclarationBasic {
        static CXChildVisitResult visitChildren(CXCursor cursor,
                                                CXCursor parent,
                                                CXClientData client_data);

    public:
        static EnumDeclaration visit(CXCursor c);

        std::vector<EnumMember> members{};
        const std::string name;
        const VarDeclare type;

        EnumDeclaration(std::string name, VarDeclare type);

        friend std::ostream& operator<<(std::ostream&stream, const EnumDeclaration&declaration);

        [[nodiscard]] std::string const getName() const override;

        [[nodiscard]] const CXType getCXType() const override;
    };
}


#endif //JBINDGEN_ENUMDECLARATION_H
