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


public final class CXSourceRangeList implements Pointer<CXSourceRangeList> {
    public static final MemoryLayout MEMORY_LAYOUT = MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE));
    public static final long BYTE_SIZE = MEMORY_LAYOUT.byteSize();

    public static NList<CXSourceRangeList> list(Pointer<CXSourceRangeList> ptr) {
        return new NList<>(ptr, CXSourceRangeList::new, BYTE_SIZE);
    }

    public static NList<CXSourceRangeList> list(Pointer<CXSourceRangeList> ptr, long length) {
        return new NList<>(ptr, length, CXSourceRangeList::new, BYTE_SIZE);
    }

    public static NList<CXSourceRangeList> list(SegmentAllocator allocator, long length) {
        return new NList<>(allocator, length, CXSourceRangeList::new, BYTE_SIZE);
    }

    private final MemorySegment ptr;

    public CXSourceRangeList(Pointer<CXSourceRangeList> ptr) {
        this.ptr = ptr.pointer();
    }

    public CXSourceRangeList(SegmentAllocator allocator) {
        ptr = allocator.allocate(BYTE_SIZE);
    }

    public CXSourceRangeList reinterpretSize() {
        return new CXSourceRangeList(FunctionUtils.makePointer(ptr.reinterpret(BYTE_SIZE)));
    }

    @Override
    public MemorySegment pointer() {
        return ptr;
    }

    public int count() {
        return ptr.get(ValueLayout.JAVA_INT, 0);
    }

    public CXSourceRangeList count(int count) {
        ptr.set(ValueLayout.JAVA_INT, 0, count);
        return this;
    }

    public Pointer<CXSourceRange> ranges() {
        return FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS, 8));
    }

    public NList<CXSourceRange> ranges(long length) {
        return CXSourceRange.list(FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS, 8)), length);
    }

    public CXSourceRangeList ranges(Pointer<CXSourceRange> ranges) {
        ptr.set(ValueLayout.ADDRESS, 8, ranges.pointer());
        return this;
    }


    @Override
    public String toString() {
        if (MemorySegment.NULL.address() == ptr.address() || ptr.byteSize() < BYTE_SIZE)
            return "CXSourceRangeList{ptr=" + ptr;
        return "CXSourceRangeList{" +
                "count=" + count() +
                "ranges=" + ranges() + "}";
    }
}