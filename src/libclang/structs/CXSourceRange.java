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


public final class CXSourceRange implements Pointer<CXSourceRange> {
    public static final MemoryLayout MEMORY_LAYOUT = MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE));
    public static final long BYTE_SIZE = MEMORY_LAYOUT.byteSize();

    public static NList<CXSourceRange> list(Pointer<CXSourceRange> ptr) {
        return new NList<>(ptr, CXSourceRange::new, BYTE_SIZE);
    }

    public static NList<CXSourceRange> list(Pointer<CXSourceRange> ptr, long length) {
        return new NList<>(ptr, length, CXSourceRange::new, BYTE_SIZE);
    }

    public static NList<CXSourceRange> list(SegmentAllocator allocator, long length) {
        return new NList<>(allocator, length, CXSourceRange::new, BYTE_SIZE);
    }

    private final MemorySegment ptr;

    public CXSourceRange(Pointer<CXSourceRange> ptr) {
        this.ptr = ptr.pointer();
    }

    public CXSourceRange(SegmentAllocator allocator) {
        ptr = allocator.allocate(BYTE_SIZE);
    }

    public CXSourceRange reinterpretSize() {
        return new CXSourceRange(FunctionUtils.makePointer(ptr.reinterpret(BYTE_SIZE)));
    }

    @Override
    public MemorySegment pointer() {
        return ptr;
    }

    public Pointer<Pointer<?>> ptr_data() {
        return FunctionUtils.makePointer(ptr.asSlice(0, 16));
    }

    public CXSourceRange ptr_data(Pointer<Pointer<?>> ptr_data) {
        MemorySegment.copy(ptr_data.pointer(), 0, ptr, 0, Math.min(16, ptr_data.pointer().byteSize()));
        return this;
    }

    public int begin_int_data() {
        return ptr.get(ValueLayout.JAVA_INT, 16);
    }

    public CXSourceRange begin_int_data(int begin_int_data) {
        ptr.set(ValueLayout.JAVA_INT, 16, begin_int_data);
        return this;
    }

    public int end_int_data() {
        return ptr.get(ValueLayout.JAVA_INT, 20);
    }

    public CXSourceRange end_int_data(int end_int_data) {
        ptr.set(ValueLayout.JAVA_INT, 20, end_int_data);
        return this;
    }


    @Override
    public String toString() {
        if (MemorySegment.NULL.address() == ptr.address() || ptr.byteSize() < BYTE_SIZE)
            return "CXSourceRange{ptr=" + ptr + "}";
        return "CXSourceRange{" +
                "ptr_data=" + ptr_data() +
                "begin_int_data=" + begin_int_data() +
                "end_int_data=" + end_int_data() + "}";
    }
}