//
// Created by snownf on 24-2-8.
//

#include "analyser/Analyser.h"
#include "generator/Generator.h"

int main() {
    const char *args[] = {"-I", "/usr/include"};
    jbindgen::AnalyserConfig analyserConfig = jbindgen::defaultAnalyserConfig(
            GEN_FILE, args, 2);
    // the file contains 'acceptedPath' to generate bindings
    // used for #include in .h files
    // here just set as 'example' because 'example.h' is in 'example' dir
    analyserConfig.acceptedPath = "example";

    // do analyse
    jbindgen::Analyser analysed(analyserConfig);

    jbindgen::GeneratorConfig generatorConfig = jbindgen::defaultGeneratorConfig(
            // the result dir, relative, used for package path
            "./generation/enluminous/natives/example",
            // prefix of some class
            // here just as 'Example'
            "Example",
            // package name
            "enluminous.natives.example",
            // previous analysed
            analysed);

    // generate base-libraries
    generatorConfig.shared.skipGenerate = false;
    // change shared package name
    generatorConfig.changeSharedPackage(
            // package name
            "enluminous.natives.shared",
            "./generation/enluminous/natives/shared");
    jbindgen::Generator generator(generatorConfig);
    generator.generate();
    return 0;
}
