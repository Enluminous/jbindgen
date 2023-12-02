//
// Created by nettal on 23-11-13.
//

#include "FunctionProtoTypeGenerator.h"

#include <utility>

namespace jbindgen {
    FunctionProtoTypeGenerator::FunctionProtoTypeGenerator(FunctionSymbolDeclaration declaration,
                                                           const Analyser &analyser,
                                                           std::string dir,
                                                           std::string defsCallbackPackageName,
                                                           std::string defCallbackDir,
                                                           std::string nativeFunctionPackageName,
                                                           std::string nativeStructsPackageName,
                                                           std::string nativeValuesPackageName,
                                                           std::string functionUtilsPackageName,
                                                           std::string pointerInterfacePackageName,
                                                           std::string valueInterfacePackageName,
                                                           FN_makeFunction makeFunction) :
            declaration(std::move(declaration)),
            dir(std::move(dir)),
            defCallbackDir(std::move(defCallbackDir)),
            defsCallbackPackageName(std::move(defsCallbackPackageName)),
            nativeFunctionPackageName(std::move(nativeFunctionPackageName)),
            makeFunction(std::move(makeFunction)),
            nativeStructsPackageName(std::move(nativeStructsPackageName)),
            nativeValuesPackageName(std::move(nativeValuesPackageName)),
            functionUtilsPackageName(std::move(functionUtilsPackageName)),
            pointerInterfacePackageName(std::move(pointerInterfacePackageName)),
            valueInterfacePackageName(std::move(valueInterfacePackageName)),
            analyser(analyser) {}

    void FunctionProtoTypeGenerator::build() {
        //auto function = makeProtoType(&declaration, userData);
        std::string head = std::vformat("package {};\n"
                                        "\n"
                                        "import {}.*;\n"
                                        "import {}.*;\n"
                                        "import {}.*;\n"
                                        "import {};\n"
                                        "import {};\n"
                                        "\n"
                                        "import java.lang.foreign.Arena;\n"
                                        "import java.lang.foreign.MemorySegment;\n"
                                        "import java.lang.foreign.ValueLayout;\n"
                                        "import java.lang.invoke.MethodHandles;\n"
                                        "import java.lang.foreign.FunctionDescriptor;\n"
                                        "\n",
                                        std::make_format_args(defsCallbackPackageName,
                                                              nativeFunctionPackageName,
                                                              nativeStructsPackageName,
                                                              nativeValuesPackageName, functionUtilsPackageName,
                                                              pointerInterfacePackageName));
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
        auto decodedFunc = makeFunction(&declaration, analyser);
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
                                                          wrapper.decodeParameters,
                                                          decodedFunc.jParameters,
                                                          interfaceName,
                                                          className);
            funcWrapperBodies += "\n\n" + funcWrapperBody;
            wrapperSameNameCount++;
        }
        overwriteFile(defCallbackDir + "/" + className + ".java", head + func + funcWrapperBodies);
    }
} // jbindgen