package libclang.common;


import java.lang.foreign.MemorySegment;

public interface FP128Op<T extends Info<T>> extends FP128I<T> {
    @Override
    FP128OpI<T> operator();

    interface FP128OpI<T> extends Info.InfoOp<T>, Value.ValueOp<MemorySegment> {

    }
}
