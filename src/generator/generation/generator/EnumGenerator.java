package generator.generation.generator;

import generator.Dependency;
import generator.Utils;
import generator.generation.Enumerate;

public class EnumGenerator implements Generator {
    private final Enumerate enumerate;
    private final Dependency dependency;

    public EnumGenerator(Enumerate enumerate, Dependency dependency) {
        this.enumerate = enumerate;
        this.dependency = dependency;
    }

    @Override
    public void generate() {
        Utils.write(enumerate.getTypePkg().packagePath().getFilePath(), makeEnum(enumerate, dependency));
    }

    private static String makeEnum(Enumerate e, Dependency dependency) {
        String enumName = e.getTypePkg().type().typeName();
        var members = e.getTypePkg().type().getMembers().stream()
                .map(member -> "public static final %s %s = new %s(%s);".formatted(enumName, member.name(), enumName, member.val())).toList();
        return """
                %2$s
                %3$s
                
                public static final class %1$s extends VI32Basic<%1$s> {
                    public %1$s(int e) {
                        super(e);
                    }
                
                    public %1$s(Pointer<%1$s> e) {
                        super(e);
                    }
                
                    public %1$s(%1$s e) {
                        super(e);
                        str = e.str;
                    }
                
                    public static VI32List<%1$s> list(Pointer<%1$s> ptr) {
                        return new VI32List<>(ptr, %1$s::new);
                    }
                
                    public static VI32List<%1$s> list(Pointer<%1$s> ptr, long length) {
                        return new VI32List<>(ptr, length, %1$s::new);
                    }
                
                    public static VI32List<%1$s> list(SegmentAllocator allocator, long length) {
                        return new VI32List<>(allocator, length, %1$s::new);
                    }
                
                    public static VI32List<%1$s> list(SegmentAllocator allocator, %1$s[] c) {
                        return new VI32List<>(allocator, c, %1$s::new);
                    }
                
                    public static VI32List<%1$s> list(SegmentAllocator allocator, Collection<%1$s> c) {
                        return new VI32List<>(allocator, c, %1$s::new);
                    }
                
                    private String str;
                
                    @Override
                    public String toString() {
                        return str == null ? str = enumToString(this).orElse(String.valueOf(value())) : str;
                    }
                
                    public static Optional<String> enumToString(%1$s e) {
                        return LibclangEnums.enumToString(%1$s.class, e);
                    }
                
                    @Override
                    public boolean equals(Object obj) {
                        return obj instanceof %1$s that && that.value().intValue() == value().intValue();
                    }
                
                    %4$s
                }""".formatted(enumName, e.getTypePkg().packagePath().makePackage(),
                Generator.extractImports(e, dependency), String.join("\n", members));
    }
}
