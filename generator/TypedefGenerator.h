//
// Created by nettal on 23-11-18.
//

#ifndef TYPEDEFGENERATOR_H
#define TYPEDEFGENERATOR_H

#include <string>
#include <iostream>
#include <sstream>
#include <format>
#include "../analyser/NormalTypedefDeclaration.h"
#include "GenUtils.h"
#include "FunctionSymbolGeneratorUtils.h"

#define GEN_FUNCTION "GEN_FUNCTION"

namespace jbindgen {
    typedef std::tuple<std::string, std::string, bool> (*PFN_def_name)(const NormalTypedefDeclaration *declaration,
                                                                       void *pUserdata);

    typedef bool (*PFN_typedefGenerationFilter)(const NormalTypedefDeclaration *Declaration, void *pUserData);

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
        const PFN_def_name name;
        const PFN_typedefGenerationFilter filter;

    public:
        TypedefGenerator(NormalTypedefDeclaration declaration, std::string defStructPackageName,
                         std::string defValuePackageName, std::string defEnumPackageName, std::string defEnumDir,
                         std::string defStructDir, std::string defValueDir, std::string defCallbackPackageName,
                         std::string defCallbackDir, std::string nativeFunctionPackageName,
                         PFN_def_name name, PFN_typedefGenerationFilter filter);

        void build(void *nameUserData) {
            std::cout << declaration.oriStr << " -> " << declaration.mappedStr << std::endl;
            if (filter(&declaration, nameUserData))
                return;
            std::tuple<std::string, std::string, bool> result = name(&declaration, nameUserData);
            std::string target = std::get<0>(result);
            std::string ori = std::get<1>(result);
            bool drop = std::get<2>(result);
            if (drop)
                return;
            if (std::equal(ori.begin(), ori.end(), GEN_FUNCTION)) {
                std::string head = std::vformat("package {};\n"
                                                "\n"
                                                "import {};\n\n"
                                                "import java.lang.foreign.Arena;\n"
                                                "import java.lang.foreign.MemorySegment;\n"
                                                "import java.lang.foreign.ValueLayout;\n"
                                                "import java.lang.invoke.MethodHandles;\n"
                                                "import java.lang.foreign.FunctionDescriptor;\n"
                                                "\n",
                                                std::make_format_args(defsCallbackPackageName, nativeFunctionPackageName));
                if (DEBUG_LOG) {
                    unsigned line;
                    unsigned column;
                    CXFile file;
                    unsigned offset;
                    clang_getSpellingLocation(clang_getCursorLocation(declaration.cursor), &file, &line, &column,
                                              &offset);
                    std::cout << "processing: " << toString(clang_getFileName(file)) << ":" << line << ":" << column
                              << std::endl << std::flush;
                }
                auto funcDeclaration = FunctionTypedefDeclaration::visit(declaration.cursor);
                std::string className = funcDeclaration.function.name;
                FunctionDeclaration fDec(funcDeclaration.function, funcDeclaration.ret, funcDeclaration.canonicalName);
                for (const auto &para: funcDeclaration.paras)
                    fDec.addPara(para);
                auto decodedFunc = defaultMakeFunctionInfo(&fDec, nullptr);
                std::stringstream jPara;
                for (int i = 0; i < decodedFunc.jParameters.size(); ++i) {
                    std::string &para = decodedFunc.jParameters[i];
                    jPara << (i == 0 ? "" : " ") << para << ((i == decodedFunc.jParameters.size() - 1) ? "" : ",");
                }
                std::stringstream fds;
                for (int i = 0; i < decodedFunc.functionDescriptors.size(); ++i) {
                    std::string &fd = decodedFunc.functionDescriptors[i];
                    fds << (i == 0 ? "" : " ") << fd << ((i == decodedFunc.functionDescriptors.size() - 1) ? "" : ",");
                }
                std::string func = std::vformat(
                        "@FunctionalInterface\n"
                        "public interface {} {{\n"
                        "    MemorySegment function({});\n"
                        "\n"
                        "    default MemorySegment toPointer(Arena arena) {{\n"
                        "        return NativeFunction.toMemorySegment(MethodHandles.lookup(), arena, FunctionDescriptor.of({}), this, \"function\");\n"
                        "    }}\n"
                        "}}",
                        std::make_format_args(className, jPara.str(), fds.str()));
                overwriteFile(defCallbackDir + "/" + className + ".java", head + func);
            } else {
                std::string s =
                        std::vformat("package {0};\n"
                                     "\n"
                                     "class {1} extends {2} {{\n"
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

#endif //TYPEDEFGENERATOR_H
