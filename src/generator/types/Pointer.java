package generator.types;

import generator.types.operations.CommonValueBased;
import generator.types.operations.OperationAttr;

import static generator.TypeNames.POINTER;

public final class Pointer extends TypeAttr.AbstractType {
    private final TypeAttr.Type pointee;

    public Pointer(TypeAttr.Type pointee) {
        super(Primitives.Address.getByteSize(), Primitives.Address.getMemoryLayout(), POINTER.formatted(pointee.getTypeName()));
        this.pointee = pointee;
    }


    @Override
    public OperationAttr.Operation getOperation() {
        return new CommonValueBased(typeName, memoryLayout);
    }
}
