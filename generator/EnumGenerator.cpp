//
// Created by snownf on 23-11-9.
//

#include "EnumGenerator.h"

#include <sstream>
#include <iostream>
#include <format>

namespace jbindgen {

    void EnumGenerator::build(void *pUserdata, void *enumGenerationFilterUserdata) {
        std::string head = std::vformat("package {1};\n"
                                        "\n"
                                        "import {2};\n"
                                        "import {3};\n"
                                        "\n"
                                        "import java.lang.foreign.MemorySegment;\n"
                                        "import java.lang.foreign.ValueLayout;\n"
                                        "import java.util.Arrays;\n"
                                        "import java.util.Objects;\n"
                                        "import java.util.Optional;\n"
                                        "\n"
                                        "class {0} {{\n"
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
                                                              sharedValuePackageName));

        std::string body;

        for (const EnumDeclaration &enumDeclaration: enumDeclarations) {
            if (filter(const_cast<EnumDeclaration *>(&enumDeclaration), enumGenerationFilterUserdata))
                continue;
            std::string enums;
            for (const auto &anEnum: enumDeclaration.members) {
                enums += std::vformat("\n        public static final int {} = {};",
                                      std::make_format_args(anEnum.type.name, anEnum.declValue));
            }
            std::string className = rename(enumDeclaration, pUserdata);
            body += std::vformat("    public static final class {0} implements Value<Integer> {{\n"
                                 "        private final int e;\n"
                                 "\n"
                                 "        public {0}(int e) {{\n"
                                 "            this.e = e;\n"
                                 "        }}\n"
                                 "\n"
                                 "        public {0}(Pointer<?> e) {{\n"
                                 "            this.e = e.pointer().get(ValueLayout.JAVA_INT, 0);\n"
                                 "        }}\n"
                                 "\n"
                                 "        public {0}(MemorySegment e) {{\n"
                                 "            this.e = e.get(ValueLayout.JAVA_INT, 0);\n"
                                 "        }}\n"
                                 "\n"
                                 "        private String str;\n"
                                 "\n"
                                 "        @Override\n"
                                 "        public String toString() {{\n"
                                 "            return str == null ? str = enumToString(e).orElse(String.valueOf(e)) : str;\n"
                                 "        }}\n"
                                 "\n"
                                 "        @Override\n"
                                 "        public Integer value() {{\n"
                                 "            return e;\n"
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
} // jbindgen