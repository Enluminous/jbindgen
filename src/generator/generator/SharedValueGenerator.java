package generator.generator;

import generator.Utils;
import generator.config.PackagePath;
import generator.types.CommonTypes;

import java.nio.file.Path;

public class SharedValueGenerator {
    private final Path path;
    private final String basePackageName;

    protected SharedValueGenerator(PackagePath packagePath) {
        basePackageName = packagePath.getPackage();
        path = packagePath.getPath();
    }

    public void generate() {
        genPointerInterface();
        genValueInterface();
        genValues();
        genAbstractNativeList();
    }


    private void genValues() {
        for (var primitive : CommonTypes.BindTypes.values()) {
            String list = primitive.getTypeName() + "List";
            String basic = primitive.getTypeName() + "Basic";
            String boxedTypeName = primitive.getOperation().getFuncOperation().getPrimitiveType().getBoxedTypeName();
            genValueBasic(basic, primitive.getPrimitiveType().getMemoryLayout(), boxedTypeName);
            genValue(primitive.getTypeName(), basic, list, boxedTypeName);
            genVList(list, boxedTypeName, primitive.getPrimitiveType().getByteSize(), primitive.getPrimitiveType().getMemoryLayout());
        }
    }

    private void genVList(String className, String genericValue, long byteSize, String valueLayout) {
        Utils.write(path.resolve(className + ".java"), """
                package %1$s.shared;
                
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
                """.formatted(basePackageName, className, byteSize, valueLayout, genericValue));
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
                """.formatted(basePackageName, listClassName, className, parent, baseObjectType));
    }

    private void genValueBasic(String className, String valueLayout, String objectType) {
        Utils.write(path.resolve("values/" + className + ".java"), """
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
                }""".formatted(basePackageName, className, objectType, valueLayout));
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
