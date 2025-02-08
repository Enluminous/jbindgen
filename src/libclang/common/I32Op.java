package libclang.common;


import java.lang.foreign.ValueLayout;
import libclang.common.I32I;
import libclang.common.Info;
import libclang.common.MemoryUtils;
import libclang.common.Value;

import java.util.function.Function;

public interface I32Op<T extends Info<T>> extends I32I<T> {
    @Override
    I32OpI<T> operator();

    interface I32OpI<T> extends Info.InfoOp<T>, Value.ValueOp<Integer> {

    }

    static <T extends I32I<?>> Info.Operations<T> makeOperations(Function<Integer, T> constructor) {
        return new Info.Operations<>(
                (param, offset) -> constructor.apply(MemoryUtils.getInt(param, offset)),
                (source, dest, offset) -> MemoryUtils.setInt(dest, offset, source.operator().value()),
                ValueLayout.JAVA_INT.byteSize());
    }
}
