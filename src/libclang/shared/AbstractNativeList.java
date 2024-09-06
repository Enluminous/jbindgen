package libclang.shared;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class AbstractNativeList<T> extends AbstractList<T> implements RandomAccess, Pointer<T> {

    protected final MemorySegment ptr;
    protected final Function<Pointer<T>, T> constructor;
    protected final long elementByteSize;

    private void ensureValue(MemorySegment ptr, Function<Pointer<T>, T> constructor, long byteSize) {
        Objects.requireNonNull(ptr);
        Objects.requireNonNull(constructor);
        if (byteSize <= 0)
            throw new IllegalArgumentException("Illegal byteSize: " + byteSize);
    }

    public AbstractNativeList(Pointer<T> ptr, Function<Pointer<T>, T> constructor, long elementByteSize) {
        this(ptr.pointer(), constructor, elementByteSize);
    }

    protected AbstractNativeList(MemorySegment ptr, Function<Pointer<T>, T> constructor, long elementByteSize) {
        this.ptr = ptr;
        this.constructor = constructor;
        this.elementByteSize = elementByteSize;
        ensureValue(ptr, constructor, elementByteSize);
    }

    public AbstractNativeList(Pointer<T> ptr, long length, Function<Pointer<T>, T> constructor, long elementByteSize) {
        this(ptr.pointer().reinterpret(length * elementByteSize), constructor, elementByteSize);
    }

    public AbstractNativeList(SegmentAllocator allocator, long length, Function<Pointer<T>, T> constructor, long elementByteSize) {
        this(allocator.allocate(elementByteSize * length), constructor, elementByteSize);
    }

    @Override
    public T get(int index) {
        return constructor.apply(pointerAt(index));
    }

    public Pointer<T> pointerAt(long index) {
        return () -> ptr.asSlice(index * elementByteSize, elementByteSize);
    }

    @Override
    public int size() {
        return (int) (ptr.byteSize() / elementByteSize);
    }

    @Override
    public void clear() {
        ptr.fill((byte) 0);
    }

    public AbstractNativeList<T> reinterpretSize(long length) {
        return new AbstractNativeList<>(ptr.reinterpret(length * elementByteSize), constructor, elementByteSize);
    }

    @Override
    public AbstractNativeList<T> subList(int fromIndex, int toIndex) {
        subListRangeCheck(fromIndex, toIndex, size());
        return new AbstractNativeList<>(ptr.asSlice(fromIndex * elementByteSize, (toIndex - fromIndex) * elementByteSize), constructor, elementByteSize);
    }

    public long getElementByteSize() {
        return elementByteSize;
    }

    public Function<Pointer<T>, T> getConstructor() {
        return constructor;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof AbstractNativeList<?> nativeList) return nativeList.ptr.equals(ptr);
        return false;
    }

    @Override
    public int hashCode() {
        return ptr.hashCode();
    }

    @Override
    public MemorySegment pointer() {
        return ptr;
    }

    protected static void subListRangeCheck(int fromIndex, int toIndex, int size) {
        if (fromIndex < 0)
            throw new IndexOutOfBoundsException("fromIndex = " + fromIndex);
        if (toIndex > size)
            throw new IndexOutOfBoundsException("toIndex = " + toIndex);
        if (fromIndex > toIndex)
            throw new IllegalArgumentException("fromIndex(" + fromIndex + ") > toIndex(" + toIndex + ")");
    }
}