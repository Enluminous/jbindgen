//
// Created by nettal on 23-11-18.
//

#include "TypedefGenerator.h"

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
                                       FN_def_name name) :
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
            name(std::move(name)) {
    }

    void TypedefGenerator::build() {
        if (DEBUG_LOG)
            std::cout << declaration.oriStr << " -> " << declaration.mappedStr << std::endl;
        std::tuple<std::string, std::string, bool> result = name(&declaration);
        std::string target = std::get<0>(result);
        std::string ori = std::get<1>(result);
        bool drop = std::get<2>(result);
        if (drop)
            return;
        if (std::equal(ori.begin(), ori.end(), GEN_FUNCTION)) {
            //todo: check whether analyser#typedefFunctions has
        } else {
            std::string s =
                    std::vformat("package {0};\n"
                                 "\n"
                                 "import shared.Value;\n"
                                 "import shared.pointer.{2};\n"
                                 "\n"
                                 "import java.lang.foreign.Arena;\n"
                                 "import java.lang.foreign.MemorySegment;\n"
                                 "import java.util.function.Consumer;\n"
                                 "\n"
                                 "public class {1} extends {2} {{\n"
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
                                 "}}", std::make_format_args(defsValuePackageName, target, ori));
            overwriteFile(defValueDir + "/" + target + ".java", s);
        }
    }
} // jbindgen