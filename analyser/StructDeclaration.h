//
// Created by nettal on 23-11-7.
//

#ifndef JBINDGEN_STRUCTDECLARATION_H
#define JBINDGEN_STRUCTDECLARATION_H

#include <clang-c/Index.h>
#include <vector>
#include <cstdint>
#include <memory>

#include "AnalyserUtils.h"

namespace jbindgen {
    class Analyser;

    class StructMember {
    public:
        const jbindgen::VarDeclare var;
        const int64_t offsetOfBit;

        explicit StructMember(jbindgen::VarDeclare type, int64_t offsetOfBit);

        friend std::ostream &operator<<(std::ostream &stream, const StructMember &member);
    };

    class StructDeclaration : public DeclarationBasic {
    protected:
        static CXChildVisitResult visitChildren4Members(CXCursor cursor,
                                                        CXCursor parent,
                                                        CXClientData client_data);

        static CXChildVisitResult
        visitChildren4Declarations(CXCursor cursor, CXCursor parent, CXClientData client_data);

        static CXChildVisitResult visitChildren4Usages(CXCursor cursor, CXCursor parent, CXClientData client_data);

    public:
        const VarDeclare structType;

        std::vector<jbindgen::StructMember> members{};

        std::vector<std::string> usages;

        std::shared_ptr<StructDeclaration> parent; //only use when getName();

        explicit StructDeclaration(VarDeclare structType);

        static std::shared_ptr<StructDeclaration> visit(CXCursor c, Analyser &analyser);

        static std::shared_ptr<StructDeclaration>
        visitInternalStruct(CXCursor c, std::shared_ptr<StructDeclaration> parent,
                            Analyser &analyser);

        static void visitShared(CXCursor c, std::shared_ptr<StructDeclaration> declaration, Analyser &analyser);

        friend std::ostream &operator<<(std::ostream &stream, const StructDeclaration &str);

        [[nodiscard]] std::string const getName() const override;

        [[nodiscard]] const CXType getCXType() const override {
            return structType.type;
        }

        void addUsage(const std::string &c) override;
    };
}

#endif //JBINDGEN_STRUCTDECLARATION_H
