package libclang.common;


import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.lang.foreign.ValueLayout;

public class Ptr<E> implements PtrOp<Ptr<E>, E>, Info<Ptr<E>> {
    public static final long BYTE_SIZE = ValueLayout.ADDRESS.byteSize();

    public static <I> Info.Operations<Ptr<I>> makeOperations(Info.Operations<I> operation) {
        return new Info.Operations<>(
                (param, offset) -> new Ptr<>(param.get(ValueLayout.ADDRESS, offset), operation),
                (source, dest, offset) -> dest.set(ValueLayout.ADDRESS, offset, source.segment), BYTE_SIZE);
    }

    private final MemorySegment segment;
    private final Info.Operations<E> operation;

    private MemorySegment fitByteSize(MemorySegment segment) {
        return segment.byteSize() == operation.byteSize() ? segment : segment.reinterpret(operation.byteSize());
    }

    public Ptr(MemorySegment segment, Info.Operations<E> operation) {
        this.operation = operation;
        this.segment = fitByteSize(segment);
    }

    public Ptr(ArrayOp<?, E> arr) {
        this.operation = arr.operator().elementOperation();
        this.segment = fitByteSize(arr.operator().value());
    }

    public Ptr(Info.Operations<E> operation, PtrI<E> pointee) {
        this.operation = operation;
        this.segment = fitByteSize(pointee.operator().value());
    }


    public static <I> Array<Ptr<I>> list(SegmentAllocator allocator, int len, Info.Operations<Ptr<I>> operations) {
        return new Array<>(allocator, operations, len);
    }

    @Override
    public String toString() {
        return "Ptr{" +
                "segment=" + segment +
                '}';
    }

    public Info.Operations<E> getElementOperation() {
        return operation;
    }

    @Override
    public PtrOpI<Ptr<E>, E> operator() {
        return new PtrOpI<>() {
            @Override
            public Info.Operations<E> elementOperation() {
                return operation;
            }

            @Override
            public void setPointee(E pointee) {
                operation.copy().copyTo(pointee, segment, 0);
            }

            @Override
            public E pointee() {
                return operation.constructor().create(segment, 0);
            }

            @Override
            public Info.Operations<Ptr<E>> getOperations() {
                return makeOperations(operation);
            }

            @Override
            public MemorySegment value() {
                return segment;
            }
        };
    }
}
