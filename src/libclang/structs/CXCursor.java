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


public final class CXCursor implements Pointer<CXCursor> {
    public static final MemoryLayout MEMORY_LAYOUT = MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE));
    public static final long BYTE_SIZE = MEMORY_LAYOUT.byteSize();

    public static NList<CXCursor> list(Pointer<CXCursor> ptr) {
        return new NList<>(ptr, CXCursor::new, BYTE_SIZE);
    }

    public static NList<CXCursor> list(Pointer<CXCursor> ptr, long length) {
        return new NList<>(ptr, length, CXCursor::new, BYTE_SIZE);
    }

    public static NList<CXCursor> list(SegmentAllocator allocator, long length) {
        return new NList<>(allocator, length, CXCursor::new, BYTE_SIZE);
    }

    private final MemorySegment ptr;

    public CXCursor(Pointer<CXCursor> ptr) {
        this.ptr = ptr.pointer();
    }

    public CXCursor(SegmentAllocator allocator) {
        ptr = allocator.allocate(BYTE_SIZE);
    }

    public CXCursor reinterpretSize() {
        return new CXCursor(FunctionUtils.makePointer(ptr.reinterpret(BYTE_SIZE)));
    }

    @Override
    public MemorySegment pointer() {
        return ptr;
    }

    public CXCursorKind kind() {
        return new CXCursorKind(FunctionUtils.makePointer(ptr.asSlice(0, 4)));
    }

    public CXCursor kind(CXCursorKind kind) {
        ptr.set(ValueLayout.JAVA_INT, 0, kind.value());
        return this;
    }

    public int xdata() {
        return ptr.get(ValueLayout.JAVA_INT, 4);
    }

    public CXCursor xdata(int xdata) {
        ptr.set(ValueLayout.JAVA_INT, 4, xdata);
        return this;
    }

    public Pointer<Pointer<?>> data() {
        return FunctionUtils.makePointer(ptr.asSlice(8, 24));
    }

    public CXCursor data(Pointer<Pointer<?>> data) {
        MemorySegment.copy(data.pointer(), 0, ptr, 8, Math.min(24, data.pointer().byteSize()));
        return this;
    }


    @Override
    public String toString() {
        if (MemorySegment.NULL.address() == ptr.address() || ptr.byteSize() < BYTE_SIZE)
            return "CXCursor{ptr=" + ptr;
        return "CXCursor{" +
                "kind=" + kind() +
                "xdata=" + xdata() +
                "data=" + data() + "}";
    }
}