package libclang.shared.natives;

import libclang.shared.Pointer;
import libclang.shared.Value;
import libclang.shared.values.VI16;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.lang.foreign.ValueLayout;
import java.util.function.Consumer;

public class NI16 implements Pointer<VI16<Short>>, Value<Short> {
    public static final long BYTE_SIZE = ValueLayout.JAVA_SHORT.byteSize();

    private final MemorySegment ptr;

    public NI16(Pointer<VI16<?>> ptr) {
        this.ptr = ptr.pointer();
    }

    public NI16(SegmentAllocator allocator) {
        ptr = allocator.allocate(ValueLayout.JAVA_SHORT);
    }

    public NI16(SegmentAllocator allocator, short v) {
        ptr = allocator.allocateFrom(ValueLayout.JAVA_SHORT, v);
    }

    public NI16 reinterpretSize() {
        return new NI16(() -> ptr.reinterpret(BYTE_SIZE));
    }

    @Override
    public Short value() {
        return get();
    }

    public short get() {
        return ptr.get(ValueLayout.JAVA_SHORT, 0);
    }

    public NI16 set(short value) {
        ptr.setAtIndex(ValueLayout.JAVA_SHORT, 0, value);
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
                : "NI16{ptr: " + ptr + "}";
    }
}
