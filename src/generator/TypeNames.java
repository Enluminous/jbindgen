package generator;

public class TypeNames {
    // MemorySegment srcSegment, long srcOffset,MemorySegment dstSegment, long dstOffset, long bytes
    public static final String MEM_CPY = "MemorySegment.copy(%s, %s, %s, %s, %s)";
    public static final String MEM_SET = "%s.set(%s, %s, %s)";
    public static final String MEM_GET = "%s.get(%s, %s)";
    public static final String ALLOCATOR_VAR_NAME = "segmentAllocator";
}
