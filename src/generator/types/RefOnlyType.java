package generator.types;

import java.util.Optional;
import java.util.Set;

public record RefOnlyType(String typeName) implements
        TypeAttr.ReferenceType, TypeAttr.GenerationType, TypeAttr.NamedType {

    @Override
    public Set<TypeAttr.ReferenceType> getUseImportTypes() {
        return Set.of(this);
    }

    @Override
    public Set<TypeAttr.ReferenceType> getDefineImportTypes() {
        return Set.of();
    }

    @Override
    public Optional<GenerationTypeHolder<RefOnlyType>> toGenerationTypes() {
        return Optional.of(new GenerationTypeHolder<>(this));
    }

    @Override
    public String typeName(NameType nameType) {
        return typeName;
    }

    @Override
    public String toString() {
        return "RefOnlyType{" +
               "typeName='" + typeName + '\'' +
               '}';
    }
}
