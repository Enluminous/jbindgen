package libclang.shared.values;

import libclang.shared.Pointer;
import libclang.shared.Value;
import libclang.shared.VI32List;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.util.Collection;
import java.util.function.Consumer;

public class VI32<T> extends VI32Basic<T> {

    public static <T> VI32List<VI32<T>> list(Pointer<VI32<T>> ptr) {
        return new VI32List<>(ptr, VI32::new);
    }

    public static <T> VI32List<VI32<T>> list(Pointer<VI32<T>> ptr, long length) {
        return new VI32List<>(ptr, length, VI32::new);
    }

    public static <T> VI32List<VI32<T>> list(SegmentAllocator allocator, long length) {
        return new VI32List<>(allocator, length, VI32::new);
    }

    public static <T> VI32List<VI32<T>> list(SegmentAllocator allocator, VI32<T>[] c) {
        return new VI32List<>(allocator, c, VI32::new);
    }

    public static <T> VI32List<VI32<T>> list(SegmentAllocator allocator, Collection<VI32<T>> c) {
        return new VI32List<>(allocator, c, VI32::new);
    }

    public VI32(Pointer<? extends VI32<T>> ptr) {
        super(ptr);
    }

    public VI32(int value) {
        super(value);
    }

    public VI32(Value<Integer> value) {
        super(value);
    }

    public VI32(VI32<T> value) {
        super(value);
    }
}
