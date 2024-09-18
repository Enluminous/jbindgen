package generator.types;

import java.util.Set;

public final class VoidType implements TypeAttr.NType {
    public static final VoidType JAVA_VOID = new VoidType("void");
    private final String typeName;

    public VoidType(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String getTypeName() {
        return typeName;
    }

    @Override
    public Set<TypeAttr.Type> getReferencedTypes() {
        return Set.of();
    }
}
