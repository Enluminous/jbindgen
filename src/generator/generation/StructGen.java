package generator.generation;

import generator.config.PackagePath;
import generator.types.StructType;
import generator.types.TypeAttr;

import java.util.Set;

public final class StructGen extends AbstractGeneration {
    private final StructType structType;

    public StructGen(PackagePath packagePath, StructType structType) {
        super(packagePath.end(structType.getTypeName()));
        this.structType = structType;
    }

    public StructType getStructType() {
        return structType;
    }

    @Override
    public Set<TypeAttr.Type> getReferencedTypes() {
        return structType.getReferencedTypes();
    }
}
