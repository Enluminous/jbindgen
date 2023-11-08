//
// Created by nettal on 23-11-7.
//

#include "NormalTypedefDeclaration.h"

#include <utility>
#include <iostream>
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
        if (c.kind == CXCursor_IntegerLiteral) {//declare array
            return CXChildVisit_Break;
        }
        if (c.kind == CXCursor_AlignedAttr) {//?
            return CXChildVisit_Break;
        }
        if (c.kind == CXCursor_EnumDecl) {
            analyser->visitEnum(c);
            return CXChildVisit_Break;
        }
        return CXChildVisit_Break;
    }

    NormalTypedefDeclaration::NormalTypedefDeclaration(std::string oriStr, std::string mappedStr,
                                                       std::string commit,
                                                       CXType ori, CXType mapped)
            : oriStr(std::move(oriStr)), mappedStr(std::move(mappedStr)),
              commit(std::move(commit)),
              ori(ori), mapped(mapped) {
    }

    NormalTypedefDeclaration NormalTypedefDeclaration::visit(CXCursor c, Analyser &analyser) {
        auto mappedType = clang_getCursorType(c);
        auto oriType = clang_getTypedefDeclUnderlyingType(c);
        auto oriSpell = clang_getTypeSpelling(oriType);
        NormalTypedefDeclaration declaration(toString(oriSpell), toString(mappedType), getCommit(c), oriType,
                                             mappedType);
        clang_visitChildren(c, NormalTypedefDeclaration::visitChildren, &analyser);
        return declaration;
    }

    std::ostream &operator<<(std::ostream &stream, const NormalTypedefDeclaration &declaration) {
        stream << "Typedef: mapped: " << declaration.mappedStr << " ori: " << declaration.oriStr << std::endl;
        return stream;
    }

} // jbindgen