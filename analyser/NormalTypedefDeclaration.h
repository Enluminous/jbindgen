//
// Created by nettal on 23-11-7.
//

#ifndef JAVABINDGEN_NORMALTYPEDEFDECLARATION_H
#define JAVABINDGEN_NORMALTYPEDEFDECLARATION_H

#include "AnalyserUtils.h"

namespace jbindgen {
    class Analyser;//forward declare

    class NormalTypedefDeclaration {
        static CXChildVisitResult visitChildren(CXCursor cursor,
                                                CXCursor parent,
                                                CXClientData client_data);

    public:
        const std::string oriStr;
        const std::string mappedStr;
        const std::string commit;
        const CXType ori;
        const CXType mapped;

        NormalTypedefDeclaration(std::string oriStr, std::string mappedStr,std::string commit, CXType ori, CXType mapped);

        static NormalTypedefDeclaration visit(CXCursor c, Analyser &analyser);

        friend std::ostream &operator<<(std::ostream &stream, const NormalTypedefDeclaration &declaration);
    };

} // jbindgen

#endif //JAVABINDGEN_NORMALTYPEDEFDECLARATION_H
