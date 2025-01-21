package generator.types;

import java.util.Objects;
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
    public String typeName(NameType nameType) {
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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AbstractGenerationType that)) return false;
        return byteSize == that.byteSize && Objects.equals(memoryLayout, that.memoryLayout) && Objects.equals(typeName, that.typeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(byteSize, memoryLayout, typeName);
    }
}
