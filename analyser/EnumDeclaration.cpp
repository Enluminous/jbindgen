//
// Created by nettal on 23-11-7.
//

#include "EnumDeclaration.h"
#include "Analyser.h"

#include <utility>
#include <climits>
#include <cassert>

namespace jbindgen {
    EnumMember::EnumMember(jbindgen::VarDeclare type, int64_t declValue, std::string declStr) : type(
            std::move(
                    type)), declValue(declValue), declStr(std::move(declStr)) {

    }

    std::ostream &operator<<(std::ostream &stream, const jbindgen::EnumMember &member) {
        stream << "Member Info:  " << member.type << " declValue: " << member.declValue;
        return stream;
    }

    jbindgen::EnumDeclaration::EnumDeclaration(std::string name, VarDeclare type) : name(std::move(name)),
                                                                                    type(std::move(type)) {

    }

    jbindgen::EnumDeclaration jbindgen::EnumDeclaration::visit(CXCursor c) {
        assert(c.kind == CXCursor_EnumDecl || c.kind == CXCursor_EnumConstantDecl);
        CXType type = clang_getCursorType(c);
        assert(type.kind == CXType_Enum);
        auto name = toStringWithoutConst(type);
        auto enumType = clang_getEnumDeclIntegerType(c);
        auto enumTyped = VarDeclare(NO_NAME, enumType, clang_Type_getSizeOf(enumType), getCommit(c), c);
        EnumDeclaration declaration(name, enumTyped);
        clang_visitChildren(c, EnumDeclaration::visitChildren, &declaration);
        return declaration;
    }

    CXChildVisitResult
    jbindgen::EnumDeclaration::visitChildren(CXCursor cursor, CXCursor parent, CXClientData client_data) {
        if (clang_getCursorKind(cursor) == CXCursor_EnumConstantDecl) {
            auto type = clang_getCursorType(cursor);
            auto enumName = toString(clang_getCursorSpelling(cursor));
            auto size = clang_Type_getSizeOf(type);
            auto typed = VarDeclare(enumName, type, size, getCommit(cursor), cursor);

//        auto typeSpelling = clang_getTypeSpelling(type);
            auto declValue = clang_getEnumConstantDeclValue(cursor);
            if (declValue == LLONG_MIN) {
                throw std::runtime_error(std::to_string(declValue));
            }
            reinterpret_cast<EnumDeclaration *>(client_data)->members.emplace_back(typed, declValue, enumName);
        } else {
            throw std::runtime_error(toString(clang_getCursorSpelling(cursor)));
        }
        return CXChildVisit_Continue;
    }

    std::ostream &operator<<(std::ostream &stream, const jbindgen::EnumDeclaration &declaration) {
        stream << "#### Enum " << declaration.name << " " << declaration.type << std::endl;
        for (auto &item: declaration.members) {
            stream << "  " << item << std::endl;
        }
        return stream;
    }


}
