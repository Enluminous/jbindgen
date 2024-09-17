package generator.generation;

import generator.config.PackagePath;
import generator.types.StructType;

public final class StructGen extends AbstractGeneration {
    private final StructType structType;

    public StructGen(PackagePath packagePath, StructType structType) {
        super(packagePath.end(structType.getTypeName()));
        this.structType = structType;
    }
}
