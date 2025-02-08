package libclang.common;

import libclang.common.Value;


public interface FP32I<I> extends Value<Float> {
    static <I> FP32I<I> of(float value) {
        return new FP32I<>() {
            @Override
            public ValueOp<Float> operator() {
                return () -> value;
            }

            @Override
            public String toString() {
                return String.valueOf(value);
            }
        };
    }

    static <I> FP32I<I> of(FP32I<?> value) {
        return of(value.operator().value());
    }
}
