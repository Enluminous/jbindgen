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
import libclang.opaques.CXVirtualFileOverlayImpl;

import java.lang.foreign.MemorySegment;

public class CXVirtualFileOverlay implements PtrOp<CXVirtualFileOverlay, CXVirtualFileOverlayImpl>, Info<CXVirtualFileOverlay> {
    public static final Operations<CXVirtualFileOverlayImpl> ELEMENT_OPERATIONS = CXVirtualFileOverlayImpl.OPERATIONS;
    public static final Operations<CXVirtualFileOverlay> OPERATIONS = PtrOp.makeOperations(CXVirtualFileOverlay::new);
    public static final long BYTE_SIZE = OPERATIONS.byteSize();

    private final MemorySegment segment;

    private MemorySegment fitByteSize(MemorySegment segment) {
        return segment.byteSize() == ELEMENT_OPERATIONS.byteSize() ? segment : segment.reinterpret(ELEMENT_OPERATIONS.byteSize());
    }

    public CXVirtualFileOverlay(MemorySegment segment) {
        this.segment = fitByteSize(segment);
    }

    public CXVirtualFileOverlay(ArrayOp<?, CXVirtualFileOverlayImpl> arrayOperation) {
        this.segment = fitByteSize(arrayOperation.operator().value());
    }

    public CXVirtualFileOverlay(Value<MemorySegment> pointee) {
        this.segment = fitByteSize(pointee.operator().value());
    }

    public CXVirtualFileOverlay(PtrI<CXVirtualFileOverlayImpl> pointee) {
        this.segment = fitByteSize(pointee.operator().value());
    }

    public static Array<CXVirtualFileOverlay> list(SegmentAllocator allocator, long len) {
        return new Array<>(allocator, CXVirtualFileOverlay.OPERATIONS, len);
    }

    @Override
    public String toString() {
        return "CXVirtualFileOverlay{" +
                "segment=" + segment +
                '}';
    }

    @Override
    public PtrOpI<CXVirtualFileOverlay, CXVirtualFileOverlayImpl> operator() {
        return new PtrOpI<>() {
            @Override
            public MemorySegment value() {
                return segment;
            }

            @Override
            public CXVirtualFileOverlayImpl pointee() {
                return ELEMENT_OPERATIONS.constructor().create(segment, 0);
            }

            @Override
            public Operations<CXVirtualFileOverlay> getOperations() {
                return OPERATIONS;
            }

            @Override
            public Operations<CXVirtualFileOverlayImpl> elementOperation() {
                return ELEMENT_OPERATIONS;
            }

            @Override
            public void setPointee(CXVirtualFileOverlayImpl pointee) {
                ELEMENT_OPERATIONS.copy().copyTo(pointee, segment, 0);
            }
        };
    }
}
