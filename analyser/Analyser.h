//
// Created by nettal on 23-11-7.
//

#ifndef JAVABINDGEN_ANALYSER_H
#define JAVABINDGEN_ANALYSER_H

#include <string>
#include <clang-c/Index.h>
#include <vector>
#include "StructDeclaration.h"
#include "UnionDeclaration.h"
#include "EnumDeclaration.h"
#include "TypedefDeclaration.h"

namespace jbindgen {
    constexpr bool DEBUG_LOG = true;
    constexpr const char *NO_NAME = "#NO_NAME#";

    class Analyser {
    private:
        CXIndex index{};
        CXTranslationUnit unit{};
        std::vector<StructDeclaration> structs{};
        std::vector<UnionDeclaration> unions{};
        std::vector<EnumDeclaration> enums{};
        std::vector<TypedefDeclaration> typedefs{};

    public:
        Analyser(const std::string &path, const char *const *command_line_args,
                 int num_command_line_args);

        ~Analyser() {
            clang_disposeTranslationUnit(unit);
            clang_disposeIndex(index);
        }

        Analyser(const Analyser &that) = delete;

        Analyser &operator=(const Analyser &) = delete;

        void visitStruct(CXCursor param);

        void visitUnion(CXCursor param);

        void visitEnum(CXCursor param);

        void visitTypedef(CXCursor param);
    };
}


#endif //JAVABINDGEN_ANALYSER_H
