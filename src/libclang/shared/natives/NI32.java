package libclang.shared.natives;

import libclang.shared.Pointer;
import libclang.shared.Value;
import libclang.shared.values.VI32;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.lang.foreign.ValueLayout;
import java.util.function.Consumer;

public class NI32 implements Pointer<VI32<Integer>>, Value<Integer> {
    public static final long BYTE_SIZE = ValueLayout.JAVA_INT.byteSize();

    private final MemorySegment ptr;

    public NI32(Pointer<VI32<?>> ptr) {
        this.ptr = ptr.pointer();
    }

    public NI32(SegmentAllocator allocator) {
        ptr = allocator.allocate(ValueLayout.JAVA_INT);
    }

    public NI32(SegmentAllocator allocator, int v) {
        ptr = allocator.allocateFrom(ValueLayout.JAVA_INT, v);
    }

    public NI32 reinterpretSize() {
        return new NI32(() -> ptr.reinterpret(BYTE_SIZE));
    }

    @Override
    public Integer value() {
        return get();
    }

    public int get() {
        return ptr.get(ValueLayout.JAVA_INT, 0);
    }

    public NI32 set(int value) {
        ptr.setAtIndex(ValueLayout.JAVA_INT, 0, value);
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
                : "NI32{ptr: " + ptr + "}";
    }
}
