package generator.types;

import java.util.Set;

public record RefOnlyType(String typeName) implements TypeAttr.NType {

    @Override
    public Set<TypeAttr.Type> getReferencedTypes() {
        return Set.of();
    }
}
