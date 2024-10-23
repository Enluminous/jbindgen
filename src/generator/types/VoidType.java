package generator.types;

import java.util.Objects;
import java.util.Set;

public final class VoidType implements TypeAttr.NType {
    public static final VoidType JAVA_VOID = new VoidType("void");
    private final String typeName;

    public VoidType(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String typeName() {
        return typeName;
    }

    @Override
    public Set<TypeAttr.Type> getReferencedTypes() {
        return Set.of();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VoidType voidType)) return false;
        return Objects.equals(typeName, voidType.typeName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(typeName);
    }

    @Override
    public String toString() {
        return "VoidType{" +
               "typeName='" + typeName + '\'' +
               '}';
    }
}
