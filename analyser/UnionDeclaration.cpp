//
// Created by snownf on 23-11-7.
//

#include <iostream>
#include <utility>
#include "UnionDeclaration.h"
#include "Analyser.h"

namespace jbindgen {
    std::shared_ptr<UnionDeclaration> UnionDeclaration::visit(CXCursor c, Analyser &analyser) {
        assertAppend(c.kind == CXCursor_UnionDecl,
                     "c.kind is " + toStringIfNullptr(clang_getCursorKindSpelling(c.kind)));
        auto type = clang_getCursorType(c);
        assertAppend(type.kind == CXType_Record,
                     "type.kind is :" + toStringIfNullptr(clang_getTypeKindSpelling(type.kind)));
        assertAppend(!clang_Cursor_isAnonymous(c), "");
        auto name = toStringWithoutConst(type);
        if (name.starts_with("union ")) {
            name = name.substr(std::string_view("union ").length());
        }
        std::shared_ptr<UnionDeclaration> shared_ptr = std::make_shared<UnionDeclaration>(
                VarDeclare(name, type, clang_Type_getSizeOf(type),
                           getComment(c), c));
        analyser.updateCXCursorMap(c, shared_ptr);
        visitShared(c, shared_ptr, analyser);
        analyser.visitCXType(type);
        return shared_ptr;
    }

    std::shared_ptr<UnionDeclaration>
    UnionDeclaration::visitInternalUnion(CXCursor c, std::shared_ptr<StructDeclaration> parent, Analyser &analyser,
                                         const std::string &candidateName) {
        assertAppend(c.kind == CXCursor_UnionDecl,
                     "current is " + toStringIfNullptr(clang_getCursorKindSpelling(c.kind)));
        CXType type = clang_getCursorType(c);
        assertAppend(c.kind == CXCursor_UnionDecl,
                     "current is " + toStringIfNullptr(clang_getCursorKindSpelling(c.kind)));
        assertAppend(type.kind == CXType_Record,
                     "type.kind is :" + toStringIfNullptr(clang_getTypeKindSpelling(type.kind)));
        assertAppend(parent != nullptr, "");
        auto name = toStringWithoutConst(type);
        if (name.starts_with("union ")) {
            name = name.substr(std::string_view("union ").length());
        }
        if (clang_Cursor_isAnonymous(c)) {
            name = NO_NAME;
        }
        std::shared_ptr<UnionDeclaration> shared_ptr = std::make_shared<UnionDeclaration>(
                UnionDeclaration(VarDeclare(name, type, clang_Type_getSizeOf(type), getComment(c),
                                            c)));

        shared_ptr->parent = std::move(parent);
        shared_ptr->candidateName = candidateName;
        analyser.updateCXCursorMap(c, shared_ptr);
        visitShared(c, shared_ptr, analyser);
        analyser.visitCXType(type);
        assertAppend(shared_ptr->parent != nullptr,"");
        return shared_ptr;
    }

    std::ostream &operator<<(std::ostream &stream, const UnionDeclaration &str) {
        stream << "#### Union " << str.structType << std::endl;
        for (auto &item: str.members) {
            stream << "  " << item << std::endl;
        }
        return stream;
    }
}
