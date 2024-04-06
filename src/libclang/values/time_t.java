package libclang.values;


import libclang.shared.Pointer;
import libclang.shared.Value;
import libclang.shared.VI64List;
import libclang.shared.values.VI64Basic;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.util.Collection;
import java.util.function.Consumer;

public class time_t extends VI64Basic<time_t> {

    public static VI64List<time_t> list(Pointer<time_t> ptr) {
        return new VI64List<>(ptr, time_t::new);
    }

    public static VI64List<time_t> list(Pointer<time_t> ptr, long length) {
        return new VI64List<>(ptr, length, time_t::new);
    }

    public static VI64List<time_t> list(SegmentAllocator allocator, long length) {
        return new VI64List<>(allocator, length, time_t::new);
    }

    public static VI64List<time_t> list(SegmentAllocator allocator, time_t[] c) {
        return new VI64List<>(allocator, c, time_t::new);
    }

    public static VI64List<time_t> list(SegmentAllocator allocator, Collection<time_t> c) {
        return new VI64List<>(allocator, c, time_t::new);
    }

    public time_t(Pointer<time_t> ptr) {
        super(ptr);
    }

    public time_t(long value) {
        super(value);
    }

    public time_t(Value<Long> value) {
        super(value);
    }

    public time_t(time_t value) {
        super(value);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof time_t that && that.value().equals(value());
    }
}
