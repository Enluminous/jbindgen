#include <iostream>
#include "analyser/Analyser.h"
#include "generator/Generator.h"

int main() {
    const char* args[] = {"-I", "/usr/include"};
    jbindgen::Analyser analysed(jbindgen::defaultAnalyserConfig("../test/miniaudio.h", args, 2));

    jbindgen::Generator generator(
        jbindgen::defaultGeneratorConfig("./generation", "miniaudio", "miniaudio", analysed));
    generator.generateEnum(analysed.enums);
    generator.generateFunctions(analysed.functions);
    generator.generateFunctions(analysed.noCXCursorFunctions);

    for (auto&item: analysed.structs)
        generator.generateStructs(item, nullptr, nullptr, &analysed, &analysed, nullptr);
    for (auto&item: analysed.typedefs) {
        generator.generateTypedef(item, nullptr);
    }
    for (const auto&item: analysed.typedefFunctions) {
        generator.generateTypedefFunction(item, nullptr);
    }
    generator.generateNormalMacro(analysed.normalMacro);
    generator.generateVarDeclares(analysed.vars);
    return 0;
}
