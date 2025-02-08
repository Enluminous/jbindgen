package libclang.common;


import java.lang.foreign.MemorySegment;
import libclang.common.Info;
import libclang.common.Ptr;
import libclang.common.StructI;
import libclang.common.Value;

import java.util.function.Function;

public interface StructOp<E extends Info<? extends E>> extends StructI<E> {

    @Override
    StructOpI<E> operator();

    interface StructOpI<E extends Info<? extends E>> extends Info.InfoOp<E>, Value.ValueOp<MemorySegment> {
        E reinterpret();

        Ptr<E> getPointer();
    }

    static <E extends StructOp<?>> Info.Operations<E> makeOperations(Function<MemorySegment, E> constructor, long byteSize) {
        return new Info.Operations<>(
                (param, offset) -> constructor.apply(param.asSlice(offset, byteSize)),
                (source, dest, offset) -> dest.asSlice(offset).copyFrom(source.operator().value()), byteSize);
    }
}