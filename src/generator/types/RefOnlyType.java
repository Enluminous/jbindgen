package generator.types;

import java.util.Set;

public record RefOnlyType(String typeName) implements TypeAttr.NType {

    @Override
    public Set<TypeAttr.Type> getReferencedTypes() {
        return Set.of();
    }

    @Override
    public String toString() {
        return "RefOnlyType{" +
               "typeName='" + typeName + '\'' +
               '}';
    }
}
