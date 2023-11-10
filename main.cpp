#include "analyser/Analyser.h"
#include "generator/Generator.h"

int main() {
    const char *args[] = {"-I", "/usr/include"};
    jbindgen::Analyser analysed("../test/miniaudio.h", args, 2);

    jbindgen::Generator generator(jbindgen::defaultConfig("./generation", "miniaudio", "miniaudio"));
    generator.generateEnum(analysed.enums, nullptr);
    for (auto &item: analysed.structs)
        generator.generateStructs(item, nullptr, nullptr, &analysed, &analysed);
    return 0;
}