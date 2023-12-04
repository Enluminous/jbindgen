//
// Created by snownf on 23-12-4.
//
#include <iostream>
#include "../analyser/Analyser.h"
#include "../generator/Generator.h"

#ifndef CURRENT_BUILD_DIR
#error CURRENT_BUILD_DIR not defined
#endif

int main() {
    const char *args[] = {"-I", "/usr/include"};
    std::string headPath = CURRENT_BUILD_DIR"/tests/miniaudio/src/miniaudio-git/extras/miniaudio_split/miniaudio.h";
    jbindgen::Analyser analysed(jbindgen::defaultAnalyserConfig(headPath, args, 2));
    jbindgen::Generator generator(
            jbindgen::defaultGeneratorConfig("./generation/miniaudio", "miniaudio", "miniaudio", analysed));
    generator.generate();
    return 0;
}
