package generator.types;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public record SymbolProviderType(
        String className) implements TypeAttr.ReferenceType, TypeAttr.GenerationType, TypeAttr.NamedType {

    public SymbolProviderType {
        Objects.requireNonNull(className);
    }

    @Override
    public Set<TypeAttr.ReferenceType> getUseImportTypes() {
        return Set.of(this);
    }

    @Override
    public Set<TypeAttr.ReferenceType> getDefineImportTypes() {
        return Set.of();
    }

    @Override
    public Optional<GenerationTypeHolder<SymbolProviderType>> toGenerationTypes() {
        return Optional.of(new GenerationTypeHolder<>(this));
    }

    @Override
    public String typeName(NameType nameType) {
        return className;
    }
}
