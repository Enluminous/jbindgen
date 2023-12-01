//
// Created by snownf on 23-11-7.
//

#include <iostream>
#include <utility>
#include <cassert>
#include "UnionDeclaration.h"
#include "Analyser.h"

namespace jbindgen {
    std::shared_ptr<UnionDeclaration> UnionDeclaration::visit(CXCursor c, Analyser &analyser) {
        assert(c.kind == CXCursor_UnionDecl);
        auto type = clang_getCursorType(c);
        assert(type.kind == CXType_Record);
        assert(!clang_Cursor_isAnonymous(c));
        auto name = toStringWithoutConst(type);
        if (name.starts_with("union ")) {
            name = name.substr(std::string_view("union ").length());
        }
        std::shared_ptr<UnionDeclaration> shared_ptr = std::make_shared<UnionDeclaration>(
                VarDeclare(name, type, clang_Type_getSizeOf(type),
                           getCommit(c), clang_getTypeDeclaration(type)));
        analyser.updateCXCursorMap(c, shared_ptr);
        visitShared(c, shared_ptr, analyser);
        analyser.visitCXType(type);
        return shared_ptr;
    }

    std::shared_ptr<UnionDeclaration>
    UnionDeclaration::visitInternalUnion(CXCursor c, std::shared_ptr<StructDeclaration> parent, Analyser &analyser,
                                         const std::string &candidateName) {
        assert(c.kind == CXCursor_UnionDecl);
        CXType type = clang_getCursorType(c);
        c = clang_getTypeDeclaration(type);
        assert(c.kind == CXCursor_UnionDecl);
        assert(type.kind == CXType_Record);
        assert(parent != nullptr);
        auto name = toStringWithoutConst(type);
        if (name.starts_with("union ")) {
            name = name.substr(std::string_view("union ").length());
        }
        if (clang_Cursor_isAnonymous(c)) {
            name = NO_NAME;
        }
        std::shared_ptr<UnionDeclaration> shared_ptr = std::make_shared<UnionDeclaration>(
                UnionDeclaration(VarDeclare(name, type, clang_Type_getSizeOf(type), getCommit(c),
                                            clang_getTypeDeclaration(type))));

        shared_ptr->parent = std::move(parent);
        shared_ptr->candidateName = candidateName;
        analyser.updateCXCursorMap(c, shared_ptr);
        visitShared(c, shared_ptr, analyser);
        analyser.visitCXType(type);
        assert(shared_ptr->parent != nullptr);
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
