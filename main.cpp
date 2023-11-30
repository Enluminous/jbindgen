#include <iostream>
#include "analyser/Analyser.h"
#include "generator/Generator.h"

int main() {
    const char* args[] = {"-I", "/usr/include"};
    jbindgen::Analyser analysed(jbindgen::defaultAnalyserConfig("../test/miniaudio.h", args, 2));

    jbindgen::Generator generator(
        jbindgen::defaultGeneratorConfig("./generation", "miniaudio", "miniaudio", analysed));
    std::vector<jbindgen::EnumDeclaration> enums;
    for (const auto&enum_declaration: analysed.enums) {
        enums.emplace_back(*enum_declaration);
    }
    generator.generateEnum(enums, nullptr);
    std::vector<jbindgen::FunctionDeclaration> function_declarations;
    for (const auto&function_declaration: analysed.functions) {
        function_declarations.emplace_back(*function_declaration);
    }
    generator.generateFunctions(function_declarations);

    for (auto&item: analysed.structs)
        generator.generateStructs(*item, nullptr, nullptr, &analysed, &analysed, nullptr);
    for (auto&item: analysed.typedefs) {
        generator.generateTypedef(*item, nullptr);
    }
    for (const auto&item: analysed.typedefFunctions) {
        generator.generateTypedefFunction(*item, nullptr);
    }
    std::vector<jbindgen::NormalMacroDeclaration> macro_declarations;
    for (const auto&normal_macro_declaration: analysed.normalMacro) {
        macro_declarations.emplace_back(*normal_macro_declaration);
    }
    generator.generateNormalMacro(macro_declarations);
    std::vector<jbindgen::VarDeclaration> var_declarations;
    for (const auto&var_declaration: analysed.vars)
        var_declarations.emplace_back(*var_declaration);
    generator.generateVarDeclares(var_declarations);
    return 0;
}
