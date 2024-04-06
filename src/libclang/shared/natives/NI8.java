package libclang.shared.natives;

import libclang.shared.Pointer;
import libclang.shared.Value;
import libclang.shared.values.VI8;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.lang.foreign.ValueLayout;
import java.util.function.Consumer;

public class NI8 implements Pointer<VI8<Byte>>, Value<Byte> {
    public static final long BYTE_SIZE = ValueLayout.JAVA_BYTE.byteSize();

    private final MemorySegment ptr;

    public NI8(Pointer<VI8<?>> ptr) {
        this.ptr = ptr.pointer();
    }

    public NI8(SegmentAllocator allocator) {
        ptr = allocator.allocate(ValueLayout.JAVA_BYTE);
    }

    public NI8(SegmentAllocator allocator, byte v) {
        ptr = allocator.allocateFrom(ValueLayout.JAVA_BYTE, v);
    }

    public NI8 reinterpretSize() {
        return new NI8(() -> ptr.reinterpret(BYTE_SIZE));
    }

    @Override
    public Byte value() {
        return get();
    }

    public byte get() {
        return ptr.get(ValueLayout.JAVA_BYTE, 0);
    }

    public NI8 set(byte value) {
        ptr.setAtIndex(ValueLayout.JAVA_BYTE, 0, value);
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
                : "NI8{ptr: " + ptr + "}";
    }
}
