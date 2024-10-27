package generator.types;

import java.util.Set;

public record VoidType(String typeName) implements TypeAttr.ReferenceType, TypeAttr.NamedType {
    public static final VoidType JAVA_VOID = new VoidType("void");

    @Override
    public Set<TypeAttr.ReferenceType> getReferenceTypes() {
        return Set.of(this);
    }

    @Override
    public Set<TypeAttr.ReferenceType> getDefineReferTypes() {
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
