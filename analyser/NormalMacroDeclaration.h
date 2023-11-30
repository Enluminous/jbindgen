//
// Created by snownf on 23-11-7.
//

#ifndef JBINDGEN_NORMALDEFINITIONDECLARATION_H
#define JBINDGEN_NORMALDEFINITIONDECLARATION_H

#include <clang-c/Index.h>

#include "AnalyserUtils.h"

namespace jbindgen {

    class NormalMacroDeclaration :public DeclarationBasic{
        explicit NormalMacroDeclaration(std::pair<std::string, std::string> pair1,
                                        CXCursor cursor);

    public:
        const std::pair<std::string, std::string> normalDefines;
        const CXCursor cursor;

        [[nodiscard]] std::string const getName() const override;

        static NormalMacroDeclaration visit(CXCursor param);

        friend std::ostream &operator<<(std::ostream &stream, const NormalMacroDeclaration &normal);
    };

} // jbindgen

#endif //JBINDGEN_NORMALDEFINITIONDECLARATION_H
