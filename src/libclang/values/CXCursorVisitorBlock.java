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
import libclang.opaques._CXChildVisitResult;

import java.lang.foreign.MemorySegment;

public class CXCursorVisitorBlock implements PtrOp<CXCursorVisitorBlock, _CXChildVisitResult>, Info<CXCursorVisitorBlock> {
    public static final Operations<_CXChildVisitResult> ELEMENT_OPERATIONS = _CXChildVisitResult.OPERATIONS;
    public static final Operations<CXCursorVisitorBlock> OPERATIONS = PtrOp.makeOperations(CXCursorVisitorBlock::new);
    public static final long BYTE_SIZE = OPERATIONS.byteSize();

    private final MemorySegment segment;

    private MemorySegment fitByteSize(MemorySegment segment) {
        return segment.byteSize() == ELEMENT_OPERATIONS.byteSize() ? segment : segment.reinterpret(ELEMENT_OPERATIONS.byteSize());
    }

    public CXCursorVisitorBlock(MemorySegment segment) {
        this.segment = fitByteSize(segment);
    }

    public CXCursorVisitorBlock(ArrayOp<?, _CXChildVisitResult> arrayOperation) {
        this.segment = fitByteSize(arrayOperation.operator().value());
    }

    public CXCursorVisitorBlock(Value<MemorySegment> pointee) {
        this.segment = fitByteSize(pointee.operator().value());
    }

    public CXCursorVisitorBlock(PtrI<_CXChildVisitResult> pointee) {
        this.segment = fitByteSize(pointee.operator().value());
    }

    public static Array<CXCursorVisitorBlock> list(SegmentAllocator allocator, long len) {
        return new Array<>(allocator, CXCursorVisitorBlock.OPERATIONS, len);
    }

    @Override
    public String toString() {
        return "CXCursorVisitorBlock{" +
                "segment=" + segment +
                '}';
    }

    @Override
    public PtrOpI<CXCursorVisitorBlock, _CXChildVisitResult> operator() {
        return new PtrOpI<>() {
            @Override
            public MemorySegment value() {
                return segment;
            }

            @Override
            public _CXChildVisitResult pointee() {
                return ELEMENT_OPERATIONS.constructor().create(segment, 0);
            }

            @Override
            public Operations<CXCursorVisitorBlock> getOperations() {
                return OPERATIONS;
            }

            @Override
            public Operations<_CXChildVisitResult> elementOperation() {
                return ELEMENT_OPERATIONS;
            }

            @Override
            public void setPointee(_CXChildVisitResult pointee) {
                ELEMENT_OPERATIONS.copy().copyTo(pointee, segment, 0);
            }
        };
    }
}
