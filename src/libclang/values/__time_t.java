package libclang.values;


import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import libclang.common.Array;
import libclang.common.I64;
import libclang.common.I64Op;
import libclang.common.Info;

public class __time_t implements I64Op<__time_t>, Info<__time_t> {
    public static final Info.Operations<__time_t> OPERATIONS = I64Op.makeOperations(__time_t::new);;
    public static final long BYTE_SIZE = OPERATIONS.byteSize();
    private final long val;

    public __time_t(long val) {
        this.val = val;
    }

    public static Array<__time_t> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public I64OpI<__time_t> operator() {
        return new I64OpI<>() {
            @Override
            public Info.Operations<__time_t> getOperations() {
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
