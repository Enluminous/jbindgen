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

import java.lang.foreign.MemorySegment;

public class CXDiagnosticSet implements PtrOp<CXDiagnosticSet, Void>, Info<CXDiagnosticSet> {
    public static final Operations<Void> ELEMENT_OPERATIONS = Info.makeOperations();
    public static final Operations<CXDiagnosticSet> OPERATIONS = PtrOp.makeOperations(CXDiagnosticSet::new);
    public static final long BYTE_SIZE = OPERATIONS.byteSize();

    private final MemorySegment segment;

    private MemorySegment fitByteSize(MemorySegment segment) {
        return segment.byteSize() == ELEMENT_OPERATIONS.byteSize() ? segment : segment.reinterpret(ELEMENT_OPERATIONS.byteSize());
    }

    public CXDiagnosticSet(MemorySegment segment) {
        this.segment = fitByteSize(segment);
    }

    public CXDiagnosticSet(ArrayOp<?, Void> arrayOperation) {
        this.segment = fitByteSize(arrayOperation.operator().value());
    }

    public CXDiagnosticSet(Value<MemorySegment> pointee) {
        this.segment = fitByteSize(pointee.operator().value());
    }

    public CXDiagnosticSet(PtrI<Void> pointee) {
        this.segment = fitByteSize(pointee.operator().value());
    }

    public static Array<CXDiagnosticSet> list(SegmentAllocator allocator, long len) {
        return new Array<>(allocator, CXDiagnosticSet.OPERATIONS, len);
    }

    @Override
    public String toString() {
        return "CXDiagnosticSet{" +
                "segment=" + segment +
                '}';
    }

    @Override
    public PtrOpI<CXDiagnosticSet, Void> operator() {
        return new PtrOpI<>() {
            @Override
            public MemorySegment value() {
                return segment;
            }

            @Override
            public Void pointee() {
                return ELEMENT_OPERATIONS.constructor().create(segment, 0);
            }

            @Override
            public Operations<CXDiagnosticSet> getOperations() {
                return OPERATIONS;
            }

            @Override
            public Operations<Void> elementOperation() {
                return ELEMENT_OPERATIONS;
            }

            @Override
            public void setPointee(Void pointee) {
                ELEMENT_OPERATIONS.copy().copyTo(pointee, segment, 0);
            }
        };
    }
}
