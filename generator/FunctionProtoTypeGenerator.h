//
// Created by nettal on 23-11-13.
//

#ifndef JBINDGEN_FUNCTIONPROTOTYPEGENERATOR_H
#define JBINDGEN_FUNCTIONPROTOTYPEGENERATOR_H

#include <utility>
#include <format>
#include <iostream>

#include "../analyser/FunctionTypeDefDeclaration.h"
#include "GenUtils.h"
#include "FunctionGeneratorUtils.h"
#include "TypedefGeneratorUtils.h"

namespace jbindgen {
    typedef FunctionInfo(*PFN_makeProtoType)(const jbindgen::FunctionTypedefDeclaration *declaration,
                                             void *pUserdata);

    class FunctionProtoTypeGenerator {
        const FunctionSymbolDeclaration declaration;
        const Analyser &analyser;
        const std::string dir;
        const PFN_makeFunction makeFunction;

        const std::string defsCallbackPackageName;
        const std::string defCallbackDir;
        const std::string nativeFunctionPackageName;
    public:
        FunctionProtoTypeGenerator(FunctionSymbolDeclaration declaration, const Analyser &analyser,
                                   std::string dir,
                                   std::string defsCallbackPackageName, std::string defCallbackDir,
                                   std::string nativeFunctionPackageName, PFN_makeFunction makeProtoType);

        void build(void *userData) {
            //auto function = makeProtoType(&declaration, userData);
            std::string head = std::vformat("package {};\n"
                                            "\n"
                                            "import {};\n\n"
                                            "import java.lang.foreign.Arena;\n"
                                            "import java.lang.foreign.MemorySegment;\n"
                                            "import java.lang.foreign.ValueLayout;\n"
                                            "import java.lang.invoke.MethodHandles;\n"
                                            "import java.lang.foreign.FunctionDescriptor;\n"
                                            "\n",
                                            std::make_format_args(defsCallbackPackageName,
                                                                  nativeFunctionPackageName));
            if (DEBUG_LOG) {
                unsigned line;
                unsigned column;
                CXFile file;
                unsigned offset;
                clang_getSpellingLocation(clang_getCursorLocation(declaration.function.cursor), &file, &line, &column,
                                          &offset);
                std::cout << "processing: " << toString(clang_getFileName(file)) << ":" << line << ":" << column
                          << std::endl << std::flush;
            }
            std::string className = declaration.getName();
            auto decodedFunc = makeFunction(&declaration, analyser, nullptr);
            std::string func =
                    TypedefGeneratorUtils::GenFuncSym(decodedFunc.jParameters,
                                                      decodedFunc.parameterDescriptors, className);
            std::string funcWrapperBodies;
            int wrapperSameNameCount = 0;
            for (const auto &wrapper: decodedFunc.wrappers) {
                std::string interfaceName = wrapper.wrapperName + "$" + std::to_string(wrapperSameNameCount);
                auto funcWrapperBody =
                        TypedefGeneratorUtils::GenFuncWrapper(wrapper.jParameters,
                                                              wrapper.encodeParameters,
                                                              wrapper.decodeParameters, interfaceName,
                                                              className);
                funcWrapperBodies += "\n\n" + funcWrapperBody;
                wrapperSameNameCount++;
            }
            overwriteFile(defCallbackDir + "/" + className + ".java", head + func + funcWrapperBodies);
        }
    };

} // jbindgen


#endif //JBINDGEN_FUNCTIONPROTOTYPEGENERATOR_H
