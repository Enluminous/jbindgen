package generator.types;

import generator.types.operations.ValueBased;
import generator.types.operations.OperationAttr;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public final class PointerType implements TypeAttr.ReferenceType, TypeAttr.OperationType, TypeAttr.SizedType {
    private final TypeAttr.ReferenceType pointee;

    public PointerType(TypeAttr.ReferenceType pointee) {
        this.pointee = pointee;
    }

    @Override
    public OperationAttr.Operation getOperation() {
        return new ValueBased("typeName", CommonTypes.Primitives.ADDRESS);
    }

    @Override
    public Set<TypeAttr.ReferenceType> getDefineReferTypes() {
        Set<TypeAttr.ReferenceType> types = new HashSet<>();
        types.addAll(pointee.getReferenceTypes());
        types.addAll(CommonTypes.BindTypes.Pointer.getReferenceTypes());
        return types;
    }

    @Override
    public Set<TypeAttr.ReferenceType> getReferenceTypes() {
        Set<TypeAttr.ReferenceType> types = new HashSet<>(pointee.getReferenceTypes());
        types.addAll(CommonTypes.BindTypes.Pointer.getReferenceTypes());
        return types;
    }

    @Override
    public Set<TypeAttr.GenerationType> toGenerationTypes() {
        Set<TypeAttr.GenerationType> types = new HashSet<>(pointee.toGenerationTypes());
        types.addAll(CommonTypes.BindTypes.Pointer.toGenerationTypes());
        return types;
    }

    @Override
    public String toString() {
        return "PointerType{" +
               "pointee=" + ((TypeAttr.NamedType) pointee).typeName() +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PointerType that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(pointee, that.pointee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), pointee);
    }

    @Override
    public String getMemoryLayout() {
        return CommonTypes.Primitives.ADDRESS.getMemoryLayout();
    }

    @Override
    public long getByteSize() {
        return CommonTypes.Primitives.ADDRESS.getByteSize();
    }
}
