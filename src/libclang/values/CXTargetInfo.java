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
import libclang.opaques.CXTargetInfoImpl;

import java.lang.foreign.MemorySegment;

public class CXTargetInfo implements PtrOp<CXTargetInfo, CXTargetInfoImpl>, Info<CXTargetInfo> {
    public static final Operations<CXTargetInfoImpl> ELEMENT_OPERATIONS = CXTargetInfoImpl.OPERATIONS;
    public static final Operations<CXTargetInfo> OPERATIONS = PtrOp.makeOperations(CXTargetInfo::new);
    public static final long BYTE_SIZE = OPERATIONS.byteSize();

    private final MemorySegment segment;

    private MemorySegment fitByteSize(MemorySegment segment) {
        return segment.byteSize() == ELEMENT_OPERATIONS.byteSize() ? segment : segment.reinterpret(ELEMENT_OPERATIONS.byteSize());
    }

    public CXTargetInfo(MemorySegment segment) {
        this.segment = fitByteSize(segment);
    }

    public CXTargetInfo(ArrayOp<?, CXTargetInfoImpl> arrayOperation) {
        this.segment = fitByteSize(arrayOperation.operator().value());
    }

    public CXTargetInfo(Value<MemorySegment> pointee) {
        this.segment = fitByteSize(pointee.operator().value());
    }

    public CXTargetInfo(PtrI<CXTargetInfoImpl> pointee) {
        this.segment = fitByteSize(pointee.operator().value());
    }

    public static Array<CXTargetInfo> list(SegmentAllocator allocator, long len) {
        return new Array<>(allocator, CXTargetInfo.OPERATIONS, len);
    }

    @Override
    public String toString() {
        return "CXTargetInfo{" +
                "segment=" + segment +
                '}';
    }

    @Override
    public PtrOpI<CXTargetInfo, CXTargetInfoImpl> operator() {
        return new PtrOpI<>() {
            @Override
            public MemorySegment value() {
                return segment;
            }

            @Override
            public CXTargetInfoImpl pointee() {
                return ELEMENT_OPERATIONS.constructor().create(segment, 0);
            }

            @Override
            public Operations<CXTargetInfo> getOperations() {
                return OPERATIONS;
            }

            @Override
            public Operations<CXTargetInfoImpl> elementOperation() {
                return ELEMENT_OPERATIONS;
            }

            @Override
            public void setPointee(CXTargetInfoImpl pointee) {
                ELEMENT_OPERATIONS.copy().copyTo(pointee, segment, 0);
            }
        };
    }
}
