package libclang.shared.values;

import libclang.shared.Value;
import libclang.shared.Pointer;

import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;

public class VI64Basic<T> implements Value<Long> {
    public static final MemoryLayout MEMORY_LAYOUT = ValueLayout.JAVA_LONG;
    public static final long BYTE_SIZE = MEMORY_LAYOUT.byteSize();
    private final long value;

    public VI64Basic(Pointer<? extends VI64Basic<T>> ptr) {
        this.value = ptr.pointer().get(ValueLayout.JAVA_LONG, 0);
    }

    public VI64Basic(Long value) {
        this.value = value;
    }

    public VI64Basic(Value<Long> value) {
        this.value = value.value();
    }

    @Override
    public Long value() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof VI64Basic<?> that && that.value().equals(value);
    }

    @Override
    public int hashCode() {
        return value().hashCode();
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}