package libclang.common;


import java.lang.foreign.SegmentAllocator;
import java.lang.foreign.ValueLayout;
import libclang.common.Array;
import libclang.common.I64Op;
import libclang.common.Info;

public class I64 implements I64Op<I64>, Info<I64> {
    public static final Info.Operations<I64> OPERATIONS = I64Op.makeOperations(I64::new);;
    public static final long BYTE_SIZE = OPERATIONS.byteSize();
    private final long val;

    public I64(long val) {
        this.val = val;
    }

    public static Array<I64> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public I64OpI<I64> operator() {
        return new I64OpI<>() {
            @Override
            public Info.Operations<I64> getOperations() {
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
