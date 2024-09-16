package generator.generator;

import generator.Utils;
import generator.config.PackagePath;
import generator.types.Primitives;
import generator.types.ValueBasedType;

public class PrimitiveValueGenerator extends AbstractGenerator {
    protected PrimitiveValueGenerator(PackagePath packagePath) {
        super(packagePath);
    }

    public void generate(ValueBasedType type) {
        String out;
        String list = type.getPrimitive().getTypeName() + "List";
        String basic = type.getPrimitive().getTypeName() + "Basic";
        out = getPrimitiveHead(list, basic);
        out += getPrimitiveBody(type.getTypeName(), basic, list, type.getPrimitive().getWrapperName());
        Utils.write(path.resolve(type.getTypeName() + ".java"), out);
    }

    public void generate(Primitives type) {
        String out;
        String list = type.getTypeName() + "List";
        String basic = type.getTypeName() + "Basic";
        out = getPrimitiveHead(list, basic);
        out += getPrimitiveBody(type.getTypeName(), basic, list, type.getWrapperName());
        Utils.write(path.resolve(type.getTypeName() + ".java"), out);
    }

    private String getPrimitiveHead(String list, String basic) {
        return """
                package %1$s.values;
                
                import %1$s.shared.Pointer;
                import %1$s.shared.Value;
                import %1$s.shared.%2$s;
                import %1$s.shared.values.%3$s;""".formatted(basePackageName, list, basic);
    }

    private static String getPrimitiveBody(String className, String basicClassName, String specializedList,
                                           String baseObjectType) {
        return """
                
                
                import java.lang.foreign.MemorySegment;
                import java.lang.foreign.SegmentAllocator;
                import java.util.Collection;
                import java.util.function.Consumer;
                
                public class %1$s extends %3$s<%1$s> {
                
                    public static %2$s<%1$s> list(Pointer<%1$s> ptr) {
                        return new %2$s<>(ptr, %1$s::new);
                    }
                
                    public static %2$s<%1$s> list(Pointer<%1$s> ptr, long length) {
                        return new %2$s<>(ptr, length, %1$s::new);
                    }
                
                    public static %2$s<%1$s> list(SegmentAllocator allocator, long length) {
                        return new %2$s<>(allocator, length, %1$s::new);
                    }
                
                    public static %2$s<%1$s> list(SegmentAllocator allocator, %1$s[] c) {
                        return new %2$s<>(allocator, c, %1$s::new);
                    }
                
                    public static %2$s<%1$s> list(SegmentAllocator allocator, Collection<%1$s> c) {
                        return new %2$s<>(allocator, c, %1$s::new);
                    }
                
                    public %1$s(Pointer<%1$s> ptr) {
                        super(ptr);
                    }
                
                    public %1$s(%4$s value) {
                        super(value);
                    }
                
                    public %1$s(Value<%4$s> value) {
                        super(value);
                    }
                
                    public %1$s(%1$s value) {
                        super(value);
                    }
                
                    @Override
                    public boolean equals(Object obj) {
                        return obj instanceof %1$s that && that.value().equals(value());
                    }
                }
                """.formatted(className, specializedList, basicClassName, baseObjectType);
    }

}
