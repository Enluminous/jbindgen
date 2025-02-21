package analyser.types;

import analyser.PrimitiveTypes;

public final class Primitive extends Type {
    private final PrimitiveTypes.CType primitiveType;

    public Primitive(String typeName, long sizeof, PrimitiveTypes.CType primitiveType, String location, long align) {
        super(typeName, sizeof, location, align);
        this.primitiveType = primitiveType;
    }

    @Override
    public String toString() {
        return "Primitive{" +
                "typeName='" + typeName + '\'' +
                '}';
    }

    public PrimitiveTypes.CType getPrimitiveType() {
        return primitiveType;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Primitive && super.equals(o);
    }
}
