package libclang.common;


import java.lang.foreign.ValueLayout;
import libclang.common.FP32I;
import libclang.common.Info;
import libclang.common.MemoryUtils;
import libclang.common.Value;

import java.util.function.Function;

public interface FP32Op<T extends Info<T>> extends FP32I<T> {
    @Override
    FP32OpI<T> operator();

    interface FP32OpI<T> extends Info.InfoOp<T>, Value.ValueOp<Float> {

    }

    static <T extends FP32I<?>> Info.Operations<T> makeOperations(Function<Float, T> constructor) {
        return new Info.Operations<>(
                (param, offset) -> constructor.apply(MemoryUtils.getFloat(param, offset)),
                (source, dest, offset) -> MemoryUtils.setFloat(dest, offset, source.operator().value()),
                ValueLayout.JAVA_FLOAT.byteSize());
    }
}
