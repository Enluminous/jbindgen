package libclang.shared.values;

import libclang.shared.Pointer;
import libclang.shared.Value;
import libclang.shared.VFP64List;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.util.Collection;
import java.util.function.Consumer;

public class VFP64<T> extends VFP64Basic<T> {

    public static <T> VFP64List<VFP64<T>> list(Pointer<VFP64<T>> ptr) {
        return new VFP64List<>(ptr, VFP64::new);
    }

    public static <T> VFP64List<VFP64<T>> list(Pointer<VFP64<T>> ptr, long length) {
        return new VFP64List<>(ptr, length, VFP64::new);
    }

    public static <T> VFP64List<VFP64<T>> list(SegmentAllocator allocator, long length) {
        return new VFP64List<>(allocator, length, VFP64::new);
    }

    public static <T> VFP64List<VFP64<T>> list(SegmentAllocator allocator, VFP64<T>[] c) {
        return new VFP64List<>(allocator, c, VFP64::new);
    }

    public static <T> VFP64List<VFP64<T>> list(SegmentAllocator allocator, Collection<VFP64<T>> c) {
        return new VFP64List<>(allocator, c, VFP64::new);
    }

    public VFP64(Pointer<? extends VFP64<T>> ptr) {
        super(ptr);
    }

    public VFP64(double value) {
        super(value);
    }

    public VFP64(Value<Double> value) {
        super(value);
    }

    public VFP64(VFP64<T> value) {
        super(value);
    }
}
