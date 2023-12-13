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
                                                           std::string sharedBasePackageName,
                                                           std::string pointerInterfacePackageName,
                                                           std::string valueInterfacePackageName,
                                                           std::string sharedValuePackageName,
                                                           std::string enumFullyQualifiedName,
                                                           FN_makeFunction makeFunction) :
            declaration(std::move(declaration)),
            dir(std::move(dir)),
            defCallbackDir(std::move(defCallbackDir)),
            defsCallbackPackageName(std::move(defsCallbackPackageName)),
            nativeFunctionPackageName(std::move(nativeFunctionPackageName)),
            makeFunction(std::move(makeFunction)),
            nativeStructsPackageName(std::move(nativeStructsPackageName)),
            nativeValuesPackageName(std::move(nativeValuesPackageName)),
            sharedBasePackageName(std::move(sharedBasePackageName)),
            pointerInterfacePackageName(std::move(pointerInterfacePackageName)),
            valueInterfacePackageName(std::move(valueInterfacePackageName)),
            sharedValuePackageName(std::move(sharedValuePackageName)),
            enumFullyQualifiedName(std::move(enumFullyQualifiedName)),
            analyser(analyser) {}

    void FunctionProtoTypeGenerator::build() {
        //auto function = makeProtoType(&declaration, userData);
        std::string result = std::vformat("package {};\n"
                                          "\n"
                                          "import {}.*;\n"
                                          "import {}.*;\n"
                                          "import {}.*;\n"
                                          "import {}.*;\n"
                                          "import {}.*;\n"
                                          "import {}.*;\n"
                                          "import {}.*;\n"
                                          "\n"
                                          "import java.lang.foreign.Arena;\n"
                                          "import java.lang.foreign.MemorySegment;\n"
                                          "import java.lang.foreign.ValueLayout;\n"
                                          "import java.lang.invoke.MethodHandle;\n"
                                          "import java.lang.invoke.MethodHandles;\n"
                                          "import java.lang.foreign.FunctionDescriptor;\n"
                                          "\n",
                                          std::make_format_args(defsCallbackPackageName,
                                                                nativeFunctionPackageName,
                                                                nativeStructsPackageName,
                                                                nativeValuesPackageName,
                                                                sharedBasePackageName,
                                                                pointerInterfacePackageName,
                                                                sharedValuePackageName, enumFullyQualifiedName));
        if (DEBUG_LOG) {
            unsigned line;
            unsigned column;
            CXFile file;
            unsigned offset;
            clang_getSpellingLocation(clang_getCursorLocation(declaration.function.cursor), &file, &line, &column,
                                      &offset);
            std::cout << "processing: " << toStringIfNullptr(clang_getFileName(file)) << ":" << line << ":" << column
                      << std::endl << std::flush;
        }
        std::string className = declaration.getName();
        auto decodedFunc = makeFunction(&declaration, analyser);
        result += TypedefGeneratorUtils::getFuncSymContent(decodedFunc.jParameters,
                                                           decodedFunc.parameterDescriptors, className,
                                                           decodedFunc.hasResult,
                                                           decodedFunc.jResult);
        int wrapperSameNameCount = 0;
        for (const auto &wrapper: decodedFunc.wrappers) {
            std::string interfaceName = wrapper.wrapperName + "$" + std::to_string(wrapperSameNameCount);
            result += "\n" +
                      TypedefGeneratorUtils::getFuncWrapperContent(wrapper.jParameters,
                                                                   wrapper.encodeParameters,
                                                                   wrapper.decodeParameters,
                                                                   decodedFunc.jParameters,
                                                                   interfaceName,
                                                                   className, decodedFunc.hasResult,
                                                                   wrapper.wrappedResult,
                                                                   decodedFunc.jResult,
                                                                   wrapper.wrappedResultCall);
            wrapperSameNameCount++;
        }
        result += TypedefGeneratorUtils::getOfPointerContent(className, decodedFunc.jResult,
                                                             decodedFunc.jParameters,
                                                             decodedFunc.parameterDescriptors,
                                                             decodedFunc.hasResult,
                                                             decodedFunc.critical,
                                                             decodedFunc.jResult,
                                                             decodedFunc.invokeParameters);
        result += "}";
        overwriteFile(defCallbackDir + "/" + className + ".java", result);
    }
} // jbindgen