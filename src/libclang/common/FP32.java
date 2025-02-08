package libclang.common;


import java.lang.foreign.SegmentAllocator;
import java.lang.foreign.ValueLayout;
import libclang.common.Array;
import libclang.common.FP32Op;
import libclang.common.Info;

public class FP32 implements FP32Op<FP32>, Info<FP32> {
    public static final Info.Operations<FP32> OPERATIONS = FP32Op.makeOperations(FP32::new);;
    public static final long BYTE_SIZE = OPERATIONS.byteSize();
    private final float val;

    public FP32(float val) {
        this.val = val;
    }

    public static Array<FP32> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public FP32OpI<FP32> operator() {
        return new FP32OpI<>() {
            @Override
            public Info.Operations<FP32> getOperations() {
                return OPERATIONS;
            }

            @Override
            public Float value() {
                return val;
            }
        };
    }

    @Override
    public String toString() {
        return String.valueOf(val);
    }
}
