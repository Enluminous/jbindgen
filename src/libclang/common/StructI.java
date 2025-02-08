package libclang.common;


import java.lang.foreign.MemorySegment;
import libclang.common.Value;


public interface StructI<E> extends Value<MemorySegment> {
    static <I> StructI<I> of(MemorySegment value) {
        return new StructI<>() {
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

    static <I> StructI<I> of(StructI<?> value) {
        return of(value.operator().value());
    }
}
