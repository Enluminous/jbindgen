package generator.types;

import java.util.List;
import java.util.Objects;

public sealed abstract class AbstractGenerationType
        implements TypeAttr.SizedType, TypeAttr.OperationType, TypeAttr.NamedType, TypeAttr.TypeRefer, TypeAttr.GenerationType
        permits EnumType, FunctionPtrType, StructType, ValueBasedType {
    protected final MemoryLayouts memoryLayout;
    protected final String typeName;

    public AbstractGenerationType(MemoryLayouts memoryLayout, String typeName) {
        this.memoryLayout = memoryLayout;
        this.typeName = typeName;
    }

    @Deprecated
    public static MemoryLayouts makeMemoryLayout(long bytes) {
        return MemoryLayouts.structLayout(List.of(MemoryLayouts.sequenceLayout(CommonTypes.Primitives.JAVA_BYTE.getMemoryLayout(), bytes)));
    }

    @Override
    public MemoryLayouts getMemoryLayout() {
        return memoryLayout;
    }

    @Override
    public String typeName(TypeAttr.NameType nameType) {
        return typeName;
    }

    @Override
    public TypeImports getUseImportTypes() {
        return new TypeImports(this);
    }

    @Override
    public String toString() {
        return "AbstractType{" + "typeName='" + typeName + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AbstractGenerationType that)) return false;
        return Objects.equals(memoryLayout, that.memoryLayout) && Objects.equals(typeName, that.typeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memoryLayout, typeName);
    }
}
