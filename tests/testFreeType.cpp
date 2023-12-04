#include "../analyser/Analyser.h"
#include "../generator/Generator.h"

#ifndef TEST_SRC_DIR
#error TEST_SRC_DIR not defined
#endif

//
// Created by nettal on 23-12-4.
//
int main() {
    const char* args[] = {"-I", TEST_SRC_DIR"/include/","-I","/usr/include"};
    jbindgen::AnalyserConfig config = jbindgen::defaultAnalyserConfig(TEST_SRC_DIR"/include/freetype/freetype.h", args,
                                                                      4);
    config.acceptedPath="freetype";
    jbindgen::Analyser analysed(config);

    jbindgen::Generator generator(
            jbindgen::defaultGeneratorConfig("./generation/freetype", "freetype", "freetype", analysed));
    generator.generate();
    return 0;
}
