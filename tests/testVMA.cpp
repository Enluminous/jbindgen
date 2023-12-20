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

    auto preConfig = jbindgen::defaultGeneratorConfig("./generation/vulkan", "vulkan",
                                                      "vulkan", analysed_vk);
    jbindgen::Generator generator(
            jbindgen::defaultGeneratorConfig("./generation/vma", "vma", "vma", analyse,
                                             &preConfig).changeSharedPackage("vulkan.shared","./generation/vulkan/shared"));
    generator.generate();
    jbindgen::Generator(preConfig).generate();
    return 0;
}
