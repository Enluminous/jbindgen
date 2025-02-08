package libclang.common;

import libclang.common.Value;


public interface I8I<I> extends Value<Byte> {
    static <I> I8I<I> of(byte value) {
        return new I8I<>() {
            @Override
            public ValueOp<Byte> operator() {
                return () -> value;
            }

            @Override
            public String toString() {
                return String.valueOf(value);
            }
        };
    }

    static <I> I8I<I> of(I8I<?> value) {
        return of(value.operator().value());
    }
}
