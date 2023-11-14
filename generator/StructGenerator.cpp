//
// Created by snownf on 23-11-9.
//

#include <sstream>
#include "StructGenerator.h"
#include "StructGeneratorUtils.h"

namespace jbindgen {

    StructGenerator::StructGenerator(StructDeclaration declaration, std::string structsDir, std::string packageName,
                                     PFN_structName structRename, PFN_structMemberName memberRename,
                                     PFN_decodeGetter decodeGetter, PFN_decodeSetter decodeSetter,
                                     PFN_StructGenerationFilter filter) : declaration(std::move(declaration)),
                                                                          structsDir(std::move(structsDir)),
                                                                          packageName(std::move(packageName)),
                                                                          structRename(structRename),
                                                                          memberRename(memberRename),
                                                                          decodeGetter(decodeGetter),
                                                                          decodeSetter(decodeSetter),
                                                                          filter(filter) {
    }

    std::string StructGenerator::makeGetterSetter(const std::string &structName, void *memberRenameUserData,
                                                  void *decodeGetterUserData, void *decodeSetterUserData) {
        std::stringstream ss;
        auto members = declaration.members;
        for (const auto &member: members) {
            if (filter(&declaration))
                continue;

            std::cout << "StructGenerator#makeGetterSetter: process member \"" << member.var.name << "\" in struct " << structName << std::endl;
            std::string memberName = memberRename(declaration, member, memberRenameUserData);
            constexpr auto ptrName = "ptr";
            for (const auto &getter: decodeGetter(member, std::string(ptrName), decodeGetterUserData)) {
                ss << "public " << getter.returnTypeName << "} " << memberName << "(" << getter.parameterString << ") {"
                   << std::endl
                   << "    return " << getter.creator << ";" << std::endl
                   << "}" << std::endl;
            }
            for (const auto &setter: decodeSetter(member, std::string(ptrName), decodeSetterUserData)) {
                ss << "public " << structName << " " << memberName << "(" << setter.parameterString << ") {"
                   << std::endl
                   << setter.creator << ";" << std::endl
                   << "    return this;" << std::endl
                   << "}" << std::endl;
            }
        }
        return ss.str();
    }

    void StructGenerator::build(void *structNameUserData, void *memberNameUserData,
                                void *decodeGetterUserData, void *decodeSetterUserData) {
        std::string structName = structRename(declaration, structNameUserData);
        std::string core = StructGeneratorUtils::makeCore("", packageName, structName, declaration.structType.size, "",
                                                          makeGetterSetter(structName, memberNameUserData,
                                                                           decodeGetterUserData, decodeSetterUserData));
        overwriteFile(structsDir + "/" + structName + ".java", core);
    }
} // jbindgen