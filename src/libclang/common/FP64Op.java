package libclang.common;


import java.lang.foreign.ValueLayout;
import libclang.common.FP64I;
import libclang.common.Info;
import libclang.common.MemoryUtils;
import libclang.common.Value;

import java.util.function.Function;

public interface FP64Op<T extends Info<T>> extends FP64I<T> {
    @Override
    FP64OpI<T> operator();

    interface FP64OpI<T> extends Info.InfoOp<T>, Value.ValueOp<Double> {

    }

    static <T extends FP64I<?>> Info.Operations<T> makeOperations(Function<Double, T> constructor) {
        return new Info.Operations<>(
                (param, offset) -> constructor.apply(MemoryUtils.getDouble(param, offset)),
                (source, dest, offset) -> MemoryUtils.setDouble(dest, offset, source.operator().value()),
                ValueLayout.JAVA_DOUBLE.byteSize());
    }
}
