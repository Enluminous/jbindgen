//
// Created by nettal on 23-11-18.
//

#ifndef JBINDGEN_TYPEDEFGENERATOR_H
#define JBINDGEN_TYPEDEFGENERATOR_H

#include <string>
#include <iostream>
#include <sstream>
#include <format>
#include "../analyser/NormalTypedefDeclaration.h"
#include "GenUtils.h"
#include "FunctionGeneratorUtils.h"

#define GEN_FUNCTION "GEN_FUNCTION"

namespace jbindgen {
    typedef std::function<std::tuple<std::string, std::string, bool>(const NormalTypedefDeclaration *declaration)> FN_def_name;

    class TypedefGenerator {
        NormalTypedefDeclaration declaration;
        const std::string defsStructPackageName;
        const std::string defsEnumPackageName;
        const std::string defsValuePackageName;
        const std::string defEnumDir;
        const std::string defStructDir;
        const std::string defValueDir;
        const std::string defsCallbackPackageName;
        const std::string defCallbackDir;
        const std::string nativeFunctionPackageName;
        const FN_def_name name;

    public:
        TypedefGenerator(NormalTypedefDeclaration declaration, std::string defStructPackageName,
                         std::string defValuePackageName, std::string defEnumPackageName, std::string defEnumDir,
                         std::string defStructDir, std::string defValueDir, std::string defCallbackPackageName,
                         std::string defCallbackDir, std::string nativeFunctionPackageName,
                         FN_def_name name);

        void build() {
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
    };
} // jbindgen

#endif //JBINDGEN_TYPEDEFGENERATOR_H
