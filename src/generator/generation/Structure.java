package generator.generation;

import generator.TypePkg;
import generator.config.PackagePath;
import generator.types.StructType;
import generator.types.TypeAttr;

import java.util.Set;

public final class Structure implements Generation<StructType> {
    private final TypePkg<StructType> structType;

    public Structure(PackagePath packagePath, StructType structType) {
        this.structType = new TypePkg<>(structType, packagePath.end(structType.typeName()));
    }

    public TypePkg<StructType> getStructType() {
        return structType;
    }

    @Override
    public Set<TypePkg<StructType>> getImplTypes() {
        return Set.of(structType);
    }

    @Override
    public Set<TypeAttr.Type> getRefTypes() {
        return structType.type().getReferencedTypes();
    }
}
