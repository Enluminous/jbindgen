package libclang.common;


import java.lang.foreign.SegmentAllocator;
import java.lang.foreign.ValueLayout;
import libclang.common.Array;
import libclang.common.I16Op;
import libclang.common.Info;

public class I16 implements I16Op<I16>, Info<I16> {
    public static final Info.Operations<I16> OPERATIONS = I16Op.makeOperations(I16::new);;
    public static final long BYTE_SIZE = OPERATIONS.byteSize();
    private final short val;

    public I16(short val) {
        this.val = val;
    }

    public static Array<I16> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public I16OpI<I16> operator() {
        return new I16OpI<>() {
            @Override
            public Info.Operations<I16> getOperations() {
                return OPERATIONS;
            }

            @Override
            public Short value() {
                return val;
            }
        };
    }

    @Override
    public String toString() {
        return String.valueOf(val);
    }
}
