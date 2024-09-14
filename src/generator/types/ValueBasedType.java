package generator.types;

public final class ValueBasedType extends TypeAttr.AbstractType implements TypeAttr.ValueBased {
    public ValueBasedType(String typeName, Primitives primitive) {
        super(primitive.getByteSize(), primitive.getMemoryLayout(), typeName);
    }
}
