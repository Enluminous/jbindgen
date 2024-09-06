package libclang.shared;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.lang.foreign.ValueLayout;
import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class VI32List<T extends Value<Integer>> extends AbstractNativeList<T> {
    public static final long ELEMENT_BYTE_SIZE = 4;

    protected VI32List(MemorySegment ptr, Function<Pointer<T>, T> constructor) {
        super(ptr, constructor, ELEMENT_BYTE_SIZE);
    }

    public VI32List(Pointer<T> ptr, Function<Pointer<T>, T> constructor) {
        super(ptr, constructor, ELEMENT_BYTE_SIZE);
    }

    public VI32List(Pointer<T> ptr, long length, Function<Pointer<T>, T> constructor) {
        super(ptr, length, constructor, ELEMENT_BYTE_SIZE);
    }

    public VI32List(SegmentAllocator allocator, long length, Function<Pointer<T>, T> constructor, BiConsumer<Long, VI32List<T>> creator) {
        super(allocator.allocate(ELEMENT_BYTE_SIZE * length, 4), constructor, ELEMENT_BYTE_SIZE);
        for (int i = 0; i < length; i++) {
            creator.accept((long) i, subList(i, i));
        }
    }

    public VI32List(SegmentAllocator allocator, long length, Function<Pointer<T>, T> constructor) {
        super(allocator.allocate(length * ELEMENT_BYTE_SIZE), constructor, ELEMENT_BYTE_SIZE);
    }

    public VI32List(SegmentAllocator allocator, T[] objs, Function<Pointer<T>, T> constructor) {
        super(allocator.allocate(objs.length * ELEMENT_BYTE_SIZE), constructor, ELEMENT_BYTE_SIZE);
        long index = 0;
        for (T obj : objs) {
            ptr.setAtIndex(ValueLayout.JAVA_INT, index, obj.value());
            index++;
        }
    }

    public VI32List(SegmentAllocator allocator, Collection<T> objs, Function<Pointer<T>, T> constructor) {
        super(allocator.allocate(objs.size() * ELEMENT_BYTE_SIZE), constructor, ELEMENT_BYTE_SIZE);
        long index = 0;
        for (T obj : objs) {
            ptr.setAtIndex(ValueLayout.JAVA_INT, index, obj.value());
            index++;
        }
    }

    public VI32List(SegmentAllocator allocator, Integer[] objs, Function<Pointer<T>, T> constructor) {
        super(allocator.allocate(objs.length * ELEMENT_BYTE_SIZE), constructor, ELEMENT_BYTE_SIZE);
        long index = 0;
        for (Integer obj : objs) {
            ptr.setAtIndex(ValueLayout.JAVA_INT, index, obj);
            index++;
        }
    }

    @Override
    public T set(int index, T element) {
        ptr.setAtIndex(ValueLayout.JAVA_INT, index, element.value());
        return element;
    }

    public Integer set(int index, Integer element) {
        ptr.setAtIndex(ValueLayout.JAVA_INT, index, element);
        return element;
    }

    @Override
    public VI32List<T> reinterpretSize(long length) {
        return new VI32List<>(ptr.reinterpret(length * ELEMENT_BYTE_SIZE), constructor);
    }

    @Override
    public VI32List<T> subList(int fromIndex, int toIndex) {
        subListRangeCheck(fromIndex, toIndex, size());
        return new VI32List<>(ptr.asSlice(fromIndex * elementByteSize, (toIndex - fromIndex) * elementByteSize), constructor);
    }

    @Override
    public String toString() {
        return pointer().byteSize() % elementByteSize == 0 ? super.toString() : "VI32List{ptr:" + ptr;
    }
}
