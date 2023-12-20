#include "../analyser/Analyser.h"
#include "../generator/Generator.h"

int main() {
    const char* args_vk[] = {"-I", "/usr/include"};
    auto analyser_config = jbindgen::defaultAnalyserConfig(
        TEST_SRC_DIR"/../../../vulkan/src/vulkan-src/include/vulkan/vulkan_core.h",
        args_vk, 2);
    analyser_config.declFilter = [](const CXCursor&c, const CXCursor&parent, const jbindgen::AnalyserConfig&config) {
        unsigned line;
        unsigned column;
        CXFile file;
        unsigned offset;
        clang_getSpellingLocation(clang_getCursorLocation(c), &file, &line, &column, &offset);
        if (!jbindgen::toStringIfNullptr(clang_getFileName(file)).contains(config.acceptedPath)) {
            return false;
        }
        const auto&cursorKind = c.kind;
        const auto&linkage = clang_getCursorLinkage(c);
        if (cursorKind == CXCursor_StructDecl) {
            return true;
        }
        if (cursorKind == CXCursor_UnionDecl) {
            return true;
        }
        if (cursorKind == CXCursor_TypedefDecl) {
            if (linkage == CXLinkage_External || linkage == CXLinkage_NoLinkage) {
                return true;
            }
        }
        if (cursorKind == CXCursor_FunctionDecl) {
            if (linkage == CXLinkage_External) {
                return true;
            } //only process external symbol
        }
        if (cursorKind == CXCursor_VarDecl) {
            if (linkage == CXLinkage_External || linkage == CXLinkage_Internal) {
                return true;
            }
        }
        if (cursorKind == CXCursor_EnumConstantDecl || cursorKind == CXCursor_EnumDecl) {
            if (linkage == CXLinkage_External) {
                return true;
            }
        }
        return false;
    };
    jbindgen::Analyser vk_analysed(
        analyser_config);
    auto vk_generator_config = jbindgen::defaultGeneratorConfig("./generation/vulkan", "vulkan",
                                                                "vulkan", vk_analysed);
    jbindgen::Generator(vk_generator_config).generate();


    const char* args[] = {"-I", "/usr/include"};
    jbindgen::Analyser vma_analysed(
        jbindgen::defaultAnalyserConfig(TEST_SRC_DIR"/include/vk_mem_alloc.h", args, 2));
    jbindgen::Generator generator(
        jbindgen::defaultGeneratorConfig("./generation/vma", "vma", "vma", vma_analysed,
                                         &vk_generator_config).changeSharedPackage(
            "vulkan.shared", "./generation/vulkan/shared"));
    generator.generate();
    return 0;
}
