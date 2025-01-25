package generator.generation.generator;

import generator.Dependency;
import generator.PackagePath;
import generator.TypePkg;
import generator.Utils;
import generator.generation.Common;
import generator.types.CommonTypes;
import generator.types.TypeAttr;

import static utils.CommonUtils.Assert;


public class CommonGenerator implements Generator {
    private final Common common;
    private final Dependency dependency;

    public CommonGenerator(Common common, Dependency dependency) {
        this.common = common;
        this.dependency = dependency;
    }

    @Override
    public void generate() {
        for (TypePkg<? extends CommonTypes.BaseType> implType : common.getImplTypes()) {
            PackagePath packagePath = dependency.getTypePackagePath(implType.type());
            String imports = Generator.extractImports(common, dependency);
            switch (implType.type()) {
                case CommonTypes.BindTypes bindTypes -> {
                    Assert(bindTypes.getPrimitiveType().enable());
                    genBindTypes(packagePath, bindTypes, imports);
                }
                case CommonTypes.BindTypeOperations btOp -> {
                    Assert(btOp.getValue().getPrimitive().enable());
                }
                case CommonTypes.ValueInterface v -> {
                    Assert(v.getPrimitive().enable());
                    genValueInterface(packagePath, v, imports);
                }
                case CommonTypes.SpecificTypes specificTypes -> {
                    switch (specificTypes) {
                        case Array -> genArray(packagePath, imports);
                        case NString -> genNstring(packagePath, imports);
                        case AbstractNativeList -> genAbstractNativeList(packagePath, imports);
                        case Utils -> genUtils(packagePath);
                    }
                }
                case CommonTypes.FFMTypes FFMTypes -> {

                }
                case CommonTypes.BasicOperations basic -> {

                }
            }
        }
    }

    private void genArray(PackagePath path, String imports) {
        Utils.write(path.getFilePath(), """
                %s
                %s
                
                import java.lang.foreign.MemorySegment;
                import java.lang.foreign.SegmentAllocator;
                import java.util.function.Consumer;
                import java.util.function.Function;
                
                public class Array<E extends Pointer<?>> extends AbstractNativeList<E> {
                    public Array(Pointer<E> ptr, Function<Pointer<E>, E> constructor, long elementByteSize) {
                        super(ptr, constructor, elementByteSize);
                    }
                
                    public Array(Pointer<E> ptr, long length, Function<Pointer<E>, E> constructor, long elementByteSize) {
                        super(ptr, length, constructor, elementByteSize);
                    }
                
                    public Array(SegmentAllocator allocator, long length, Function<Pointer<E>, E> constructor, long elementByteSize) {
                        super(allocator, length, constructor, elementByteSize);
                    }
                
                    @Override
                    public Array<E> reinterpretSize(long length) {
                        return new Array<>(() -> ptr.reinterpret(length * elementByteSize), constructor, elementByteSize);
                    }
                
                    /**
                     * @param index   index of the element to replace
                     * @param element element to be stored at the specified position
                     * @return the element
                     */
                    @Override
                    public E set(int index, E element) {
                        if (element.value().byteSize() != elementByteSize)
                            throw new IllegalArgumentException("elementByteSize is " + elementByteSize + ", but element.value().byteSize() is " + element.value().byteSize());
                        MemorySegment.copy(element.value(), 0, ptr, index * elementByteSize, elementByteSize);
                        return element;
                    }
                
                    @Override
                    public String toString() {
                        return value().byteSize() %% elementByteSize == 0 ? super.toString() : "Array{ptr: " + ptr;
                    }
                }""".formatted(path.makePackage(), imports));
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

    private static void genNstring(PackagePath packagePath, String imports) {
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
                    public static class NativeStringList extends Array<NString> {
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
                
                    public static Array<NString> list(Pointer<Pointer<VI8<Byte>>> ptr, long length) {
                        return new NativeStringList(ptr, length);
                    }
                
                    public static Array<NString> list(SegmentAllocator allocator, String[] strings) {
                        MemorySegment strPtr = allocator.allocate(ValueLayout.ADDRESS, strings.length);
                        int i = 0;
                        for (String string : strings) {
                            strPtr.setAtIndex(ValueLayout.ADDRESS, i, allocator.allocateFrom(string));
                            i++;
                        }
                        return new NativeStringList(strPtr);
                    }
                
                    public static Array<NString> list(SegmentAllocator allocator, Collection<String> strings) {
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
                """.formatted(packagePath.makePackage(), imports));
    }


    private void genPrimitiveList(PackagePath path, CommonTypes.BindTypes bindTypes, String imports) {
        Utils.write(path.getFilePath(), """
                %1$s
                %6$s
                
                import java.lang.foreign.MemorySegment;
                import java.lang.foreign.SegmentAllocator;
                import java.lang.foreign.AddressLayout;
                import java.util.Collection;
                import java.util.function.BiConsumer;
                import java.util.function.Consumer;
                import java.util.function.Function;
                
                public class %2$s<T extends %7$s<?>> extends AbstractNativeList<T> {
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
                            ptr.setAtIndex(%4$s, index, obj.value());
                            index++;
                        }
                    }
                
                    public %2$s(SegmentAllocator allocator, Collection<T> objs, Function<Pointer<T>, T> constructor) {
                        super(allocator.allocate(objs.size() * ELEMENT_BYTE_SIZE), constructor, ELEMENT_BYTE_SIZE);
                        long index = 0;
                        for (T obj : objs) {
                            ptr.setAtIndex(%4$s, index, obj.value());
                            index++;
                        }
                    }
                
                    @Override
                    public T set(int index, T element) {
                        ptr.setAtIndex(%4$s, index, element.value());
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
                        return value().byteSize() %% elementByteSize == 0 ? super.toString() : "%2$s{ptr:" + ptr;
                    }
                }
                """);
//                .formatted(path.makePackage(), bindTypes.getRawName(), bindTypes.getElementType().getByteSize(),
//                bindTypes.getElementType().getMemoryLayout(), bindTypes.getElementType().typeName(TypeAttr.NamedType.NameType.GENERIC), imports, bindTypes.getElementType().getValueInterface().getTypeName()));
    }

    private void genValueInterface(PackagePath path, CommonTypes.ValueInterface type, String imports) {
        Utils.write(path.getFilePath(), """
                %s
                %s
                
                public interface %s<I> extends Value<%s> {
                }
                """.formatted(path.makePackage(), imports, type.typeName(TypeAttr.NamedType.NameType.RAW),
                type.getPrimitive().getBoxedTypeName()));
    }

    private void genBindTypes(PackagePath path, CommonTypes.BindTypes bindTypes, String imports) {
        Utils.write(path.getFilePath(), """
                %s
                
                %s
                
                import java.lang.foreign.AddressLayout;
                import java.lang.foreign.MemoryLayout;
                import java.util.Objects;
                
                public class %3$s<T> implements %6$s<T> {
                    public static final MemoryLayout MEMORY_LAYOUT = %5$s;
                    public static final long BYTE_SIZE = MEMORY_LAYOUT.byteSize();
                    private final %4$s value;
                
                    public %3$s(Pointer<? extends %3$s<? extends T>> ptr) {
                        this.value = ptr.value().get(%5$s, 0);
                    }
                
                    public %3$s(%4$s value) {
                        this.value = value;
                    }
                
                    @Override
                    public %4$s value() {
                        return value;
                    }
                
                    @Override
                    public boolean equals(Object o) {
                        if (!(o instanceof %3$s<?> that)) return false;
                        return Objects.equals(value, that.value);
                    }
                
                    @Override
                    public int hashCode() {
                        return Objects.hashCode(value);
                    }
                
                    @Override
                    public String toString() {
                        return String.valueOf(value);
                    }
                }""".formatted(path.makePackage(), imports, bindTypes.getRawName(),
                bindTypes.getPrimitiveType().getPrimitiveTypeName(), bindTypes.getMemoryLayout(),
                bindTypes.getOperations().typeName(TypeAttr.NamedType.NameType.RAW)));
    }

    private void genAbstractNativeList(PackagePath path, String imports) {
        Utils.write(path.getFilePath(), """
                %s
                
                %s
                
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
                        this(ptr.value(), constructor, elementByteSize);
                    }
                
                    protected AbstractNativeList(MemorySegment ptr, Function<Pointer<T>, T> constructor, long elementByteSize) {
                        this.ptr = ptr;
                        this.constructor = constructor;
                        this.elementByteSize = elementByteSize;
                        ensureValue(ptr, constructor, elementByteSize);
                    }
                
                    public AbstractNativeList(Pointer<T> ptr, long length, Function<Pointer<T>, T> constructor, long elementByteSize) {
                        this(ptr.value().reinterpret(length * elementByteSize), constructor, elementByteSize);
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
                    public MemorySegment value() {
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
                }""".formatted(path.makePackage(), imports));
    }

    private void genUtils(PackagePath path) {
        Utils.write(path.getFilePath(), """
                %s
                
                import java.lang.foreign.*;
                import java.lang.invoke.MethodHandle;
                import java.lang.invoke.MethodHandles;
                import java.lang.reflect.Modifier;
                import java.util.Arrays;
                import java.util.Objects;
                import java.util.Optional;
                
                public class Utils {
                    public static class SymbolNotFound extends RuntimeException {
                        public SymbolNotFound(String cause) {
                            super(cause);
                        }
                
                        public SymbolNotFound(Throwable t) {
                            super(t);
                        }
                
                        public SymbolNotFound() {
                        }
                    }
                
                    public static class InvokeException extends RuntimeException {
                        public InvokeException(Throwable cause) {
                            super(cause);
                        }
                    }
                
                    public static <T> Pointer<T> makePointer(MemorySegment ms) {
                        return new Pointer<>() {
                            @Override
                            public String toString() {
                                return String.valueOf(ms);
                            }
                
                            @Override
                            public MemorySegment value() {
                                return ms;
                            }
                        };
                    }
                
                    public static <E extends BasicI32<?>> Optional<String> enumToString(Class<?> klass, E e) {
                        return Arrays.stream(klass.getFields()).map(field -> {
                            try {
                                return (Modifier.isStatic(field.getModifiers()) && e.equals(field.get(null))) ? field.getName() : null;
                            } catch (IllegalAccessException ex) {
                                return null;
                            }
                        }).filter(Objects::nonNull).findFirst();
                    }
                
                    public static MemorySegment toMemorySegment(Arena arena, MethodHandle methodHandle, FunctionDescriptor functionDescriptor) {
                        return Linker.nativeLinker().upcallStub(methodHandle, functionDescriptor, arena);
                    }
                
                    public static MemorySegment toMemorySegment(MethodHandles.Lookup lookup, Arena arena, FunctionDescriptor functionDescriptor, Object function, String functionName) {
                        var handle = toMethodHandle(lookup, functionDescriptor, function, functionName);
                        return toMemorySegment(arena, handle, functionDescriptor);
                    }
                
                    public static MethodHandle toMethodHandle(MethodHandles.Lookup lookup, FunctionDescriptor functionDescriptor, Object function, String functionName) {
                        try {
                            return lookup.findVirtual(function.getClass(), functionName, functionDescriptor.toMethodType()).bindTo(function);
                        } catch (NoSuchMethodException | IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }
                
                    public static Optional<MethodHandle> toMethodHandle(SymbolLookup lookup, String functionName, FunctionDescriptor functionDescriptor, boolean critical) {
                        return Objects.requireNonNull(lookup).find(Objects.requireNonNull(functionName)).map(ms -> critical ?
                                Linker.nativeLinker().downcallHandle(ms, functionDescriptor, Linker.Option.critical(true))
                                : Linker.nativeLinker().downcallHandle(ms, functionDescriptor));
                    }
                
                    public static Optional<MethodHandle> toMethodHandle(MemorySegment memorySegment, FunctionDescriptor functionDescriptor, boolean critical) {
                        return toMethodHandle(Optional.ofNullable(memorySegment), functionDescriptor, critical);
                    }
                
                    public static Optional<MethodHandle> toMethodHandle(Optional<MemorySegment> memorySegment, FunctionDescriptor functionDescriptor, boolean critical) {
                        return memorySegment.map(ms -> critical ?
                                Linker.nativeLinker().downcallHandle(ms, functionDescriptor, Linker.Option.critical(true))
                                : Linker.nativeLinker().downcallHandle(ms, functionDescriptor));
                    }
                }
                """.formatted(path.makePackage()));
    }
}
