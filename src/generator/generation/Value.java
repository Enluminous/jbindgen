package generator.generation;

import generator.TypePkg;
import generator.config.PackagePath;
import generator.types.TypeAttr;
import generator.types.ValueBasedType;

import java.util.Set;

public final class Value implements Generation<ValueBasedType> {
    private final TypePkg<ValueBasedType> type;

    public Value(PackagePath packagePath, ValueBasedType type) {
        this.type = new TypePkg<>(type, packagePath.end(type.typeName()));
    }

    @Override
    public Set<TypePkg<ValueBasedType>> getImplTypes() {
        return Set.of(type);
    }

    @Override
    public Set<TypeAttr.Type> getRefTypes() {
        return type.type().getReferencedTypes();
    }
}
