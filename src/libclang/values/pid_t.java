package libclang.values;


import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import libclang.common.Array;
import libclang.common.I32;
import libclang.common.I32Op;
import libclang.common.Info;

public class pid_t implements I32Op<pid_t>, Info<pid_t> {
    public static final Info.Operations<pid_t> OPERATIONS = I32Op.makeOperations(pid_t::new);;
    public static final long BYTE_SIZE = OPERATIONS.byteSize();
    private final int val;

    public pid_t(int val) {
        this.val = val;
    }

    public static Array<pid_t> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public I32OpI<pid_t> operator() {
        return new I32OpI<>() {
            @Override
            public Info.Operations<pid_t> getOperations() {
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
