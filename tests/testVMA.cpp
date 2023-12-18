#include <iostream>
#include "../analyser/Analyser.h"
#include "../generator/Generator.h"

int main() {
    const char *args_vk[] = {"-I", "/usr/include"};
    jbindgen::Analyser analysed_vk(
            jbindgen::defaultAnalyserConfig(TEST_SRC_DIR"/../../../vulkan/src/vulkan-src/include/vulkan/vulkan_core.h",
                                            args_vk, 2));

    const char *args[] = {"-I", "/usr/include"};
    jbindgen::Analyser analyse(
            jbindgen::defaultAnalyserConfig(TEST_SRC_DIR"/include/vk_mem_alloc.h", args, 2));

    jbindgen::Generator generator(
            jbindgen::defaultGeneratorConfig("./generation/vma", "vma", "vma", analyse,
                                             {jbindgen::defaultGeneratorConfig("./generation/vulkan", "vulkan",
                                                                               "vulkan", analysed_vk)}));
    generator.generate();
    return 0;
}
