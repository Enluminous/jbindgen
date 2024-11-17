package generator.types;

import java.util.Objects;
import java.util.Set;

public sealed abstract class AbstractGenerationType
        implements IGenerationType
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
    public Set<TypeAttr.ReferenceType> getReferenceTypes() {
        return Set.of(this);
    }

    @Override
    public Set<TypeAttr.GenerationType> toGenerationTypes() {
        return Set.of(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractGenerationType that)) return false;
        return byteSize == that.byteSize && Objects.equals(memoryLayout, that.memoryLayout) && Objects.equals(typeName, that.typeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(byteSize, memoryLayout, typeName);
    }

    @Override
    public String toString() {
        return "AbstractType{" + "typeName='" + typeName + '\'' + '}';
    }
}
