package generator.types;

import java.util.Optional;
import java.util.Set;

public record RefOnlyType(String typeName) implements
        TypeAttr.TypeRefer, TypeAttr.GenerationType, TypeAttr.NamedType {

    @Override
    public Set<Holder<TypeAttr.TypeRefer>> getUseImportTypes() {
        return Set.of(new Holder<>(this));
    }

    @Override
    public Set<Holder<TypeAttr.TypeRefer>> getDefineImportTypes() {
        return Set.of();
    }

    @Override
    public Optional<Holder<RefOnlyType>> toGenerationTypes() {
        return Optional.of(new Holder<>(this));
    }

    @Override
    public String typeName(NameType nameType) {
        return typeName;
    }
}
