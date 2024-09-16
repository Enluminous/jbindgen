package generator.generator;

import generator.Utils;
import generator.config.PackagePath;
import generator.types.Primitives;
import generator.types.ValueBasedType;

public class PrimitiveValueGeneration extends AbstractGenerator {
    protected PrimitiveValueGeneration(PackagePath packagePath) {
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
                package %s.values;
                
                import %s.shared.Pointer;
                import %s.shared.Value;
                import %s.shared.%s;
                import %s.shared.values.%s;""".formatted(basePackageName, basePackageName, basePackageName,
                basePackageName, list,
                basePackageName, basic);
    }

    private static String getPrimitiveBody(String className, String basicClassName, String specializedList,
                                           String baseObjectType) {
        return """
                
                
                import java.lang.foreign.MemorySegment;
                import java.lang.foreign.SegmentAllocator;
                import java.util.Collection;
                import java.util.function.Consumer;
                
                public class %s extends %s<%s> {
                
                    public static %s<%s> list(Pointer<%s> ptr) {
                        return new %s<>(ptr, %s::new);
                    }
                
                    public static %s<%s> list(Pointer<%s> ptr, long length) {
                        return new %s<>(ptr, length, %s::new);
                    }
                
                    public static %s<%s> list(SegmentAllocator allocator, long length) {
                        return new %s<>(allocator, length, %s::new);
                    }
                
                    public static %s<%s> list(SegmentAllocator allocator, %s[] c) {
                        return new %s<>(allocator, c, %s::new);
                    }
                
                    public static %s<%s> list(SegmentAllocator allocator, Collection<%s> c) {
                        return new %s<>(allocator, c, %s::new);
                    }
                
                    public %s(Pointer<%s> ptr) {
                        super(ptr);
                    }
                
                    public %s(%s value) {
                        super(value);
                    }
                
                    public %s(Value<%s> value) {
                        super(value);
                    }
                
                    public %s(%s value) {
                        super(value);
                    }
                
                    @Override
                    public boolean equals(Object obj) {
                        return obj instanceof %s that && that.value().equals(value());
                    }
                }
                """.formatted(className, basicClassName, className, //
                specializedList, className, className, specializedList, className,//
                specializedList, className, className, specializedList, className,//
                specializedList, className, specializedList, className,//
                specializedList, className, className, specializedList, className,//
                specializedList, className, className, specializedList, className,//
                className, className,//
                className, baseObjectType,//
                className, baseObjectType,//
                className, className,//
                className//
        );
    }

}
