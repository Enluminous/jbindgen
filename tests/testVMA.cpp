#include <iostream>
#include "../analyser/Analyser.h"
#include "../generator/Generator.h"

int main() {
    const char *args_vk[] = {"-I", "/usr/include"};
    jbindgen::Analyser analysed_vk(
            jbindgen::defaultAnalyserConfig(TEST_SRC_DIR"/../../../vulkan/src/vulkan-src/include/vulkan/vulkan_core.h",
                                            args_vk, 2));

    jbindgen::Generator generator_vk(
            jbindgen::defaultGeneratorConfig("./generation/vulkan", "vulkan", "vulkan", analysed_vk));
    generator_vk.generate();

    std::vector<jbindgen::Analyser *> analysed;
    analysed.push_back(&analysed_vk);
    const char *args[] = {"-I", "/usr/include"};
    jbindgen::Analyser analyse(
            jbindgen::defaultAnalyserConfig(TEST_SRC_DIR"/include/vk_mem_alloc.h", args, 2, analysed));

    jbindgen::Generator generator(
            jbindgen::defaultGeneratorConfig("./generation/vma", "vma", "vma", analyse));
    generator.generate();
    return 0;
}
