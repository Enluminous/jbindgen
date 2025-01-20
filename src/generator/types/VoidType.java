package generator.types;

import java.util.Optional;
import java.util.Set;

public record VoidType(String typeName) implements TypeAttr.ReferenceType, TypeAttr.NamedType, TypeAttr.GenerationType {
    @Override
    public Set<TypeAttr.ReferenceType> getUseImportTypes() {
        return Set.of(this);
    }

    @Override
    public Set<TypeAttr.ReferenceType> getDefineImportTypes() {
        return Set.of();
    }

    @Override
    public Optional<GenerationTypeHolder<VoidType>> toGenerationTypes() {
        return Optional.empty();
    }

    @Override
    public String typeName() {
        return typeName;
    }
}
