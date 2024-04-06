package libclang.values;


import libclang.shared.Pointer;
import libclang.shared.Value;
import libclang.shared.VI64List;
import libclang.shared.values.VI64Basic;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.util.Collection;
import java.util.function.Consumer;

public class __time_t extends VI64Basic<__time_t> {

    public static VI64List<__time_t> list(Pointer<__time_t> ptr) {
        return new VI64List<>(ptr, __time_t::new);
    }

    public static VI64List<__time_t> list(Pointer<__time_t> ptr, long length) {
        return new VI64List<>(ptr, length, __time_t::new);
    }

    public static VI64List<__time_t> list(SegmentAllocator allocator, long length) {
        return new VI64List<>(allocator, length, __time_t::new);
    }

    public static VI64List<__time_t> list(SegmentAllocator allocator, __time_t[] c) {
        return new VI64List<>(allocator, c, __time_t::new);
    }

    public static VI64List<__time_t> list(SegmentAllocator allocator, Collection<__time_t> c) {
        return new VI64List<>(allocator, c, __time_t::new);
    }

    public __time_t(Pointer<__time_t> ptr) {
        super(ptr);
    }

    public __time_t(long value) {
        super(value);
    }

    public __time_t(Value<Long> value) {
        super(value);
    }

    public __time_t(__time_t value) {
        super(value);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof __time_t that && that.value().equals(value());
    }
}
