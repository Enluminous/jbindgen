package generator.types;

public final class VoidType implements TypeAttr.Type {
    public static final VoidType JAVA_VOID = new VoidType("void");
    private final String typeName;

    public VoidType(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String getTypeName() {
        return typeName;
    }
}
