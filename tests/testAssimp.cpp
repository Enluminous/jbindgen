//
// Created by snownf on 23-12-6.
//

#include "../analyser/Analyser.h"
#include "../generator/Generator.h"

#ifndef TEST_SRC_DIR
#error TEST_SRC_DIR not defined
#endif

#ifndef MAIN_SRC_DIR
#error MAIN_SRC_DIR not defined
#endif

int main() {
    const char *args[] = {"-I", "/usr/include", "-I", TEST_SRC_DIR"/include"};
    jbindgen::AnalyserConfig config = jbindgen::defaultAnalyserConfig(MAIN_SRC_DIR"/tests/testAssimpIndex.h", args,
                                                                      4);
    config.acceptedPath = "assimp";
    jbindgen::Analyser analysed(config);

    jbindgen::Generator generator(
            jbindgen::defaultGeneratorConfig("./generation/assimp", "assimp", "assimp", analysed));
    generator.generate();
    return 0;
}
