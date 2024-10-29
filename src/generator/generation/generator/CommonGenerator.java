package generator.generation.generator;

import generator.Dependency;
import generator.TypePkg;
import generator.Utils;
import generator.PackagePath;
import generator.generation.Common;
import generator.types.CommonTypes;

import java.util.Set;


public class CommonGenerator implements Generator {
    private final Common common;
    private final Dependency dependency;

    public CommonGenerator(Common common, Dependency dependency) {
        this.common = common;
        this.dependency = dependency;
    }

    @Override
    public void generate() {
        for (TypePkg<CommonTypes.BaseType> implType : common.getImplTypes()) {
            switch (implType.type()) {
                case CommonTypes.BindTypes bindTypes -> {

                }
                case CommonTypes.ListTypes listTypes -> {
                }
                case CommonTypes.SpecificTypes specificTypes -> {
                }
                case CommonTypes.Primitives primitives -> {
                    // no op
                }
                case CommonTypes.FFMTypes ffmTypes -> {
                }
            }
        }
    }

    private static void genNative(PackagePath path, String className, String valueType, String objType, String valueLayout) {
        Utils.write(path.getFilePath(), """
                package %1$s.shared.natives;
                
                import %1$s.shared.Pointer;
                import %1$s.shared.Value;
                import %1$s.shared.values.%2$s;
                
                import java.lang.foreign.MemorySegment;
                import java.lang.foreign.SegmentAllocator;
                import java.lang.foreign.ValueLayout;
                import java.util.function.Consumer;
                
                public class %3$s implements Pointer<%2$s<%4$s>>, Value<%4$s> {
                    public static final long BYTE_SIZE = ValueLayout.ADDRESS.byteSize();
                
                    private final MemorySegment ptr;
                
                    public %3$s(Pointer<%2$s<?>> ptr) {
                        this.ptr = ptr.pointer();
                    }
                
                    public %3$s(SegmentAllocator allocator) {
                        ptr = allocator.allocate(ValueLayout.%5$s);
                    }
                
                    public %3$s(SegmentAllocator allocator, %4$s v) {
                        ptr = allocator.allocateFrom(ValueLayout.%5$s, v);
                    }
                
                    public NPointer reinterpretSize() {
                        return new NPointer(() -> ptr.reinterpret(BYTE_SIZE));
                    }
                
                    @Override
                    public %4$s value() {
                        return get();
                    }
                
                    public %4$s get() {
                        return ptr.get(ValueLayout.%5$s, 0);
                    }
                
                    public %3$s set(%4$s value) {
                        ptr.setAtIndex(ValueLayout.%5$s, 0, value);
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
                """.formatted(path.makePackage(), valueType, className, objType, valueLayout));
    }

    private void genNList(PackagePath path) {
        Utils.write(path.getFilePath(), """
                %s
                
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
                }""".formatted(path.makePackage()));
    }

    private void genNPtrList(PackagePath path) {
        Utils.write(path.getFilePath(), """
                
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
                }""".formatted(path.makePackage()));
    }

    private void genNstring(PackagePath packagePath) {
        Utils.write(packagePath.getFilePath(), """
                %s
                
                %s
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
                """.formatted(packagePath.makePackage(), dependency.getTypeImports(Set.of(CommonTypes.SpecificTypes.NList))));
    }

    private void genVList(PackagePath path, String className, String genericValue, long byteSize, String valueLayout) {
        Utils.write(path.getFilePath(), """
                %1$s
                
                import java.lang.foreign.MemorySegment;
                import java.lang.foreign.SegmentAllocator;
                import java.lang.foreign.ValueLayout;
                import java.util.Collection;
                import java.util.function.BiConsumer;
                import java.util.function.Consumer;
                import java.util.function.Function;
                
                public class %2$s<T extends Value<%5$s>> extends AbstractNativeList<T> {
                    public static final long ELEMENT_BYTE_SIZE = %3$s;
                
                    protected %2$s(MemorySegment ptr, Function<Pointer<T>, T> constructor) {
                        super(ptr, constructor, ELEMENT_BYTE_SIZE);
                    }
                
                    public %2$s(Pointer<T> ptr, Function<Pointer<T>, T> constructor) {
                        super(ptr, constructor, ELEMENT_BYTE_SIZE);
                    }
                
                    public %2$s(Pointer<T> ptr, long length, Function<Pointer<T>, T> constructor) {
                        super(ptr, length, constructor, ELEMENT_BYTE_SIZE);
                    }
                
                    public %2$s(SegmentAllocator allocator, long length, Function<Pointer<T>, T> constructor, BiConsumer<Long, %2$s<T>> creator) {
                        super(allocator.allocate(ELEMENT_BYTE_SIZE * length, 4), constructor, ELEMENT_BYTE_SIZE);
                        for (int i = 0; i < length; i++) {
                            creator.accept((long) i, subList(i, i));
                        }
                    }
                
                    public %2$s(SegmentAllocator allocator, long length, Function<Pointer<T>, T> constructor) {
                        super(allocator.allocate(length * ELEMENT_BYTE_SIZE), constructor, ELEMENT_BYTE_SIZE);
                    }
                
                    public %2$s(SegmentAllocator allocator, T[] objs, Function<Pointer<T>, T> constructor) {
                        super(allocator.allocate(objs.length * ELEMENT_BYTE_SIZE), constructor, ELEMENT_BYTE_SIZE);
                        long index = 0;
                        for (T obj : objs) {
                            ptr.setAtIndex(ValueLayout.%4$s, index, obj.value());
                            index++;
                        }
                    }
                
                    public %2$s(SegmentAllocator allocator, Collection<T> objs, Function<Pointer<T>, T> constructor) {
                        super(allocator.allocate(objs.size() * ELEMENT_BYTE_SIZE), constructor, ELEMENT_BYTE_SIZE);
                        long index = 0;
                        for (T obj : objs) {
                            ptr.setAtIndex(ValueLayout.%4$s, index, obj.value());
                            index++;
                        }
                    }
                
                    public %2$s(SegmentAllocator allocator, %2$s[] objs, Function<Pointer<T>, T> constructor) {
                        super(allocator.allocate(objs.length * ELEMENT_BYTE_SIZE), constructor, ELEMENT_BYTE_SIZE);
                        long index = 0;
                        for (%2$s obj : objs) {
                            ptr.setAtIndex(ValueLayout.%4$s, index, obj);
                            index++;
                        }
                    }
                
                    @Override
                    public T set(int index, T element) {
                        ptr.setAtIndex(ValueLayout.%4$s, index, element.value());
                        return element;
                    }
                
                    public %2$s set(int index, %2$s element) {
                        ptr.setAtIndex(ValueLayout.%4$s, index, element);
                        return element;
                    }
                
                    @Override
                    public %2$s<T> reinterpretSize(long length) {
                        return new %2$s<>(ptr.reinterpret(length * ELEMENT_BYTE_SIZE), constructor);
                    }
                
                    @Override
                    public %2$s<T> subList(int fromIndex, int toIndex) {
                        subListRangeCheck(fromIndex, toIndex, size());
                        return new %2$s<>(ptr.asSlice(fromIndex * elementByteSize, (toIndex - fromIndex) * elementByteSize), constructor);
                    }
                
                    @Override
                    public String toString() {
                        return pointer().byteSize() %% elementByteSize == 0 ? super.toString() : "%2$s{ptr:" + ptr;
                    }
                }
                """.formatted(path.makePackage(), className, byteSize, valueLayout, genericValue));
    }

    private void genPointerInterface(PackagePath path) {
        Utils.write(path.getFilePath(), """
                %s;
                
                import java.lang.foreign.MemorySegment;
                
                public interface Pointer<P> {
                    MemorySegment pointer();
                }
                """.formatted(path.makePackage()));
    }

    private void genValueInterface(PackagePath path) {
        Utils.write(path.getFilePath(), """
                package %s.shared;
                
                public interface Value<T>{
                    T value();
                }
                """.formatted(path.makePackage()));
    }

    private void genValue(PackagePath path, String className, String parent, String listClassName, String baseObjectType) {
        Utils.write(path.getFilePath(), """
                package %1$s.shared.values;
                
                import %1$s.shared.Pointer;
                import %1$s.shared.Value;
                import %1%s.shared.%2$s;
                
                import java.lang.foreign.MemorySegment;
                import java.lang.foreign.SegmentAllocator;
                import java.util.Collection;
                import java.util.function.Consumer;
                
                public class %3$s<T> extends %4$s<T> {
                
                    public static <T> %2$s<%3$s<T>> list(Pointer<%3$s<T>> ptr) {
                        return new %2$s<>(ptr, %3$s::new);
                    }
                
                    public static <T> %2$s<%3$s<T>> list(Pointer<%3$s<T>> ptr, long length) {
                        return new %2$s<>(ptr, length, %3$s::new);
                    }
                
                    public static <T> %2$s<%3$s<T>> list(SegmentAllocator allocator, long length) {
                        return new %2$s<>(allocator, length, %3$s::new);
                    }
                
                    public static <T> %2$s<%3$s<T>> list(SegmentAllocator allocator, %3$s<T>[] c) {
                        return new %2$s<>(allocator, c, %3$s::new);
                    }
                
                    public static <T> %2$s<%3$s<T>> list(SegmentAllocator allocator, Collection<%3$s<T>> c) {
                        return new %2$s<>(allocator, c, %3$s::new);
                    }
                
                    public %3$s(Pointer<? extends %3$s<T>> ptr) {
                        super(ptr);
                    }
                
                    public %3$s(%5$s value) {
                        super(value);
                    }
                
                    public %3$s(Value<%5$s> value) {
                        super(value);
                    }
                
                    public %3$s(%3$s<T> value) {
                        super(value);
                    }
                }
                """.formatted(path.makePackage(), listClassName, className, parent, baseObjectType));
    }

    private void genValueBasic(PackagePath path, String className, String valueLayout, String objectType) {
        Utils.write(path.getFilePath(), """
                package %1$s.shared.values;
                
                import %1$s.shared.Value;
                import %1$s.shared.Pointer;
                
                import java.lang.foreign.MemoryLayout;
                import java.lang.foreign.MemorySegment;
                import java.lang.foreign.ValueLayout;
                
                public class %2$s<T> implements Value<%3$s> {
                    public static final MemoryLayout MEMORY_LAYOUT = ValueLayout.%4$s;
                    public static final long BYTE_SIZE = MEMORY_LAYOUT.byteSize();
                    private final %3$s value;
                
                    public %2$s(Pointer<? extends %2$s<T>> ptr) {
                        this.value = ptr.pointer().get(ValueLayout.%4$s, 0);
                    }
                
                    public %2$s(%3$s value) {
                        this.value = value;
                    }
                
                    public %2$s(Value<%3$s> value) {
                        this.value = value.value();
                    }
                
                    @Override
                    public %3$s value() {
                        return value;
                    }
                
                    @Override
                    public boolean equals(Object obj) {
                        return obj instanceof %2$s<?> that && that.value().equals(value);
                    }
                
                    @Override
                    public int hashCode() {
                        return value().hashCode();
                    }
                
                    @Override
                    public String toString() {
                        return String.valueOf(value);
                    }
                }""".formatted(path.makePackage(), className, objectType, valueLayout));
    }

    private void genAbstractNativeList(PackagePath path) {
        Utils.write(path.getFilePath(), """
                package %s.shared;
                
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
                }""".formatted(path.makePackage()));
    }
}
