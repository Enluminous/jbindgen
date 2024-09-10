package generator;

import java.nio.file.Path;

public class Generator {
    private final String basePackage;
    private final SharedGeneration sharedGeneration;

    public Generator(String basePackage, Path path) {
        this.basePackage = basePackage;
        sharedGeneration = new SharedGeneration(basePackage, path);
    }

    public void generate() {
        sharedGeneration.gen();
    }
}
