#include <iostream>
#include "analyser/Analyser.h"

int main() {
    const char *args[] = {"-I", "/usr/include"};
    jbindgen::Analyser analysed("/usr/include/vulkan/vulkan_core.h", args, 2);
    return 0;
}