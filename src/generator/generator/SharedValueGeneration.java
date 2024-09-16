package generator.generator;

import generator.Utils;
import generator.config.PackagePath;
import generator.types.Primitives;

public class SharedValueGeneration extends AbstractGenerator {
    protected SharedValueGeneration(PackagePath packagePath) {
        super(packagePath);
    }

    public void generate() {
        genPointerInterface();
        genValueInterface();
        genValues();
        genAbstractNativeList();
    }


    private void genValues() {
        for (var primitive : Primitives.values()) {
            String list = primitive.getTypeName() + "List";
            String basic = primitive.getTypeName() + "Basic";
            genValueBasic(basic, primitive.getMemoryLayout(), primitive.getWrapperName());
            genValue(primitive.getTypeName(), basic, list, primitive.getWrapperName());
            genVList(list, primitive.getWrapperName(), primitive.getByteSize(), primitive.getMemoryLayout());
        }
    }

    private void genVList(String className, String genericValue, long byteSize, String valueLayout) {
        Utils.write(path.resolve(className + ".java"), """
                package %s.shared;
                
                import java.lang.foreign.MemorySegment;
                import java.lang.foreign.SegmentAllocator;
                import java.lang.foreign.ValueLayout;
                import java.util.Collection;
                import java.util.function.BiConsumer;
                import java.util.function.Consumer;
                import java.util.function.Function;
                
                public class %s<T extends Value<%s>> extends AbstractNativeList<T> {
                    public static final long ELEMENT_BYTE_SIZE = %s;
                
                    protected %s(MemorySegment ptr, Function<Pointer<T>, T> constructor) {
                        super(ptr, constructor, ELEMENT_BYTE_SIZE);
                    }
                
                    public %s(Pointer<T> ptr, Function<Pointer<T>, T> constructor) {
                        super(ptr, constructor, ELEMENT_BYTE_SIZE);
                    }
                
                    public %s(Pointer<T> ptr, long length, Function<Pointer<T>, T> constructor) {
                        super(ptr, length, constructor, ELEMENT_BYTE_SIZE);
                    }
                
                    public %s(SegmentAllocator allocator, long length, Function<Pointer<T>, T> constructor, BiConsumer<Long, %s<T>> creator) {
                        super(allocator.allocate(ELEMENT_BYTE_SIZE * length, 4), constructor, ELEMENT_BYTE_SIZE);
                        for (int i = 0; i < length; i++) {
                            creator.accept((long) i, subList(i, i));
                        }
                    }
                
                    public %s(SegmentAllocator allocator, long length, Function<Pointer<T>, T> constructor) {
                        super(allocator.allocate(length * ELEMENT_BYTE_SIZE), constructor, ELEMENT_BYTE_SIZE);
                    }
                
                    public %s(SegmentAllocator allocator, T[] objs, Function<Pointer<T>, T> constructor) {
                        super(allocator.allocate(objs.length * ELEMENT_BYTE_SIZE), constructor, ELEMENT_BYTE_SIZE);
                        long index = 0;
                        for (T obj : objs) {
                            ptr.setAtIndex(ValueLayout.%s, index, obj.value());
                            index++;
                        }
                    }
                
                    public %s(SegmentAllocator allocator, Collection<T> objs, Function<Pointer<T>, T> constructor) {
                        super(allocator.allocate(objs.size() * ELEMENT_BYTE_SIZE), constructor, ELEMENT_BYTE_SIZE);
                        long index = 0;
                        for (T obj : objs) {
                            ptr.setAtIndex(ValueLayout.%s, index, obj.value());
                            index++;
                        }
                    }
                
                    public %s(SegmentAllocator allocator, %s[] objs, Function<Pointer<T>, T> constructor) {
                        super(allocator.allocate(objs.length * ELEMENT_BYTE_SIZE), constructor, ELEMENT_BYTE_SIZE);
                        long index = 0;
                        for (%s obj : objs) {
                            ptr.setAtIndex(ValueLayout.%s, index, obj);
                            index++;
                        }
                    }
                
                    @Override
                    public T set(int index, T element) {
                        ptr.setAtIndex(ValueLayout.%s, index, element.value());
                        return element;
                    }
                
                    public %s set(int index, %s element) {
                        ptr.setAtIndex(ValueLayout.%s, index, element);
                        return element;
                    }
                
                    @Override
                    public %s<T> reinterpretSize(long length) {
                        return new %s<>(ptr.reinterpret(length * ELEMENT_BYTE_SIZE), constructor);
                    }
                
                    @Override
                    public %s<T> subList(int fromIndex, int toIndex) {
                        subListRangeCheck(fromIndex, toIndex, size());
                        return new %s<>(ptr.asSlice(fromIndex * elementByteSize, (toIndex - fromIndex) * elementByteSize), constructor);
                    }
                
                    @Override
                    public String toString() {
                        return pointer().byteSize() %% elementByteSize == 0 ? super.toString() : "%s{ptr:" + ptr;
                    }
                }
                """.formatted(basePackageName, className, genericValue,
                byteSize,
                className,
                className,
                className,
                className, className, className,
                className, valueLayout, className, valueLayout,
                className, genericValue, genericValue, valueLayout, valueLayout, genericValue, genericValue, valueLayout, className,
                className, className, className, className
        ));
    }

    private void genPointerInterface() {
        Utils.write(path.resolve("Pointer.java"), """
                package %s.shared;
                
                import java.lang.foreign.MemorySegment;
                
                public interface Pointer<P> {
                    MemorySegment pointer();
                }
                """.formatted(basePackageName));
    }

    private void genValueInterface() {
        Utils.write(path.resolve("Value.java"), """
                package %s.shared;
                
                public interface Value<T>{
                    T value();
                }
                """.formatted(basePackageName));
    }

    private void genValue(String className, String parent, String listClassName, String baseObjectType) {
        Utils.write(path.resolve("values/" + className + ".java"), """
                package %s.shared.values;
                
                import %s.shared.Pointer;
                import %s.shared.Value;
                import %s.shared.%s;
                
                import java.lang.foreign.MemorySegment;
                import java.lang.foreign.SegmentAllocator;
                import java.util.Collection;
                import java.util.function.Consumer;
                
                public class %s<T> extends %s<T> {
                
                    public static <T> %s<%s<T>> list(Pointer<%s<T>> ptr) {
                        return new %s<>(ptr, %s::new);
                    }
                
                    public static <T> %s<%s<T>> list(Pointer<%s<T>> ptr, long length) {
                        return new %s<>(ptr, length, %s::new);
                    }
                
                    public static <T> %s<%s<T>> list(SegmentAllocator allocator, long length) {
                        return new %s<>(allocator, length, %s::new);
                    }
                
                    public static <T> %s<%s<T>> list(SegmentAllocator allocator, %s<T>[] c) {
                        return new %s<>(allocator, c, %s::new);
                    }
                
                    public static <T> %s<%s<T>> list(SegmentAllocator allocator, Collection<%s<T>> c) {
                        return new %s<>(allocator, c, %s::new);
                    }
                
                    public %s(Pointer<? extends %s<T>> ptr) {
                        super(ptr);
                    }
                
                    public %s(%s value) {
                        super(value);
                    }
                
                    public %s(Value<%s> value) {
                        super(value);
                    }
                
                    public %s(%s<T> value) {
                        super(value);
                    }
                }
                """.formatted(basePackageName, basePackageName, basePackageName, basePackageName,
                listClassName, className, parent,
                listClassName, className, className, listClassName, className,
                listClassName, className, className, listClassName, className,
                listClassName, className, listClassName, className,
                listClassName, className, className, listClassName, className,
                listClassName, className, className, listClassName, className, className, className, className, baseObjectType, className,
                baseObjectType, className, className));
    }

    private void genValueBasic(String className, String valueLayout, String objectType) {
        Utils.write(path.resolve("values/" + className + ".java"), """
                package %s.shared.values;
                
                import %s.shared.Value;
                import %s.shared.Pointer;
                
                import java.lang.foreign.MemoryLayout;
                import java.lang.foreign.MemorySegment;
                import java.lang.foreign.ValueLayout;
                
                public class %s<T> implements Value<%s> {
                    public static final MemoryLayout MEMORY_LAYOUT = ValueLayout.%s;
                    public static final long BYTE_SIZE = MEMORY_LAYOUT.byteSize();
                    private final %s value;
                
                    public %s(Pointer<? extends %s<T>> ptr) {
                        this.value = ptr.pointer().get(ValueLayout.%s, 0);
                    }
                
                    public %s(%s value) {
                        this.value = value;
                    }
                
                    public %s(Value<%s> value) {
                        this.value = value.value();
                    }
                
                    @Override
                    public %s value() {
                        return value;
                    }
                
                    @Override
                    public boolean equals(Object obj) {
                        return obj instanceof %s<?> that && that.value().equals(value);
                    }
                
                    @Override
                    public int hashCode() {
                        return value().hashCode();
                    }
                
                    @Override
                    public String toString() {
                        return String.valueOf(value);
                    }
                }""".formatted(basePackageName, basePackageName, basePackageName, className, objectType, valueLayout, objectType,
                className, className, valueLayout, className, objectType, className, objectType, objectType, className));
    }

    private void genAbstractNativeList() {
        Utils.write(path.resolve("AbstractNativeList.java"), """
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
                }""".formatted(basePackageName));
    }
}
