package generator.generation;

import generator.TypePkg;
import generator.config.PackagePath;
import generator.types.StructType;
import generator.types.TypeAttr;

import java.util.Set;

public final class StructGen implements Generation {
    private final TypePkg<StructType> structType;

    public StructGen(PackagePath packagePath, StructType structType) {
        this.structType = new TypePkg<>(structType, packagePath.end(structType.getTypeName()));
    }

    public TypePkg<StructType> getStructType() {
        return structType;
    }

    @Override
    public Set<TypePkg<?>> getImplTypes() {
        return Set.of(structType);
    }

    @Override
    public Set<TypeAttr.Type> getRefTypes() {
        return structType.type().getReferencedTypes();
    }
}
