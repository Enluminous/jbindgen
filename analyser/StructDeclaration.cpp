//
// Created by nettal on 23-11-7.
//

#include "StructDeclaration.h"
#include "Analyser.h"

#include <utility>
#include <iostream>

#include "../generator/GenUtils.h"

using std::cout;

namespace jbindgen {
    StructDeclaration::StructDeclaration(VarDeclare structType) : structType(std::move(structType)) {
        assertAppend(this->structType.type.kind == CXType_Record,
                     "current is: " + toStringIfNullptr(clang_getTypeKindSpelling((this->structType.type.kind))));
    }

    std::string const StructDeclaration::getName() const {
        if (parent != nullptr) {
            if (usages.empty()) {
                if (structType.name == NO_NAME) {
                    //no var name for this
                    return parent->getName() + "$" + candidateName;
                }
                return parent->getName() + "$" + structType.name;//internal but has name
            }
            if (usages[0] == NO_NAME) {
                return parent->getName() + "$" + candidateName;
            }
            assertAppend(usages.size() == 1,
                         "current is: " + std::to_string(usages.size())); //note: maybe greater than 1
            return parent->getName() + "$" + usages[0];
        }
        assertAppend(structType.name != NO_NAME, "");
        assertAppend(!structType.name.empty(), "");
        return structType.name;
    }

    void StructDeclaration::addUsage(const std::string &c) {
        usages.emplace_back(c);
    }

    std::shared_ptr<StructDeclaration> StructDeclaration::visit(CXCursor c, Analyser &analyser) {
        assertAppend(c.kind == CXCursor_StructDecl,
                     "current is: " + toStringIfNullptr(clang_getCursorKindSpelling((c.kind))));
        CXType type = clang_getCursorType(c);
        assertAppend(type.kind == CXType_Record,
                     "current is: " + toStringIfNullptr(clang_getTypeKindSpelling((type.kind))));
        assertAppend(!clang_Cursor_isAnonymous(c), "");
        auto name = toStringWithoutConst(type);
        if (name.starts_with("struct ")) {
            name = name.substr(std::string_view("struct ").length());
        }
        std::shared_ptr<StructDeclaration> shared_ptr = std::make_shared<StructDeclaration>
                (StructDeclaration(VarDeclare(name, type, clang_Type_getSizeOf(type),
                                              getComment(c), c)));
        analyser.updateCXCursorMap(c, shared_ptr);
        visitShared(c, shared_ptr, analyser);
        analyser.visitCXType(type);
        return shared_ptr;
    }

    std::shared_ptr<StructDeclaration>
    StructDeclaration::visitInternalStruct(CXCursor c, std::shared_ptr<StructDeclaration> parent, Analyser &analyser,
                                           const std::string &candidateName) {
        assertAppend(c.kind == CXCursor_StructDecl,
                     "current is: " + toStringIfNullptr(clang_getCursorKindSpelling((c.kind))));
        CXType type = clang_getCursorType(c);
        assertAppend(type.kind == CXType_Record,
                     "current is: " + toStringIfNullptr(clang_getTypeKindSpelling((type.kind))));
        auto name = toStringWithoutConst(type);
        if (name.starts_with("struct ")) {
            name = name.substr(std::string_view("struct ").length());
        }
        if (clang_Cursor_isAnonymous(c)) {
            name = NO_NAME;
        }
        assertAppend(parent != nullptr, "");
        std::shared_ptr<StructDeclaration> shared_ptr = std::make_shared<StructDeclaration>(
                VarDeclare(name, type, clang_Type_getSizeOf(type),
                           getComment(c), c));
        analyser.updateCXCursorMap(c, shared_ptr);
        shared_ptr->parent = std::move(parent);
        shared_ptr->candidateName = candidateName;
        visitShared(c, shared_ptr, analyser);
        analyser.visitCXType(type);
        assertAppend(shared_ptr->parent != nullptr, "");
        return shared_ptr;
    }

    CXChildVisitResult StructDeclaration::visitChildren4Members(CXCursor cursor, CXCursor parent,
                                                                CXClientData client_data) {
        auto *this_ptr = reinterpret_cast<std::shared_ptr<StructDeclaration> *>(static_cast<intptr_t *>(client_data)[0]);
        const auto theAnalyser = reinterpret_cast<Analyser *>(static_cast<intptr_t *>(client_data)[1]);
        if (clang_getCursorKind(cursor) == CXCursor_FieldDecl) {
            const int64_t offset = clang_Cursor_getOffsetOfField(cursor);
            if (offset < 0) {
                assertAppend(0, "struct name: " + toStringWithoutConst(this_ptr->get()->structType.type) +
                                ", member name: " + toStringIfNullptr(clang_getCursorSpelling(cursor)) +
                                ", illegal offset, maybe header missing,offset: " + std::to_string(offset));
            }
            const CXType &cursorType = clang_getCursorType(cursor);
            const auto memberName = toString(clang_getCursorSpelling(cursor));
            auto member = StructMember(VarDeclare(
                    memberName, cursorType, clang_Type_getSizeOf(cursorType), getComment(cursor),
                    cursor), offset);
            (*this_ptr)->members.emplace_back(member);
        }
        return CXChildVisit_Continue;
    }

    CXChildVisitResult StructDeclaration::visitChildren4Declarations(CXCursor cursor, CXCursor parent,
                                                                     CXClientData client_data) {
        auto *this_ptr = reinterpret_cast<std::shared_ptr<StructDeclaration> *>(static_cast<intptr_t *>(client_data)[0]);
        const auto theAnalyser = reinterpret_cast<Analyser *>(static_cast<intptr_t *>(client_data)[1]);
        CXCursorKind cursorKind = clang_getCursorKind(cursor);
        if (cursorKind == CXCursor_FieldDecl) {
            auto type = clang_getCursorType(cursor);
            //for internal function ptr
            if (isPointer(type.kind)) {
                auto pointee = toDeepPointeeOrArrayType(type);
                if (isFunctionProto(pointee.kind)) {
                    (*this_ptr)->unnamedCount++;
                    theAnalyser->visitStructInternalFunctionPointer(cursor, *this_ptr,
                                                                    makeUnnamedMemberNamed((*this_ptr)->unnamedCount));
                }
            }
            return CXChildVisit_Continue;
        }
        if (cursorKind == CXCursor_TypedefDecl) {
            assertAppend(0, "cursorKind == CXCursor_TypedefDecl");
        }
        if (cursorKind == CXCursor_FunctionDecl) {
            assertAppend(0, "cursorKind == CXCursor_FunctionDecl");
        }
        if (cursorKind == CXCursor_StructDecl) {
            (*this_ptr)->unnamedCount++;
            theAnalyser->visitStructInternalStruct(cursor, *this_ptr,
                                                   makeUnnamedMemberNamed((*this_ptr)->unnamedCount));
            return CXChildVisit_Continue;
        }
        if (cursorKind == CXCursor_UnionDecl) {
            (*this_ptr)->unnamedCount++;
            theAnalyser->visitStructInternalUnion(cursor, *this_ptr,
                                                  makeUnnamedMemberNamed((*this_ptr)->unnamedCount));
            return CXChildVisit_Continue;
        }
        if (cursorKind == CXCursor_VisibilityAttr)
            return CXChildVisit_Continue;
        if (cursorKind == CXCursor_PackedAttr)
            return CXChildVisit_Continue;
        assertAppend(0, "should not reach here")
    }

    CXChildVisitResult StructDeclaration::visitChildren4Usages(CXCursor cursor, CXCursor parent,
                                                               CXClientData client_data) {
        auto *this_ptr = reinterpret_cast<std::shared_ptr<StructDeclaration> *>(static_cast<intptr_t *>(client_data)[0]);
        const auto theAnalyser = reinterpret_cast<Analyser *>(static_cast<intptr_t *>(client_data)[1]);
        if (clang_getCursorKind(cursor) == CXCursor_FieldDecl) {
            const CXType &cursorType = clang_getCursorType(cursor);
            auto memberName = toString(clang_getCursorSpelling(cursor));
            if (memberName.empty()) {
                memberName = NO_NAME;
            }
            //visit member type here to prevent internal named struct become independent,
            //and then have equal class name in generated java class
            theAnalyser->visitCXType(cursorType);
            if (hasDeclaration(cursorType)) {
                auto decl = clang_getTypeDeclaration(cursorType);
                assertAppend(theAnalyser->getCXCursorMap().contains(decl), "");
                auto value = theAnalyser->getCXCursorMap().at(decl);
                value->addUsage(memberName);
            } else if (theAnalyser->getCXCursorMap().contains(cursor)) {
                theAnalyser->getCXCursorMap().at(cursor)->addUsage(memberName);
            } else if (isPointer(cursorType.kind) || isArrayType(cursorType.kind)) {
                auto pointee = toDeepPointeeOrArrayType(cursorType);
                if (hasDeclaration(pointee)) {
                    auto decl = clang_getTypeDeclaration(pointee);
                    assertAppend(theAnalyser->getCXCursorMap().contains(decl), "");
                    auto value = theAnalyser->getCXCursorMap().at(decl);
                    value->addUsage(memberName);
                }
            }
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


    void StructDeclaration::visitShared(CXCursor c, std::shared_ptr<StructDeclaration> declaration,
                                        Analyser &analyser) {
        intptr_t pUser[] = {
                reinterpret_cast<intptr_t>(&declaration), reinterpret_cast<intptr_t>(&analyser)
        };
        clang_visitChildren(c, StructDeclaration::visitChildren4Members, pUser);
        clang_visitChildren(c, StructDeclaration::visitChildren4Declarations, pUser);
        clang_visitChildren(c, StructDeclaration::visitChildren4Usages, pUser);
    }

    const CXType StructDeclaration::getCXType() const {
        return structType.type;
    }

    size_t StructDeclaration::visitResult() const {
        return structType.byteSize;
    }

    StructMember::StructMember(jbindgen::VarDeclare type, int64_t offsetOfBit) : var(std::move(type)),
                                                                                 offsetOfBit(offsetOfBit) {
    }

    std::ostream &operator<<(std::ostream &stream, const StructMember &member) {
        stream << "Struct Member Info:  " << member.var << " offsetOfBit: " << member.offsetOfBit;
        return stream;
    }
}
