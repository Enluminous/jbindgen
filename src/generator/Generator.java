package generator;

import java.nio.file.Path;

public class Generator {
    private final SharedValueGeneration sharedValueGeneration;
    private final SharedNativeGeneration sharedNativeGeneration;

    public Generator(String basePackage, Path path) {
        sharedValueGeneration = new SharedValueGeneration(basePackage, path.resolve("shared"));
        sharedNativeGeneration = new SharedNativeGeneration(basePackage, path.resolve("shared"));
    }

    public void generate() {
        sharedValueGeneration.gen();
        sharedNativeGeneration.gen();
    }
}
