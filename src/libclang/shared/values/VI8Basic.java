package libclang.shared.values;

import libclang.shared.Value;
import libclang.shared.Pointer;

import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;

public class VI8Basic<T> implements Value<Byte> {
    public static final MemoryLayout MEMORY_LAYOUT = ValueLayout.JAVA_BYTE;
    public static final long BYTE_SIZE = MEMORY_LAYOUT.byteSize();
    private final byte value;

    public VI8Basic(Pointer<? extends VI8Basic<T>> ptr) {
        this.value = ptr.pointer().get(ValueLayout.JAVA_BYTE, 0);
    }

    public VI8Basic(Byte value) {
        this.value = value;
    }

    public VI8Basic(Value<Byte> value) {
        this.value = value.value();
    }

    @Override
    public Byte value() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof VI8Basic<?> that && that.value().equals(value);
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