package generator.types;

import generator.Utils;
import generator.types.operations.MemoryBased;
import generator.types.operations.OperationAttr;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public final class ArrayType extends AbstractGenerationType {
    private final long length;
    private final TypeAttr.SizedType sizedType;

    public ArrayType(String typeName, long length, TypeAttr.SizedType sizedType, long byteSize) {
        super(byteSize, Utils.makeMemoryLayout(byteSize), typeName);
        this.length = length;
        this.sizedType = sizedType;
    }

    @Override
    public OperationAttr.Operation getOperation() {
        return new MemoryBased(typeName, byteSize);
    }

    @Override
    public Set<TypeAttr.ReferenceType> getDefineReferTypes() {
        Set<TypeAttr.ReferenceType> types = new HashSet<>(((TypeAttr.ReferenceType) sizedType).getReferenceTypes());
        types.addAll(CommonTypes.SpecificTypes.NList.getReferenceTypes());
        return types;
    }

    public long getLength() {
        return length;
    }

    @Override
    public String toString() {
        return "ArrayType{" +
               "length=" + length +
               ", sizedType=" + sizedType +
               ", typeName='" + typeName + '\'' +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArrayType arrayType)) return false;
        if (!super.equals(o)) return false;
        return length == arrayType.length && Objects.equals(sizedType, arrayType.sizedType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), length, sizedType);
    }
}
