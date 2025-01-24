package analyser.types;

public final class Array extends Type {
    private final Type elementType;
    private final long elementCount;

    public Array(String typeName, Type elementType, long elementCount, long sizeOf) {
        super(typeName, sizeOf);
        this.elementType = elementType;
        this.elementCount = elementCount;
    }

    public Type getElementType() {
        return elementType;
    }

    public long getElementCount() {
        return elementCount;
    }

    @Override
    public String toString() {
        return "Array{" +
                "elementType=" + elementType +
                ", elementCount=" + elementCount +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}
