package libclang.shared.values;

import libclang.shared.Value;
import libclang.shared.Pointer;

import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;

public class VPointerBasic<T> implements Value<MemorySegment> {
    public static final MemoryLayout MEMORY_LAYOUT = ValueLayout.ADDRESS;
    public static final long BYTE_SIZE = MEMORY_LAYOUT.byteSize();
    private final MemorySegment value;

    public VPointerBasic(Pointer<? extends VPointerBasic<T>> ptr) {
        this.value = ptr.pointer().get(ValueLayout.ADDRESS, 0);
    }

    public VPointerBasic(MemorySegment value) {
        this.value = value;
    }

    public VPointerBasic(Value<MemorySegment> value) {
        this.value = value.value();
    }

    @Override
    public MemorySegment value() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof VPointerBasic<?> that && that.value().equals(value);
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