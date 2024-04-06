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


public final class CXTUResourceUsage implements Pointer<CXTUResourceUsage> {
    public static final MemoryLayout MEMORY_LAYOUT = MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE));
    public static final long BYTE_SIZE = MEMORY_LAYOUT.byteSize();

    public static NList<CXTUResourceUsage> list(Pointer<CXTUResourceUsage> ptr) {
        return new NList<>(ptr, CXTUResourceUsage::new, BYTE_SIZE);
    }

    public static NList<CXTUResourceUsage> list(Pointer<CXTUResourceUsage> ptr, long length) {
        return new NList<>(ptr, length, CXTUResourceUsage::new, BYTE_SIZE);
    }

    public static NList<CXTUResourceUsage> list(SegmentAllocator allocator, long length) {
        return new NList<>(allocator, length, CXTUResourceUsage::new, BYTE_SIZE);
    }

    private final MemorySegment ptr;

    public CXTUResourceUsage(Pointer<CXTUResourceUsage> ptr) {
        this.ptr = ptr.pointer();
    }

    public CXTUResourceUsage(SegmentAllocator allocator) {
        ptr = allocator.allocate(BYTE_SIZE);
    }

    public CXTUResourceUsage reinterpretSize() {
        return new CXTUResourceUsage(FunctionUtils.makePointer(ptr.reinterpret(BYTE_SIZE)));
    }

    @Override
    public MemorySegment pointer() {
        return ptr;
    }

    public Pointer<?> data() {
        return FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS,0));
    }

    public CXTUResourceUsage data(Pointer<?> data) {
        ptr.set(ValueLayout.ADDRESS, 0, data.pointer());
        return this;
    }

    public int numEntries() {
        return ptr.get(ValueLayout.JAVA_INT, 8);
    }

    public CXTUResourceUsage numEntries(int numEntries) {
        ptr.set(ValueLayout.JAVA_INT, 8, numEntries);
        return this;
    }

    public Pointer<CXTUResourceUsageEntry> entries() {
        return FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS,16));
    }

    public NList<CXTUResourceUsageEntry> entries(long length) {
        return CXTUResourceUsageEntry.list(FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS,16)), length);
    }

    public CXTUResourceUsage entries(Pointer<CXTUResourceUsageEntry> entries) {
        ptr.set(ValueLayout.ADDRESS, 16, entries.pointer());
        return this;
    }


    @Override
    public String toString() {
        if (MemorySegment.NULL.address() == ptr.address() || ptr.byteSize() < BYTE_SIZE)
            return STR."CXTUResourceUsage{ptr=\{ptr}}";
        return STR."""
                CXTUResourceUsage{\
                data=\{data()},\
                numEntries=\{numEntries()},\
                entries=\{entries()}}""";
    }
}