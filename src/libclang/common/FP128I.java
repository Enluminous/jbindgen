package libclang.common;


import java.lang.foreign.MemorySegment;

public interface FP128I<I> {
    Value.ValueOp<MemorySegment> operator();
}
