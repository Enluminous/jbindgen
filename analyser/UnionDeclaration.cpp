//
// Created by snownf on 23-11-7.
//

#include <iostream>
#include <utility>
#include <cassert>
#include "UnionDeclaration.h"
#include "Analyser.h"

namespace jbindgen {
     std::shared_ptr<UnionDeclaration> UnionDeclaration::visit(CXCursor c, Analyser&analyser) {
        assert(c.kind == CXCursor_UnionDecl);
        auto type = clang_getCursorType(c);
        assert(type.kind == CXType_Record);
        assert(!clang_Cursor_isAnonymous(c));
        auto name = toStringWithoutConst(type);
        if (name.starts_with("union ")) {
            name = name.substr(std::string_view("union ").length());
        }
        analyser.visitCXCursor(clang_getTypeDeclaration(type));
        UnionDeclaration declaration(VarDeclare(name, type, clang_Type_getSizeOf(type),
                                                getCommit(c), clang_getTypeDeclaration(type)));
        std::shared_ptr<UnionDeclaration> shared_ptr = std::make_shared<UnionDeclaration>(declaration);
        visitShared(c, shared_ptr, analyser);
        return shared_ptr;
    }

     std::shared_ptr<UnionDeclaration> UnionDeclaration::visitInternalStruct(CXCursor c, std::shared_ptr<StructDeclaration> parent,
                                                           Analyser&analyser) {
        assert(c.kind == CXCursor_UnionDecl);
        CXType type = clang_getCursorType(c);
        c = clang_getTypeDeclaration(type);
        assert(c.kind == CXCursor_UnionDecl);
        assert(type.kind == CXType_Record);
        assert(clang_Cursor_isAnonymous(c));
         assert(parent!=nullptr);
        UnionDeclaration declaration(VarDeclare(NO_NAME, type, clang_Type_getSizeOf(type), getCommit(c),
                                                clang_getTypeDeclaration(type)));
        std::shared_ptr<UnionDeclaration> shared_ptr = std::make_shared<UnionDeclaration>(declaration);
        shared_ptr->parent = std::move(parent);
        visitShared(c, shared_ptr, analyser);
         assert(shared_ptr->parent!=nullptr);
        return shared_ptr;
    }

    void UnionDeclaration::visitShared(CXCursor c, std::shared_ptr<UnionDeclaration> declaration,
                                                   Analyser&analyser) {
        if (declaration->structType.byteSize < 0) {
            return;
        }
        intptr_t pUser[] = {reinterpret_cast<intptr_t>(&declaration), reinterpret_cast<intptr_t>(&analyser)};
        clang_visitChildren(c, UnionDeclaration::visitChildren4Members, pUser);
        clang_visitChildren(c, UnionDeclaration::visitChildren4Declarations, pUser);
        clang_visitChildren(c, UnionDeclaration::visitChildren4Usages, pUser);
        if (DEBUG_LOG) {
            std::cout << declaration;
        }
    }

    UnionDeclaration::UnionDeclaration(VarDeclare typed) : StructDeclaration(std::move(typed)) {
    }

    std::ostream& operator<<(std::ostream&stream, const UnionDeclaration&str) {
        stream << "#### Union " << str.structType << std::endl;
        for (auto&item: str.members) {
            stream << "  " << item << std::endl;
        }
        return stream;
    }
}
