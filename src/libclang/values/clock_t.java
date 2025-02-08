package libclang.values;


import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import libclang.common.Array;
import libclang.common.I64;
import libclang.common.I64Op;
import libclang.common.Info;

public class clock_t implements I64Op<clock_t>, Info<clock_t> {
    public static final Info.Operations<clock_t> OPERATIONS = I64Op.makeOperations(clock_t::new);;
    public static final long BYTE_SIZE = OPERATIONS.byteSize();
    private final long val;

    public clock_t(long val) {
        this.val = val;
    }

    public static Array<clock_t> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public I64OpI<clock_t> operator() {
        return new I64OpI<>() {
            @Override
            public Info.Operations<clock_t> getOperations() {
                return OPERATIONS;
            }

            @Override
            public Long value() {
                return val;
            }
        };
    }

    @Override
    public String toString() {
        return String.valueOf(val);
    }
}
