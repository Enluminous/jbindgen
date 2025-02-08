package libclang.common;

import java.lang.foreign.MemorySegment;
import libclang.common.Value;


public interface PtrI<I> extends Value<MemorySegment> {
    static <I> PtrI<I> of(MemorySegment value) {
        return new PtrI<>() {
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

    static <I> PtrI<I> of(PtrI<?> value) {
        return of(value.operator().value());
    }
}
