package libclang.structs;


import libclang.structs.*;
import libclang.LibclangEnums.*;
import libclang.functions.*;
import libclang.values.*;
import libclang.shared.values.*;
import libclang.shared.*;
import libclang.shared.natives.*;
import libclang.shared.Value;
import libclang.shared.Pointer;
import libclang.shared.FunctionUtils;

import java.lang.foreign.*;
import java.util.function.Consumer;


public final class CXModuleMapDescriptorImpl implements Pointer<CXModuleMapDescriptorImpl> {
    public static final MemoryLayout MEMORY_LAYOUT = MemoryLayout.structLayout(MemoryLayout.sequenceLayout(1, ValueLayout.JAVA_BYTE));
    public static final long BYTE_SIZE = MEMORY_LAYOUT.byteSize();

    public static NList<CXModuleMapDescriptorImpl> list(Pointer<CXModuleMapDescriptorImpl> ptr) {
        return new NList<>(ptr, CXModuleMapDescriptorImpl::new, BYTE_SIZE);
    }

    public static NList<CXModuleMapDescriptorImpl> list(Pointer<CXModuleMapDescriptorImpl> ptr, long length) {
        return new NList<>(ptr, length, CXModuleMapDescriptorImpl::new, BYTE_SIZE);
    }

    public static NList<CXModuleMapDescriptorImpl> list(SegmentAllocator allocator, long length) {
        return new NList<>(allocator, length, CXModuleMapDescriptorImpl::new, BYTE_SIZE);
    }

    private final MemorySegment ptr;

    public CXModuleMapDescriptorImpl(Pointer<CXModuleMapDescriptorImpl> ptr) {
        this.ptr = ptr.pointer();
    }

    public CXModuleMapDescriptorImpl(SegmentAllocator allocator) {
        ptr = allocator.allocate(BYTE_SIZE);
    }

    public CXModuleMapDescriptorImpl reinterpretSize() {
        return new CXModuleMapDescriptorImpl(FunctionUtils.makePointer(ptr.reinterpret(BYTE_SIZE)));
    }

    @Override
    public MemorySegment pointer() {
        return ptr;
    }


    @Override
    public String toString() {
        if (MemorySegment.NULL.address() == ptr.address() || ptr.byteSize() < BYTE_SIZE)
            return "CXModuleMapDescriptorImpl{ptr=" + ptr;
        return """
                                CXModuleMapDescriptorImpl{\
                }""";
    }
}