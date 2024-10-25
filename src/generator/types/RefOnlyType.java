package generator.types;

import java.util.Set;

public record RefOnlyType(String typeName) implements TypeAttr.NType {

    @Override
    public Set<TypeAttr.Type> getReferenceTypes() {
        return Set.of(this);
    }

    @Override
    public Set<TypeAttr.Type> getDefineReferTypes() {
        return Set.of();
    }

    @Override
    public Set<TypeAttr.Type> toGenerationTypes() {
        return Set.of(this);
    }

    @Override
    public String toString() {
        return "RefOnlyType{" +
               "typeName='" + typeName + '\'' +
               '}';
    }
}
