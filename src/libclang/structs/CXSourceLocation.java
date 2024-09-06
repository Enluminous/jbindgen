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


public final class CXSourceLocation implements Pointer<CXSourceLocation> {
    public static final MemoryLayout MEMORY_LAYOUT = MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE));
    public static final long BYTE_SIZE = MEMORY_LAYOUT.byteSize();

    public static NList<CXSourceLocation> list(Pointer<CXSourceLocation> ptr) {
        return new NList<>(ptr, CXSourceLocation::new, BYTE_SIZE);
    }

    public static NList<CXSourceLocation> list(Pointer<CXSourceLocation> ptr, long length) {
        return new NList<>(ptr, length, CXSourceLocation::new, BYTE_SIZE);
    }

    public static NList<CXSourceLocation> list(SegmentAllocator allocator, long length) {
        return new NList<>(allocator, length, CXSourceLocation::new, BYTE_SIZE);
    }

    private final MemorySegment ptr;

    public CXSourceLocation(Pointer<CXSourceLocation> ptr) {
        this.ptr = ptr.pointer();
    }

    public CXSourceLocation(SegmentAllocator allocator) {
        ptr = allocator.allocate(BYTE_SIZE);
    }

    public CXSourceLocation reinterpretSize() {
        return new CXSourceLocation(FunctionUtils.makePointer(ptr.reinterpret(BYTE_SIZE)));
    }

    @Override
    public MemorySegment pointer() {
        return ptr;
    }

    public Pointer<Pointer<?>> ptr_data() {
        return FunctionUtils.makePointer(ptr.asSlice(0, 16));
    }

    public CXSourceLocation ptr_data(Pointer<Pointer<?>> ptr_data) {
        MemorySegment.copy(ptr_data.pointer(), 0, ptr, 0, Math.min(16, ptr_data.pointer().byteSize()));
        return this;
    }

    public int int_data() {
        return ptr.get(ValueLayout.JAVA_INT, 16);
    }

    public CXSourceLocation int_data(int int_data) {
        ptr.set(ValueLayout.JAVA_INT, 16, int_data);
        return this;
    }


    @Override
    public String toString() {
        if (MemorySegment.NULL.address() == ptr.address() || ptr.byteSize() < BYTE_SIZE)
            return "CXSourceLocation{ptr=" + ptr;
        return "CXSourceLocation{" +
                "ptr_data=" + ptr_data() +
                "int_data=" + int_data() + "}";
    }
}