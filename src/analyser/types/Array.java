package analyser.types;

public class Array extends Type {
    private final Type elementType;
    private final long elementCount;

    public Array(String typeName, Type elementType, long elementCount) {
        super(typeName);
        this.elementType = elementType;
        this.elementCount = elementCount;
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
