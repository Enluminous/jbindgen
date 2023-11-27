#include <iostream>
#include "analyser/Analyser.h"
#include "generator/Generator.h"
#include "Utils.h"

int main() {
    const char *args[] = {"-I", "/usr/include"};
    jbindgen::Analyser analysed(jbindgen::defaultAnalyserConfig("../test/miniaudio.h", args, 2));

    jbindgen::Generator generator(jbindgen::defaultGeneratorConfig("./generation", "miniaudio", "miniaudio"));
    generator.generateEnum(analysed.enums, nullptr);

    generator.generateFunctions(analysed.functions);

    for (auto &item: analysed.structs)
        generator.generateStructs(item, nullptr, nullptr, &analysed, &analysed, nullptr);
    for (auto &item: analysed.typedefs) {
        generator.generateTypedef(item, nullptr,
                                  [](const jbindgen::NormalTypedefDeclaration *declaration, void *userData) {
                                      return false;
                                  });
    }
    for (const auto &item: analysed.typedefFunctions) {
        generator.generateTypedefFunction(item, nullptr);
    }

    generator.generateNormalMacro(analysed.normalMacro);

    generator.generateVarDeclares(analysed.vars);
    return 0;
}