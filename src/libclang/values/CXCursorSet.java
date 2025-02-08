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
import libclang.opaques.CXCursorSetImpl;

import java.lang.foreign.MemorySegment;

public class CXCursorSet implements PtrOp<CXCursorSet, CXCursorSetImpl>, Info<CXCursorSet> {
    public static final Operations<CXCursorSetImpl> ELEMENT_OPERATIONS = CXCursorSetImpl.OPERATIONS;
    public static final Operations<CXCursorSet> OPERATIONS = PtrOp.makeOperations(CXCursorSet::new);
    public static final long BYTE_SIZE = OPERATIONS.byteSize();

    private final MemorySegment segment;

    private MemorySegment fitByteSize(MemorySegment segment) {
        return segment.byteSize() == ELEMENT_OPERATIONS.byteSize() ? segment : segment.reinterpret(ELEMENT_OPERATIONS.byteSize());
    }

    public CXCursorSet(MemorySegment segment) {
        this.segment = fitByteSize(segment);
    }

    public CXCursorSet(ArrayOp<?, CXCursorSetImpl> arrayOperation) {
        this.segment = fitByteSize(arrayOperation.operator().value());
    }

    public CXCursorSet(Value<MemorySegment> pointee) {
        this.segment = fitByteSize(pointee.operator().value());
    }

    public CXCursorSet(PtrI<CXCursorSetImpl> pointee) {
        this.segment = fitByteSize(pointee.operator().value());
    }

    public static Array<CXCursorSet> list(SegmentAllocator allocator, long len) {
        return new Array<>(allocator, CXCursorSet.OPERATIONS, len);
    }

    @Override
    public String toString() {
        return "CXCursorSet{" +
                "segment=" + segment +
                '}';
    }

    @Override
    public PtrOpI<CXCursorSet, CXCursorSetImpl> operator() {
        return new PtrOpI<>() {
            @Override
            public MemorySegment value() {
                return segment;
            }

            @Override
            public CXCursorSetImpl pointee() {
                return ELEMENT_OPERATIONS.constructor().create(segment, 0);
            }

            @Override
            public Operations<CXCursorSet> getOperations() {
                return OPERATIONS;
            }

            @Override
            public Operations<CXCursorSetImpl> elementOperation() {
                return ELEMENT_OPERATIONS;
            }

            @Override
            public void setPointee(CXCursorSetImpl pointee) {
                ELEMENT_OPERATIONS.copy().copyTo(pointee, segment, 0);
            }
        };
    }
}
