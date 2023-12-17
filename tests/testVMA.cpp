#include <iostream>
#include "../analyser/Analyser.h"
#include "../generator/Generator.h"

int main() {
    const char *args_vk[] = {"-I", "/usr/include"};
    jbindgen::Analyser analysed_vk(jbindgen::defaultAnalyserConfig("/usr/include/vulkan/vulkan_core.h", args_vk, 2));

    jbindgen::Generator generator_vk(
            jbindgen::defaultGeneratorConfig("./generation/vulkan", "vulkan", "vulkan", analysed_vk));
    generator_vk.generate();

    const char *args[] = {"-I", "/usr/include"};
    jbindgen::Analyser analysed(jbindgen::defaultAnalyserConfig(TEST_SRC_DIR"/include/vk_mem_alloc.h", args, 2),
                                &analysed_vk);

    jbindgen::Generator generator(
            jbindgen::defaultGeneratorConfig("./generation/vma", "vma", "vma", analysed));
    generator.generate();
    return 0;
}
