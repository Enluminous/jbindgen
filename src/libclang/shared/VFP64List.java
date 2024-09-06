package libclang.shared;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.lang.foreign.ValueLayout;
import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class VFP64List<T extends Value<Double>> extends AbstractNativeList<T> {
    public static final long ELEMENT_BYTE_SIZE = 8;

    protected VFP64List(MemorySegment ptr, Function<Pointer<T>, T> constructor) {
        super(ptr, constructor, ELEMENT_BYTE_SIZE);
    }

    public VFP64List(Pointer<T> ptr, Function<Pointer<T>, T> constructor) {
        super(ptr, constructor, ELEMENT_BYTE_SIZE);
    }

    public VFP64List(Pointer<T> ptr, long length, Function<Pointer<T>, T> constructor) {
        super(ptr, length, constructor, ELEMENT_BYTE_SIZE);
    }

    public VFP64List(SegmentAllocator allocator, long length, Function<Pointer<T>, T> constructor, BiConsumer<Long, VFP64List<T>> creator) {
        super(allocator.allocate(ELEMENT_BYTE_SIZE * length, 4), constructor, ELEMENT_BYTE_SIZE);
        for (int i = 0; i < length; i++) {
            creator.accept((long) i, subList(i, i));
        }
    }

    public VFP64List(SegmentAllocator allocator, long length, Function<Pointer<T>, T> constructor) {
        super(allocator.allocate(length * ELEMENT_BYTE_SIZE), constructor, ELEMENT_BYTE_SIZE);
    }

    public VFP64List(SegmentAllocator allocator, T[] objs, Function<Pointer<T>, T> constructor) {
        super(allocator.allocate(objs.length * ELEMENT_BYTE_SIZE), constructor, ELEMENT_BYTE_SIZE);
        long index = 0;
        for (T obj : objs) {
            ptr.setAtIndex(ValueLayout.JAVA_DOUBLE, index, obj.value());
            index++;
        }
    }

    public VFP64List(SegmentAllocator allocator, Collection<T> objs, Function<Pointer<T>, T> constructor) {
        super(allocator.allocate(objs.size() * ELEMENT_BYTE_SIZE), constructor, ELEMENT_BYTE_SIZE);
        long index = 0;
        for (T obj : objs) {
            ptr.setAtIndex(ValueLayout.JAVA_DOUBLE, index, obj.value());
            index++;
        }
    }

    public VFP64List(SegmentAllocator allocator, Double[] objs, Function<Pointer<T>, T> constructor) {
        super(allocator.allocate(objs.length * ELEMENT_BYTE_SIZE), constructor, ELEMENT_BYTE_SIZE);
        long index = 0;
        for (Double obj : objs) {
            ptr.setAtIndex(ValueLayout.JAVA_DOUBLE, index, obj);
            index++;
        }
    }

    @Override
    public T set(int index, T element) {
        ptr.setAtIndex(ValueLayout.JAVA_DOUBLE, index, element.value());
        return element;
    }

    public Double set(int index, Double element) {
        ptr.setAtIndex(ValueLayout.JAVA_DOUBLE, index, element);
        return element;
    }

    @Override
    public VFP64List<T> reinterpretSize(long length) {
        return new VFP64List<>(ptr.reinterpret(length * ELEMENT_BYTE_SIZE), constructor);
    }

    @Override
    public VFP64List<T> subList(int fromIndex, int toIndex) {
        subListRangeCheck(fromIndex, toIndex, size());
        return new VFP64List<>(ptr.asSlice(fromIndex * elementByteSize, (toIndex - fromIndex) * elementByteSize), constructor);
    }

    @Override
    public String toString() {
        return pointer().byteSize() % elementByteSize == 0 ? super.toString() : "VFP64List{ptr:" + ptr;
    }
}
