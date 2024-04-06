package libclang.shared.values;

import libclang.shared.Value;
import libclang.shared.Pointer;

import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;

public class VFP32Basic<T> implements Value<Float> {
    public static final MemoryLayout MEMORY_LAYOUT = ValueLayout.JAVA_FLOAT;
    public static final long BYTE_SIZE = MEMORY_LAYOUT.byteSize();
    private final float value;

    public VFP32Basic(Pointer<? extends VFP32Basic<T>> ptr) {
        this.value = ptr.pointer().get(ValueLayout.JAVA_FLOAT, 0);
    }

    public VFP32Basic(Float value) {
        this.value = value;
    }

    public VFP32Basic(Value<Float> value) {
        this.value = value.value();
    }

    @Override
    public Float value() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof VFP32Basic<?> that && that.value().equals(value);
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