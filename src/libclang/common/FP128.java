package libclang.common;


import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.nio.ByteOrder;

public class FP128 implements FP128Op<FP128>, Info<FP128> {
    public static final long BYTE_SIZE = Double.BYTES * 2;
    public static final Operations<FP128> OPERATIONS = new Operations<>((param, offset) -> new FP128(param.asSlice(offset)),
            (source, dest, offset) -> MemoryUtils.memcpy(source.val, 0, dest, offset, BYTE_SIZE), BYTE_SIZE);
    private final MemorySegment val;

    private FP128(MemorySegment val) {
        this.val = val;
    }

    public FP128(long low, long high) {
        this.val = MemorySegment.ofArray(new long[2]);
        val.asByteBuffer().order(ByteOrder.nativeOrder()).putLong(low).putLong(high);
    }

    public static Array<FP128> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public FP128OpI<FP128> operator() {
        return new FP128OpI<>() {
            @Override
            public Operations<FP128> getOperations() {
                return OPERATIONS;
            }

            @Override
            public MemorySegment value() {
                return val;
            }
        };
    }

    @Override
    public String toString() {
        return String.valueOf(val);
    }
}
