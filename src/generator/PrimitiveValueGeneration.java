package generator;

import analyser.types.Primitive;
import analyser.types.Type;

import java.nio.file.Path;

public class PrimitiveValueGeneration {
    private final String basePackageName;
    private final Path path;

    public PrimitiveValueGeneration(String basePackageName, Path path) {
        this.basePackageName = basePackageName;
        this.path = path;
    }


    public void gen(Type flatType) {
        Primitive primitive = Utils.findRootPrimitive(flatType);
        Utils.Mapping mapping = Utils.getTypeMappings().get(primitive.getTypeName());
        String out = "";
        if (mapping != null) {
            out = getPrimitiveHead(mapping);
            out += getPrimitiveBody(flatType.getTypeName(), mapping.sharedValueBasicClass(),
                    mapping.sharedValueListClass(), mapping.objType());
        } else if (primitive.getTypeName().equals("void")) {
            out = """
                    package %s.values;
                    
                    public class %s {
                        private %s() {
                            throw new UnsupportedOperationException();
                        }
                    }
                    """.formatted(basePackageName, flatType.getTypeName(), flatType.getTypeName());
        } else {
            throw new RuntimeException("Unhandled type " + flatType + " primitive " + primitive);
        }
        Utils.write(path.resolve(flatType.getTypeName() + ".java"), out);
    }

    private String getPrimitiveHead(Utils.Mapping mapping) {
        return """
                package %s.values;
                
                import %s.shared.Pointer;
                import %s.shared.Value;
                import %s.shared.%s;
                import %s.shared.values.%s;""".formatted(basePackageName, basePackageName, basePackageName,
                basePackageName, mapping.sharedValueListClass(), basePackageName, mapping.sharedValueBasicClass());
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
