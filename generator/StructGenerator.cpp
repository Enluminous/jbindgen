//
// Created by snownf on 23-11-9.
//

#include <sstream>
#include "StructGenerator.h"
#include "StructGeneratorUtils.h"

namespace jbindgen {
    std::string StructGenerator::makeGetterSetter(const std::string& structName) {
        std::stringstream ss;
        auto members = declaration.members;
        for (const auto &member: members) {
            std::string memberName = memberRename(member.type.name);
            for (const auto &getter: decodeGetter(member)) {
                ss << "public " << getter.returnTypeName << "} " << memberName << "(" << getter.parameterString << ") {" << std::endl
                   << "    return " << getter.creator_make("ptr", member) << ";" << std::endl
                   << "}" << std::endl;
            }
            for (const auto &setter: decodeSetter(member)) {
                ss << "public " << structName << " " << memberName << "(" << setter.parameterString << ") {" << std::endl
                   << setter.creator_make("ptr", member) << ";" << std::endl
                   << "    return this;" << std::endl
                   << "}" << std::endl;
            }
        }
        return ss.str();
    }
    void StructGenerator::build() {
        std::string structName = structRename(declaration.structType.name);
        std::string core = StructGeneratorUtils::makeCore("", packageName, structName, declaration.structType.size, "", makeGetterSetter(structName));

        //bindgen.Utils.overwriteFile(structsDir.resolve(structName + ".java"), core);
    }

} // jbindgen