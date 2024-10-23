package generator.types;

import java.util.Set;

public record VoidType(String typeName) implements TypeAttr.NType {
    public static final VoidType JAVA_VOID = new VoidType("void");

    @Override
    public Set<TypeAttr.Type> getReferencedTypes() {
        return Set.of();
    }
}
