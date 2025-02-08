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
import libclang.opaques.CXModuleMapDescriptorImpl;

import java.lang.foreign.MemorySegment;

public class CXModuleMapDescriptor implements PtrOp<CXModuleMapDescriptor, CXModuleMapDescriptorImpl>, Info<CXModuleMapDescriptor> {
    public static final Operations<CXModuleMapDescriptorImpl> ELEMENT_OPERATIONS = CXModuleMapDescriptorImpl.OPERATIONS;
    public static final Operations<CXModuleMapDescriptor> OPERATIONS = PtrOp.makeOperations(CXModuleMapDescriptor::new);
    public static final long BYTE_SIZE = OPERATIONS.byteSize();

    private final MemorySegment segment;

    private MemorySegment fitByteSize(MemorySegment segment) {
        return segment.byteSize() == ELEMENT_OPERATIONS.byteSize() ? segment : segment.reinterpret(ELEMENT_OPERATIONS.byteSize());
    }

    public CXModuleMapDescriptor(MemorySegment segment) {
        this.segment = fitByteSize(segment);
    }

    public CXModuleMapDescriptor(ArrayOp<?, CXModuleMapDescriptorImpl> arrayOperation) {
        this.segment = fitByteSize(arrayOperation.operator().value());
    }

    public CXModuleMapDescriptor(Value<MemorySegment> pointee) {
        this.segment = fitByteSize(pointee.operator().value());
    }

    public CXModuleMapDescriptor(PtrI<CXModuleMapDescriptorImpl> pointee) {
        this.segment = fitByteSize(pointee.operator().value());
    }

    public static Array<CXModuleMapDescriptor> list(SegmentAllocator allocator, long len) {
        return new Array<>(allocator, CXModuleMapDescriptor.OPERATIONS, len);
    }

    @Override
    public String toString() {
        return "CXModuleMapDescriptor{" +
                "segment=" + segment +
                '}';
    }

    @Override
    public PtrOpI<CXModuleMapDescriptor, CXModuleMapDescriptorImpl> operator() {
        return new PtrOpI<>() {
            @Override
            public MemorySegment value() {
                return segment;
            }

            @Override
            public CXModuleMapDescriptorImpl pointee() {
                return ELEMENT_OPERATIONS.constructor().create(segment, 0);
            }

            @Override
            public Operations<CXModuleMapDescriptor> getOperations() {
                return OPERATIONS;
            }

            @Override
            public Operations<CXModuleMapDescriptorImpl> elementOperation() {
                return ELEMENT_OPERATIONS;
            }

            @Override
            public void setPointee(CXModuleMapDescriptorImpl pointee) {
                ELEMENT_OPERATIONS.copy().copyTo(pointee, segment, 0);
            }
        };
    }
}
