#include <iostream>
#include "analyser/Analyser.h"
#include "generator/Generator.h"
#include "Utils.h"

int main() {
    const char *args[] = {"-I", "/usr/include"};
    jbindgen::Analyser analysed("../test/miniaudio.h", args, 2);

    jbindgen::Generator generator(jbindgen::defaultConfig("./generation", "miniaudio", "miniaudio"));
    generator.generateEnum(analysed.enums, nullptr, [](jbindgen::EnumDeclaration *declaration, void *userdata) {
        if (string_contains(declaration->name, "unnamed")) {
            if (string_contains(declaration->name, "/usr/include")) {
                std::cout << "filtrate a enum declaration: " << declaration->name << std::endl;
                return true;
            } else
                std::cout << "WARNING: filtrate a unnamed enum declaration: " << declaration->name << std::endl;
            return true;
        }
        return false;
    }, nullptr);

    generator.generateFunctions(analysed.functions);

    for (auto &item: analysed.structs)
        generator.generateStructs(item, nullptr, nullptr, &analysed, &analysed,
                                  [](const jbindgen::StructDeclaration *declaration, void *userdata) {
                                      std::cout << "structGenerationFilter: tests " << declaration->structType.name
                                                << std::endl;
                                      if (!string_startsWith(declaration->structType.name, "ma_")) {
                                          std::cout << "filtrate a struct declaration: " << declaration->structType.name
                                                    << " because struct name not start with \"ma_\"" << std::endl;
                                          return true;
                                      }
                                      return false;
                                  }, nullptr);
    for (auto &item: analysed.typedefs){
        generator.generateTypedef(item);
    }
    return 0;
}