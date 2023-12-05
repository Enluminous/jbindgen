//
// Created by nettal on 23-11-18.
//

#include "TypedefGenerator.h"
#include "TypedefGeneratorUtils.h"

#include <utility>

namespace jbindgen {
    TypedefGenerator::TypedefGenerator(NormalTypedefDeclaration declaration,
                                       std::string defStructPackageName,
                                       std::string defValuePackageName,
                                       std::string defEnumPackageName,
                                       std::string defEnumDir,
                                       std::string defStructDir,
                                       std::string defValueDir,
                                       std::string defCallbackPackageName,
                                       std::string defCallbackDir,
                                       std::string nativeFunctionPackageName,
                                       std::string sharedValueInterfacePackageName,
                                       std::string sharedValuePackageName) :
            declaration(std::move(declaration)),
            defsStructPackageName(std::move(defStructPackageName)),
            defsValuePackageName(std::move(defValuePackageName)),
            defEnumDir(std::move(defEnumDir)),
            defStructDir(std::move(defStructDir)),
            defValueDir(std::move(defValueDir)),
            defsEnumPackageName(std::move(defEnumPackageName)),
            defCallbackDir(std::move(defCallbackDir)),
            defsCallbackPackageName(std::move(defCallbackPackageName)),
            nativeFunctionPackageName(std::move(nativeFunctionPackageName)),
            sharedValuePackageName(std::move(sharedValuePackageName)),
            sharedValueInterfacePackageName(std::move(sharedValueInterfacePackageName)) {
    }

    void TypedefGenerator::build() {
        if (DEBUG_LOG)
            std::cout << declaration.oriStr << " -> " << declaration.mappedStr << std::endl;
        auto result = jbindgen::TypedefGeneratorUtils::defaultNameFunction(&declaration);
        std::string target = declaration.mappedStr;
        value::jbasic::ValueType valueType = std::get<0>(result);
        auto extra = std::get<1>(result);
        bool drop = std::get<2>(result);
        if (drop)
            return;
        if (valueType.wrapper() == value::jbasic::VOther.wrapper()) {
            assert(extra.length() != 0);
            if (std::equal(extra.begin(), extra.end(), GEN_FUNCTION)) {
                //todo: check whether analyser#typedefFunctions has
            } else if (extra == value::jext::Pointer.wrapper()) {
                //todo: NPointer
            } else if (extra.starts_with("__ARRAY__")) {
                //todo: ARRAY
            } else {
                //other non-primitive type.
            }
        } else {
            assert(extra.length() == 0);
            std::string s =
                    std::vformat("package {0};\n"
                                 "\n"
                                 "import {6};\n"
                                 "import {5}.{2};\n"
                                 "\n"
                                 "import java.lang.foreign.Arena;\n"
                                 "import java.lang.foreign.MemorySegment;\n"
                                 "import java.util.function.Consumer;\n"
                                 "\n"
                                 "public class {1} extends {2} {{\n"
                                 "    public {1}(MemorySegment ptr) {{\n"
                                 "        super(ptr);\n"
                                 "    }}\n"
                                 "\n"
                                 "    public {1}({3} value) {{\n"
                                 "        super(value);\n"
                                 "    }}\n"
                                 "\n"
                                 "    public {1}(Value<{4}> value) {{\n"
                                 "        super(value);\n"
                                 "    }}\n"
                                 "\n"
                                 "    public static NativeList<{1}> list(MemorySegment ptr) {{\n"
                                 "        return new NativeList<>(ptr, {1}::new, BYTE_SIZE);\n"
                                 "    }}\n"
                                 "\n"
                                 "    public static NativeList<{1}> list(MemorySegment ptr, long length) {{\n"
                                 "        return new NativeList<>(ptr, length, {1}::new, BYTE_SIZE);\n"
                                 "    }}\n"
                                 "\n"
                                 "    public static NativeList<{1}> list(MemorySegment ptr, long length, Arena arena, Consumer<MemorySegment> cleanup) {{\n"
                                 "        return new NativeList<>(ptr, length, arena, cleanup, {1}::new, BYTE_SIZE);\n"
                                 "    }}\n"
                                 "\n"
                                 "    public static NativeList<{1}> list(Arena arena, long length) {{\n"
                                 "        return new NativeList<>(arena, length, {1}::new, BYTE_SIZE);\n"
                                 "    }}\n"
                                 "\n"
                                 "}}", std::make_format_args(defsValuePackageName, target,
                                                             valueType.wrapper(), valueType.primitive(),
                                                             valueType.objectPrimitiveName(), sharedValuePackageName,
                                                             sharedValueInterfacePackageName));
            overwriteFile(defValueDir + "/" + target + ".java", s);
        }
    }
} // jbindgen