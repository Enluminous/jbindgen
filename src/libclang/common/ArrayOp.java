package libclang.common;


import java.lang.foreign.MemorySegment;
import libclang.common.ArrayI;
import libclang.common.Info;
import libclang.common.Ptr;
import libclang.common.PtrOp;
import libclang.common.Value;

import java.util.AbstractList;
import java.util.List;
import java.util.RandomAccess;

public interface ArrayOp<A extends Info<A>, E> extends ArrayI<E>, PtrOp<A, E>, List<E> {
    interface ArrayOpI<A, E> extends Value.ValueOp<MemorySegment>, Info.InfoOp<A>, PtrOpI<A, E> {
        A reinterpret(long length);

        Ptr<E> pointerAt(long index);

        List<Ptr<E>> pointerList();
    }

    abstract class AbstractRandomAccessList<E> extends AbstractList<E> implements RandomAccess {
    }

    interface FixedArrayOp<A, E> extends ArrayOpI<A, E> {
        A reinterpret();
    }

    ArrayOpI<A, E> operator();
}