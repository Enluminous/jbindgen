package libclang.shared.values;

import libclang.shared.Value;
import libclang.shared.Pointer;

import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;

public class VFP64Basic<T> implements Value<Double> {
    public static final MemoryLayout MEMORY_LAYOUT = ValueLayout.JAVA_DOUBLE;
    public static final long BYTE_SIZE = MEMORY_LAYOUT.byteSize();
    private final double value;

    public VFP64Basic(Pointer<? extends VFP64Basic<T>> ptr) {
        this.value = ptr.pointer().get(ValueLayout.JAVA_DOUBLE, 0);
    }

    public VFP64Basic(Double value) {
        this.value = value;
    }

    public VFP64Basic(Value<Double> value) {
        this.value = value.value();
    }

    @Override
    public Double value() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof VFP64Basic<?> that && that.value().equals(value);
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