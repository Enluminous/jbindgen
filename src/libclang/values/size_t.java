package libclang.values;


import libclang.shared.Pointer;
import libclang.shared.Value;
import libclang.shared.VI64List;
import libclang.shared.values.VI64Basic;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.util.Collection;
import java.util.function.Consumer;

public class size_t extends VI64Basic<size_t> {

    public static VI64List<size_t> list(Pointer<size_t> ptr) {
        return new VI64List<>(ptr, size_t::new);
    }

    public static VI64List<size_t> list(Pointer<size_t> ptr, long length) {
        return new VI64List<>(ptr, length, size_t::new);
    }

    public static VI64List<size_t> list(SegmentAllocator allocator, long length) {
        return new VI64List<>(allocator, length, size_t::new);
    }

    public static VI64List<size_t> list(SegmentAllocator allocator, size_t[] c) {
        return new VI64List<>(allocator, c, size_t::new);
    }

    public static VI64List<size_t> list(SegmentAllocator allocator, Collection<size_t> c) {
        return new VI64List<>(allocator, c, size_t::new);
    }

    public size_t(Pointer<size_t> ptr) {
        super(ptr);
    }

    public size_t(long value) {
        super(value);
    }

    public size_t(Value<Long> value) {
        super(value);
    }

    public size_t(size_t value) {
        super(value);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof size_t that && that.value().equals(value());
    }
}
