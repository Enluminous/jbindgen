package libclang.common;


import java.lang.foreign.ValueLayout;
import libclang.common.I64I;
import libclang.common.Info;
import libclang.common.MemoryUtils;
import libclang.common.Value;

import java.util.function.Function;

public interface I64Op<T extends Info<T>> extends I64I<T> {
    @Override
    I64OpI<T> operator();

    interface I64OpI<T> extends Info.InfoOp<T>, Value.ValueOp<Long> {

    }

    static <T extends I64I<?>> Info.Operations<T> makeOperations(Function<Long, T> constructor) {
        return new Info.Operations<>(
                (param, offset) -> constructor.apply(MemoryUtils.getLong(param, offset)),
                (source, dest, offset) -> MemoryUtils.setLong(dest, offset, source.operator().value()),
                ValueLayout.JAVA_LONG.byteSize());
    }
}
