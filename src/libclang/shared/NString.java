package libclang.shared;

import libclang.shared.values.VI8;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.lang.foreign.ValueLayout;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.function.Consumer;

public class NString extends VI8List<VI8<Byte>> implements Value<String> {
    public static class NativeStringList extends NList<NString> {
        private static final long ELEMENT_BYTE_SIZE = ValueLayout.JAVA_LONG.byteSize();

        public NativeStringList(Pointer<Pointer<VI8<Byte>>> ptr) {
            super(ptr::pointer, nStringPointer -> new NString(nStringPointer::pointer), ELEMENT_BYTE_SIZE);
        }

        protected NativeStringList(MemorySegment ptr) {
            this(() -> ptr);
        }

        public NativeStringList(Pointer<Pointer<VI8<Byte>>> ptr, long length) {
            this(ptr.pointer().reinterpret(length * ELEMENT_BYTE_SIZE));
        }

        public NativeStringList(SegmentAllocator allocator, String[] strings) {
            this(allocator.allocate(ValueLayout.ADDRESS, strings.length));
            int i = 0;
            for (String string : strings) {
                ptr.setAtIndex(ValueLayout.ADDRESS, i, allocator.allocateFrom(string));
                i++;
            }
        }

        public NativeStringList(SegmentAllocator allocator, Collection<String> strings) {
            this(allocator.allocate(ValueLayout.ADDRESS, strings.size()));
            int i = 0;
            for (String string : strings) {
                ptr.setAtIndex(ValueLayout.ADDRESS, i, allocator.allocateFrom(string));
                i++;
            }
        }

        @Override
        public NativeStringList subList(int fromIndex, int toIndex) {
            subListRangeCheck(fromIndex, toIndex, size());
            return new NativeStringList(super.pointer().asSlice(fromIndex * ELEMENT_BYTE_SIZE, (toIndex - fromIndex) * ELEMENT_BYTE_SIZE));
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        public NString get(int index) {
            MemorySegment segment = super.pointer().getAtIndex(ValueLayout.ADDRESS, index).reinterpret(Integer.MAX_VALUE);
            return constructor.apply(() -> segment);
        }

        @Override
        public NString set(int index, NString element) {
            ptr.setAtIndex(ValueLayout.ADDRESS, index, element.pointer());
            return element;
        }
    }

    public static NList<NString> list(Pointer<Pointer<VI8<Byte>>> ptr, long length) {
        return new NativeStringList(ptr, length);
    }

    public static NList<NString> list(SegmentAllocator allocator, String[] strings) {
        MemorySegment strPtr = allocator.allocate(ValueLayout.ADDRESS, strings.length);
        int i = 0;
        for (String string : strings) {
            strPtr.setAtIndex(ValueLayout.ADDRESS, i, allocator.allocateFrom(string));
            i++;
        }
        return new NativeStringList(strPtr);
    }

    public static NList<NString> list(SegmentAllocator allocator, Collection<String> strings) {
        MemorySegment strPtr = allocator.allocate(ValueLayout.ADDRESS, strings.size());
        int i = 0;
        for (String string : strings) {
            strPtr.setAtIndex(ValueLayout.ADDRESS, i, allocator.allocateFrom(string));
            i++;
        }
        return new NativeStringList(strPtr);
    }

    public NString(Pointer<VI8<Byte>> ptr) {
        super(ptr, VI8::new);
    }

    protected NString(MemorySegment ptr) {
        super(ptr, VI8::new);
    }

    public NString(Pointer<VI8<Byte>> ptr, long byteSize) {
        this(ptr.pointer().reinterpret(byteSize));
    }

    public NString(SegmentAllocator allocator, String s) {
        this(allocator.allocateFrom(s, StandardCharsets.UTF_8));
    }

    public NString reinterpretSize(long byteSize) {
        return new NString(() -> ptr.reinterpret(byteSize));
    }

    @Override
    public String value() {
        return get();
    }

    public String get() {
        return MemorySegment.NULL.address() == ptr.address() ? null : toString();
    }

    public NString set(String value) {
        ptr.setString(0, value, StandardCharsets.UTF_8);
        return this;
    }

    @Override
    public String toString() {
        return MemorySegment.NULL.address() == ptr.address()
                ? STR."NString{ptr=\{ptr}\{'}'}"
                : ptr.getString(0, StandardCharsets.UTF_8);
    }
}