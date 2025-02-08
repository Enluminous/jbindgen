package libclang.values;


import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import libclang.common.Array;
import libclang.common.I64;
import libclang.common.I64Op;
import libclang.common.Info;

public class __syscall_slong_t implements I64Op<__syscall_slong_t>, Info<__syscall_slong_t> {
    public static final Info.Operations<__syscall_slong_t> OPERATIONS = I64Op.makeOperations(__syscall_slong_t::new);;
    public static final long BYTE_SIZE = OPERATIONS.byteSize();
    private final long val;

    public __syscall_slong_t(long val) {
        this.val = val;
    }

    public static Array<__syscall_slong_t> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public I64OpI<__syscall_slong_t> operator() {
        return new I64OpI<>() {
            @Override
            public Info.Operations<__syscall_slong_t> getOperations() {
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
