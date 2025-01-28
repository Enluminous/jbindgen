package generator.types;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public record SymbolProviderType(
        String className) implements TypeAttr.TypeRefer, TypeAttr.GenerationType, TypeAttr.NamedType {

    public SymbolProviderType {
        Objects.requireNonNull(className);
    }

    @Override
    public Set<Holder<TypeAttr.TypeRefer>> getUseImportTypes() {
        return Set.of(new Holder<>(this));
    }

    @Override
    public Set<Holder<TypeAttr.TypeRefer>> getDefineImportTypes() {
        return Set.of();
    }

    @Override
    public Optional<Holder<SymbolProviderType>> toGenerationTypes() {
        return Optional.of(new Holder<>(this));
    }

    @Override
    public String typeName(TypeAttr.NameType nameType) {
        return className;
    }
}
