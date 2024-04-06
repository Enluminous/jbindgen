package libclang.shared.values;

import libclang.shared.Value;
import libclang.shared.Pointer;

import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;

public class VI32Basic<T> implements Value<Integer> {
    public static final MemoryLayout MEMORY_LAYOUT = ValueLayout.JAVA_INT;
    public static final long BYTE_SIZE = MEMORY_LAYOUT.byteSize();
    private final int value;

    public VI32Basic(Pointer<? extends VI32Basic<T>> ptr) {
        this.value = ptr.pointer().get(ValueLayout.JAVA_INT, 0);
    }

    public VI32Basic(Integer value) {
        this.value = value;
    }

    public VI32Basic(Value<Integer> value) {
        this.value = value.value();
    }

    @Override
    public Integer value() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof VI32Basic<?> that && that.value().equals(value);
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