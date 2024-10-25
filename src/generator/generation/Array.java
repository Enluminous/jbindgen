package generator.generation;

import generator.Dependency;
import generator.PackagePath;
import generator.types.ArrayType;

public final class Array extends AbstractGeneration<ArrayType> {
    public Array(PackagePath packagePath, ArrayType type) {
        super(packagePath, type);
    }

    @Override
    public void generate(Dependency dependency) {
        System.err.println("todo: generate this");
    }
}
