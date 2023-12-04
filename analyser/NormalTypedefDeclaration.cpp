//
// Created by nettal on 23-11-7.
//

#include "NormalTypedefDeclaration.h"

#include <utility>
#include <iostream>
#include <cassert>
#include "Analyser.h"

using std::cout;

namespace jbindgen {
    CXChildVisitResult NormalTypedefDeclaration::visitChildren(CXCursor c, CXCursor parent, CXClientData client_data) {
        auto analyser = reinterpret_cast<Analyser *>(client_data);
        if (c.kind == CXCursor_ParmDecl) {
            analyser->visitTypeDefFunction(parent);
            return CXChildVisit_Break;
        }
        if (c.kind == CXCursor_StructDecl) {
            analyser->visitStruct(c);
            return CXChildVisit_Break;
        }
        if (c.kind == CXCursor_UnionDecl) {
            analyser->visitUnion(c);
            return CXChildVisit_Break;
        }
        if (c.kind == CXCursor_TypeRef) {
            return CXChildVisit_Break;
        }
        if (c.kind == CXCursor_IntegerLiteral) {
            //declare array
            return CXChildVisit_Break;
        }
        if (c.kind == CXCursor_AlignedAttr) {
            //?
            return CXChildVisit_Break;
        }
        if (c.kind == CXCursor_EnumDecl) {
            analyser->visitEnum(c);
            return CXChildVisit_Break;
        }
        return CXChildVisit_Break;
    }

    std::string const NormalTypedefDeclaration::getName() const {
        return mappedStr;
    }

    NormalTypedefDeclaration::NormalTypedefDeclaration(std::string oriStr, std::string mappedStr,
                                                       std::string comment,
                                                       CXType ori, CXType mapped, CXCursor cursor)
            : oriStr(std::move(oriStr)), mappedStr(std::move(mappedStr)),
              comment(std::move(comment)),
              ori(ori), mapped(mapped), cursor(cursor) {
        assert(this->mapped.kind == CXType_Typedef);
    }

    std::shared_ptr<NormalTypedefDeclaration> NormalTypedefDeclaration::visit(CXCursor c, Analyser &analyser) {
        assert(c.kind == CXCursor_TypedefDecl);
        auto mappedType = clang_getCursorType(c);
        auto oriType = clang_getTypedefDeclUnderlyingType(c);
        analyser.visitCXType(oriType);
        std::shared_ptr<NormalTypedefDeclaration> shared_ptr = std::make_shared<NormalTypedefDeclaration>(
                NormalTypedefDeclaration(toStringWithoutConst(oriType), toStringWithoutConst(mappedType),
                                         getComment(c), oriType,
                                         mappedType, c));
        clang_visitChildren(c, NormalTypedefDeclaration::visitChildren, &analyser);
        if (!analyser.getCXCursorMap().contains(c)) {
            analyser.updateCXCursorMap(c,shared_ptr);
        }
        analyser.visitCXType(mappedType);
        return shared_ptr;
    }

    const CXType NormalTypedefDeclaration::getCXType() const {
        return mapped;
    }

    std::ostream &operator<<(std::ostream &stream, const NormalTypedefDeclaration &declaration) {
        stream << "Typedef: mapped: " << declaration.mappedStr << " ori: " << declaration.oriStr << std::endl;
        return stream;
    }
} // jbindgen
