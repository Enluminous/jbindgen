//
// Created by snownf on 23-11-7.
//

#include <clang-c/CXString.h>
#include <clang-c/Index.h>
#include <iostream>
#include "FunctionLikeMacroDeclaration.h"
#include "Analyser.h"


namespace jbindgen {
    FunctionLikeMacroDeclaration FunctionLikeMacroDeclaration::visit(CXCursor c) {
        CXTranslationUnit tu = clang_Cursor_getTranslationUnit(c);
        CXString spelling = clang_getCursorSpelling(c);
        CXSourceRange extent = clang_getCursorExtent(c);
        if (DEBUG_LOG)
            printf("Found function-like macro: %s ", toString(spelling).c_str());
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
//        macroFunctionLike.emplace_back(Typedef(cxstring2string(spelling), map));
//        return Typedef(cxstring2string(spelling), map);
        FunctionLikeMacroDeclaration def;
        return def;
    }
} // jbindgen