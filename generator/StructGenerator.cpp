//
// Created by snownf on 23-11-9.
//

#include <sstream>
#include <utility>
#include "StructGenerator.h"
#include "StructGeneratorUtils.h"
#include <format>

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

    std::string StructGenerator::makeToString() {
        std::stringstream core;
        for (int i = 0; i < declaration.members.size(); ++i) {
            auto mem = declaration.members[i];
            auto named = structMemberName(declaration, analyser, mem);
            core << "                " << named << "=" << "\\{" << named << "()}"
                 << (i + 1 == declaration.members.size() ? "" : ",\\\n");
        }
        std::string str = "    @Override\n"
                          "    public String toString() {\n"
                          "        if (MemorySegment.NULL.address() == ptr.address() || ptr.byteSize() < BYTE_SIZE)\n"
                          "            return STR.\"" + declaration.getName() + "{ptr=\\{ptr}}\";\n" +
                          "        return STR.\"\"\"\n"
                          "                " + declaration.getName() +
                          "{\\\n" + core.str() + "}\"\"\";\n" +
                          "    }";
        return str;
    }

    std::string StructGenerator::makeGetterSetter() {
        std::stringstream ss;
        auto members = declaration.members;
        for (const auto &member: members) {
            if (DEBUG_LOG)
                std::cout << "StructGenerator#makeGetterSetter: process member \"" << member.var.name << "\" in struct "
                          << declaration.getName() << std::endl;
            std::string memberName = structMemberName(declaration, analyser, member);
            constexpr auto ptrName = "ptr";
            for (const auto &getter: decodeGetter(member, analyser, std::string(ptrName))) {
                ss << std::vformat("    public {} {}({}) {{\n",
                                   std::make_format_args(getter.returnTypeName, memberName, getter.parameterString))
                   << std::vformat("        return {};\n", std::make_format_args(getter.creator))
                   << "    }\n\n";
            }
            for (const auto &setter: decodeSetter(member, analyser, std::string(ptrName))) {
                ss << std::vformat("    public {} {}({}) {{\n",
                                   std::make_format_args(declaration.getName(), memberName, setter.parameterString))
                   << std::vformat("        {};\n", std::make_format_args(setter.creator))
                   << "        return this;\n"
                   << "    }\n\n";
            }
        }
        return ss.str();
    }

    void StructGenerator::build(const std::string &className) {
        int64_t size = declaration.structType.byteSize;
        if (!isValidSize(size))
            size = value::jbasic::Byte.byteSize;//like cpp, make it byteSize 1
        std::string core = StructGeneratorUtils::makeCore("import vulkan.shared.NList;\n"
                                                          "import vulkan.shared.Pointer;\n"
                                                          "import vulkan.shared.values.VI8;\n"
                                                          "import vulkan.values.*;\n", packageName, className, size,
                                                          makeToString(),
                                                          makeGetterSetter());
        overwriteFile(structsDir + "/" + className + ".java", core);
    }

    void StructGenerator::build() {
        build(declaration.getName());
    }
} // jbindgen