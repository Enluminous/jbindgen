#include <iostream>
#include "analyser/Analyser.h"
#include "generator/Generator.h"

int main() {
    const char* args[] = {"-I", "/usr/include"};
    jbindgen::Analyser analysed(jbindgen::defaultAnalyserConfig("/usr/include/vulkan/vulkan_core.h", args, 2));

    jbindgen::Generator generator(
        jbindgen::defaultGeneratorConfig("./generation", "miniaudio", "miniaudio", analysed));
    generator.generateEnum(analysed.enums);
    generator.generateFunctionSymbols(analysed.functionSymbols);

    for (auto&item: analysed.structs)
        generator.generateStructs(item);
    for (auto&item: analysed.typedefs) {
        generator.generateTypedef(item);
    }
    for (const auto&item: analysed.typedefFunctions) {
        generator.generateTypedefFunction(item);
    }
    for (const auto &item: analysed.functionsPointers)
        generator.generateTypedefFunction(item);
    generator.generateNormalMacro(analysed.normalMacro);
    generator.generateVarDeclares(analysed.vars);
    return 0;
}
