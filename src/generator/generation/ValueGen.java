package generator.generation;

import generator.config.PackagePath;
import generator.types.TypeAttr;
import generator.types.ValueBasedType;

import java.util.Set;

public final class ValueGen extends AbstractGeneration {
    private final ValueBasedType type;

    public ValueGen(PackagePath packagePath, ValueBasedType type) {
        super(packagePath.end(type.getTypeName()));
        this.type = type;
    }

    @Override
    public Set<TypeAttr.NType> getSelfTypes() {
        return Set.of(type);
    }

    @Override
    public Set<TypeAttr.Type> getReferencedTypes() {
        return type.getReferencedTypes();
    }
}
