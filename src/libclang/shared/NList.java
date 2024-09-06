package libclang.shared;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.util.function.Consumer;
import java.util.function.Function;

public class NList<E extends Pointer<?>> extends AbstractNativeList<E> {
    public NList(Pointer<E> ptr, Function<Pointer<E>, E> constructor, long elementByteSize) {
        super(ptr, constructor, elementByteSize);
    }

    public NList(Pointer<E> ptr, long length, Function<Pointer<E>, E> constructor, long elementByteSize) {
        super(ptr, length, constructor, elementByteSize);
    }

    public NList(SegmentAllocator allocator, long length, Function<Pointer<E>, E> constructor, long elementByteSize) {
        super(allocator, length, constructor, elementByteSize);
    }

    @Override
    public NList<E> reinterpretSize(long length) {
        return new NList<>(() -> ptr.reinterpret(length * elementByteSize), constructor, elementByteSize);
    }

    /**
     * @param index   index of the element to replace
     * @param element element to be stored at the specified position
     * @return the element
     */
    @Override
    public E set(int index, E element) {
        if (element.pointer().byteSize() != elementByteSize)
            throw new IllegalArgumentException("elementByteSize is " + elementByteSize + ", but element.pointer().byteSize() is " + element.pointer().byteSize());
        MemorySegment.copy(element.pointer(), 0, ptr, index * elementByteSize, elementByteSize);
        return element;
    }

    @Override
    public String toString() {
        return pointer().byteSize() % elementByteSize == 0 ? super.toString() : "NList{ptr: " + ptr;
    }
}