package generator.generator;

import generator.config.PackagePath;

import java.nio.file.Path;

public abstract class AbstractGenerator {
    protected final String basePackageName;
    protected final Path path;

    protected AbstractGenerator(PackagePath packagePath) {
        this.basePackageName = packagePath.getPackage();
        this.path = packagePath.getPath();
    }
}
