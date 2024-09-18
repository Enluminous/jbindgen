package generator.types;

import generator.types.operations.CommonValueBased;
import generator.types.operations.OperationAttr;

import java.util.Set;

import static generator.TypeNames.POINTER;

public final class PointerType extends TypeAttr.AbstractType {
    private final TypeAttr.Type pointee;

    public PointerType(TypeAttr.Type pointee) {
        super(Primitives.Address.getByteSize(), Primitives.Address.getMemoryLayout(), POINTER.formatted(pointee.getTypeName()));
        this.pointee = pointee;
    }


    @Override
    public OperationAttr.Operation getOperation() {
        return new CommonValueBased(typeName, memoryLayout);
    }

    @Override
    public Primitives getNonWrappedType() {
        return Primitives.Address;
    }

    @Override
    public Set<TypeAttr.Type> getReferencedTypes() {
        // todo we need decl the pointer<%s> type
        return null;
    }
}
