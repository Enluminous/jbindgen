package libclang.common;

import libclang.common.Value;


public interface FP64I<I> extends Value<Double> {
    static <I> FP64I<I> of(double value) {
        return new FP64I<>() {
            @Override
            public ValueOp<Double> operator() {
                return () -> value;
            }

            @Override
            public String toString() {
                return String.valueOf(value);
            }
        };
    }

    static <I> FP64I<I> of(FP64I<?> value) {
        return of(value.operator().value());
    }
}
