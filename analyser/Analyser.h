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
#include "CXCursorMap.h"
#include <functional>

namespace jbindgen {
    struct AnalyserConfig;
    /**
     * filter
     * @param c the visiting cursor
     * @param parent
     * @return true to visit this declaration
     */
    typedef std::function<bool(const CXCursor &c, const CXCursor &parent, const AnalyserConfig &config)> AnalyserFilter;

    struct AnalyserConfig {
        std::string path;
        std::string acceptedPath;
        const char *const *command_line_args;
        int num_command_line_args;
        AnalyserFilter declFilter;
        AnalyserFilter macroFilter;
    };

    AnalyserConfig defaultAnalyserConfig(const std::string &path, const char *const *command_line_args,
                                         int num_command_line_args);

    class Analyser {
        CXIndex index4declaration{};
        CXTranslationUnit unit4declaration{};
        CXIndex index4macro{};
        CXTranslationUnit unit4macro{};
        CXCursorMap cxCursorMap;

    public:
        std::vector<std::shared_ptr<StructDeclaration>> _structs{};
        std::vector<std::shared_ptr<UnionDeclaration>> _unions{};
        std::vector<std::shared_ptr<VarDeclaration>> _vars{};
        std::vector<std::shared_ptr<EnumDeclaration>> _enums{};
        std::vector<std::shared_ptr<NormalMacroDeclaration>> _normalMacro{};
        std::vector<std::shared_ptr<FunctionLikeMacroDeclaration>> _functionLikeMacro{};
        std::vector<std::shared_ptr<FunctionSymbolDeclaration>> _functions{};
        std::vector<std::shared_ptr<FunctionSymbolDeclaration>> _noCXCursorFunctions{};
        std::vector<std::shared_ptr<FunctionTypedefDeclaration>> _typedefFunctions{};
        std::vector<std::shared_ptr<NormalTypedefDeclaration>> _typedefs{};

        std::vector<StructDeclaration> structs{};
        std::vector<UnionDeclaration> unions{};
        std::vector<VarDeclaration> vars{};
        std::vector<EnumDeclaration> enums{};
        std::vector<NormalMacroDeclaration> normalMacro{};
        std::vector<FunctionLikeMacroDeclaration> functionLikeMacro{};
        std::vector<FunctionSymbolDeclaration> functionSymbols{};
        std::vector<FunctionSymbolDeclaration> functionPointers{};
        std::vector<FunctionTypedefDeclaration> typedefFunctions{};
        std::vector<NormalTypedefDeclaration> typedefs{};

        explicit Analyser(const AnalyserConfig &config);

        ~Analyser();

        Analyser() = delete;

        Analyser(const Analyser &that) = delete;

        Analyser &operator=(const Analyser &) = delete;

        static CXChildVisitResult visitCXCursorStatic(const CXCursor &c, Analyser &pAnalyser);

        const CXCursorMap &getCXCursorMap() const;

        void updateCXCursorMap(const CXCursor &c, const std::shared_ptr<DeclarationBasic> &declarationBasic);

        void visitStruct(CXCursor param);

        void visitUnion(CXCursor param);

        void visitEnum(CXCursor param);

        void visitVar(CXCursor param);

        void visitTypedef(CXCursor param);

        void visitNormalMacro(CXCursor param);

        void visitFunctionLikeMacro(CXCursor param);

        void visitFunction(CXCursor param);

        void visitTypeDefFunction(CXCursor param);

        std::shared_ptr<FunctionSymbolDeclaration>
        visitNoCursorFunction(const CXType &cxType, const std::shared_ptr<DeclarationBasic> &parent,
                              const std::string &candidateName);

        void visitStructInternalFunctionPointer(CXCursor param, std::shared_ptr<StructDeclaration> &parent,
                                                const std::string &candidateName);

        void
        visitTypedefFunctionWithName(CXCursor param, const std::string &candidateName);

        void visitStructInternalStruct(CXCursor param, const std::shared_ptr<StructDeclaration> &parent,
                                       const std::string &candidateName);

        void visitStructInternalUnion(CXCursor param, const std::shared_ptr<StructDeclaration> &parent,
                                      const std::string &candidateName);

        void visitCXCursor(const CXCursor &c);

        void visitCXType(const CXType &c);

        static CXCursor gotoDeclaration(const CXCursor &param);
    };
}


#endif //JBINDGEN_ANALYSER_H
