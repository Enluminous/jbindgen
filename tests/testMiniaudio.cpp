//
// Created by snownf on 23-12-4.
//
#include <iostream>
#include "../analyser/Analyser.h"
#include "../generator/Generator.h"

#ifndef TEST_SRC_DIR
#error TEST_SRC_DIR not defined
#endif

int main() {
    const char *args[] = {"-I", "/usr/include"};
    std::string headPath = TEST_SRC_DIR"/extras/miniaudio_split/miniaudio.h";
    jbindgen::Analyser analysed(jbindgen::defaultAnalyserConfig(headPath, args, 2));
    jbindgen::GeneratorConfig generatorConfig = jbindgen::defaultGeneratorConfig("./generation/miniaudio",
                                                                                 "miniaudio", "miniaudio",
                                                                                 analysed);
    generatorConfig.changeSharedPackage("shared", "./generation/shared");
    jbindgen::Generator generator(
            generatorConfig);
    generator.generate();
    return 0;
}
