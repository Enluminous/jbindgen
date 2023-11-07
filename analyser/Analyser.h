//
// Created by nettal on 23-11-7.
//

#ifndef JAVABINDGEN_ANALYSER_H
#define JAVABINDGEN_ANALYSER_H

#include <string>
#include <clang-c/Index.h>
#include <vector>
#include "StructDeclaration.h"

namespace jbindgen {
    constexpr bool DEBUG_LOG = true;

    class Analyser {
    private:
        CXIndex index;
        CXTranslationUnit unit;
        std::vector<StructDeclaration> structs{};
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
    };
}


#endif //JAVABINDGEN_ANALYSER_H
