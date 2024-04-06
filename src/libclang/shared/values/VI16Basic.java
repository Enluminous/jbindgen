package libclang.shared.values;

import libclang.shared.Value;
import libclang.shared.Pointer;

import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;

public class VI16Basic<T> implements Value<Short> {
    public static final MemoryLayout MEMORY_LAYOUT = ValueLayout.JAVA_SHORT;
    public static final long BYTE_SIZE = MEMORY_LAYOUT.byteSize();
    private final short value;

    public VI16Basic(Pointer<? extends VI16Basic<T>> ptr) {
        this.value = ptr.pointer().get(ValueLayout.JAVA_SHORT, 0);
    }

    public VI16Basic(Short value) {
        this.value = value;
    }

    public VI16Basic(Value<Short> value) {
        this.value = value.value();
    }

    @Override
    public Short value() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof VI16Basic<?> that && that.value().equals(value);
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