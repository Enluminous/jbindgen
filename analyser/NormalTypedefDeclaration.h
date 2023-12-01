//
// Created by nettal on 23-11-7.
//

#ifndef JBINDGEN_NORMALTYPEDEFDECLARATION_H
#define JBINDGEN_NORMALTYPEDEFDECLARATION_H

#include <memory>
#include "AnalyserUtils.h"

namespace jbindgen {
    class Analyser; //forward declare

    class NormalTypedefDeclaration : public DeclarationBasic {
        static CXChildVisitResult visitChildren(CXCursor cursor,
                                                CXCursor parent,
                                                CXClientData client_data);

    public:
        const std::string oriStr;
        const std::string mappedStr;
        const std::string commit;
        const CXType ori;
        const CXType mapped;
        const CXCursor cursor;

        [[nodiscard]] std::string const getName() const override;

        [[nodiscard]] const CXType getCXType() const override;

        NormalTypedefDeclaration(std::string oriStr, std::string mappedStr, std::string commit, CXType ori,
                                 CXType mapped, CXCursor cursor);

        static std::shared_ptr<NormalTypedefDeclaration> visit(CXCursor c, Analyser&analyser);

        friend std::ostream& operator<<(std::ostream&stream, const NormalTypedefDeclaration&declaration);
    };
} // jbindgen

#endif //JBINDGEN_NORMALTYPEDEFDECLARATION_H
