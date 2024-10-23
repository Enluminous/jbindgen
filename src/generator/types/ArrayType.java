package generator.types;

import generator.Utils;
import generator.types.operations.MemoryBased;
import generator.types.operations.OperationAttr;

import java.util.HashSet;
import java.util.Set;

public final class ArrayType extends TypeAttr.AbstractType {
    private final long length;
    private final CommonTypes.BindTypes bindType;
    private final TypeAttr.NormalType normalType;

    public ArrayType(String typeName, long length, TypeAttr.NormalType normalType, long byteSize) {
        super(byteSize, Utils.makeMemoryLayout(byteSize), typeName);
        this.length = length;
        this.normalType = normalType;
        this.bindType = null;
    }

    public ArrayType(String typeName, long length, CommonTypes.BindTypes bindType) {
        super(length * bindType.getPrimitiveType().getByteSize(),
                "MemoryLayout.sequenceLayout(" + bindType.getPrimitiveType().getMemoryLayout() + ")", typeName);
        this.length = length;
        this.bindType = bindType;
        this.normalType = null;
    }

    @Override
    public OperationAttr.Operation getOperation() {
        return new MemoryBased(typeName, byteSize);
    }

    @Override
    public Set<TypeAttr.Type> getReferencedTypes() {
        TypeAttr.Type t = bindType == null ? normalType : bindType;
        Set<TypeAttr.Type> types = new HashSet<>();
        types.add(t);
        types.addAll(t.getReferencedTypes());
        return Set.copyOf(types);
    }

    public long getLength() {
        return length;
    }

    @Override
    public String toString() {
        return "ArrayType{" +
                "length=" + length +
                ", bindType=" + bindType +
                ", normalType=" + normalType +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}
