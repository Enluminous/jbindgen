package generator.generator;

import generator.Utils;
import generator.config.PackagePath;
import generator.types.Struct;

public class StructGeneration extends AbstractGenerator {
    protected StructGeneration(PackagePath packagePath) {
        super(packagePath);
    }

    public void generate(Struct struct) {
        StringBuilder stringBuilder = new StringBuilder();

        for (Struct.Member member : struct.getMembers()) {
            GetterAndSetter getterAndSetter = getterAndSetter(member);
            stringBuilder.append(getterAndSetter.getter).append(getterAndSetter.setter);
        }

        String out = getHeader(basePackageName);
        out += getMain(struct.getTypeName(), struct.getByteSize(), stringBuilder.toString());

        Utils.write(path.resolve(struct.getTypeName() + ".java"), out);
    }

    record GetterAndSetter(String getter, String setter) {
    }

    private GetterAndSetter getterAndSetter(Struct.Member member) {
        return new GetterAndSetter(member.type().getOperation().getMemoryOperation().copyFromMS("ptr", member.offset() / 8),
                member.type().getOperation().getMemoryOperation().copyToMS("ptr", member.offset() / 8, member.name()));
    }

    private String getHeader(String basePackageName) {
        return """
                package %s.structs;
                
                
                import %s.structs.*;
                import %s.functions.*;
                import %s.shared.values.*;
                import %s.shared.*;
                import %s.shared.natives.*;
                import %s.shared.Value;
                import %s.shared.Pointer;
                import %s.shared.FunctionUtils;
                
                import java.lang.foreign.*;
                import java.util.function.Consumer;""".formatted(basePackageName, basePackageName, basePackageName,
                basePackageName, basePackageName, basePackageName, basePackageName, basePackageName, basePackageName);
    }

    private String getMain(String className, long byteSize, String ext) {
        return """
                public final class %s implements Pointer<%s> {
                    public static final MemoryLayout MEMORY_LAYOUT =  MemoryLayout.structLayout(MemoryLayout.sequenceLayout(%s, ValueLayout.JAVA_BYTE));;
                    public static final long BYTE_SIZE = MEMORY_LAYOUT.byteSize();
                
                    public static NList<%s> list(Pointer<%s> ptr) {
                        return new NList<>(ptr, %s::new, BYTE_SIZE);
                    }
                
                    public static NList<%s> list(Pointer<%s> ptr, long length) {
                        return new NList<>(ptr, length, %s::new, BYTE_SIZE);
                    }
                
                    public static NList<%s> list(SegmentAllocator allocator, long length) {
                        return new NList<>(allocator, length, %s::new, BYTE_SIZE);
                    }
                
                    private final MemorySegment ptr;
                
                    public %s(Pointer<%s> ptr) {
                        this.ptr = ptr.pointer();
                    }
                
                    public %s(SegmentAllocator allocator) {
                        ptr = allocator.allocate(BYTE_SIZE);
                    }
                
                    public %s reinterpretSize() {
                        return new %s(FunctionUtils.makePointer(ptr.reinterpret(BYTE_SIZE)));
                    }
                
                    @Override
                    public MemorySegment pointer() {
                        return ptr;
                    }
                
                %s
                }""".formatted(className, className,//
                byteSize, className, className, className, // part1
                className, className, className,// part2
                className, className, // part3
                className, className, className, className, className, ext);
    }
}
