//
// Created by nettal on 23-11-7.
//

#include "StructDeclaration.h"
#include "Analyser.h"

#include <utility>
#include <iostream>
#include <cassert>

using std::cout;

namespace jbindgen {
    StructDeclaration::StructDeclaration(VarDeclare structType) : structType(std::move(structType)) {
    }

    StructDeclaration StructDeclaration::visit(CXCursor c, Analyser &analyser) {
        CXType type = clang_getCursorType(c);
        auto name = toStringWithoutConst(type);
        StructDeclaration declaration(VarDeclare(name, type, clang_Type_getSizeOf(type), getCommit(c), c));
        if (declaration.structType.byteSize < 0) {
            return declaration;
        }
        intptr_t pUser[] = {
                reinterpret_cast<intptr_t>(&declaration), reinterpret_cast<intptr_t>(&analyser)
        };
        clang_visitChildren(c, StructDeclaration::visitChildren, pUser);
        return declaration;
    }

    CXChildVisitResult StructDeclaration::visitChildren(CXCursor cursor, CXCursor parent, CXClientData client_data) {
        auto *this_ptr = reinterpret_cast<StructDeclaration *>(static_cast<intptr_t *>(client_data)[0]);
        const auto theAnalyser = reinterpret_cast<Analyser *>((static_cast<intptr_t *>(client_data)[1]));
        if (clang_getCursorKind(cursor) == CXCursor_FieldDecl) {
            const int64_t offset = clang_Cursor_getOffsetOfField(cursor);
            if (offset < 0) {
                throw std::runtime_error(std::to_string(offset));
            }
            const CXType &cursorType = clang_getCursorType(cursor);
            const auto memberName = toString(clang_getCursorSpelling(cursor));

            std::any extra;
            const auto &unnamedTypeName = this_ptr->structType.name + "$" + memberName;
            if (cursorType.kind == CXType_Elaborated &&//unnamed struct
                clang_getTypeDeclaration(cursorType).kind == CXCursor_StructDecl) {
                theAnalyser->visitStructUnnamedStruct(cursor, unnamedTypeName);
                extra = std::any(std::string(unnamedTypeName));
            }
            if (cursorType.kind == CXType_Elaborated &&//unnamed union
                clang_getTypeDeclaration(cursorType).kind == CXCursor_UnionDecl) {
                theAnalyser->visitStructUnnamedStruct(cursor, unnamedTypeName);
                extra = std::any(std::string(unnamedTypeName));
            }
            if (cursorType.kind == CXType_Pointer && (//unnamed function ptr
                    clang_getPointeeType(cursorType).kind == CXType_FunctionProto ||
                    clang_getPointeeType(cursorType).kind == CXType_FunctionNoProto)) {
                theAnalyser->visitStructUnnamedFunction(cursor, unnamedTypeName);
                extra = std::any(std::string(unnamedTypeName));
            }
            if (cursorType.kind == CXType_ConstantArray ||
                cursorType.kind == CXType_IncompleteArray ||
                cursorType.kind == CXType_VariableArray ||
                cursorType.kind == CXType_DependentSizedArray) {
                const auto &element = clang_getTypeDeclaration(clang_getArrayElementType(cursorType));
                if (clang_Cursor_isAnonymous(element)) {
                    extra = std::any(std::string(unnamedTypeName));
                    if (element.kind == CXCursor_StructDecl) {
                        theAnalyser->visitStructUnnamedStruct(element, unnamedTypeName);
                    } else if (element.kind == CXCursor_UnionDecl) {
                        theAnalyser->visitStructUnnamedUnion(element, unnamedTypeName);
                    } else
                        assert(0);
                }
            }
            auto member = StructMember(
                    VarDeclare(memberName, cursorType, clang_Type_getSizeOf(cursorType), getCommit(cursor), cursor,
                               extra),
                    offset);
            this_ptr->members.emplace_back(member);
            return CXChildVisit_Continue;
        }
        return CXChildVisit_Continue;
    }

    std::ostream &operator<<(std::ostream &stream, const StructDeclaration &str) {
        stream << "#### Structure " << str.structType << std::endl;
        for (auto &item: str.members) {
            stream << "  " << item << std::endl;
        }
        return stream;
    }

    StructDeclaration StructDeclaration::visitStructUnnamed(CXCursor c, const std::string &name,
                                                            Analyser &analyser) {
        CXType type = clang_getCursorType(c);
        StructDeclaration declaration(VarDeclare(name, type, clang_Type_getSizeOf(type), getCommit(c), c));
        if (declaration.structType.byteSize < 0) {
            return declaration;
        }
        intptr_t pUser[] = {
                reinterpret_cast<intptr_t>(&declaration), reinterpret_cast<intptr_t>(&analyser)
        };
        clang_visitChildren(c, StructDeclaration::visitChildren, pUser);
        return declaration;
    }

    StructMember::StructMember(jbindgen::VarDeclare type, int64_t offsetOfBit) : var(std::move(type)),
                                                                                 offsetOfBit(offsetOfBit) {
    }

    std::ostream &operator<<(std::ostream &stream, const StructMember &member) {
        stream << "Struct Member Info:  " << member.var << " offsetOfBit: " << member.offsetOfBit;
        return stream;
    }
}
