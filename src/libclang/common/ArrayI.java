package libclang.common;


import java.lang.foreign.MemorySegment;
import libclang.common.Value;


public interface ArrayI<E> extends Value<MemorySegment> {
    static <I> ArrayI<I> of(MemorySegment value) {
        return new ArrayI<>() {
            @Override
            public ValueOp<MemorySegment> operator() {
                return () -> value;
            }

            @Override
            public String toString() {
                return String.valueOf(value);
            }
        };
    }

    static <I> ArrayI<I> of(ArrayI<?> value) {
        return of(value.operator().value());
    }
}
