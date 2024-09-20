package generator.types;

import generator.types.operations.CommonValueBased;
import generator.types.operations.OperationAttr;

import java.util.Set;

public final class ValueBasedType extends TypeAttr.AbstractType implements TypeAttr.ValueBased {
    private final CommonTypes.BindTypes bindTypes;

    public ValueBasedType(String typeName, CommonTypes.BindTypes bindTypes) {
        super(bindTypes.getPrimitiveType().getByteSize(), bindTypes.getPrimitiveType().getMemoryLayout(), typeName);
        this.bindTypes = bindTypes;
    }

    @Override
    public OperationAttr.Operation getOperation() {
        return new CommonValueBased(typeName, bindTypes.getPrimitiveType());
    }

    public CommonTypes.BindTypes getBindTypes() {
        return bindTypes;
    }

    @Override
    public Set<TypeAttr.Type> getReferencedTypes() {
        return Set.of(bindTypes);
    }
}
