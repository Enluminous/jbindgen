package generator.generation;

import generator.PackagePath;
import generator.types.ArrayType;

public final class Array extends AbstractGeneration<ArrayType> {
    public Array(PackagePath packagePath, ArrayType type) {
        super(packagePath, type);
    }
}
