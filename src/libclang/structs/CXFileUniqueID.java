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


public final class CXFileUniqueID implements Pointer<CXFileUniqueID> {
    public static final MemoryLayout MEMORY_LAYOUT = MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE));
    public static final long BYTE_SIZE = MEMORY_LAYOUT.byteSize();

    public static NList<CXFileUniqueID> list(Pointer<CXFileUniqueID> ptr) {
        return new NList<>(ptr, CXFileUniqueID::new, BYTE_SIZE);
    }

    public static NList<CXFileUniqueID> list(Pointer<CXFileUniqueID> ptr, long length) {
        return new NList<>(ptr, length, CXFileUniqueID::new, BYTE_SIZE);
    }

    public static NList<CXFileUniqueID> list(SegmentAllocator allocator, long length) {
        return new NList<>(allocator, length, CXFileUniqueID::new, BYTE_SIZE);
    }

    private final MemorySegment ptr;

    public CXFileUniqueID(Pointer<CXFileUniqueID> ptr) {
        this.ptr = ptr.pointer();
    }

    public CXFileUniqueID(SegmentAllocator allocator) {
        ptr = allocator.allocate(BYTE_SIZE);
    }

    public CXFileUniqueID reinterpretSize() {
        return new CXFileUniqueID(FunctionUtils.makePointer(ptr.reinterpret(BYTE_SIZE)));
    }

    @Override
    public MemorySegment pointer() {
        return ptr;
    }

    public VI64List<VI64<Long>> data() {
        return VI64.list(FunctionUtils.makePointer(ptr.asSlice(0, 24)));
    }

    public CXFileUniqueID data(VI64List<VI64<Long>>data) {
        MemorySegment.copy(data.pointer(), 0,ptr, 0, Math.min(24,data.pointer().byteSize()));
        return this;
    }


    @Override
    public String toString() {
        if (MemorySegment.NULL.address() == ptr.address() || ptr.byteSize() < BYTE_SIZE)
            return STR."CXFileUniqueID{ptr=\{ptr}}";
        return STR."""
                CXFileUniqueID{\
                data=\{data()}}""";
    }
}