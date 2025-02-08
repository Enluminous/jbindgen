package libclang.common;


import java.lang.foreign.SegmentAllocator;
import java.lang.foreign.ValueLayout;
import libclang.common.Array;
import libclang.common.I8Op;
import libclang.common.Info;

public class I8 implements I8Op<I8>, Info<I8> {
    public static final Info.Operations<I8> OPERATIONS = I8Op.makeOperations(I8::new);;
    public static final long BYTE_SIZE = OPERATIONS.byteSize();
    private final byte val;

    public I8(byte val) {
        this.val = val;
    }

    public static Array<I8> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public I8OpI<I8> operator() {
        return new I8OpI<>() {
            @Override
            public Info.Operations<I8> getOperations() {
                return OPERATIONS;
            }

            @Override
            public Byte value() {
                return val;
            }
        };
    }

    @Override
    public String toString() {
        return String.valueOf(val);
    }
}
