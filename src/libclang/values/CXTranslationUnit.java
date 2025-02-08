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
import libclang.opaques.CXTranslationUnitImpl;

import java.lang.foreign.MemorySegment;

public class CXTranslationUnit implements PtrOp<CXTranslationUnit, CXTranslationUnitImpl>, Info<CXTranslationUnit> {
    public static final Operations<CXTranslationUnitImpl> ELEMENT_OPERATIONS = CXTranslationUnitImpl.OPERATIONS;
    public static final Operations<CXTranslationUnit> OPERATIONS = PtrOp.makeOperations(CXTranslationUnit::new);
    public static final long BYTE_SIZE = OPERATIONS.byteSize();

    private final MemorySegment segment;

    private MemorySegment fitByteSize(MemorySegment segment) {
        return segment.byteSize() == ELEMENT_OPERATIONS.byteSize() ? segment : segment.reinterpret(ELEMENT_OPERATIONS.byteSize());
    }

    public CXTranslationUnit(MemorySegment segment) {
        this.segment = fitByteSize(segment);
    }

    public CXTranslationUnit(ArrayOp<?, CXTranslationUnitImpl> arrayOperation) {
        this.segment = fitByteSize(arrayOperation.operator().value());
    }

    public CXTranslationUnit(Value<MemorySegment> pointee) {
        this.segment = fitByteSize(pointee.operator().value());
    }

    public CXTranslationUnit(PtrI<CXTranslationUnitImpl> pointee) {
        this.segment = fitByteSize(pointee.operator().value());
    }

    public static Array<CXTranslationUnit> list(SegmentAllocator allocator, long len) {
        return new Array<>(allocator, CXTranslationUnit.OPERATIONS, len);
    }

    @Override
    public String toString() {
        return "CXTranslationUnit{" +
                "segment=" + segment +
                '}';
    }

    @Override
    public PtrOpI<CXTranslationUnit, CXTranslationUnitImpl> operator() {
        return new PtrOpI<>() {
            @Override
            public MemorySegment value() {
                return segment;
            }

            @Override
            public CXTranslationUnitImpl pointee() {
                return ELEMENT_OPERATIONS.constructor().create(segment, 0);
            }

            @Override
            public Operations<CXTranslationUnit> getOperations() {
                return OPERATIONS;
            }

            @Override
            public Operations<CXTranslationUnitImpl> elementOperation() {
                return ELEMENT_OPERATIONS;
            }

            @Override
            public void setPointee(CXTranslationUnitImpl pointee) {
                ELEMENT_OPERATIONS.copy().copyTo(pointee, segment, 0);
            }
        };
    }
}
