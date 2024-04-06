package libclang.shared.values;

import libclang.shared.Pointer;
import libclang.shared.Value;
import libclang.shared.VI8List;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.util.Collection;
import java.util.function.Consumer;

public class VI8<T> extends VI8Basic<T> {

    public static <T> VI8List<VI8<T>> list(Pointer<VI8<T>> ptr) {
        return new VI8List<>(ptr, VI8::new);
    }

    public static <T> VI8List<VI8<T>> list(Pointer<VI8<T>> ptr, long length) {
        return new VI8List<>(ptr, length, VI8::new);
    }

    public static <T> VI8List<VI8<T>> list(SegmentAllocator allocator, long length) {
        return new VI8List<>(allocator, length, VI8::new);
    }

    public static <T> VI8List<VI8<T>> list(SegmentAllocator allocator, VI8<T>[] c) {
        return new VI8List<>(allocator, c, VI8::new);
    }

    public static <T> VI8List<VI8<T>> list(SegmentAllocator allocator, Collection<VI8<T>> c) {
        return new VI8List<>(allocator, c, VI8::new);
    }

    public VI8(Pointer<? extends VI8<T>> ptr) {
        super(ptr);
    }

    public VI8(byte value) {
        super(value);
    }

    public VI8(Value<Byte> value) {
        super(value);
    }

    public VI8(VI8<T> value) {
        super(value);
    }
}
