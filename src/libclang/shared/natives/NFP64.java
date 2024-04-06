package libclang.shared.natives;

import libclang.shared.Pointer;
import libclang.shared.Value;
import libclang.shared.values.VFP64;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.lang.foreign.ValueLayout;
import java.util.function.Consumer;

public class NFP64 implements Pointer<VFP64<Double>>, Value<Double> {
    public static final long BYTE_SIZE = ValueLayout.JAVA_DOUBLE.byteSize();

    private final MemorySegment ptr;

    public NFP64(Pointer<VFP64<?>> ptr) {
        this.ptr = ptr.pointer();
    }

    public NFP64(SegmentAllocator allocator) {
        ptr = allocator.allocate(ValueLayout.JAVA_DOUBLE);
    }

    public NFP64(SegmentAllocator allocator, double v) {
        ptr = allocator.allocateFrom(ValueLayout.JAVA_DOUBLE, v);
    }

    public NFP64 reinterpretSize() {
        return new NFP64(() -> ptr.reinterpret(BYTE_SIZE));
    }

    @Override
    public Double value() {
        return get();
    }

    public double get() {
        return ptr.get(ValueLayout.JAVA_DOUBLE, 0);
    }

    public NFP64 set(double value) {
        ptr.setAtIndex(ValueLayout.JAVA_DOUBLE, 0, value);
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
                : "NFP64{ptr: " + ptr + "}";
    }
}
