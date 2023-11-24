//
// Created by nettal on 23-11-13.
//

#ifndef JAVABINDGEN_FUNCTIONPROTOTYPEGENERATOR_H
#define JAVABINDGEN_FUNCTIONPROTOTYPEGENERATOR_H

#include <utility>
#include <format>
#include <iostream>

#include "../analyser/FunctionTypeDefDeclaration.h"
#include "GenUtils.h"
#include "FunctionSymbolGeneratorUtils.h"

namespace jbindgen {
    struct FunctionProtoTypeWrapperInfo {
        std::string wrapperClassName;
        std::string wrapperName;
        std::vector<std::string> jParameters;
        std::vector<std::string> encodeParameters;
        std::vector<std::string> decodeParameters;
        std::string resultDescriptor;//optional, depend on hasResult
        std::string jResult;//optional, depend on hasResult
    };

    struct FunctionProtoTypeInfo {
        std::string functionName;
        std::string className;
        std::vector<std::string> jParameters;
        std::vector<std::string> functionDescriptors;
        std::vector<std::string> invokeParameters;
        std::vector<FunctionProtoTypeWrapperInfo> wrappers;
        std::string resultDescriptor;
        std::string jResult;
        bool hasResult;
        bool needAllocator;
        bool critical;
    };


    typedef FunctionProtoTypeInfo(*PFN_makeProtoType)(const jbindgen::FunctionTypedefDeclaration *declaration,
                                                      void *pUserdata);

    class FunctionProtoTypeGenerator {
        const FunctionTypedefDeclaration declaration;
        const std::string dir;
        const PFN_makeProtoType makeProtoType;

        const std::string defsCallbackPackageName;
        const std::string defCallbackDir;
        const std::string nativeFunctionPackageName;
    public:
        FunctionProtoTypeGenerator(FunctionTypedefDeclaration declaration, std::string dir,
                                   std::string defsCallbackPackageName,
                                   std::string defCallbackDir,
                                   std::string nativeFunctionPackageName,
                                   PFN_makeProtoType makeProtoType);

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
            auto funcDeclaration = declaration;
            std::string className = funcDeclaration.function.name;
            FunctionDeclaration fDec(funcDeclaration.function, funcDeclaration.ret, funcDeclaration.canonicalName);
            for (const auto &para: funcDeclaration.paras)
                fDec.addPara(para);
            auto decodedFunc = FunctionSymbolGeneratorUtils::defaultMakeFunction(&fDec, nullptr);
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
        }
    };

} // jbindgen


#endif //JAVABINDGEN_FUNCTIONPROTOTYPEGENERATOR_H
