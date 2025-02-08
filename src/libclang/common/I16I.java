package libclang.common;

import libclang.common.Value;


public interface I16I<I> extends Value<Short> {
    static <I> I16I<I> of(short value) {
        return new I16I<>() {
            @Override
            public ValueOp<Short> operator() {
                return () -> value;
            }

            @Override
            public String toString() {
                return String.valueOf(value);
            }
        };
    }

    static <I> I16I<I> of(I16I<?> value) {
        return of(value.operator().value());
    }
}
