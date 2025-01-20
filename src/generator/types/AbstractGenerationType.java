package generator.types;

import java.util.Set;

public sealed abstract class AbstractGenerationType
        implements TypeAttr.SizedType, TypeAttr.OperationType, TypeAttr.NamedType, TypeAttr.ReferenceType, TypeAttr.GenerationType
        permits EnumType, FunctionPtrType, StructType, ValueBasedType {
    protected final long byteSize;
    protected final String memoryLayout;
    protected final String typeName;

    public AbstractGenerationType(long byteSize, String memoryLayout, String typeName) {
        this.byteSize = byteSize;
        this.memoryLayout = memoryLayout;
        this.typeName = typeName;
    }

    @Override
    public long getByteSize() {
        return byteSize;
    }

    @Override
    public String getMemoryLayout() {
        return memoryLayout;
    }

    @Override
    public String typeName() {
        return typeName;
    }

    @Override
    public Set<TypeAttr.ReferenceType> getUseImportTypes() {
        return Set.of(this);
    }

    @Override
    public String toString() {
        return "AbstractType{" + "typeName='" + typeName + '\'' + '}';
    }
}
