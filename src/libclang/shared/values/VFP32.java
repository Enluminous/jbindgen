package libclang.shared.values;

import libclang.shared.Pointer;
import libclang.shared.Value;
import libclang.shared.VFP32List;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.util.Collection;
import java.util.function.Consumer;

public class VFP32<T> extends VFP32Basic<T> {

    public static <T> VFP32List<VFP32<T>> list(Pointer<VFP32<T>> ptr) {
        return new VFP32List<>(ptr, VFP32::new);
    }

    public static <T> VFP32List<VFP32<T>> list(Pointer<VFP32<T>> ptr, long length) {
        return new VFP32List<>(ptr, length, VFP32::new);
    }

    public static <T> VFP32List<VFP32<T>> list(SegmentAllocator allocator, long length) {
        return new VFP32List<>(allocator, length, VFP32::new);
    }

    public static <T> VFP32List<VFP32<T>> list(SegmentAllocator allocator, VFP32<T>[] c) {
        return new VFP32List<>(allocator, c, VFP32::new);
    }

    public static <T> VFP32List<VFP32<T>> list(SegmentAllocator allocator, Collection<VFP32<T>> c) {
        return new VFP32List<>(allocator, c, VFP32::new);
    }

    public VFP32(Pointer<? extends VFP32<T>> ptr) {
        super(ptr);
    }

    public VFP32(float value) {
        super(value);
    }

    public VFP32(Value<Float> value) {
        super(value);
    }

    public VFP32(VFP32<T> value) {
        super(value);
    }
}
