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


public final class CXTUResourceUsageEntry implements Pointer<CXTUResourceUsageEntry> {
    public static final MemoryLayout MEMORY_LAYOUT = MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE));
    public static final long BYTE_SIZE = MEMORY_LAYOUT.byteSize();

    public static NList<CXTUResourceUsageEntry> list(Pointer<CXTUResourceUsageEntry> ptr) {
        return new NList<>(ptr, CXTUResourceUsageEntry::new, BYTE_SIZE);
    }

    public static NList<CXTUResourceUsageEntry> list(Pointer<CXTUResourceUsageEntry> ptr, long length) {
        return new NList<>(ptr, length, CXTUResourceUsageEntry::new, BYTE_SIZE);
    }

    public static NList<CXTUResourceUsageEntry> list(SegmentAllocator allocator, long length) {
        return new NList<>(allocator, length, CXTUResourceUsageEntry::new, BYTE_SIZE);
    }

    private final MemorySegment ptr;

    public CXTUResourceUsageEntry(Pointer<CXTUResourceUsageEntry> ptr) {
        this.ptr = ptr.pointer();
    }

    public CXTUResourceUsageEntry(SegmentAllocator allocator) {
        ptr = allocator.allocate(BYTE_SIZE);
    }

    public CXTUResourceUsageEntry reinterpretSize() {
        return new CXTUResourceUsageEntry(FunctionUtils.makePointer(ptr.reinterpret(BYTE_SIZE)));
    }

    @Override
    public MemorySegment pointer() {
        return ptr;
    }

    public CXTUResourceUsageKind kind() {
        return new CXTUResourceUsageKind(FunctionUtils.makePointer(ptr.asSlice(0,4)));
    }

    public CXTUResourceUsageEntry kind(CXTUResourceUsageKind kind) {
        ptr.set(ValueLayout.JAVA_INT, 0, kind.value());
        return this;
    }

    public long amount() {
        return ptr.get(ValueLayout.JAVA_LONG, 8);
    }

    public CXTUResourceUsageEntry amount(long amount) {
        ptr.set(ValueLayout.JAVA_LONG, 8, amount);
        return this;
    }


    @Override
    public String toString() {
        if (MemorySegment.NULL.address() == ptr.address() || ptr.byteSize() < BYTE_SIZE)
            return STR."CXTUResourceUsageEntry{ptr=\{ptr}}";
        return STR."""
                CXTUResourceUsageEntry{\
                kind=\{kind()},\
                amount=\{amount()}}""";
    }
}