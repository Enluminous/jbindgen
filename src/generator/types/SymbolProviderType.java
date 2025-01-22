package generator.types;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public final class SymbolProviderType implements TypeAttr.ReferenceType, TypeAttr.GenerationType, TypeAttr.NamedType {
    private final String libraryName;

    public SymbolProviderType(String libraryName) {
        this.libraryName = libraryName;
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
        return libraryName + "Symbols";
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SymbolProviderType that)) return false;
        return Objects.equals(libraryName, that.libraryName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(libraryName);
    }
}
