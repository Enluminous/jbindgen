package libclang.shared.natives;

import libclang.shared.Pointer;
import libclang.shared.Value;
import libclang.shared.values.VI64;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.lang.foreign.ValueLayout;
import java.util.function.Consumer;

public class NI64 implements Pointer<VI64<Long>>, Value<Long> {
    public static final long BYTE_SIZE = ValueLayout.JAVA_LONG.byteSize();

    private final MemorySegment ptr;

    public NI64(Pointer<VI64<?>> ptr) {
        this.ptr = ptr.pointer();
    }

    public NI64(SegmentAllocator allocator) {
        ptr = allocator.allocate(ValueLayout.JAVA_LONG);
    }

    public NI64(SegmentAllocator allocator, long v) {
        ptr = allocator.allocateFrom(ValueLayout.JAVA_LONG, v);
    }

    public NI64 reinterpretSize() {
        return new NI64(() -> ptr.reinterpret(BYTE_SIZE));
    }

    @Override
    public Long value() {
        return get();
    }

    public long get() {
        return ptr.get(ValueLayout.JAVA_LONG, 0);
    }

    public NI64 set(long value) {
        ptr.setAtIndex(ValueLayout.JAVA_LONG, 0, value);
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
                : "NI64{ptr: " + ptr + "}";
    }
}
