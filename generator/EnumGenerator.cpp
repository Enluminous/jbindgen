//
// Created by snownf on 23-11-9.
//

#include "EnumGenerator.h"
#include "TypeManager.h"

#include <iostream>
#include <format>
#include <utility>

namespace jbindgen {

    void EnumGenerator::build() {
        std::string head = std::vformat("package {1};\n"
                                        "\n"
                                        "import {2};\n"
                                        "import {4}." + value::jbasic::VInteger.list_type() + ";\n" +
                                        "import {3}." + value::jbasic::VInteger.wrapper() + "Basic" + ";\n" +
                                        "{5}"
                                        "\n"
                                        "import java.lang.foreign.Arena;\n"
                                        "import java.lang.reflect.Modifier;\n"
                                        "import java.util.Arrays;\n"
                                        "import java.util.Collection;\n"
                                        "import java.util.Objects;\n"
                                        "import java.util.Optional;\n"
                                        "import java.util.function.Consumer;\n"
                                        "\n"
                                        "public class {0} {{\n"
                                        "    private {0}() {{\n"
                                        "    }}\n"
                                        "\n"
                                        "\n"
                                        "    private static <E extends VI32Basic<?>> Optional<String> enumToString(Class<?> klass, E e) {{\n"
                                        "        return Arrays.stream(klass.getFields()).map(field -> {{\n"
                                        "            try {{\n"
                                        "                return (Modifier.isStatic(field.getModifiers()) && e.equals(field.get(null))) ? field.getName() : null;\n"
                                        "            }} catch (IllegalAccessException ex) {{\n"
                                        "                return null;\n"
                                        "            }}\n"
                                        "        }}).filter(Objects::nonNull).findFirst();\n"
                                        "    }}\n"
                                        "\n",
                                        std::make_format_args(enumClassName, enumPackageName,
                                                              sharedPointerPackageName,
                                                              sharedValuesPackageName, sharedBasePackageName,
                                                              typeManager->getPreviousImports()));

        std::string body;

        for (auto &enumDeclaration: enumDeclarations) {
            if ((*typeManager).isAlreadyGenerated(enumDeclaration)) {
                std::cout << "ignore generated: " << enumDeclaration.getName() << std::endl;
                continue;
            }
            std::string className = name(enumDeclaration);
            std::string enums;
            for (const auto &anEnum: enumDeclaration.members) {
                enums += std::vformat("\n        public static final {} {} = new {}({});",
                                      std::make_format_args(className, anEnum.type.name, className, anEnum.declValue));
            }
            body += std::vformat("    public static final class {0} extends VI32Basic<{0}> {{\n"
                                 "        public {0}(int e) {{\n"
                                 "            super(e);\n"
                                 "        }}\n"
                                 "\n"
                                 "        public {0}(Pointer<{0}> e) {{\n"
                                 "            super(e);\n"
                                 "        }}\n"
                                 "\n"
                                 "        public {0}({0} e) {{\n"
                                 "            super(e);\n"
                                 "            str = e.str;\n"
                                 "        }}\n"
                                 "\n"
                                 "        public static VI32List<{0}> list(Pointer<{0}> ptr) {{\n"
                                 "            return new VI32List<>(ptr, {0}::new);\n"
                                 "        }}\n"
                                 "\n"
                                 "        public static VI32List<{0}> list(Pointer<{0}> ptr, long length) {{\n"
                                 "            return new VI32List<>(ptr, length, {0}::new);\n"
                                 "        }}\n"
                                 "\n"
                                 "        public static VI32List<{0}> list(Pointer<{0}> ptr, long length, Arena arena, Consumer<Pointer<{0}>> cleanup) {{\n"
                                 "            return new VI32List<>(ptr, length, arena, cleanup, {0}::new);\n"
                                 "        }}\n"
                                 "\n"
                                 "        public static VI32List<{0}> list(SegmentAllocator allocator, long length) {{\n"
                                 "            return new VI32List<>(allocator, length, {0}::new);\n"
                                 "        }}\n"
                                 "\n"
                                 "        public static VI32List<{0}> list(SegmentAllocator allocator, {0}[] c) {{\n"
                                 "            return new VI32List<>(allocator, c, {0}::new);\n"
                                 "        }}\n"
                                 "\n"
                                 "        public static VI32List<{0}> list(SegmentAllocator allocator, Collection<{0}> c) {{\n"
                                 "            return new VI32List<>(allocator, c, {0}::new);\n"
                                 "        }}\n"
                                 "\n"
                                 "        private String str;\n"
                                 "\n"
                                 "        @Override\n"
                                 "        public String toString() {{\n"
                                 "            return str == null ? str = enumToString(this).orElse(String.valueOf(value())) : str;\n"
                                 "        }}\n"
                                 "\n"
                                 "        public static Optional<String> enumToString({0} e) {{\n"
                                 "            return {2}.enumToString({0}.class, e);\n"
                                 "        }}\n"
                                 "\n"
                                 "        @Override\n"
                                 "        public boolean equals(Object obj) {{\n"
                                 "            return obj instanceof {0} that && that.value().intValue() == value().intValue();\n"
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
                                 std::string sharedBasePackageName, std::string sharedValuesPackageName,
                                 std::string enumDir, PFN_enum_name name, std::shared_ptr<TypeManager> typeManager)
            : enumDeclarations(enumDeclarations), enumPackageName(std::move(enumPackageName)),
              enumClassName(std::move(enumClassName)), enumDir(std::move(enumDir)), name(std::move(name)),
              sharedPointerPackageName(std::move(sharedPointerPackageName)),
              sharedValuesPackageName(std::move(sharedValuesPackageName)),
              sharedBasePackageName(std::move(sharedBasePackageName)),
              typeManager(std::move(typeManager)) {
    }
} // jbindgen