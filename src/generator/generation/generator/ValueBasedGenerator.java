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
        String content = valueBased.getTypePkg().packagePath().makePackage();
        content += makeValue(valueBased, dependency);
        Utils.write(valueBased.getTypePkg().packagePath().getFilePath(), content);
    }

    private static String makeValue(ValueBased valueBased, Dependency dependency) {
        ValueBasedType type = valueBased.getTypePkg().type();
        String typeName = Generator.getTypeName(type);
        return """
                %3$s
                
                import java.lang.foreign.SegmentAllocator;
                import java.util.Collection;
                
                public class %1$s extends %2$s<%1$s> {
                
                    public static %4$s<%1$s> list(Pointer<%1$s> ptr) {
                        return new %4$s<>(ptr, %1$s::new);
                    }
                
                    public static %4$s<%1$s> list(Pointer<%1$s> ptr, long length) {
                        return new %4$s<>(ptr, length, %1$s::new);
                    }
                
                    public static %4$s<%1$s> list(SegmentAllocator allocator, long length) {
                        return new %4$s<>(allocator, length, %1$s::new);
                    }
                
                    public static %4$s<%1$s> list(SegmentAllocator allocator, %1$s[] c) {
                        return new %4$s<>(allocator, c, %1$s::new);
                    }
                
                    public static %4$s<%1$s> list(SegmentAllocator allocator, Collection<%1$s> c) {
                        return new %4$s<>(allocator, c, %1$s::new);
                    }
                
                    public %1$s(Pointer<%1$s> ptr) {
                        super(ptr);
                    }
                
                    public %1$s(%1$s value) {
                        super(value.value());
                    }
                }""".formatted(typeName, type.getBindTypes().typeName(), Generator.extractImports(valueBased, dependency),
                type.getBindTypes().getListType().getRawName());
    }
}
