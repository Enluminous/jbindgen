package libclang.common;


import java.lang.foreign.ValueLayout;
import libclang.common.Info;
import libclang.common.MemoryUtils;
import libclang.common.PteI;
import libclang.common.PtrI;
import libclang.common.Value;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.util.function.Function;

public interface PtrOp<S extends Info<S>, E> extends PtrI<E>, PteI<E> {
    @Override
    PtrOpI<S, E> operator();

    interface PtrOpI<S, E> extends Info.InfoOp<S>, Value.ValueOp<MemorySegment>, PointeeOp<E> {
        Info.Operations<E> elementOperation();

        void setPointee(E pointee);
    }

    static <T extends PtrI<?>> Info.Operations<T> makeOperations(Function<MemorySegment, T> constructor) {
        return new Info.Operations<>(
                    (param, offset) -> constructor.apply(param.get(ValueLayout.ADDRESS, offset)),
                    (source, dest, offset) -> dest.set(ValueLayout.ADDRESS, offset, source.operator().value()),
                    ValueLayout.ADDRESS.byteSize());
    }
}
