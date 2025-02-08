package libclang.common;


import java.lang.foreign.ValueLayout;
import libclang.common.I8I;
import libclang.common.Info;
import libclang.common.MemoryUtils;
import libclang.common.Value;

import java.util.function.Function;

public interface I8Op<T extends Info<T>> extends I8I<T> {
    @Override
    I8OpI<T> operator();

    interface I8OpI<T> extends Info.InfoOp<T>, Value.ValueOp<Byte> {

    }

    static <T extends I8I<?>> Info.Operations<T> makeOperations(Function<Byte, T> constructor) {
        return new Info.Operations<>(
                (param, offset) -> constructor.apply(MemoryUtils.getByte(param, offset)),
                (source, dest, offset) -> MemoryUtils.setByte(dest, offset, source.operator().value()),
                ValueLayout.JAVA_BYTE.byteSize());
    }
}
