//
// Created by snownf on 23-11-7.
//

#include <clang-c/CXString.h>
#include <clang-c/Index.h>
#include <iostream>
#include <utility>
#include <cassert>
#include "FunctionLikeMacroDeclaration.h"
#include "Analyser.h"


namespace jbindgen {
    FunctionLikeMacroDeclaration FunctionLikeMacroDeclaration::visit(CXCursor c) {
        assert(clang_Cursor_isMacroFunctionLike(c));
        CXTranslationUnit tu = clang_Cursor_getTranslationUnit(c);
        CXString spelling = clang_getCursorSpelling(c);
        CXSourceRange extent = clang_getCursorExtent(c);
        if (DEBUG_LOG)
            printf("Found function-like macro: %s \n", toString(spelling).c_str());
        CXToken *tokens;
        unsigned numTokens;
        std::string map;
        clang_tokenize(tu, extent, &tokens, &numTokens);
        for (unsigned i = 1; i < numTokens; ++i) {
            CXToken token = tokens[i];
            auto token_spelling = toString(clang_getTokenSpelling(tu, token));
            map += token_spelling + " ";
            if (DEBUG_LOG)
                std::cout << token_spelling + " " << std::flush;
        }
        std::cout << std::endl;
        FunctionLikeMacroDeclaration def(toString(spelling), map);
        return def;
    }

    FunctionLikeMacroDeclaration::FunctionLikeMacroDeclaration(std::string ori, std::string map) : ori(std::move(ori)),
                                                                                                   map(std::move(map)) {
    }

    std::ostream &operator<<(std::ostream &stream, const FunctionLikeMacroDeclaration &normal) {
        stream << "#### FunctionLikeMacro " << std::endl;
        stream << "  " << normal.ori << " "
               << normal.map << std::endl;
        return stream;
    }
} // jbindgen