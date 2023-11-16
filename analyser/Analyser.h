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
#include "NormalTypedefDeclaration.h"
#include "NormalMacroDeclaration.h"
#include "FunctionLikeMacroDeclaration.h"
#include "FunctionSymbolDeclaration.h"
#include "FunctionProtoTypeDeclaration.h"

namespace jbindgen {
    class Analyser {
    private:
        CXIndex index4declaration{};
        CXTranslationUnit unit4declaration{};
        CXIndex index4macro{};
        CXTranslationUnit unit4macro{};

    public:
        std::vector<StructDeclaration> structs{};
        std::vector<UnionDeclaration> unions{};
        std::vector<EnumDeclaration> enums{};
        std::vector<NormalMacroDeclaration> normalMacro{};
        std::vector<FunctionLikeMacroDeclaration> functionLikeMacro{};
        std::vector<FunctionDeclaration> functions{};
        std::vector<FunctionTypedefDeclaration> typedefFunctions{};
        std::vector<NormalTypedefDeclaration> typedefs{};

    public:
        Analyser(const std::string&path, const char* const * command_line_args,
                 int num_command_line_args);

        ~Analyser();

        Analyser(const Analyser&that) = delete;

        Analyser& operator=(const Analyser&) = delete;

        void visitStruct(CXCursor param);

        void visitUnion(CXCursor param);

        void visitEnum(CXCursor param);

        void visitTypedef(CXCursor param);

        void visitNormalMacro(CXCursor param);

        void visitFunctionLikeMacro(CXCursor param);

        void visitFunction(CXCursor param);

        void visitTypeDefFunction(CXCursor param);

        void visitStructUnnamedFunction(CXCursor param, const std::string&functionName);

        void visitStructUnnamedStruct(CXCursor param, const std::string &structName);

        void visitStructUnnamedUnion(CXCursor param, const std::string &structName);
    };
}


#endif //JAVABINDGEN_ANALYSER_H
