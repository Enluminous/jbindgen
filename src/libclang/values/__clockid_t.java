package libclang.values;


import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import libclang.common.Array;
import libclang.common.I32;
import libclang.common.I32Op;
import libclang.common.Info;

public class __clockid_t implements I32Op<__clockid_t>, Info<__clockid_t> {
    public static final Info.Operations<__clockid_t> OPERATIONS = I32Op.makeOperations(__clockid_t::new);;
    public static final long BYTE_SIZE = OPERATIONS.byteSize();
    private final int val;

    public __clockid_t(int val) {
        this.val = val;
    }

    public static Array<__clockid_t> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public I32OpI<__clockid_t> operator() {
        return new I32OpI<>() {
            @Override
            public Info.Operations<__clockid_t> getOperations() {
                return OPERATIONS;
            }

            @Override
            public Integer value() {
                return val;
            }
        };
    }

    @Override
    public String toString() {
        return String.valueOf(val);
    }
}
