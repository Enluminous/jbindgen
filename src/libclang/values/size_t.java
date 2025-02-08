package libclang.values;


import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import libclang.common.Array;
import libclang.common.I64;
import libclang.common.I64Op;
import libclang.common.Info;

public class size_t implements I64Op<size_t>, Info<size_t> {
    public static final Info.Operations<size_t> OPERATIONS = I64Op.makeOperations(size_t::new);;
    public static final long BYTE_SIZE = OPERATIONS.byteSize();
    private final long val;

    public size_t(long val) {
        this.val = val;
    }

    public static Array<size_t> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public I64OpI<size_t> operator() {
        return new I64OpI<>() {
            @Override
            public Info.Operations<size_t> getOperations() {
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
