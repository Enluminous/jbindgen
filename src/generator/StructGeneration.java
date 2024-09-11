package generator;

import analyser.Para;
import analyser.types.Struct;

import java.nio.file.Path;

public class StructGeneration {

    private final String basePackageName;
    private final Path path;

    public StructGeneration(String basePackageName, Path path) {
        this.basePackageName = basePackageName;
        this.path = path.resolve("structs");
    }

    public void gen(Struct struct) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Para para : struct.getParas()) {
            GetterAndSetter getterAndSetter = getterAndSetter(para, struct.getTypeName());
            if (getterAndSetter == null)
                continue;
            stringBuilder.append(getterAndSetter.getter).append(getterAndSetter.setter);
        }
        String out = getMain(struct.getTypeName(), "0", stringBuilder.toString());
        Utils.write(path.resolve("structs/" + struct.getTypeName() + ".java"), out);
    }

    record GetterAndSetter(String getter, String setter) {
    }

    private GetterAndSetter getterAndSetter(Para para, String className) {
        if (Utils.isPrimitiveType(para.paraType())) {
            Utils.Mapping mapping = Utils.getTypeMappings().get(Utils.findRootPrimitive(para.paraType()).getTypeName());
            if (mapping == null) {
                return null;
            }
            return new GetterAndSetter("""
                        public %s %s() {
                        return ptr.get(ValueLayout.%s, %s);
                    }
                    """.formatted(mapping.objType(), para.paraName(), mapping.valueLayout(), "0"), """
                        public %s %s(%s %s) {
                            ptr.set(ValueLayout.%s, %s, %s);
                            return this;
                        }
                    """.formatted(className, para.paraName(), mapping.objType(), para.paraName(),//
                    mapping.valueLayout(), "0", para.paraName()));
        }
        return null;
    }

    private String getHeader() {
        return "";
    }

    private String getMain(String className, String byteSize, String ext) {
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
                
                    %s""".formatted(className, className,//
                byteSize, className, className, className, // part1
                className, className, className,// part2
                className, className, // part3
                className, className, className, className, className, ext);
    }
}
