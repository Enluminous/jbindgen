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


public final class CXToken implements Pointer<CXToken> {
    public static final MemoryLayout MEMORY_LAYOUT = MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE));
    public static final long BYTE_SIZE = MEMORY_LAYOUT.byteSize();

    public static NList<CXToken> list(Pointer<CXToken> ptr) {
        return new NList<>(ptr, CXToken::new, BYTE_SIZE);
    }

    public static NList<CXToken> list(Pointer<CXToken> ptr, long length) {
        return new NList<>(ptr, length, CXToken::new, BYTE_SIZE);
    }

    public static NList<CXToken> list(SegmentAllocator allocator, long length) {
        return new NList<>(allocator, length, CXToken::new, BYTE_SIZE);
    }

    private final MemorySegment ptr;

    public CXToken(Pointer<CXToken> ptr) {
        this.ptr = ptr.pointer();
    }

    public CXToken(SegmentAllocator allocator) {
        ptr = allocator.allocate(BYTE_SIZE);
    }

    public CXToken reinterpretSize() {
        return new CXToken(FunctionUtils.makePointer(ptr.reinterpret(BYTE_SIZE)));
    }

    @Override
    public MemorySegment pointer() {
        return ptr;
    }

    public VI32List<VI32<Integer>> int_data() {
        return VI32.list(FunctionUtils.makePointer(ptr.asSlice(0, 16)));
    }

    public CXToken int_data(VI32List<VI32<Integer>> int_data) {
        MemorySegment.copy(int_data.pointer(), 0, ptr, 0, Math.min(16, int_data.pointer().byteSize()));
        return this;
    }

    public Pointer<?> ptr_data() {
        return FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS, 16));
    }

    public CXToken ptr_data(Pointer<?> ptr_data) {
        ptr.set(ValueLayout.ADDRESS, 16, ptr_data.pointer());
        return this;
    }


    @Override
    public String toString() {
        if (MemorySegment.NULL.address() == ptr.address() || ptr.byteSize() < BYTE_SIZE)
            return "CXToken{ptr=" + ptr;
        return "CXToken{" +
                "int_data=" + int_data() +
                "ptr_data=" + ptr_data() + "}";
    }
}