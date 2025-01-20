package generator.types;

import java.util.Set;

public record RefOnlyType(String typeName) implements TypeAttr.ReferenceType, TypeAttr.GenerationType, TypeAttr.NamedType {

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
        return Set.of(this);
    }

    @Override
    public String typeName() {
        return typeName;
    }

    @Override
    public String toString() {
        return "RefOnlyType{" +
               "typeName='" + typeName + '\'' +
               '}';
    }
}
