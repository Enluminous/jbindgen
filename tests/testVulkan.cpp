//
// Created by snownf on 23-12-6.
//

#include "../analyser/Analyser.h"
#include "../generator/Generator.h"

#ifndef TEST_SRC_DIR
#error TEST_SRC_DIR not defined
#endif

#ifndef MAIN_SRC_DIR
#error MAIN_SRC_DIR not defined
#endif

int main() {
    const char *args[] = {"-I", "/usr/include"};
    jbindgen::AnalyserConfig config = jbindgen::defaultAnalyserConfig(TEST_SRC_DIR"/include/vulkan/vulkan.h", args,
                                                                      2);
    jbindgen::Analyser analysed(config);
    jbindgen::Generator generator(
            jbindgen::defaultGeneratorConfig("./generation/vulkan", "vulkan", "vulkan", analysed));
    generator.generate();
    return 0;
}
