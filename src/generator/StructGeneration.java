package generator;

import java.nio.file.Path;

public class StructGeneration {

    private final String basePackageName;
    private final Path path;

    public StructGeneration(String basePackageName, Path path) {
        this.basePackageName = basePackageName;
        this.path = path;
    }

    private String getHeader() {
        return "";
    }

    private String getMain(String className, String byteSize, String ext) {
        return """
                public final class CXCursor implements Pointer<CXCursor> {
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
                
                    %s""".formatted(byteSize, className, className, className, // part1
                className, className, className,// part2
                className, className, // part3
                className, className, className, className, className, ext);
    }
}
