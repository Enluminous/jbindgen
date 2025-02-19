package generator.types;

import java.lang.foreign.ValueLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MemoryLayouts {
    private final TypeImports typeImports;
    private final String memoryLayout;
    private final long byteSize;
    private final long byteAlignment;

    private MemoryLayouts(TypeImports typeImports, String memoryLayout, long byteSize, long byteAlignment) {
        this.memoryLayout = memoryLayout;
        this.typeImports = typeImports;
        this.byteSize = byteSize;
        this.byteAlignment = byteAlignment;
    }

    public static MemoryLayouts withName(MemoryLayouts l, String name) {
        return new MemoryLayouts(l.typeImports, l.memoryLayout + ".withName(%s)".formatted(name), l.byteSize, l.byteAlignment);
    }

    public static MemoryLayouts structLayout(List<MemoryLayouts> inner, long byteSize, long byteAlignment) {
        TypeImports imports = new TypeImports();
        ArrayList<String> memoryLayouts = new ArrayList<>();
        for (MemoryLayouts memoryLayout : inner) {
            imports.addImport(memoryLayout.getTypeImports());
            memoryLayouts.add(memoryLayout.memoryLayout);
        }
        imports.addUseImports(CommonTypes.FFMTypes.MEMORY_LAYOUT);
        return new MemoryLayouts(imports, "MemoryLayout.structLayout(%s)".formatted(String.join(", ", memoryLayouts)), byteSize, byteAlignment);
    }

    public static MemoryLayouts structLayout(List<MemoryLayouts> inner) {
        long byteSize = 0;
        long byteAlignment = 0;
        for (MemoryLayouts memoryLayout : inner) {
            byteSize += memoryLayout.byteSize;
            byteAlignment += memoryLayout.byteAlignment;
        }
        return structLayout(inner, byteSize, byteAlignment);
    }

    public static MemoryLayouts sequenceLayout(MemoryLayouts inner, long len) {
        return sequenceLayout(inner, len, inner.byteSize * len, inner.byteAlignment);
    }

    public static MemoryLayouts sequenceLayout(MemoryLayouts inner, long len, long byteSize, long byteAlignment) {
        return new MemoryLayouts(inner.typeImports.duplicate().addUseImports(CommonTypes.FFMTypes.MEMORY_LAYOUT),
                "MemoryLayout.sequenceLayout(%s, %s)".formatted(len, inner.memoryLayout), byteSize, byteAlignment);
    }

    public static MemoryLayouts paddingLayout(long byteSize) {
        return new MemoryLayouts(new TypeImports().addUseImports(CommonTypes.FFMTypes.MEMORY_LAYOUT),
                "MemoryLayout.paddingLayout(%s)".formatted(byteSize), byteSize, 1);
    }

    public static final MemoryLayouts ADDRESS = new MemoryLayouts(new TypeImports().addUseImports(CommonTypes.FFMTypes.VALUE_LAYOUT),
            "ValueLayout.ADDRESS", ValueLayout.ADDRESS.byteSize(), ValueLayout.ADDRESS.byteAlignment());
    public static final MemoryLayouts JAVA_BYTE = new MemoryLayouts(new TypeImports().addUseImports(CommonTypes.FFMTypes.VALUE_LAYOUT),
            "ValueLayout.JAVA_BYTE", ValueLayout.JAVA_BYTE.byteSize(), ValueLayout.JAVA_BYTE.byteAlignment());
    public static final MemoryLayouts JAVA_BOOLEAN = new MemoryLayouts(new TypeImports().addUseImports(CommonTypes.FFMTypes.VALUE_LAYOUT),
            "ValueLayout.JAVA_BOOLEAN", ValueLayout.JAVA_BOOLEAN.byteSize(), ValueLayout.JAVA_BOOLEAN.byteAlignment());
    public static final MemoryLayouts JAVA_CHAR = new MemoryLayouts(new TypeImports().addUseImports(CommonTypes.FFMTypes.VALUE_LAYOUT),
            "ValueLayout.JAVA_CHAR", ValueLayout.JAVA_CHAR.byteSize(), ValueLayout.JAVA_CHAR.byteAlignment());
    public static final MemoryLayouts JAVA_SHORT = new MemoryLayouts(new TypeImports().addUseImports(CommonTypes.FFMTypes.VALUE_LAYOUT),
            "ValueLayout.JAVA_SHORT", ValueLayout.JAVA_SHORT.byteSize(), ValueLayout.JAVA_SHORT.byteAlignment());
    public static final MemoryLayouts JAVA_INT = new MemoryLayouts(new TypeImports().addUseImports(CommonTypes.FFMTypes.VALUE_LAYOUT),
            "ValueLayout.JAVA_INT", ValueLayout.JAVA_INT.byteSize(), ValueLayout.JAVA_INT.byteAlignment());
    public static final MemoryLayouts JAVA_LONG = new MemoryLayouts(new TypeImports().addUseImports(CommonTypes.FFMTypes.VALUE_LAYOUT),
            "ValueLayout.JAVA_LONG", ValueLayout.JAVA_LONG.byteSize(), ValueLayout.JAVA_LONG.byteAlignment());
    public static final MemoryLayouts JAVA_FLOAT = new MemoryLayouts(new TypeImports().addUseImports(CommonTypes.FFMTypes.VALUE_LAYOUT),
            "ValueLayout.JAVA_FLOAT", ValueLayout.JAVA_FLOAT.byteSize(), ValueLayout.JAVA_FLOAT.byteAlignment());
    public static final MemoryLayouts JAVA_DOUBLE = new MemoryLayouts(new TypeImports().addUseImports(CommonTypes.FFMTypes.VALUE_LAYOUT),
            "ValueLayout.JAVA_DOUBLE", ValueLayout.JAVA_DOUBLE.byteSize(), ValueLayout.JAVA_DOUBLE.byteAlignment());

    @Override
    public String toString() {
        return "MemoryLayouts{" +
               "typeImports=" + typeImports +
               ", memoryLayout='" + memoryLayout + '\'' +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MemoryLayouts that)) return false;
        return byteSize == that.byteSize && byteAlignment == that.byteAlignment && Objects.equals(typeImports, that.typeImports) && Objects.equals(memoryLayout, that.memoryLayout);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeImports, memoryLayout, byteSize, byteAlignment);
    }

    public long getByteAlignment() {
        return byteAlignment;
    }

    public long getByteSize() {
        return byteSize;
    }

    public String getMemoryLayout() {
        return memoryLayout;
    }

    public TypeImports getTypeImports() {
        return typeImports;
    }
}
