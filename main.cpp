#include <iostream>
#include "analyser/Analyser.h"

int main() {
    const char *args[] = {"-I", "/usr/include"};
    jbindgen::Analyser analysed("../test/miniaudio.h", args, 2);
    return 0;
}