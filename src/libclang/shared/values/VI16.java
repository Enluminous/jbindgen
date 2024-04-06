package libclang.shared.values;

import libclang.shared.Pointer;
import libclang.shared.Value;
import libclang.shared.VI16List;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.util.Collection;
import java.util.function.Consumer;

public class VI16<T> extends VI16Basic<T> {

    public static <T> VI16List<VI16<T>> list(Pointer<VI16<T>> ptr) {
        return new VI16List<>(ptr, VI16::new);
    }

    public static <T> VI16List<VI16<T>> list(Pointer<VI16<T>> ptr, long length) {
        return new VI16List<>(ptr, length, VI16::new);
    }

    public static <T> VI16List<VI16<T>> list(SegmentAllocator allocator, long length) {
        return new VI16List<>(allocator, length, VI16::new);
    }

    public static <T> VI16List<VI16<T>> list(SegmentAllocator allocator, VI16<T>[] c) {
        return new VI16List<>(allocator, c, VI16::new);
    }

    public static <T> VI16List<VI16<T>> list(SegmentAllocator allocator, Collection<VI16<T>> c) {
        return new VI16List<>(allocator, c, VI16::new);
    }

    public VI16(Pointer<? extends VI16<T>> ptr) {
        super(ptr);
    }

    public VI16(short value) {
        super(value);
    }

    public VI16(Value<Short> value) {
        super(value);
    }

    public VI16(VI16<T> value) {
        super(value);
    }
}
