package libclang.shared;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.lang.foreign.ValueLayout;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;

public class NPtrList<T extends Pointer<?>> extends NList<T> {
    private final long[] elementsByteSize;

    protected NPtrList(MemorySegment ptr, Function<Pointer<T>, T> constructor, long elementByteSize) {
        super(() -> ptr, constructor, ValueLayout.ADDRESS.byteSize());
        this.elementsByteSize = new long[]{elementByteSize};
    }

    protected NPtrList(MemorySegment ptr, Function<Pointer<T>, T> constructor, long[] elementsByteSize) {
        super(() -> ptr, constructor, ValueLayout.ADDRESS.byteSize());
        this.elementsByteSize = elementsByteSize;
    }

    public NPtrList(Pointer<Pointer<T>> ptr, Function<Pointer<T>, T> constructor, long elementByteSize, long length) {
        this(ptr.pointer().reinterpret(length * ValueLayout.ADDRESS.byteSize()), constructor, elementByteSize);
    }

    public NPtrList(Pointer<Pointer<T>> ptr, Function<Pointer<T>, T> constructor, long[] elementsByteSize) {
        this(ptr.pointer().reinterpret(elementsByteSize.length * ValueLayout.ADDRESS.byteSize()), constructor, elementsByteSize);
    }

    public NPtrList(SegmentAllocator allocator, Function<Pointer<T>, T> constructor, T[] t) {
        this(allocator.allocate(ValueLayout.ADDRESS.byteSize() * t.length), constructor, Arrays.stream(t).mapToLong(v -> v.pointer().byteSize()).toArray());
        for (int i = 0; i < t.length; i++) {
            ptr.setAtIndex(ValueLayout.ADDRESS, i, t[i].pointer());
        }
    }

    public NPtrList(SegmentAllocator allocator, Function<Pointer<T>, T> constructor, Collection<T> t) {
        this(allocator.allocate(ValueLayout.ADDRESS.byteSize() * t.size()), constructor, t.stream().mapToLong(v -> v.pointer().byteSize()).toArray());
        int i = 0;
        for (T t1 : t) {
            ptr.setAtIndex(ValueLayout.ADDRESS, i, t1.pointer());
            i++;
        }
    }

    public NPtrList(Pointer<Pointer<T>> ptr, Function<Pointer<T>, T> constructor, long elementByteSize) {
        this(ptr.pointer(), constructor, elementByteSize);
    }


    @Override
    public T get(int index) {
        return constructor.apply(() -> ptr.getAtIndex(ValueLayout.ADDRESS, index).reinterpret(elementsByteSize.length > 1 ? elementsByteSize[index] : elementsByteSize[0]));
    }

    @Override
    public T set(int index, T value) {
        ptr.setAtIndex(ValueLayout.ADDRESS, index, value.pointer());
        return value;
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }
}