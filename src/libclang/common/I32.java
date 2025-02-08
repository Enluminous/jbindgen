package libclang.common;


import java.lang.foreign.SegmentAllocator;
import java.lang.foreign.ValueLayout;
import libclang.common.Array;
import libclang.common.I32Op;
import libclang.common.Info;

public class I32 implements I32Op<I32>, Info<I32> {
    public static final Info.Operations<I32> OPERATIONS = I32Op.makeOperations(I32::new);;
    public static final long BYTE_SIZE = OPERATIONS.byteSize();
    private final int val;

    public I32(int val) {
        this.val = val;
    }

    public static Array<I32> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public I32OpI<I32> operator() {
        return new I32OpI<>() {
            @Override
            public Info.Operations<I32> getOperations() {
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
