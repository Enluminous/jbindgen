package generator.types;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public record VoidType(String typeName) implements TypeAttr.TypeRefer, TypeAttr.NamedType, TypeAttr.GenerationType {
    public VoidType {
        Objects.requireNonNull(typeName, "use VoidType.VOID instead");
    }

    public static final VoidType VOID = new VoidType("Void");

    @Override
    public Set<Holder<TypeAttr.TypeRefer>> getUseImportTypes() {
        return Set.of(new Holder<>(this));
    }

    @Override
    public Set<Holder<TypeAttr.TypeRefer>> getDefineImportTypes() {
        return Set.of();
    }

    @Override
    public Optional<Holder<VoidType>> toGenerationTypes() {
        return Optional.of(new Holder<>(this));
    }

    @Override
    public String typeName(TypeAttr.NameType nameType) {
        return typeName;
    }
}
