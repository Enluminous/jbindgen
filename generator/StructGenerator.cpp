//
// Created by snownf on 23-11-9.
//

#include <sstream>
#include <utility>
#include "StructGenerator.h"
#include "StructGeneratorUtils.h"

namespace jbindgen {

    StructGenerator::StructGenerator(StructDeclaration declaration, std::string structsDir, std::string packageName,
                                     FN_structMemberName memberRename,
                                     FN_decodeGetter decodeGetter, FN_decodeSetter decodeSetter,
                                     const Analyser &analyser)
            : declaration(std::move(declaration)),
              structsDir(std::move(structsDir)),
              packageName(std::move(packageName)),
              structMemberName(std::move(memberRename)),
              decodeGetter(std::move(decodeGetter)),
              decodeSetter(std::move(decodeSetter)),
              analyser(analyser) {
    }

    std::string StructGenerator::makeGetterSetter(const std::string &structName) {
        std::stringstream ss;
        auto members = declaration.members;
        for (const auto &member: members) {
            std::cout << "StructGenerator#makeGetterSetter: process member \"" << member.var.name << "\" in struct "
                      << structName << std::endl;
            std::string memberName = structMemberName(declaration, analyser, member);
            constexpr auto ptrName = "ptr";
            for (const auto &getter: decodeGetter(member, analyser, std::string(ptrName))) {
                ss << "    public " << getter.returnTypeName << " " << memberName << "(" << getter.parameterString
                   << ") {"
                   << std::endl
                   << "        return " << getter.creator << ";" << std::endl
                   << "    }" << std::endl;
            }
            for (const auto &setter: decodeSetter(member, analyser, std::string(ptrName))) {
                ss << "    public " << structName << " " << memberName << "(" << setter.parameterString << ") {"
                   << std::endl
                   << "        " << setter.creator << ";" << std::endl
                   << "        return this;" << std::endl
                   << "    }" << std::endl;
            }
        }
        return ss.str();
    }

    void StructGenerator::build() {
        std::string structName = declaration.getName();
        int64_t size = declaration.structType.byteSize;
        if (!isValidSize(size))
            size = value::jbasic::Byte.byteSize;//like cpp, make it byteSize 1
        std::string core = StructGeneratorUtils::makeCore("", packageName, structName, size,
                                                          "",
                                                          makeGetterSetter(structName));
        overwriteFile(structsDir + "/" + structName + ".java", core);
    }
} // jbindgen