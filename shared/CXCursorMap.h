//
// Created by nettal on 23-11-26.
//

#ifndef JBINDGEN_CXCURSORMAP_H
#define JBINDGEN_CXCURSORMAP_H

#include <unordered_map>
#include <clang-c/Index.h>
#include <any>

namespace jbindgen {

    struct CXCursorHash {
        std::size_t operator()(const CXCursor &type) const {
            return clang_hashCursor(type);
        }
    };

    struct CXCursorEqual {
        bool operator()(const CXCursor &a, const CXCursor &b) const {
            return clang_equalCursors(a, b);
        }
    };

    using CXCursorMap = std::unordered_map<CXCursor, std::any, CXCursorHash, CXCursorEqual>;
} // jbindgen

#endif //JBINDGEN_CXCURSORMAP_H
