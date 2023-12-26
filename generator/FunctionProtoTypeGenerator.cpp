//
// Created by nettal on 23-11-13.
//

#include "FunctionProtoTypeGenerator.h"
#include "Generator.h"

#include <utility>

namespace jbindgen {
    FunctionProtoTypeGenerator::FunctionProtoTypeGenerator(FunctionSymbolDeclaration declaration,
                                                           std::shared_ptr<TypeManager> typeManager,
                                                           const GeneratorConfig &config) :
            declaration(std::move(declaration)),
            defCallbackDir(config.typedefFunc.typedefFuncDir),
            makeFunction(config.typedefFunc.makeProtoType),
            analyser(config.analyser),
            typeManager(std::move(typeManager)),
            config(config) {}

    void FunctionProtoTypeGenerator::build() {
        //auto function = makeProtoType(&declaration, userData);
        std::string result = std::vformat("package {};\n"
                                          "\n"
                                          "{}"
                                          "{}"
                                          "\n"
                                          "import java.lang.foreign.*;\n"
                                          "import java.lang.invoke.MethodHandle;\n"
                                          "import java.lang.invoke.MethodHandles;\n"
                                          "\n",
                                          std::make_format_args(config.typedefFunc.typedefFuncPackageName,
                                                                typeManager->getImports(&config, true),
                                                                typeManager->getImports()));
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
        if ((*typeManager).isAlreadyGenerated(declaration)) {
            std::cout << "ignore already generated functionProtocol: " << className << std::endl;
            return;
        }
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