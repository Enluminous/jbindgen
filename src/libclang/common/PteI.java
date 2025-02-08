package libclang.common;


import java.lang.foreign.MemorySegment;
import libclang.common.Operation;
import libclang.common.Value;


public interface PteI<E> extends Operation {
    interface PointeeOp<E> extends Value.ValueOp<MemorySegment> {
        E pointee();
    }

    @Override
    PointeeOp<E> operator();
}