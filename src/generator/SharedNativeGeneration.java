package generator;

import java.nio.file.Path;

public class SharedNativeGeneration {
    private final String basePackageName;
    private final Path path;

    public SharedNativeGeneration(String basePackageName, Path path) {
        this.basePackageName = basePackageName;
        this.path = path;
    }

    public void gen() {
        genNList();
        genNstring();
        genNPtrList();

        genNative("NPointer", "VPointer", "MemorySegment", "ADDRESS");
        genNative("NI64", "VI64", "Long", "JAVA_LONG");
        genNative("NI32", "VI32", "Integer", "JAVA_INT");
        genNative("NI16", "VI16", "Short", "JAVA_SHORT");
        genNative("NI8", "VI8", "Byte", "JAVA_BYTE");

        genNative("NFP64", "VFP64", "Double", "JAVA_DOUBLE");
        genNative("NFP32", "VFP32", "Float", "JAVA_FLOAT");
    }

    private void genNative(String className, String valueType, String objType, String valueLayout) {
        Utils.write(path.resolve("natives/" + className + ".java"), """
                package %s.shared.natives;
                
                import %s.shared.Pointer;
                import %s.shared.Value;
                import %s.shared.values.%s;
                
                import java.lang.foreign.MemorySegment;
                import java.lang.foreign.SegmentAllocator;
                import java.lang.foreign.ValueLayout;
                import java.util.function.Consumer;
                
                public class %s implements Pointer<%s<%s>>, Value<%s> {
                    public static final long BYTE_SIZE = ValueLayout.ADDRESS.byteSize();
                
                    private final MemorySegment ptr;
                
                    public %s(Pointer<%s<?>> ptr) {
                        this.ptr = ptr.pointer();
                    }
                
                    public %s(SegmentAllocator allocator) {
                        ptr = allocator.allocate(ValueLayout.%s);
                    }
                
                    public %s(SegmentAllocator allocator, %s v) {
                        ptr = allocator.allocateFrom(ValueLayout.%s, v);
                    }
                
                    public NPointer reinterpretSize() {
                        return new NPointer(() -> ptr.reinterpret(BYTE_SIZE));
                    }
                
                    @Override
                    public %s value() {
                        return get();
                    }
                
                    public %s get() {
                        return ptr.get(ValueLayout.%s, 0);
                    }
                
                    public %s set(%s value) {
                        ptr.setAtIndex(ValueLayout.%s, 0, value);
                        return this;
                    }
                
                    @Override
                    public MemorySegment pointer() {
                        return ptr;
                    }
                
                    @Override
                    public String toString() {
                        return MemorySegment.NULL.address() != ptr.address() && ptr.byteSize() >= BYTE_SIZE
                                ? String.valueOf(get())
                                : "%s{ptr: " + ptr + "}";
                    }
                }
                """.formatted(basePackageName, basePackageName, basePackageName, basePackageName, valueType,// imports
                className, valueType, objType, objType,
                className, valueType,
                className, valueLayout,
                className, objType, valueLayout,
                objType,
                objType, valueLayout,
                className, objType, valueLayout,
                className));
    }

    private void genNList() {
        Utils.write(path.resolve("NList.java"), """
                package %s.shared;
                
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
                        return pointer().byteSize() %% elementByteSize == 0 ? super.toString() : "NList{ptr: " + ptr;
                    }
                }""".formatted(basePackageName));
    }

    private void genNPtrList() {
        Utils.write(path.resolve("NPtrList.java"), """
                package %s.shared;
                
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
                }""".formatted(basePackageName));
    }

    private void genNstring() {
        Utils.write(path.resolve("NString.java"), """
                package %s.shared;
                
                import %s.shared.values.VI8;
                
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
                                ? "NString{ptr=" + ptr + '}'
                                : ptr.getString(0, StandardCharsets.UTF_8);
                    }
                }
                """.formatted(basePackageName, basePackageName));
    }
}
