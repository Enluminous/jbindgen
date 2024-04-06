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


public final class CXIdxContainerInfo implements Pointer<CXIdxContainerInfo> {
    public static final MemoryLayout MEMORY_LAYOUT = MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE));
    public static final long BYTE_SIZE = MEMORY_LAYOUT.byteSize();

    public static NList<CXIdxContainerInfo> list(Pointer<CXIdxContainerInfo> ptr) {
        return new NList<>(ptr, CXIdxContainerInfo::new, BYTE_SIZE);
    }

    public static NList<CXIdxContainerInfo> list(Pointer<CXIdxContainerInfo> ptr, long length) {
        return new NList<>(ptr, length, CXIdxContainerInfo::new, BYTE_SIZE);
    }

    public static NList<CXIdxContainerInfo> list(SegmentAllocator allocator, long length) {
        return new NList<>(allocator, length, CXIdxContainerInfo::new, BYTE_SIZE);
    }

    private final MemorySegment ptr;

    public CXIdxContainerInfo(Pointer<CXIdxContainerInfo> ptr) {
        this.ptr = ptr.pointer();
    }

    public CXIdxContainerInfo(SegmentAllocator allocator) {
        ptr = allocator.allocate(BYTE_SIZE);
    }

    public CXIdxContainerInfo reinterpretSize() {
        return new CXIdxContainerInfo(FunctionUtils.makePointer(ptr.reinterpret(BYTE_SIZE)));
    }

    @Override
    public MemorySegment pointer() {
        return ptr;
    }

    public CXCursor cursor() {
        return new CXCursor(FunctionUtils.makePointer(ptr.asSlice(0, 32)));
    }

    public CXIdxContainerInfo cursor(CXCursor cursor) {
        MemorySegment.copy(cursor.pointer(), 0,ptr, 0, Math.min(32,cursor.pointer().byteSize()));
        return this;
    }


    @Override
    public String toString() {
        if (MemorySegment.NULL.address() == ptr.address() || ptr.byteSize() < BYTE_SIZE)
            return STR."CXIdxContainerInfo{ptr=\{ptr}}";
        return STR."""
                CXIdxContainerInfo{\
                cursor=\{cursor()}}""";
    }
}