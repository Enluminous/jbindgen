#include <iostream>
#include "analyser/Analyser.h"
#include "generator/Generator.h"

int main() {
    const char *args[] = {"-I", "/usr/include"};
    jbindgen::Analyser analysed("../test/miniaudio.h", args, 2);

    jbindgen::Generator generator(jbindgen::defaultConfig("./generation", "miniaudio", "miniaudio"));
    generator.generateEnum(analysed.enums, nullptr, [](jbindgen::EnumDeclaration *declaration) {
        if (declaration->name.find("unnamed") != std::string::npos) {
            if (declaration->name.find("/usr/include") != std::string::npos) {
                std::cout << "filtrate a declaration: " << declaration->name << std::endl;
                return true;
            } else
                std::cout << "WARNING: filtrate a unnamed declaration: " << declaration->name << std::endl;
            return false;
        }
        return false;
    });
    for (auto &item: analysed.structs)
        generator.generateStructs(item, nullptr, nullptr, &analysed, &analysed);
    return 0;
}