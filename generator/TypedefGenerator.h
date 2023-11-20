//
// Created by nettal on 23-11-18.
//

#ifndef TYPEDEFGENERATOR_H
#define TYPEDEFGENERATOR_H

#include <string>
#include <iostream>
#include <sstream>
#include "../analyser/NormalTypedefDeclaration.h"
#include "GenUtils.h"

namespace jbindgen {
    typedef std::tuple<std::string, std::string> (*PFN_def_name)(const NormalTypedefDeclaration *declaration,
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
        const PFN_def_name name;
        const PFN_typedefGenerationFilter filter;

    public:
        TypedefGenerator(NormalTypedefDeclaration declaration, std::string defStructPackageName,
                         std::string defValuePackageName, std::string defEnumPackageName,
                         std::string defEnumDir, std::string defStructDir, std::string defValueDir,
                         PFN_def_name name,
                         PFN_typedefGenerationFilter filter);

        void build(void *nameUserData) {
            if (filter(&declaration, nameUserData))
                return;
            std::cout << declaration.oriStr << " -> " << declaration.mappedStr << std::endl;
            std::tuple<std::string, std::string> targetAndOriName = name(&declaration, nameUserData);
            std::stringstream ss;
            std::string target = std::get<0>(targetAndOriName);
            std::string ori = std::get<1>(targetAndOriName);
            ss <<
               "package " << defsValuePackageName << ";" << std::endl <<
               "    class " << target << " extends " << ori << "{\n"
                    << "    public static NativeList<" << target << "> list(MemorySegment ptr) {" << "\n"
                    << "        return new NativeList<>(ptr, " << target << "::new, BYTE_SIZE);" << "\n"
                    << "    }"
            "\n"
                    << "    public static NativeList<" << target << "> list(MemorySegment ptr, long length) {"
                    << "\n"
                    << "        return new NativeList<>(ptr, length, " << target << "::new, BYTE_SIZE);" << "\n"
                    << "    }" << "\n"
                                  "\n"
                    << "    public static NativeList<" << target
                    << "> list(MemorySegment ptr, long length, Arena arena, Consumer<MemorySegment> cleanup) {" <<
                                                                                                                "\n"
                    << "        return new NativeList<>(ptr, length, arena, cleanup, " << target <<
                    "::new, BYTE_SIZE);"
                    << "\n"
                    << "    }" << "\n"
            "\n"
                    << "    public static NativeList<" << target << "> list(Arena arena, long length) {" << "\n"
                    << "        return new NativeList<>(arena, length, " << target << "::new, BYTE_SIZE);" <<
                                                                                                               "\n"
                    << "    }" << "\n"
               "        \n"
               "    }";
            overwriteFile(defValueDir + "/" + target + ".java", ss.str());
        }
    };
} // jbindgen

#endif //TYPEDEFGENERATOR_H
