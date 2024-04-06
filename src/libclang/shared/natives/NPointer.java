package libclang.shared.natives;

import libclang.shared.Pointer;
import libclang.shared.Value;
import libclang.shared.values.VPointer;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.lang.foreign.ValueLayout;
import java.util.function.Consumer;

public class NPointer implements Pointer<VPointer<MemorySegment>>, Value<MemorySegment> {
    public static final long BYTE_SIZE = ValueLayout.ADDRESS.byteSize();

    private final MemorySegment ptr;

    public NPointer(Pointer<VPointer<?>> ptr) {
        this.ptr = ptr.pointer();
    }

    public NPointer(SegmentAllocator allocator) {
        ptr = allocator.allocate(ValueLayout.ADDRESS);
    }

    public NPointer(SegmentAllocator allocator, MemorySegment v) {
        ptr = allocator.allocateFrom(ValueLayout.ADDRESS, v);
    }

    public NPointer reinterpretSize() {
        return new NPointer(() -> ptr.reinterpret(BYTE_SIZE));
    }

    @Override
    public MemorySegment value() {
        return get();
    }

    public MemorySegment get() {
        return ptr.get(ValueLayout.ADDRESS, 0);
    }

    public NPointer set(MemorySegment value) {
        ptr.setAtIndex(ValueLayout.ADDRESS, 0, value);
        return this;
    }

    @Override
    public MemorySegment pointer() {
        return ptr;
    }

    @Override
    public String toString() {
        return MemorySegment.NULL.address() != ptr.address() && ptr.byteSize() >= BYTE_SIZE
                ? String.valueOf(get())
                : "NPointer{ptr: " + ptr + "}";
    }
}
