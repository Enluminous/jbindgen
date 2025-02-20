package generator.types;

import generator.types.operations.CommonOperation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MemoryLayouts {
    private final TypeImports typeImports;
    private final String memoryLayout;

    private MemoryLayouts(TypeImports typeImports, String memoryLayout) {
        this.memoryLayout = memoryLayout;
        this.typeImports = typeImports;
    }

    public static MemoryLayouts withName(MemoryLayouts l, String name) {
        return new MemoryLayouts(l.typeImports, l.memoryLayout + ".withName(%s)".formatted(name));
    }

    public static MemoryLayouts structLayout(List<MemoryLayouts> inner) {
        TypeImports imports = new TypeImports();
        ArrayList<String> memoryLayouts = new ArrayList<>();
        for (MemoryLayouts memoryLayout : inner) {
            imports.addImport(memoryLayout.getTypeImports());
            memoryLayouts.add(memoryLayout.memoryLayout);
        }
        imports.addUseImports(CommonTypes.FFMTypes.MEMORY_LAYOUT);
        return new MemoryLayouts(imports, "MemoryLayout.structLayout(%s)".formatted(String.join(", ", memoryLayouts)));
    }

    public static MemoryLayouts sequenceLayout(MemoryLayouts inner, long len) {
        return new MemoryLayouts(inner.typeImports.duplicate().addUseImports(CommonTypes.FFMTypes.MEMORY_LAYOUT),
                "MemoryLayout.sequenceLayout(%s, %s)".formatted(len, inner.memoryLayout));
    }

    public static MemoryLayouts paddingLayout(long byteSize) {
        return new MemoryLayouts(new TypeImports().addUseImports(CommonTypes.FFMTypes.MEMORY_LAYOUT),
                "MemoryLayout.paddingLayout(%s)".formatted(byteSize));
    }

    public static MemoryLayouts operationLayout(CommonOperation.Operation operation) {
        return new MemoryLayouts(operation.imports(), operation.str() + ".memoryLayout()");
    }

    public static final MemoryLayouts ADDRESS = new MemoryLayouts(new TypeImports().addUseImports(CommonTypes.FFMTypes.VALUE_LAYOUT), "ValueLayout.ADDRESS");
    public static final MemoryLayouts JAVA_BYTE = new MemoryLayouts(new TypeImports().addUseImports(CommonTypes.FFMTypes.VALUE_LAYOUT), "ValueLayout.JAVA_BYTE");
    public static final MemoryLayouts JAVA_BOOLEAN = new MemoryLayouts(new TypeImports().addUseImports(CommonTypes.FFMTypes.VALUE_LAYOUT), "ValueLayout.JAVA_BOOLEAN");
    public static final MemoryLayouts JAVA_CHAR = new MemoryLayouts(new TypeImports().addUseImports(CommonTypes.FFMTypes.VALUE_LAYOUT), "ValueLayout.JAVA_CHAR");
    public static final MemoryLayouts JAVA_SHORT = new MemoryLayouts(new TypeImports().addUseImports(CommonTypes.FFMTypes.VALUE_LAYOUT), "ValueLayout.JAVA_SHORT");
    public static final MemoryLayouts JAVA_INT = new MemoryLayouts(new TypeImports().addUseImports(CommonTypes.FFMTypes.VALUE_LAYOUT), "ValueLayout.JAVA_INT");
    public static final MemoryLayouts JAVA_LONG = new MemoryLayouts(new TypeImports().addUseImports(CommonTypes.FFMTypes.VALUE_LAYOUT), "ValueLayout.JAVA_LONG");
    public static final MemoryLayouts JAVA_FLOAT = new MemoryLayouts(new TypeImports().addUseImports(CommonTypes.FFMTypes.VALUE_LAYOUT), "ValueLayout.JAVA_FLOAT");
    public static final MemoryLayouts JAVA_DOUBLE = new MemoryLayouts(new TypeImports().addUseImports(CommonTypes.FFMTypes.VALUE_LAYOUT), "ValueLayout.JAVA_DOUBLE");

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
        return Objects.equals(typeImports, that.typeImports) && Objects.equals(memoryLayout, that.memoryLayout);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeImports, memoryLayout);
    }

    public String getMemoryLayout() {
        return memoryLayout;
    }

    public TypeImports getTypeImports() {
        return typeImports;
    }
}
