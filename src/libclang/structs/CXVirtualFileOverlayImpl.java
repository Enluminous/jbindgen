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


public final class CXVirtualFileOverlayImpl implements Pointer<CXVirtualFileOverlayImpl> {
    public static final MemoryLayout MEMORY_LAYOUT = MemoryLayout.structLayout(MemoryLayout.sequenceLayout(1, ValueLayout.JAVA_BYTE));
    public static final long BYTE_SIZE = MEMORY_LAYOUT.byteSize();

    public static NList<CXVirtualFileOverlayImpl> list(Pointer<CXVirtualFileOverlayImpl> ptr) {
        return new NList<>(ptr, CXVirtualFileOverlayImpl::new, BYTE_SIZE);
    }

    public static NList<CXVirtualFileOverlayImpl> list(Pointer<CXVirtualFileOverlayImpl> ptr, long length) {
        return new NList<>(ptr, length, CXVirtualFileOverlayImpl::new, BYTE_SIZE);
    }

    public static NList<CXVirtualFileOverlayImpl> list(SegmentAllocator allocator, long length) {
        return new NList<>(allocator, length, CXVirtualFileOverlayImpl::new, BYTE_SIZE);
    }

    private final MemorySegment ptr;

    public CXVirtualFileOverlayImpl(Pointer<CXVirtualFileOverlayImpl> ptr) {
        this.ptr = ptr.pointer();
    }

    public CXVirtualFileOverlayImpl(SegmentAllocator allocator) {
        ptr = allocator.allocate(BYTE_SIZE);
    }

    public CXVirtualFileOverlayImpl reinterpretSize() {
        return new CXVirtualFileOverlayImpl(FunctionUtils.makePointer(ptr.reinterpret(BYTE_SIZE)));
    }

    @Override
    public MemorySegment pointer() {
        return ptr;
    }


    @Override
    public String toString() {
        if (MemorySegment.NULL.address() == ptr.address() || ptr.byteSize() < BYTE_SIZE)
            return STR."CXVirtualFileOverlayImpl{ptr=\{ptr}}";
        return STR."""
                CXVirtualFileOverlayImpl{\
}""";
    }
}