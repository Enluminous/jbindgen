//
// Created by nettal on 23-11-7.
//

#ifndef JBINDGEN_ANALYSER_H
#define JBINDGEN_ANALYSER_H

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
#include "FunctionTypeDefDeclaration.h"
#include "VarDeclaration.h"
#include "../shared/CXCursorMap.h"

namespace jbindgen {
    /**
     * filter
     * @param c the visting cursor
     * @param parent
     * @return true to visit this declaration
     */
    typedef bool (*AnalyserFilter)(CXCursor c, CXCursor parent);

    struct AnalyserConfig {
        std::string path;
        const char *const *command_line_args;
        int num_command_line_args;
        AnalyserFilter filter;
    };

    bool defaultAnalyserFilter(CXCursor c, CXCursor parent);

    AnalyserConfig defaultAnalyserConfig(const std::string &path, const char *const *command_line_args,
                                         int num_command_line_args);

    class Analyser {
        CXIndex index4declaration{};
        CXTranslationUnit unit4declaration{};
        CXIndex index4macro{};
        CXTranslationUnit unit4macro{};

    public:
        CXCursorMap cxCursorMap;
        std::vector<std::shared_ptr<StructDeclaration>> structs{};
        std::vector<std::shared_ptr<UnionDeclaration>> unions{};
        std::vector<std::shared_ptr<VarDeclaration>> vars{};
        std::vector<std::shared_ptr<EnumDeclaration>> enums{};
        std::vector<std::shared_ptr<NormalMacroDeclaration>> normalMacro{};
        std::vector<std::shared_ptr<FunctionLikeMacroDeclaration>> functionLikeMacro{};
        std::vector<std::shared_ptr<FunctionDeclaration>> functions{};
        std::vector<std::shared_ptr<FunctionDeclaration>> noCXCursorFunctions{};
        std::vector<std::shared_ptr<FunctionTypedefDeclaration>> typedefFunctions{};
        std::vector<std::shared_ptr<NormalTypedefDeclaration>> typedefs{};

        explicit Analyser(const AnalyserConfig &config);

        ~Analyser();

        Analyser(const Analyser &that) = delete;

        Analyser &operator=(const Analyser &) = delete;

        static CXChildVisitResult visitCXCursorStatic(const CXCursor &c, Analyser &pAnalyser);

        void visitStruct(const CXCursor &param);

        void visitUnion(const CXCursor &param);

        void visitEnum(const CXCursor &param);

        void visitVar(const CXCursor &param);

        void visitTypedef(const CXCursor &param);

        void visitNormalMacro(const CXCursor &param);

        void visitFunctionLikeMacro(const CXCursor &param);

        void visitFunction(const CXCursor &param);

        void visitTypeDefFunction(const CXCursor &param);

        std::shared_ptr<FunctionDeclaration>
        visitNoCursorFunction(const CXType &cxType, const std::shared_ptr<DeclarationBasic> &parent,
                              const std::string &candidateName);

        void visitStructInternalFunctionPointer(const CXCursor &param, std::shared_ptr<StructDeclaration> &parent);

        void visitStructInternalStruct(const CXCursor &param, const std::shared_ptr<StructDeclaration> &parent,
                                       const std::string &candidateName);

        void visitStructInternalUnion(const CXCursor &param, const std::shared_ptr<StructDeclaration>& parant,
                                      const std::string &candidateName);

        [[nodiscard]]
        bool checkAndMakeVisited(const CXCursor &c);

        void visitCXCursor(const CXCursor &c);

        static void checkCXCursor(const CXCursor &c);

        void visitCXType(const CXType &c);
    };
}


#endif //JBINDGEN_ANALYSER_H
