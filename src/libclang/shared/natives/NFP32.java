package libclang.shared.natives;

import libclang.shared.Pointer;
import libclang.shared.Value;
import libclang.shared.values.VFP32;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.lang.foreign.ValueLayout;
import java.util.function.Consumer;

public class NFP32 implements Pointer<VFP32<Float>>, Value<Float> {
    public static final long BYTE_SIZE = ValueLayout.JAVA_FLOAT.byteSize();

    private final MemorySegment ptr;

    public NFP32(Pointer<VFP32<?>> ptr) {
        this.ptr = ptr.pointer();
    }

    public NFP32(SegmentAllocator allocator) {
        ptr = allocator.allocate(ValueLayout.JAVA_FLOAT);
    }

    public NFP32(SegmentAllocator allocator, float v) {
        ptr = allocator.allocateFrom(ValueLayout.JAVA_FLOAT, v);
    }

    public NFP32 reinterpretSize() {
        return new NFP32(() -> ptr.reinterpret(BYTE_SIZE));
    }

    @Override
    public Float value() {
        return get();
    }

    public float get() {
        return ptr.get(ValueLayout.JAVA_FLOAT, 0);
    }

    public NFP32 set(float value) {
        ptr.setAtIndex(ValueLayout.JAVA_FLOAT, 0, value);
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
                : "NFP32{ptr: " + ptr + "}";
    }
}
