package libclang.shared.values;

import libclang.shared.Pointer;
import libclang.shared.Value;
import libclang.shared.VI64List;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.util.Collection;
import java.util.function.Consumer;

public class VI64<T> extends VI64Basic<T> {

    public static <T> VI64List<VI64<T>> list(Pointer<VI64<T>> ptr) {
        return new VI64List<>(ptr, VI64::new);
    }

    public static <T> VI64List<VI64<T>> list(Pointer<VI64<T>> ptr, long length) {
        return new VI64List<>(ptr, length, VI64::new);
    }

    public static <T> VI64List<VI64<T>> list(SegmentAllocator allocator, long length) {
        return new VI64List<>(allocator, length, VI64::new);
    }

    public static <T> VI64List<VI64<T>> list(SegmentAllocator allocator, VI64<T>[] c) {
        return new VI64List<>(allocator, c, VI64::new);
    }

    public static <T> VI64List<VI64<T>> list(SegmentAllocator allocator, Collection<VI64<T>> c) {
        return new VI64List<>(allocator, c, VI64::new);
    }

    public VI64(Pointer<? extends VI64<T>> ptr) {
        super(ptr);
    }

    public VI64(long value) {
        super(value);
    }

    public VI64(Value<Long> value) {
        super(value);
    }

    public VI64(VI64<T> value) {
        super(value);
    }
}
