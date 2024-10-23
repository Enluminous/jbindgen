package generator.generation;

import generator.config.PackagePath;
import generator.types.ArrayType;

public class Array extends AbstractGeneration<ArrayType> {
    public Array(PackagePath packagePath, ArrayType type) {
        super(packagePath, type);
    }
}
