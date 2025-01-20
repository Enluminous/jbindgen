package generator.types;

import java.util.Set;

public record VoidType(String typeName) implements TypeAttr.ReferenceType, TypeAttr.NamedType {
    @Override
    public Set<TypeAttr.ReferenceType> getUseImportTypes() {
        return Set.of(this);
    }

    @Override
    public Set<TypeAttr.ReferenceType> getDefineImportTypes() {
        return Set.of();
    }

    @Override
    public Set<TypeAttr.GenerationType> toGenerationTypes() {
        return Set.of();
    }

    @Override
    public String typeName() {
        return typeName;
    }
}
