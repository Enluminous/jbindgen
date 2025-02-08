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
import libclang.opaques._CXCursorAndRangeVisitorBlock;

import java.lang.foreign.MemorySegment;

public class CXCursorAndRangeVisitorBlock implements PtrOp<CXCursorAndRangeVisitorBlock, _CXCursorAndRangeVisitorBlock>, Info<CXCursorAndRangeVisitorBlock> {
    public static final Operations<_CXCursorAndRangeVisitorBlock> ELEMENT_OPERATIONS = _CXCursorAndRangeVisitorBlock.OPERATIONS;
    public static final Operations<CXCursorAndRangeVisitorBlock> OPERATIONS = PtrOp.makeOperations(CXCursorAndRangeVisitorBlock::new);
    public static final long BYTE_SIZE = OPERATIONS.byteSize();

    private final MemorySegment segment;

    private MemorySegment fitByteSize(MemorySegment segment) {
        return segment.byteSize() == ELEMENT_OPERATIONS.byteSize() ? segment : segment.reinterpret(ELEMENT_OPERATIONS.byteSize());
    }

    public CXCursorAndRangeVisitorBlock(MemorySegment segment) {
        this.segment = fitByteSize(segment);
    }

    public CXCursorAndRangeVisitorBlock(ArrayOp<?, _CXCursorAndRangeVisitorBlock> arrayOperation) {
        this.segment = fitByteSize(arrayOperation.operator().value());
    }

    public CXCursorAndRangeVisitorBlock(Value<MemorySegment> pointee) {
        this.segment = fitByteSize(pointee.operator().value());
    }

    public CXCursorAndRangeVisitorBlock(PtrI<_CXCursorAndRangeVisitorBlock> pointee) {
        this.segment = fitByteSize(pointee.operator().value());
    }

    public static Array<CXCursorAndRangeVisitorBlock> list(SegmentAllocator allocator, long len) {
        return new Array<>(allocator, CXCursorAndRangeVisitorBlock.OPERATIONS, len);
    }

    @Override
    public String toString() {
        return "CXCursorAndRangeVisitorBlock{" +
                "segment=" + segment +
                '}';
    }

    @Override
    public PtrOpI<CXCursorAndRangeVisitorBlock, _CXCursorAndRangeVisitorBlock> operator() {
        return new PtrOpI<>() {
            @Override
            public MemorySegment value() {
                return segment;
            }

            @Override
            public _CXCursorAndRangeVisitorBlock pointee() {
                return ELEMENT_OPERATIONS.constructor().create(segment, 0);
            }

            @Override
            public Operations<CXCursorAndRangeVisitorBlock> getOperations() {
                return OPERATIONS;
            }

            @Override
            public Operations<_CXCursorAndRangeVisitorBlock> elementOperation() {
                return ELEMENT_OPERATIONS;
            }

            @Override
            public void setPointee(_CXCursorAndRangeVisitorBlock pointee) {
                ELEMENT_OPERATIONS.copy().copyTo(pointee, segment, 0);
            }
        };
    }
}
