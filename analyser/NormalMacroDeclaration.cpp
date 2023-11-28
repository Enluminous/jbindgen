//
// Created by snownf on 23-11-7.
//

#include <clang-c/Index.h>
#include <string>
#include <cstring>
#include <iostream>
#include <utility>
#include <cassert>
#include "NormalMacroDeclaration.h"
#include "Analyser.h"

namespace jbindgen {
    std::string NormalMacroDeclaration::getName() {
        throw std::runtime_error("shoudle not call this");
        return normalDefines.second;//mapped
    }

    NormalMacroDeclaration NormalMacroDeclaration::visit(CXCursor param) {
        assert(param.kind == CXCursor_MacroDefinition);
        CXTranslationUnit tu = clang_Cursor_getTranslationUnit(param);
        const std::string &ori = toString(clang_getCursorSpelling(param));
        std::string mapped;
        CXToken *tokens;
        unsigned numTokens;
        clang_tokenize(tu, clang_getCursorExtent(param), &tokens, &numTokens); // 将宏定义转换为令牌序列
        if (strcmp(toString(clang_getTokenSpelling(clang_Cursor_getTranslationUnit(param), tokens[0])).c_str(),
                   ori.c_str()) == 0) {
            for (unsigned i = 1; i < numTokens; ++i) {
/*                if (DEBUG_LOG)
                    std::cout << "token kind: " << clang_getTokenKind(tokens[i]) << std::endl << std::flush;*/
                mapped += toString(clang_getTokenSpelling(clang_Cursor_getTranslationUnit(param),
                                                          tokens[i]));
            }
        }
        NormalMacroDeclaration def = NormalMacroDeclaration(std::pair<std::string, std::string>(ori, mapped), param);
        clang_disposeTokens(clang_Cursor_getTranslationUnit(param), tokens, numTokens); // 释放令牌序列
        return def;
    }

    NormalMacroDeclaration::NormalMacroDeclaration(std::pair<std::string, std::string> pair1,
                                                   CXCursor cursor) : normalDefines(
            std::move(pair1)), cursor(cursor) {
    }

    std::ostream &operator<<(std::ostream &stream, const NormalMacroDeclaration &normal) {
        stream << "#### NormalMacro " << std::endl;
        stream << "  " << normal.normalDefines.first << "="
               << normal.normalDefines.second << std::endl;
        return stream;
    }
} // jbindgen