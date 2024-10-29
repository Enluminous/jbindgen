package generator.types;

import generator.Utils;
import generator.types.operations.MemoryBased;
import generator.types.operations.OperationAttr;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public final class ArrayType extends AbstractGenerationType {
    private final long length;
    private final TypeAttr.ReferenceType element;

    public ArrayType(String typeName, long length, TypeAttr.ReferenceType element, long byteSize) {
        super(byteSize, Utils.makeMemoryLayout(byteSize), typeName);
        this.length = length;
        this.element = element;
    }

    @Override
    public OperationAttr.Operation getOperation() {
        return new MemoryBased(typeName, byteSize);
    }

    @Override
    public Set<TypeAttr.ReferenceType> getDefineReferTypes() {
        Set<TypeAttr.ReferenceType> types = new HashSet<>(element.getReferenceTypes());
        types.addAll(CommonTypes.SpecificTypes.Array.getReferenceTypes());
        return types;
    }

    @Override
    public Set<TypeAttr.ReferenceType> getReferenceTypes() {
        Set<TypeAttr.ReferenceType> types = new HashSet<>(element.getReferenceTypes());
        types.addAll(CommonTypes.SpecificTypes.Array.getReferenceTypes());
        return types;
    }

    @Override
    public Set<TypeAttr.GenerationType> toGenerationTypes() {
        Set<TypeAttr.GenerationType> types = new HashSet<>(element.toGenerationTypes());
        types.addAll(CommonTypes.SpecificTypes.Array.toGenerationTypes());
        return types;
    }

    public long getLength() {
        return length;
    }

    @Override
    public String toString() {
        return "ArrayType{" +
               "length=" + length +
               ", sizedType=" + element +
               ", typeName='" + typeName + '\'' +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArrayType arrayType)) return false;
        if (!super.equals(o)) return false;
        return length == arrayType.length && Objects.equals(element, arrayType.element);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), length, element);
    }
}
