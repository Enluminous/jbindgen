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


public final class CXIdxLoc implements Pointer<CXIdxLoc> {
    public static final MemoryLayout MEMORY_LAYOUT = MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE));
    public static final long BYTE_SIZE = MEMORY_LAYOUT.byteSize();

    public static NList<CXIdxLoc> list(Pointer<CXIdxLoc> ptr) {
        return new NList<>(ptr, CXIdxLoc::new, BYTE_SIZE);
    }

    public static NList<CXIdxLoc> list(Pointer<CXIdxLoc> ptr, long length) {
        return new NList<>(ptr, length, CXIdxLoc::new, BYTE_SIZE);
    }

    public static NList<CXIdxLoc> list(SegmentAllocator allocator, long length) {
        return new NList<>(allocator, length, CXIdxLoc::new, BYTE_SIZE);
    }

    private final MemorySegment ptr;

    public CXIdxLoc(Pointer<CXIdxLoc> ptr) {
        this.ptr = ptr.pointer();
    }

    public CXIdxLoc(SegmentAllocator allocator) {
        ptr = allocator.allocate(BYTE_SIZE);
    }

    public CXIdxLoc reinterpretSize() {
        return new CXIdxLoc(FunctionUtils.makePointer(ptr.reinterpret(BYTE_SIZE)));
    }

    @Override
    public MemorySegment pointer() {
        return ptr;
    }

    public Pointer<Pointer<?>> ptr_data() {
        return FunctionUtils.makePointer(ptr.asSlice(0, 16));
    }

    public CXIdxLoc ptr_data(Pointer<Pointer<?>> ptr_data) {
        MemorySegment.copy(ptr_data.pointer(), 0,ptr, 0, Math.min(16,ptr_data.pointer().byteSize()));
        return this;
    }

    public int int_data() {
        return ptr.get(ValueLayout.JAVA_INT, 16);
    }

    public CXIdxLoc int_data(int int_data) {
        ptr.set(ValueLayout.JAVA_INT, 16, int_data);
        return this;
    }


    @Override
    public String toString() {
        if (MemorySegment.NULL.address() == ptr.address() || ptr.byteSize() < BYTE_SIZE)
            return STR."CXIdxLoc{ptr=\{ptr}}";
        return STR."""
                CXIdxLoc{\
                ptr_data=\{ptr_data()},\
                int_data=\{int_data()}}""";
    }
}