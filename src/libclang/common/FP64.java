package libclang.common;


import java.lang.foreign.SegmentAllocator;
import java.lang.foreign.ValueLayout;
import libclang.common.Array;
import libclang.common.FP64Op;
import libclang.common.Info;

public class FP64 implements FP64Op<FP64>, Info<FP64> {
    public static final Info.Operations<FP64> OPERATIONS = FP64Op.makeOperations(FP64::new);;
    public static final long BYTE_SIZE = OPERATIONS.byteSize();
    private final double val;

    public FP64(double val) {
        this.val = val;
    }

    public static Array<FP64> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public FP64OpI<FP64> operator() {
        return new FP64OpI<>() {
            @Override
            public Info.Operations<FP64> getOperations() {
                return OPERATIONS;
            }

            @Override
            public Double value() {
                return val;
            }
        };
    }

    @Override
    public String toString() {
        return String.valueOf(val);
    }
}
