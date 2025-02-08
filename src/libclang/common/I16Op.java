package libclang.common;


import java.lang.foreign.ValueLayout;
import libclang.common.I16I;
import libclang.common.Info;
import libclang.common.MemoryUtils;
import libclang.common.Value;

import java.util.function.Function;

public interface I16Op<T extends Info<T>> extends I16I<T> {
    @Override
    I16OpI<T> operator();

    interface I16OpI<T> extends Info.InfoOp<T>, Value.ValueOp<Short> {

    }

    static <T extends I16I<?>> Info.Operations<T> makeOperations(Function<Short, T> constructor) {
        return new Info.Operations<>(
                (param, offset) -> constructor.apply(MemoryUtils.getShort(param, offset)),
                (source, dest, offset) -> MemoryUtils.setShort(dest, offset, source.operator().value()),
                ValueLayout.JAVA_SHORT.byteSize());
    }
}
