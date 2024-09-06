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


public final class CXStringSet implements Pointer<CXStringSet> {
    public static final MemoryLayout MEMORY_LAYOUT = MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE));
    public static final long BYTE_SIZE = MEMORY_LAYOUT.byteSize();

    public static NList<CXStringSet> list(Pointer<CXStringSet> ptr) {
        return new NList<>(ptr, CXStringSet::new, BYTE_SIZE);
    }

    public static NList<CXStringSet> list(Pointer<CXStringSet> ptr, long length) {
        return new NList<>(ptr, length, CXStringSet::new, BYTE_SIZE);
    }

    public static NList<CXStringSet> list(SegmentAllocator allocator, long length) {
        return new NList<>(allocator, length, CXStringSet::new, BYTE_SIZE);
    }

    private final MemorySegment ptr;

    public CXStringSet(Pointer<CXStringSet> ptr) {
        this.ptr = ptr.pointer();
    }

    public CXStringSet(SegmentAllocator allocator) {
        ptr = allocator.allocate(BYTE_SIZE);
    }

    public CXStringSet reinterpretSize() {
        return new CXStringSet(FunctionUtils.makePointer(ptr.reinterpret(BYTE_SIZE)));
    }

    @Override
    public MemorySegment pointer() {
        return ptr;
    }

    public Pointer<CXString> Strings() {
        return FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS, 0));
    }

    public NList<CXString> Strings(long length) {
        return CXString.list(FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS, 0)), length);
    }

    public CXStringSet Strings(Pointer<CXString> Strings) {
        ptr.set(ValueLayout.ADDRESS, 0, Strings.pointer());
        return this;
    }

    public int Count() {
        return ptr.get(ValueLayout.JAVA_INT, 8);
    }

    public CXStringSet Count(int Count) {
        ptr.set(ValueLayout.JAVA_INT, 8, Count);
        return this;
    }


    @Override
    public String toString() {
        if (MemorySegment.NULL.address() == ptr.address() || ptr.byteSize() < BYTE_SIZE)
            return "CXStringSet{ptr=" + ptr;
        return "CXStringSet{" +
                "Strings=" + Strings() +
                "Count=" + Count() + "}";
    }
}