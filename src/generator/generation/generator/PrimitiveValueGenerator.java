package generator.generation.generator;

import generator.Utils;
import generator.PackagePath;
import generator.types.CommonTypes;
import generator.types.ValueBasedType;

import java.nio.file.Path;

public class PrimitiveValueGenerator {
    private final Path path;
    private final String basePackageName;

    protected PrimitiveValueGenerator(PackagePath packagePath) {
        basePackageName = packagePath.getPackage();
        path = packagePath.getPath();
    }

    public void generate(ValueBasedType type) {
        String out;
        String list = type.getBindTypes().getRawName() + "List";
        String basic = type.getBindTypes().getRawName() + "Basic";
        out = getPrimitiveHead(list, basic);
        out += getPrimitiveBody(type.typeName(), basic, list,
                type.getOperation().getFuncOperation().getPrimitiveType().getPrimitiveTypeName());
        Utils.write(path.resolve(type.typeName() + ".java"), out);
    }

    public void generate(CommonTypes.BindTypes type) {
        String out;
        String list = type.getRawName() + "List";
        String basic = type.getRawName() + "Basic";
        out = getPrimitiveHead(list, basic);
        out += getPrimitiveBody(type.getRawName(), basic, list,
                type.getOperation().getFuncOperation().getPrimitiveType().getBoxedTypeName());
        Utils.write(path.resolve(type.getRawName() + ".java"), out);
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
