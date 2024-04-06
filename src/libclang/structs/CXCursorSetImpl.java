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


public final class CXCursorSetImpl implements Pointer<CXCursorSetImpl> {
    public static final MemoryLayout MEMORY_LAYOUT = MemoryLayout.structLayout(MemoryLayout.sequenceLayout(1, ValueLayout.JAVA_BYTE));
    public static final long BYTE_SIZE = MEMORY_LAYOUT.byteSize();

    public static NList<CXCursorSetImpl> list(Pointer<CXCursorSetImpl> ptr) {
        return new NList<>(ptr, CXCursorSetImpl::new, BYTE_SIZE);
    }

    public static NList<CXCursorSetImpl> list(Pointer<CXCursorSetImpl> ptr, long length) {
        return new NList<>(ptr, length, CXCursorSetImpl::new, BYTE_SIZE);
    }

    public static NList<CXCursorSetImpl> list(SegmentAllocator allocator, long length) {
        return new NList<>(allocator, length, CXCursorSetImpl::new, BYTE_SIZE);
    }

    private final MemorySegment ptr;

    public CXCursorSetImpl(Pointer<CXCursorSetImpl> ptr) {
        this.ptr = ptr.pointer();
    }

    public CXCursorSetImpl(SegmentAllocator allocator) {
        ptr = allocator.allocate(BYTE_SIZE);
    }

    public CXCursorSetImpl reinterpretSize() {
        return new CXCursorSetImpl(FunctionUtils.makePointer(ptr.reinterpret(BYTE_SIZE)));
    }

    @Override
    public MemorySegment pointer() {
        return ptr;
    }


    @Override
    public String toString() {
        if (MemorySegment.NULL.address() == ptr.address() || ptr.byteSize() < BYTE_SIZE)
            return STR."CXCursorSetImpl{ptr=\{ptr}}";
        return STR."""
                CXCursorSetImpl{\
}""";
    }
}