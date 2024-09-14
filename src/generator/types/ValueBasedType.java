package generator.types;

import generator.operatons.CommonValueBased;
import generator.operatons.OperationAttr;

public final class ValueBasedType extends TypeAttr.AbstractType implements TypeAttr.ValueBased {
    public ValueBasedType(String typeName, Primitives primitive) {
        super(primitive.getByteSize(), primitive.getMemoryLayout(), typeName);
    }

    @Override
    public OperationAttr.Operation getOperation() {
        return new CommonValueBased(typeName, memoryLayout);
    }
}
