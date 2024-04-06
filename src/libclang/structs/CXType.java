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


public final class CXType implements Pointer<CXType> {
    public static final MemoryLayout MEMORY_LAYOUT = MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE));
    public static final long BYTE_SIZE = MEMORY_LAYOUT.byteSize();

    public static NList<CXType> list(Pointer<CXType> ptr) {
        return new NList<>(ptr, CXType::new, BYTE_SIZE);
    }

    public static NList<CXType> list(Pointer<CXType> ptr, long length) {
        return new NList<>(ptr, length, CXType::new, BYTE_SIZE);
    }

    public static NList<CXType> list(SegmentAllocator allocator, long length) {
        return new NList<>(allocator, length, CXType::new, BYTE_SIZE);
    }

    private final MemorySegment ptr;

    public CXType(Pointer<CXType> ptr) {
        this.ptr = ptr.pointer();
    }

    public CXType(SegmentAllocator allocator) {
        ptr = allocator.allocate(BYTE_SIZE);
    }

    public CXType reinterpretSize() {
        return new CXType(FunctionUtils.makePointer(ptr.reinterpret(BYTE_SIZE)));
    }

    @Override
    public MemorySegment pointer() {
        return ptr;
    }

    public CXTypeKind kind() {
        return new CXTypeKind(FunctionUtils.makePointer(ptr.asSlice(0,4)));
    }

    public CXType kind(CXTypeKind kind) {
        ptr.set(ValueLayout.JAVA_INT, 0, kind.value());
        return this;
    }

    public Pointer<Pointer<?>> data() {
        return FunctionUtils.makePointer(ptr.asSlice(8, 16));
    }

    public CXType data(Pointer<Pointer<?>> data) {
        MemorySegment.copy(data.pointer(), 0,ptr, 8, Math.min(16,data.pointer().byteSize()));
        return this;
    }


    @Override
    public String toString() {
        if (MemorySegment.NULL.address() == ptr.address() || ptr.byteSize() < BYTE_SIZE)
            return STR."CXType{ptr=\{ptr}}";
        return STR."""
                CXType{\
                kind=\{kind()},\
                data=\{data()}}""";
    }
}