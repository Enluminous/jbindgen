#include <iostream>
#include "analyser/Analyser.h"
#include "generator/Generator.h"

int main() {
    const char* args[] = {"-I", "/usr/include"};
    jbindgen::Analyser analysed(jbindgen::defaultAnalyserConfig("/usr/include/vulkan/vulkan_core.h", args, 2));

    jbindgen::Generator generator(
        jbindgen::defaultGeneratorConfig("./generation/miniaudio", "miniaudio", "miniaudio", analysed));
    generator.generate();
    return 0;
}
