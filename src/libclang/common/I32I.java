package libclang.common;

import libclang.common.Value;


public interface I32I<I> extends Value<Integer> {
    static <I> I32I<I> of(int value) {
        return new I32I<>() {
            @Override
            public ValueOp<Integer> operator() {
                return () -> value;
            }

            @Override
            public String toString() {
                return String.valueOf(value);
            }
        };
    }

    static <I> I32I<I> of(I32I<?> value) {
        return of(value.operator().value());
    }
}
