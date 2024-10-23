package generator.types;

import generator.types.operations.ValueBased;
import generator.types.operations.OperationAttr;

import java.util.Objects;
import java.util.Set;

public final class ValueBasedType extends TypeAttr.AbstractType implements TypeAttr.ValueBased {
    private final CommonTypes.BindTypes bindTypes;

    public ValueBasedType(String typeName, CommonTypes.BindTypes bindTypes) {
        super(bindTypes.getPrimitiveType().getByteSize(), bindTypes.getPrimitiveType().getMemoryLayout(), typeName);
        this.bindTypes = bindTypes;
    }

    @Override
    public OperationAttr.Operation getOperation() {
        return new ValueBased(typeName, bindTypes.getPrimitiveType());
    }

    public CommonTypes.BindTypes getBindTypes() {
        return bindTypes;
    }

    @Override
    public Set<TypeAttr.Type> getReferencedTypes() {
        return Set.of(bindTypes);
    }

    @Override
    public String toString() {
        return "ValueBasedType{" +
               "bindTypes=" + bindTypes +
               ", typeName='" + typeName + '\'' +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ValueBasedType that)) return false;
        if (!super.equals(o)) return false;
        return bindTypes == that.bindTypes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), bindTypes);
    }
}
