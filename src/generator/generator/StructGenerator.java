package generator.generator;

import generator.Dependency;
import generator.Utils;
import generator.generation.Structure;
import generator.types.StructType;

public class StructGenerator implements Generator {
    private final Structure structure;
    private final Dependency dependency;

    public StructGenerator(Structure struct, Dependency dependency) {
        this.structure = struct;
        this.dependency = dependency;
    }

    @Override
    public void generate() {
        StringBuilder stringBuilder = new StringBuilder();
        StructType structType = structure.getStructType().type();
        for (StructType.Member member : structType.getMembers()) {
            GetterAndSetter getterAndSetter = getterAndSetter(member);
            stringBuilder.append(getterAndSetter.getter).append(getterAndSetter.setter);
        }

        String out = dependency.getTypeImports(structure.getRefTypes());
        out += getMain(structType.typeName(), structType.getByteSize(), stringBuilder.toString());

        Utils.write(structure.getStructType().packagePath().getPath(), out);
    }

    record GetterAndSetter(String getter, String setter) {
    }

    private static GetterAndSetter getterAndSetter(StructType.Member member) {
        return new GetterAndSetter(member.type().getOperation().getMemoryOperation().copyFromMS("ptr", member.offset() / 8),
                member.type().getOperation().getMemoryOperation().copyToMS("ptr", member.offset() / 8, member.name()));
    }

    private static String getHeader(String basePackageName) {
        return """
                package %1$s.structs;
                
                
                import %1$s.structs.*;
                import %1$s.functions.*;
                import %1$s.shared.values.*;
                import %1$s.shared.*;
                import %1$s.shared.natives.*;
                import %1$s.shared.Value;
                import %1$s.shared.Pointer;
                import %1$s.shared.FunctionUtils;
                
                import java.lang.foreign.*;
                import java.util.function.Consumer;""".formatted(basePackageName);
    }

    private static String getMain(String className, long byteSize, String ext) {
        return """
                public final class %1$s implements Pointer<%1$s> {
                    public static final MemoryLayout MEMORY_LAYOUT =  MemoryLayout.structLayout(MemoryLayout.sequenceLayout(%2$s, ValueLayout.JAVA_BYTE));;
                    public static final long BYTE_SIZE = MEMORY_LAYOUT.byteSize();
                
                    public static NList<%1$s> list(Pointer<%1$s> ptr) {
                        return new NList<>(ptr, %1$s::new, BYTE_SIZE);
                    }
                
                    public static NList<%1$s> list(Pointer<%1$s> ptr, long length) {
                        return new NList<>(ptr, length, %1$s::new, BYTE_SIZE);
                    }
                
                    public static NList<%1$s> list(SegmentAllocator allocator, long length) {
                        return new NList<>(allocator, length, %1$s::new, BYTE_SIZE);
                    }
                
                    private final MemorySegment ptr;
                
                    public %1$s(Pointer<%1$s> ptr) {
                        this.ptr = ptr.pointer();
                    }
                
                    public %1$s(SegmentAllocator allocator) {
                        ptr = allocator.allocate(BYTE_SIZE);
                    }
                
                    public %1$s reinterpretSize() {
                        return new %1$s(FunctionUtils.makePointer(ptr.reinterpret(BYTE_SIZE)));
                    }
                
                    @Override
                    public MemorySegment pointer() {
                        return ptr;
                    }
                
                %3$s
                }""".formatted(className, byteSize, ext);
    }
}
