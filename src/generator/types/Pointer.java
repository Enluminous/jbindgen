package generator.types;

public final class Pointer extends TypeAttr.AbstractType {
    private final TypeAttr.Type pointee;

    public Pointer(long byteSize, String memoryLayout, String typeName, TypeAttr.Type pointee) {
        super(byteSize, memoryLayout, typeName);
        this.pointee = pointee;
    }
}
