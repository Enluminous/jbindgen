#include "../analyser/Analyser.h"
#include "../generator/Generator.h"

#ifndef CURRENT_BUILD_DIR
#error CURRENT_BUILD_DIR not defined
#endif

//
// Created by nettal on 23-12-4.
//
int main() {
    const char* args[] = {"-I", CURRENT_BUILD_DIR"/tests/freetype/src/freetype-git/include/","-I","/usr/include"};
    jbindgen::AnalyserConfig config = jbindgen::defaultAnalyserConfig(CURRENT_BUILD_DIR"/tests/freetype/src/freetype-git/include/freetype/freetype.h", args,
                                                                      4);
    config.acceptedPath="freetype";
    jbindgen::Analyser analysed(config);

    jbindgen::Generator generator(
            jbindgen::defaultGeneratorConfig("./generation/freetype", "freetype", "freetype", analysed));
    generator.generate();
    return 0;
}
