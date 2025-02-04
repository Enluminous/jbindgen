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
        String enumName = Generator.getTypeName(e.getTypePkg().type());
        var members = e.getTypePkg().type().getMembers().stream()
                .map(member -> "public static final %s %s = new %s(%s);".formatted(enumName, member.name(), enumName, member.val())).toList();
        return """
                %2$s
                %3$s
                
                import java.lang.foreign.SegmentAllocator;
                
                public final class %1$s implements I32Op<%1$s>, Info<%1$s> {
                    public static final Info.Operations<%1$s> OPERATIONS = I32Op.makeOperations(%1$s::new);
                    public static final long BYTE_SIZE = OPERATIONS.byteSize();
                    private final int val;
                
                    public %1$s(int val) {
                        this.val = val;
                    }
                
                    public static Array<%1$s> list(SegmentAllocator allocator, int len) {
                        return new Array<>(allocator, OPERATIONS, len);
                    }
                
                    @Override
                    public I32OpI<%1$s> operator() {
                        return new I32OpI<>() {
                            @Override
                            public Info.Operations<%1$s> getOperations() {
                                return OPERATIONS;
                            }
                
                            @Override
                            public Integer value() {
                                return val;
                            }
                        };
                    }
                
                    private String str;
                
                    @Override
                    public String toString() {
                        if (str == null) {
                            str = enumToString(this);
                            if (str == null) str = String.valueOf(val);
                        }
                        return str;
                    }
                
                    public static String enumToString(%1$s e) {
                        return Utils.enumToString(%1$s.class, e);
                    }
                
                    @Override
                    public boolean equals(Object obj) {
                        return obj instanceof %1$s that && that.val == val;
                    }
                
                    %4$s
                }""".formatted(enumName, e.getTypePkg().packagePath().makePackage(),
                Generator.extractImports(e, dependency), String.join("\n    ", members));
    }
}
