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


public final class CXString implements Pointer<CXString> {
    public static final MemoryLayout MEMORY_LAYOUT = MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE));
    public static final long BYTE_SIZE = MEMORY_LAYOUT.byteSize();

    public static NList<CXString> list(Pointer<CXString> ptr) {
        return new NList<>(ptr, CXString::new, BYTE_SIZE);
    }

    public static NList<CXString> list(Pointer<CXString> ptr, long length) {
        return new NList<>(ptr, length, CXString::new, BYTE_SIZE);
    }

    public static NList<CXString> list(SegmentAllocator allocator, long length) {
        return new NList<>(allocator, length, CXString::new, BYTE_SIZE);
    }

    private final MemorySegment ptr;

    public CXString(Pointer<CXString> ptr) {
        this.ptr = ptr.pointer();
    }

    public CXString(SegmentAllocator allocator) {
        ptr = allocator.allocate(BYTE_SIZE);
    }

    public CXString reinterpretSize() {
        return new CXString(FunctionUtils.makePointer(ptr.reinterpret(BYTE_SIZE)));
    }

    @Override
    public MemorySegment pointer() {
        return ptr;
    }

    public Pointer<?> data() {
        return FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS, 0));
    }

    public CXString data(Pointer<?> data) {
        ptr.set(ValueLayout.ADDRESS, 0, data.pointer());
        return this;
    }

    public int private_flags() {
        return ptr.get(ValueLayout.JAVA_INT, 8);
    }

    public CXString private_flags(int private_flags) {
        ptr.set(ValueLayout.JAVA_INT, 8, private_flags);
        return this;
    }


    @Override
    public String toString() {
        if (MemorySegment.NULL.address() == ptr.address() || ptr.byteSize() < BYTE_SIZE)
            return "CXString{ptr=" + ptr;
        return "CXString{" +
                "data=" + data() +
                "private_flags=" + private_flags() + "}";
    }
}