package generator.generation.generator;

import generator.Dependency;
import generator.Utils;
import generator.generation.ValueBased;
import generator.types.ValueBasedType;

public class ValueBasedGenerator implements Generator {
    private final Dependency dependency;
    private final ValueBased valueBased;

    public ValueBasedGenerator(ValueBased v, Dependency dependency) {
        this.dependency = dependency;
        this.valueBased = v;
    }

    @Override
    public void generate() {
        Utils.write(valueBased.getTypePkg().packagePath().getFilePath(), makeValue(valueBased, dependency));
    }

    private static String makeValue(ValueBased valueBased, Dependency dependency) {
        ValueBasedType type = valueBased.getTypePkg().type();
        String typeName = Generator.getTypeName(type);
        return """
                %3$s
                
                public class %1$s extends %2$s {
                
                    public static VPointerList<%1$s> list(Pointer<%1$s> ptr) {
                        return new VPointerList<>(ptr, %1$s::new);
                    }
                
                    public static VPointerList<%1$s> list(Pointer<%1$s> ptr, long length) {
                        return new VPointerList<>(ptr, length, %1$s::new);
                    }
                
                    public static VPointerList<%1$s> list(SegmentAllocator allocator, long length) {
                        return new VPointerList<>(allocator, length, %1$s::new);
                    }
                
                    public static VPointerList<%1$s> list(SegmentAllocator allocator, %1$s[] c) {
                        return new VPointerList<>(allocator, c, %1$s::new);
                    }
                
                    public static VPointerList<%1$s> list(SegmentAllocator allocator, Collection<%1$s> c) {
                        return new VPointerList<>(allocator, c, %1$s::new);
                    }
                
                    public %1$s(Pointer<%1$s> ptr) {
                        super(ptr);
                    }
                
                    public %1$s(MemorySegment value) {
                        super(value);
                    }
                
                    public %1$s(Value<MemorySegment> value) {
                        super(value);
                    }
                
                    public %1$s(%1$s value) {
                        super(value);
                    }
                
                    @Override
                    public boolean equals(Object obj) {
                        return obj instanceof %1$s that && that.value().equals(value());
                    }
                }""".formatted(typeName, type.getBindTypes().typeName(), Generator.extractImports(valueBased, dependency));
    }
}
