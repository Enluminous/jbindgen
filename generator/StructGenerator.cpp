//
// Created by snownf on 23-11-9.
//

#include <sstream>
#include <utility>
#include "StructGenerator.h"
#include "StructGeneratorUtils.h"
#include "Generator.h"
#include <format>

namespace jbindgen {

    StructGenerator::StructGenerator(StructDeclaration declaration, std::shared_ptr<TypeManager> typeManager,
                                     const GeneratorConfig &config)
            : declaration(std::move(declaration)),
              structsDir(config.structs.structsDir),
              packageName(config.structs.packageName),
              structMemberName(config.structs.memberName),
              decodeGetter(config.structs.decodeGetter),
              decodeSetter(config.structs.decodeSetter),
              analyser(config.analyser),
              config(config),
              typeManager(std::move(typeManager)) {
    }

    std::string StructGenerator::makeToString(const std::string &className) {
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
                          "            return STR.\"" + className + "{ptr=\\{ptr}}\";\n" +
                          "        return STR.\"\"\"\n"
                          "                " + className +
                          "{\\\n" + core.str() + "}\"\"\";\n" +
                          "    }";
        return str;
    }

    std::string StructGenerator::makeGetterSetter(const std::string &className) {
        std::stringstream ss;
        auto members = declaration.members;
        for (const auto &member: members) {
            if (DEBUG_LOG)
                std::cout << "StructGenerator#makeGetterSetter: process member \"" << member.var.name << "\" in struct "
                          << className << std::endl;
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
                                   std::make_format_args(className, memberName, setter.parameterString))
                   << std::vformat("        {};\n", std::make_format_args(setter.creator))
                   << "        return this;\n"
                   << "    }\n\n";
            }
        }
        return ss.str();
    }

    void StructGenerator::build(const std::string &className) {
        if ((*typeManager).isAlreadyGenerated(className)) {
            std::cout << "ignore already generated struct: " << className << std::endl;
            return;
        }
        int64_t size = declaration.structType.byteSize;
        if (!isValidSize(size))
            size = value::jbasic::Byte.byteSize;//like cpp, make it byteSize 1
        const std::string &currentImports = typeManager->getCurrentImports(&config, true);
        const std::string &previousImports = typeManager->getPreviousImports();
        std::string imports = std::vformat("{}"
                                           "{}",
                                           std::make_format_args(currentImports,
                                                                 previousImports));
        std::string core = StructGeneratorUtils::makeCore(imports, packageName, className, size,
                                                          makeToString(className),
                                                          makeGetterSetter(className));
        overwriteFile(structsDir + "/" + className + ".java", core);
    }

    void StructGenerator::build() {
        build(declaration.getName());
    }
} // jbindgen