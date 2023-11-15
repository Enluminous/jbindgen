//
// Created by nettal on 23-11-7.
//

#include "StructDeclaration.h"
#include "Analyser.h"

#include <utility>
#include <iostream>

using std::cout;

namespace jbindgen {
    StructDeclaration::StructDeclaration(VarDeclare structType) : structType(std::move(structType)) {
    }

    StructDeclaration StructDeclaration::visit(CXCursor c, Analyser&analyser) {
        CXType type = clang_getCursorType(c);
        auto name = toString(clang_getTypeSpelling(type));
        StructDeclaration declaration(VarDeclare(name, type, clang_Type_getSizeOf(type), getCommit(c), c));
        if (declaration.structType.size < 0) {
            return declaration;
        }
        intptr_t pUser[] = {
            reinterpret_cast<intptr_t>(&declaration), reinterpret_cast<intptr_t>(""),
            reinterpret_cast<intptr_t>(&analyser)
        };
        clang_visitChildren(c, StructDeclaration::visitChildren, pUser);
        return declaration;
    }

    CXChildVisitResult StructDeclaration::visitChildren(CXCursor cursor, CXCursor parent, CXClientData client_data) {
        auto* this_ptr = reinterpret_cast<StructDeclaration *>(static_cast<intptr_t *>(client_data)[0]);
        const auto perfix = reinterpret_cast<const char *>((static_cast<intptr_t *>(client_data)[1]));
        const auto theAnalyser = reinterpret_cast<Analyser *>((static_cast<intptr_t *>(client_data)[2]));
        if (clang_getCursorKind(cursor) == CXCursor_FieldDecl) {
            const int64_t offset = clang_Cursor_getOffsetOfField(cursor);
            if (offset < 0) {
                throw std::runtime_error(std::to_string(offset));
            }
            const CXType&cursorType = clang_getCursorType(cursor);
            const auto memberName = perfix + toString(clang_getCursorSpelling(cursor));
            const std::string perfixed = memberName + "$";
            if (cursorType.kind == CXType_Elaborated &&
                clang_getCursorType(clang_getTypeDeclaration(cursorType)).kind ==
                CXType_Record) {
                //unnamed struct or union
                intptr_t pUserData[] = {
                    ((intptr_t)this_ptr), (intptr_t)(perfixed.c_str()), reinterpret_cast<intptr_t>(theAnalyser)
                };
                clang_visitChildren(
                    clang_getTypeDeclaration(cursorType),
                    StructDeclaration::visitChildren,
                    pUserData);
                return CXChildVisit_Continue;
            }
            if (cursorType.kind == CXType_Pointer && (
                    clang_getPointeeType(cursorType).kind == CXType_FunctionProto ||
                    clang_getPointeeType(cursorType).kind == CXType_FunctionNoProto)) {
                theAnalyser->visitStructUnnamedFunction(cursor, this_ptr->structType.name + "$" + memberName);
                auto member = StructMember(
                    VarDeclare(memberName, cursorType, clang_Type_getSizeOf(cursorType), getCommit(cursor),
                               cursor, std::any(std::string(this_ptr->structType.name + "$" + memberName))), offset);
                this_ptr->members.emplace_back(member);
            }
            else {
                auto member = StructMember(
                    VarDeclare(memberName, cursorType, clang_Type_getSizeOf(cursorType), getCommit(cursor), cursor),
                    offset);
                this_ptr->members.emplace_back(member);
            }
        }
        return CXChildVisit_Continue;
    }

    std::ostream& operator<<(std::ostream&stream, const StructDeclaration&str) {
        stream << "#### Structure " << str.structType << std::endl;
        for (auto&item: str.members) {
            stream << "  " << item << std::endl;
        }
        return stream;
    }

    StructMember::StructMember(jbindgen::VarDeclare type, int64_t offsetOfBit) : var(std::move(type)),
        offsetOfBit(offsetOfBit) {
    }

    std::ostream& operator<<(std::ostream&stream, const StructMember&member) {
        stream << "Struct Member Info:  " << member.var << " offsetOfBit: " << member.offsetOfBit;
        return stream;
    }
}
