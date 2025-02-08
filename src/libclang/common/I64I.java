package libclang.common;

import libclang.common.Value;


public interface I64I<I> extends Value<Long> {
    static <I> I64I<I> of(long value) {
        return new I64I<>() {
            @Override
            public ValueOp<Long> operator() {
                return () -> value;
            }

            @Override
            public String toString() {
                return String.valueOf(value);
            }
        };
    }

    static <I> I64I<I> of(I64I<?> value) {
        return of(value.operator().value());
    }
}
