package generator.generation;

import generator.TypePkg;
import generator.config.PackagePath;
import generator.types.TypeAttr;
import generator.types.ValueBasedType;

import java.util.Set;

public final class ValueGen implements Generation {
    private final TypePkg<ValueBasedType> type;

    public ValueGen(PackagePath packagePath, ValueBasedType type) {
        this.type = new TypePkg<>(type, packagePath.end(type.getTypeName()));
    }

    @Override
    public Set<TypePkg<?>> getImplTypes() {
        return Set.of(type);
    }

    @Override
    public Set<TypeAttr.Type> getRefTypes() {
        return type.type().getReferencedTypes();
    }
}
