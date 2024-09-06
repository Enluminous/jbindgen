package libclang.shared;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.lang.foreign.ValueLayout;
import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class VPointerList<T extends Value<MemorySegment>> extends AbstractNativeList<T> {
    public static final long ELEMENT_BYTE_SIZE = 8;

    protected VPointerList(MemorySegment ptr, Function<Pointer<T>, T> constructor) {
        super(ptr, constructor, ELEMENT_BYTE_SIZE);
    }

    public VPointerList(Pointer<T> ptr, Function<Pointer<T>, T> constructor) {
        super(ptr, constructor, ELEMENT_BYTE_SIZE);
    }

    public VPointerList(Pointer<T> ptr, long length, Function<Pointer<T>, T> constructor) {
        super(ptr, length, constructor, ELEMENT_BYTE_SIZE);
    }

    public VPointerList(SegmentAllocator allocator, long length, Function<Pointer<T>, T> constructor, BiConsumer<Long, VPointerList<T>> creator) {
        super(allocator.allocate(ELEMENT_BYTE_SIZE * length, 4), constructor, ELEMENT_BYTE_SIZE);
        for (int i = 0; i < length; i++) {
            creator.accept((long) i, subList(i, i));
        }
    }

    public VPointerList(SegmentAllocator allocator, long length, Function<Pointer<T>, T> constructor) {
        super(allocator.allocate(length * ELEMENT_BYTE_SIZE), constructor, ELEMENT_BYTE_SIZE);
    }

    public VPointerList(SegmentAllocator allocator, T[] objs, Function<Pointer<T>, T> constructor) {
        super(allocator.allocate(objs.length * ELEMENT_BYTE_SIZE), constructor, ELEMENT_BYTE_SIZE);
        long index = 0;
        for (T obj : objs) {
            ptr.setAtIndex(ValueLayout.ADDRESS, index, obj.value());
            index++;
        }
    }

    public VPointerList(SegmentAllocator allocator, Collection<T> objs, Function<Pointer<T>, T> constructor) {
        super(allocator.allocate(objs.size() * ELEMENT_BYTE_SIZE), constructor, ELEMENT_BYTE_SIZE);
        long index = 0;
        for (T obj : objs) {
            ptr.setAtIndex(ValueLayout.ADDRESS, index, obj.value());
            index++;
        }
    }

    public VPointerList(SegmentAllocator allocator, MemorySegment[] objs, Function<Pointer<T>, T> constructor) {
        super(allocator.allocate(objs.length * ELEMENT_BYTE_SIZE), constructor, ELEMENT_BYTE_SIZE);
        long index = 0;
        for (MemorySegment obj : objs) {
            ptr.setAtIndex(ValueLayout.ADDRESS, index, obj);
            index++;
        }
    }

    @Override
    public T set(int index, T element) {
        ptr.setAtIndex(ValueLayout.ADDRESS, index, element.value());
        return element;
    }

    public MemorySegment set(int index, MemorySegment element) {
        ptr.setAtIndex(ValueLayout.ADDRESS, index, element);
        return element;
    }

    @Override
    public VPointerList<T> reinterpretSize(long length) {
        return new VPointerList<>(ptr.reinterpret(length * ELEMENT_BYTE_SIZE), constructor);
    }

    @Override
    public VPointerList<T> subList(int fromIndex, int toIndex) {
        subListRangeCheck(fromIndex, toIndex, size());
        return new VPointerList<>(ptr.asSlice(fromIndex * elementByteSize, (toIndex - fromIndex) * elementByteSize), constructor);
    }

    @Override
    public String toString() {
        return pointer().byteSize() % elementByteSize == 0 ? super.toString() : "VPointerList{ptr:" + ptr;
    }
}
