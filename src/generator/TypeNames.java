package generator;

public class TypeNames {
    public static final String POINTER = "Pointer<%s>";
    // MemorySegment srcSegment, long srcOffset,MemorySegment dstSegment, long dstOffset, long bytes
    public static final String MEM_CPY = "MemorySegment.copy(%s, %s, %s, %s, %s)";
    public static final String MEM_SET = "MemorySegment.set(%s, %s, %s)";
    public static final String MEM_GET = "MemorySegment.set(%s, %s)";
    public static final String ALLOCATOR_VAR_NAME = "segmentAllocator";
}
