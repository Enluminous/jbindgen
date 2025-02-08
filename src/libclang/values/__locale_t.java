package libclang.values;


import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import libclang.common.Array;
import libclang.common.ArrayOp;
import libclang.common.Info;
import libclang.common.Ptr;
import libclang.common.PtrI;
import libclang.common.PtrOp;
import libclang.common.Value;
import libclang.structs.__locale_struct;

import java.lang.foreign.MemorySegment;

public class __locale_t implements PtrOp<__locale_t, __locale_struct>, Info<__locale_t> {
    public static final Operations<__locale_struct> ELEMENT_OPERATIONS = __locale_struct.OPERATIONS;
    public static final Operations<__locale_t> OPERATIONS = PtrOp.makeOperations(__locale_t::new);
    public static final long BYTE_SIZE = OPERATIONS.byteSize();

    private final MemorySegment segment;

    private MemorySegment fitByteSize(MemorySegment segment) {
        return segment.byteSize() == ELEMENT_OPERATIONS.byteSize() ? segment : segment.reinterpret(ELEMENT_OPERATIONS.byteSize());
    }

    public __locale_t(MemorySegment segment) {
        this.segment = fitByteSize(segment);
    }

    public __locale_t(ArrayOp<?, __locale_struct> arrayOperation) {
        this.segment = fitByteSize(arrayOperation.operator().value());
    }

    public __locale_t(Value<MemorySegment> pointee) {
        this.segment = fitByteSize(pointee.operator().value());
    }

    public __locale_t(PtrI<__locale_struct> pointee) {
        this.segment = fitByteSize(pointee.operator().value());
    }

    public static Array<__locale_t> list(SegmentAllocator allocator, long len) {
        return new Array<>(allocator, __locale_t.OPERATIONS, len);
    }

    @Override
    public String toString() {
        return "__locale_t{" +
                "segment=" + segment +
                '}';
    }

    @Override
    public PtrOpI<__locale_t, __locale_struct> operator() {
        return new PtrOpI<>() {
            @Override
            public MemorySegment value() {
                return segment;
            }

            @Override
            public __locale_struct pointee() {
                return ELEMENT_OPERATIONS.constructor().create(segment, 0);
            }

            @Override
            public Operations<__locale_t> getOperations() {
                return OPERATIONS;
            }

            @Override
            public Operations<__locale_struct> elementOperation() {
                return ELEMENT_OPERATIONS;
            }

            @Override
            public void setPointee(__locale_struct pointee) {
                ELEMENT_OPERATIONS.copy().copyTo(pointee, segment, 0);
            }
        };
    }
}
