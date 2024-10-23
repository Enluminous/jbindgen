package generator.types;

import generator.types.operations.ValueBased;
import generator.types.operations.OperationAttr;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public final class PointerType extends TypeAttr.AbstractType {
    private final TypeAttr.NType pointee;

    public PointerType(TypeAttr.NType pointee) {
        super(CommonTypes.BindTypes.Pointer.getPrimitiveType().getByteSize(),
                CommonTypes.BindTypes.Pointer.getPrimitiveType().getMemoryLayout(),
                CommonTypes.BindTypes.Pointer.getGenericName(pointee.typeName()));
        this.pointee = pointee;
    }

    @Override
    public OperationAttr.Operation getOperation() {
        return new ValueBased(typeName, CommonTypes.Primitives.ADDRESS);
    }

    @Override
    public Set<TypeAttr.Type> getReferencedTypes() {
        Set<TypeAttr.Type> types = new HashSet<>();
        types.add(pointee);
        types.addAll(pointee.getReferencedTypes());
        types.add(CommonTypes.BindTypes.Pointer);
        types.addAll(CommonTypes.BindTypes.Pointer.getReferencedTypes());
        return Set.copyOf(types);
    }

    @Override
    public String toString() {
        return "PointerType{" +
                "pointee=" + pointee.typeName() +
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
}
