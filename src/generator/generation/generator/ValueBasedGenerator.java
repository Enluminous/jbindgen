package generator.generation.generator;

import generator.Dependency;
import generator.Utils;
import generator.generation.Value;
import generator.types.ValueBasedType;

public class ValueBasedGenerator implements Generator {
    private final Dependency dependency;
    private final Value value;

    public ValueBasedGenerator(Value v, Dependency dependency) {
        this.dependency = dependency;
        this.value = v;
    }

    @Override
    public void generate() {
        Utils.write(value.getTypePkg().packagePath().getFilePath(), makeValue(value, dependency));
    }

    private static String makeValue(Value value, Dependency dependency) {
        ValueBasedType type = value.getTypePkg().type();
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
                }""".formatted(type.typeName(), type.getBindTypes().getGenericName(type.typeName()), Generator.extractImports(value, dependency));
    }
}
