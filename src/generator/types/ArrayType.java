package generator.types;

import generator.Utils;
import generator.types.operations.ArrayOp;
import generator.types.operations.OperationAttr;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public final class ArrayType implements IGenerationType {
    private final String typeName;
    private final long length;
    private final TypeAttr.ReferenceType element;
    private final long byteSize;

    public ArrayType(Optional<String> typeName, long length, TypeAttr.ReferenceType element, long byteSize) {
        this.typeName = typeName.orElseGet(() -> CommonTypes.SpecificTypes.Array.getGenericName(((TypeAttr.NamedType) element).typeName()));
        this.length = length;
        this.element = element;
        this.byteSize = byteSize;
    }

    @Override
    public OperationAttr.Operation getOperation() {
        return new ArrayOp(typeName, ((TypeAttr.NamedType) element).typeName(), length, byteSize);
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
        return length == arrayType.length && byteSize == arrayType.byteSize && Objects.equals(typeName, arrayType.typeName) && Objects.equals(element, arrayType.element);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeName, length, element, byteSize);
    }

    @Override
    public String typeName() {
        return typeName;
    }

    @Override
    public String getMemoryLayout() {
        return Utils.makeMemoryLayout(byteSize);
    }

    @Override
    public long getByteSize() {
        return byteSize;
    }
}
