package generator.types;

import generator.types.operations.CommonValueBased;
import generator.types.operations.OperationAttr;

import java.util.HashSet;
import java.util.Set;

public final class PointerType extends TypeAttr.AbstractType {
    private final TypeAttr.NType pointee;

    public PointerType(TypeAttr.NType pointee) {
        super(CommonTypes.BindTypes.Pointer.getPrimitiveType().getByteSize(),
                CommonTypes.BindTypes.Pointer.getPrimitiveType().getMemoryLayout(),
                CommonTypes.BindTypes.Pointer.getTypeName().formatted(pointee.getTypeName()));
        this.pointee = pointee;
    }


    @Override
    public OperationAttr.Operation getOperation() {
        return new CommonValueBased(typeName, CommonTypes.Primitives.ADDRESS);
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
}
