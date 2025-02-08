package libclang.common;


import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.lang.foreign.ValueLayout;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class Array<E> extends ArrayOp.AbstractRandomAccessList<E> implements ArrayOp<Array<E>, E>, Info<Array<E>> {
    public static final long BYTE_SIZE = ValueLayout.ADDRESS.byteSize();

    public static <I> Info.Operations<Array<I>> makeOperations(Operations<I> operation, long len) {
        return new Info.Operations<>((param, offset) -> new Array<>(MemoryUtils.getAddr(param, offset).reinterpret(len * operation.byteSize()),
                operation), (source, dest, offset) -> MemoryUtils.setAddr(dest, offset, source.ptr), BYTE_SIZE);
    }

    public static <I> Info.Operations<Array<I>> makeOperations(Operations<I> operation) {
        return new Info.Operations<>((param, offset) -> new Array<>(MemoryUtils.getAddr(param, offset),
                operation), (source, dest, offset) -> MemoryUtils.setAddr(dest, offset, source.ptr), BYTE_SIZE);
    }

    protected final MemorySegment ptr;
    protected final Info.Operations<E> operations;

    public Array(PtrI<E> ptr, Info<E> info) {
        this(ptr, info.operator().getOperations());
    }

    public Array(Ptr<E> ptr) {
        this(ptr, ptr.operator().elementOperation());
    }

    public Array(PtrI<E> ptr, Info.Operations<E> operations) {
        this.ptr = ptr.operator().value();
        this.operations = operations;
    }

    public Array(PtrOp<?, E> ptr) {
        this.ptr = ptr.operator().value();
        this.operations = ptr.operator().elementOperation();
    }

    public Array(MemorySegment ptr, Info.Operations<E> operations) {
        this.ptr = ptr;
        this.operations = operations;
    }

    public Array(SegmentAllocator allocator, Info<E> info, Collection<E> elements) {
        this(allocator, info.operator().getOperations(), elements);
    }

    public Array(SegmentAllocator allocator, Info.Operations<E> operations, Collection<E> elements) {
        this.operations = operations;
        this.ptr = allocator.allocate(elements.size() * operations.byteSize());
        int i = 0;
        for (E element : elements) {
            operations.copy().copyTo(element, ptr, operations.byteSize() * i);
            i++;
        }
    }

    public Array(SegmentAllocator allocator, Info<E> info, long len) {
        this(allocator, info.operator().getOperations(), len);
    }

    public Array(SegmentAllocator allocator, Info.Operations<E> operations, long len) {
        this.operations = operations;
        this.ptr = allocator.allocate(len * operations.byteSize());
    }

    public E get(int index) {
        Objects.checkIndex(index, size());
        return operations.constructor().create(ptr, index * operations.byteSize());
    }

    @Override
    public E set(int index, E element) {
        Objects.checkIndex(index, size());
        operations.copy().copyTo(element, ptr, index * operations.byteSize());
        return element;
    }

    @Override
    public ArrayOpI<Array<E>, E> operator() {
        return new ArrayOpI<>() {
            @Override
            public Info.Operations<E> elementOperation() {
                return operations;
            }

            @Override
            public void setPointee(E pointee) {
                set(0, pointee);
            }

            @Override
            public Array<E> reinterpret(long length) {
                return new Array<>(ptr.reinterpret(length * operations.byteSize()), operations);
            }

            @Override
            public Ptr<E> pointerAt(long index) {
                Objects.checkIndex(index, size());
                return new Ptr<>(ptr.asSlice(index * operations.byteSize(), operations.byteSize()), operations);
            }

            @Override
            public List<Ptr<E>> pointerList() {
                return new ArrayOp.AbstractRandomAccessList<>() {
                    @Override
                    public Ptr<E> get(int index) {
                        return pointerAt(index);
                    }

                    @Override
                    public int size() {
                        return Array.this.size();
                    }
                };
            }

            @Override
            public Info.Operations<Array<E>> getOperations() {
                return makeOperations(operations, ptr.byteSize());
            }

            @Override
            public E pointee() {
                return get(0);
            }

            @Override
            public MemorySegment value() {
                return ptr;
            }
        };
    }

    public Ptr<E> pointerAt(long index) {
        return operator().pointerAt(index);
    }

    public List<Ptr<E>> pointerList() {
        return operator().pointerList();
    }

    @Override
    public int size() {
        return (int) (ptr.byteSize() / operations.byteSize());
    }
}
