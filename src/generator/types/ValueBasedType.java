package generator.types;

import generator.types.operations.CommonValueBased;
import generator.types.operations.OperationAttr;

public final class ValueBasedType extends TypeAttr.AbstractType implements TypeAttr.ValueBased {
    public ValueBasedType(String typeName, Primitives primitive) {
        super(primitive.getByteSize(), primitive.getMemoryLayout(), typeName);
    }

    @Override
    public OperationAttr.Operation getOperation() {
        return new CommonValueBased(typeName, memoryLayout);
    }
}
