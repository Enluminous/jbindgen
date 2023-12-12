//
// Created by snownf on 23-11-9.
//

#include "EnumGenerator.h"

#include <sstream>
#include <iostream>
#include <format>

namespace jbindgen {

    void EnumGenerator::build() {
        std::string head = std::vformat("package {1};\n"
                                        "\n"
                                        "import {2};\n"
                                        "import {3}." + value::jbasic::VInteger.wrapper() + ";\n" +
                                        "\n"
                                        "import java.util.Arrays;\n"
                                        "import java.util.Objects;\n"
                                        "import java.util.Optional;\n"
                                        "\n"
                                        "public class {0} {{\n"
                                        "    private {0}() {{\n"
                                        "    }}\n"
                                        "\n"
                                        "\n"
                                        "    private static Optional<String> enumToString(Class<?> klass, int e) {{\n"
                                        "        return Arrays.stream(klass.getFields()).map(field -> {{\n"
                                        "            try {{\n"
                                        "                return field.getInt(null) == e ? field.getName() : null;\n"
                                        "            }} catch (IllegalAccessException ex) {{\n"
                                        "                return null;\n"
                                        "            }}\n"
                                        "        }}).filter(Objects::nonNull).findFirst();\n"
                                        "    }}\n"
                                        "\n",
                                        std::make_format_args(enumClassName, enumPackageName,
                                                              sharedPointerPackageName,
                                                              sharedValuesPackageName));

        std::string body;

        for (auto &enumDeclaration: enumDeclarations) {
            std::string enums;
            for (const auto &anEnum: enumDeclaration.members) {
                enums += std::vformat("\n        public static final int {} = {};",
                                      std::make_format_args(anEnum.type.name, anEnum.declValue));
            }
            std::string className = name(enumDeclaration);
            body += std::vformat("    public static final class {0} extends VI32<{0}> {{\n"
                                 "        public {0}(int e) {{\n"
                                 "            super(e);\n"
                                 "        }}\n"
                                 "\n"
                                 "        public {0}(Pointer<{0}> e) {{\n"
                                 "            super(e);\n"
                                 "        }}\n"
                                 "\n"
                                 "        private String str;\n"
                                 "\n"
                                 "        @Override\n"
                                 "        public String toString() {{\n"
                                 "            return str == null ? str = enumToString(value()).orElse(String.valueOf(value())) : str;\n"
                                 "        }}\n"
                                 "\n"
                                 "        public static Optional<String> enumToString(int e) {{\n"
                                 "            return {2}.enumToString({0}.class, e);\n"
                                 "        }}\n"
                                 "\n"
                                 "{1}\n"
                                 "\n"
                                 "    }}\n"
                                 "\n", std::make_format_args(className, enums, enumClassName));
        }

        std::string tail = "}";

        overwriteFile(enumDir + "/" + enumClassName + ".java", head + body + tail);
    }

    EnumGenerator::EnumGenerator(const std::vector<EnumDeclaration> &enumDeclarations, std::string enumPackageName,
                                 std::string enumClassName, std::string sharedPointerPackageName,
                                 std::string sharedValuesPackageName, std::string enumDir, PFN_enum_name name)
            : enumDeclarations(enumDeclarations), enumPackageName(std::move(enumPackageName)),
              enumClassName(std::move(enumClassName)), enumDir(std::move(enumDir)), name(std::move(name)),
              sharedPointerPackageName(std::move(sharedPointerPackageName)),
              sharedValuesPackageName(std::move(sharedValuesPackageName)) {
    }
} // jbindgen