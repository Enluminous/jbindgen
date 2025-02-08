package libclang.common;


import libclang.common.Operation;


public interface Value<T> extends Operation {
    interface ValueOp<T> {
        T value();
    }

    @Override
    ValueOp<T> operator();
}
