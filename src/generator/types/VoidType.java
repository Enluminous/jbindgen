package generator.types;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public record VoidType(String typeName) implements TypeAttr.ReferenceType, TypeAttr.NamedType, TypeAttr.GenerationType {
    public VoidType {
        Objects.requireNonNull(typeName, "use VoidType.VOID instead");
    }

    public static final VoidType VOID = new VoidType("Void");

    @Override
    public Set<TypeAttr.ReferenceType> getUseImportTypes() {
        return Set.of(this);
    }

    @Override
    public Set<TypeAttr.ReferenceType> getDefineImportTypes() {
        return Set.of();
    }

    @Override
    public Optional<Holder<VoidType>> toGenerationTypes() {
        return Optional.of(new Holder<>(this));
    }

    @Override
    public String typeName(NameType nameType) {
        return typeName;
    }
}
