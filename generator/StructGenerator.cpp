//
// Created by snownf on 23-11-9.
//

#include <sstream>
#include "StructGenerator.h"
#include "StructGeneratorUtils.h"

namespace jbindgen {
    std::string StructGenerator::makeGetterSetter(const std::string &structName, void *memberRenameUserData,
                                                  void *decodeGetterUserData, void *decodeSetterUserData) {
        std::stringstream ss;
        auto members = declaration.members;
        for (const auto &member: members) {
            std::string memberName = memberRename(member.var.name, memberRenameUserData);
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

    void StructGenerator::build(void *structRenameUserData, void *memberRenameUserData,
                                void *decodeGetterUserData, void *decodeSetterUserData) {
        std::string structName = structRename(declaration.structType.name, structRenameUserData);
        std::string core = StructGeneratorUtils::makeCore("", packageName, structName, declaration.structType.size, "",
                                                          makeGetterSetter(structName, memberRenameUserData,
                                                                           decodeGetterUserData, decodeSetterUserData));

        //bindgen.Utils.overwriteFile(structsDir.resolve(structName + ".java"), core);
    }

} // jbindgen