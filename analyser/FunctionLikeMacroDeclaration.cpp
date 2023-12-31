//
// Created by snownf on 23-11-7.
//

#include <clang-c/CXString.h>
#include <clang-c/Index.h>
#include <iostream>
#include <utility>
#include "FunctionLikeMacroDeclaration.h"


namespace jbindgen {
    FunctionLikeMacroDeclaration FunctionLikeMacroDeclaration::visit(CXCursor c) {
        assertAppend(clang_Cursor_isMacroFunctionLike(c),
                  "CXCursor is not MacroFunctionLike: " +
                   toStringIfNullptr(clang_getCursorSpelling(c)))
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
        if (DEBUG_LOG)
            std::cout << std::endl;
        FunctionLikeMacroDeclaration def(toString(spelling), map, c);
        return def;
    }

    std::string const FunctionLikeMacroDeclaration::getName() const {
        assertAppend(0, "should not call FunctionLikeMacroDeclaration::getName()")
        return map;
    }

    FunctionLikeMacroDeclaration::FunctionLikeMacroDeclaration(std::string ori, std::string map, CXCursor cursor)
            : ori(std::move(ori)),
              map(std::move(map)), cursor(cursor) {
    }

    std::ostream &operator<<(std::ostream &stream, const FunctionLikeMacroDeclaration &normal) {
        stream << "#### FunctionLikeMacro " << std::endl;
        stream << "  " << normal.ori << " "
               << normal.map << std::endl;
        return stream;
    }
} // jbindgen