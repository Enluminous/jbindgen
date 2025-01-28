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
                    genBindTypeOp(packagePath, btOp, imports);
                }
                case CommonTypes.ValueInterface v -> {
                    Assert(v.getPrimitive().enable());
                    genValueInterface(packagePath, v, imports);
                }
                case CommonTypes.SpecificTypes specificTypes -> {
                    switch (specificTypes) {
                        case Array -> genArray(packagePath, imports);
                        case NStr -> genNstr(packagePath, imports);
                        case AbstractNativeList -> genAbstractNativeList(packagePath, imports);
                        case Utils -> genUtils(packagePath);
                        case ArrayOp -> genArrayOp(packagePath, imports);
                        case StructOp -> genStructOp(packagePath, imports);
                    }
                }
                case CommonTypes.FFMTypes FFMTypes -> {

                }
                case CommonTypes.BasicOperations ext -> {
                    switch (ext) {
                        case Operation -> genOperation(packagePath);
                        case Info -> genInfo(packagePath, imports);
                        case Value -> genValue(packagePath, imports);
                        case Pte -> genPointee(packagePath, imports);
                    }
                }
            }
        }
    }

    private void genBindTypeOp(PackagePath path, CommonTypes.BindTypeOperations btOp, String imports) {
        String str = """
                %1$s
                
                %2$s
                public interface %3$s<T extends Info<T>> extends %4$s<T> {
                    @Override
                    %6$s<T> operator();
                
                    interface %6$s<T> extends Info.InfoOp<T>, Value.ValueOp<%5$s> {
                
                    }
                }
                """.formatted(path.makePackage(), imports,
                btOp.typeName(TypeAttr.NameType.RAW), // 3
                btOp.getValue().typeName(TypeAttr.NameType.RAW),
                btOp.getValue().getPrimitive().getBoxedTypeName(),
                btOp.operatorTypeName());// 5
        if (btOp == CommonTypes.BindTypeOperations.PtrOp)
            str = """
                    %1$s
                    
                    %2$s
                    import java.lang.foreign.MemorySegment;
                    
                    public interface %3$s<S extends Info<S>, E> extends %5$s<E>, %6$s<E> {
                        @Override
                        %4$s<S, E> operator();
                    
                        interface %4$s<S, E> extends Info.InfoOp<S>, Value.ValueOp<MemorySegment>, PointeeOp<E> {
                            Info.Operations<E> elementOperation();
                        }
                    }
                    """.formatted(path.makePackage(), imports,
                    btOp.typeName(TypeAttr.NameType.RAW),
                    btOp.operatorTypeName(),
                    btOp.getValue().typeName(TypeAttr.NameType.RAW),
                    CommonTypes.BasicOperations.Pte.typeName(TypeAttr.NameType.RAW));// 4
        Utils.write(path.getFilePath(), str);
    }

    private void genOperation(PackagePath path) {
        Utils.write(path.getFilePath(), """
                %s
                
                public interface Operation {
                    Object operator();
                }
                """.formatted(path.makePackage()));
    }

    private void genValue(PackagePath path, String imports) {
        Utils.write(path.getFilePath(), """
                %s
                
                %s
                
                public interface Value<T> extends Operation {
                    interface ValueOp<T> {
                        T value();
                    }
                
                    @Override
                    ValueOp<T> operator();
                }
                """.formatted(path.makePackage(), imports));
    }

    private void genArrayOp(PackagePath path, String imports) {
        Utils.write(path.getFilePath(), """
                %s
                
                %s
                import java.lang.foreign.MemorySegment;
                import java.util.List;
                
                public interface %s<A extends Info<A>, E> extends Value<MemorySegment>, %4$s<A, E>, List<E> {
                    interface ArrayOpI<A, E> extends Value.ValueOp<MemorySegment>, Info.InfoOp<A>, %5$s<A, E> {
                        A reinterpret(long length);
                
                        %6$s<E> pointerAt(long index);
                    }
                
                    ArrayOpI<A, E> operator();
                }""".formatted(path.makePackage(), imports,
                CommonTypes.SpecificTypes.ArrayOp.typeName(TypeAttr.NameType.RAW),
                CommonTypes.BindTypeOperations.PtrOp.typeName(TypeAttr.NameType.RAW), // 4
                CommonTypes.BindTypeOperations.PtrOp.operatorTypeName(),
                CommonTypes.BindTypes.Ptr.typeName(TypeAttr.NameType.RAW))); // 6
    }

    private void genStructOp(PackagePath path, String imports) {
        Utils.write(path.getFilePath(), """
                %s
                
                %s
                import java.lang.foreign.MemorySegment;
                
                public interface StructOperation<E extends Info<? extends E>> extends Value<MemorySegment>, Info<E> {
                
                    @Override
                    StructOp<E> operator();
                
                    interface StructOp<E extends Info<? extends E>> extends InfoOp<E>, Value.ValueOp<MemorySegment> {
                        E reinterpret();
                
                        Pointer<E> getPointer();
                    }
                }""".formatted(path.makePackage(), imports));
    }

    private void genPointee(PackagePath path, String imports) {
        Utils.write(path.getFilePath(), """
                %s
                
                %s
                
                public interface %3$s<E> extends Operation {
                    interface PointeeOp<E> extends Value.ValueOp<MemorySegment> {
                        E pointee();
                    }
                
                    @Override
                    PointeeOp<E> operator();
                }""".formatted(path.makePackage(), imports, CommonTypes.BasicOperations.Pte.typeName(TypeAttr.NameType.RAW)));
    }

    private void genInfo(PackagePath path, String imports) {
        Utils.write(path.getFilePath(), """
                %s
                
                %s
                
                public interface Info<T> extends Operation {
                    interface InfoOp<T> {
                        Operations<T> getOperations();
                    }
                
                    record Operations<T>(Constructor<? extends T, MemorySegment> constructor, Copy<? super T> copy, long byteSize) {
                        public interface Constructor<R, P> {
                            R create(P param, long offset);
                        }
                
                        public interface Copy<S> {
                            void copyTo(S source, MemorySegment dest, long offset);
                        }
                    }
                
                    InfoOp<T> operator();
                }
                """.formatted(path.makePackage(), imports));
    }

    private void genArray(PackagePath path, String imports) {
        Utils.write(path.getFilePath(), """
                %1$s
                
                %2$s
                import java.util.AbstractList;
                import java.util.Collection;
                import java.util.Objects;
                import java.util.RandomAccess;
                
                public class Array<E> extends AbstractList<E> implements %3$s<Array<E>, E>, Info<Array<E>>, RandomAccess {
                    public static <I> Info.Operations<Array<I>> makeOperations(Operations<I> operation, long byteSize) {
                        return new Info.Operations<>((param, offset) -> new Array<>(param.get(ValueLayout.ADDRESS, offset),
                                operation), (source, dest, offset) -> dest.asSlice(offset).copyFrom(source.ptr), byteSize);
                    }
                
                    public static <I> Info.Operations<Array<I>> makeOperations(Operations<I> operation) {
                        return makeOperations(operation, 0L);
                    }
                
                    protected final MemorySegment ptr;
                    protected final Info.Operations<E> operations;
                
                    public Array(%5$s<E> ptr, Info<E> info) {
                        this(ptr, info.operator().getOperations());
                    }
                
                    public Array(%6$s<E> ptr) {
                        this(ptr, ptr.operator().elementOperation());
                    }
                
                    public Array(%5$s<E> ptr, Info.Operations<E> operations) {
                        this.ptr = ptr.operator().value();
                        this.operations = operations;
                    }
                
                    public Array(%4$s<?, E> ptr) {
                        this.ptr = ptr.operator().value();
                        this.operations = ptr.operator().elementOperation();
                    }
                
                    public Array(MemorySegment ptr, Info.Operations<E> operations) {
                        this.ptr = ptr;
                        this.operations = operations;
                    }
                
                    public Array(SegmentAllocator allocator, Collection<E> elements, Info<E> staticInfo) {
                        this(allocator, elements, staticInfo.operator().getOperations());
                    }
                
                    public Array(SegmentAllocator allocator, Collection<E> elements, Info.Operations<E> operations) {
                        this.operations = operations;
                        this.ptr = allocator.allocate(elements.size() * operations.byteSize());
                        int i = 0;
                        for (E element : elements) {
                            operations.copy().copyTo(element, ptr, operations.byteSize() * i);
                            i++;
                        }
                    }
                
                    public Array(SegmentAllocator allocator, Info<E> staticInfo, long len) {
                        this(allocator, staticInfo.operator().getOperations(), len);
                    }
                
                    public Array(SegmentAllocator allocator, Info.Operations<E> operations, long len) {
                        this.operations = operations;
                        this.ptr = allocator.allocate(len * operations.byteSize());
                    }
                
                    public E get(int index) {
                        Objects.checkIndex(index, size());
                        return operations.constructor().create(ptr, index * operations.byteSize());
                    }
                
                    @Override
                    public E set(int index, E element) {
                        Objects.checkIndex(index, size());
                        operations.copy().copyTo(element, ptr, index * operations.byteSize());
                        return element;
                    }
                
                    @Override
                    public ArrayOpI<Array<E>, E> operator() {
                        return new ArrayOpI<>() {
                            @Override
                            public Info.Operations<E> elementOperation() {
                                return operations;
                            }
                
                            @Override
                            public Array<E> reinterpret(long length) {
                                return new Array<>(ptr.reinterpret(length * operations.byteSize()), operations);
                            }
                
                            @Override
                            public %6$s<E> pointerAt(long index) {
                                Objects.checkIndex(index, size());
                                return new %6$s<>(ptr.asSlice(index * operations.byteSize(), operations.byteSize()), operations);
                            }
                
                            @Override
                            public Info.Operations<Array<E>> getOperations() {
                                return makeOperations(operations, ptr.byteSize());
                            }
                
                            @Override
                            public E pointee() {
                                return get(0);
                            }
                
                            @Override
                            public MemorySegment value() {
                                return ptr;
                            }
                        };
                    }
                
                    public %6$s<E> pointerAt(long index) {
                        return operator().pointerAt(index);
                    }
                
                    @Override
                    public int size() {
                        return (int) (ptr.byteSize() / operations.byteSize());
                    }
                }
                """.formatted(path.makePackage(), imports,
                CommonTypes.SpecificTypes.ArrayOp.typeName(TypeAttr.NameType.RAW),
                CommonTypes.BindTypeOperations.PtrOp.typeName(TypeAttr.NameType.RAW),
                CommonTypes.ValueInterface.PtrI.typeName(TypeAttr.NameType.RAW), // 5
                CommonTypes.BindTypes.Ptr.typeName(TypeAttr.NameType.RAW)));
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

    private static void genNstr(PackagePath packagePath, String imports) {
        Utils.write(packagePath.getFilePath(), """
                %1$s
                
                %2$s
                import java.lang.foreign.MemorySegment;
                import java.lang.foreign.SegmentAllocator;
                import java.lang.foreign.ValueLayout;
                import java.nio.charset.StandardCharsets;
                import java.util.AbstractList;
                import java.util.Arrays;
                import java.util.Collection;
                import java.util.List;
                import java.util.stream.Stream;
                
                public class NStr extends AbstractList<I8> implements %3$s<NStr, I8>, Info<NStr> {
                    public static final long BYTE_SIZE = ValueLayout.ADDRESS.byteSize();
                    public static final Info.Operations<NStr> OPERATIONS = new Info.Operations<>((param, offset) -> new NStr(fitByteSize(param.get(ValueLayout.ADDRESS, offset))),
                            (source, dest, offset) -> dest.set(ValueLayout.ADDRESS, offset, source.ptr), BYTE_SIZE);
                    private final MemorySegment ptr;
                
                    @Override
                    public I8 get(int index) {
                        return new I8(ptr.getAtIndex(ValueLayout.JAVA_BYTE, index));
                    }
                
                    private static Array<NStr> makeArray(SegmentAllocator allocator, Stream<String> ss) {
                        List<NStr> list = ss.map(s -> new NStr(allocator, s)).toList();
                        return new Array<>(allocator, list, list.getFirst());
                    }
                
                    private static final long HIMAGIC_FOR_BYTES = 0x8080_8080_8080_8080L;
                    private static final long LOMAGIC_FOR_BYTES = 0x0101_0101_0101_0101L;
                
                    private static boolean containZeroByte(long l) {
                        return ((l - LOMAGIC_FOR_BYTES) & (~l) & HIMAGIC_FOR_BYTES) != 0;
                    }
                
                    private static int strlen(MemorySegment segment) {
                        int count = 0;
                        while (!containZeroByte(segment.get(ValueLayout.JAVA_LONG, count))) {
                            count += 4;
                        }
                        while (segment.get(ValueLayout.JAVA_BYTE, count) != 0) {
                            segment.get(ValueLayout.JAVA_BYTE, count);
                            count++;
                        }
                        return count;
                    }
                
                    private static MemorySegment fitByteSize(MemorySegment segment) {
                        return segment.address() == 0 ? segment : segment.reinterpret(1 + strlen(segment.reinterpret(Long.MAX_VALUE)));
                    }
                
                
                    public static Array<NStr> list(SegmentAllocator allocator, String[] strings) {
                        return makeArray(allocator, Arrays.stream(strings));
                    }
                
                    public static Array<NStr> list(SegmentAllocator allocator, Collection<String> strings) {
                        return makeArray(allocator, strings.stream());
                    }
                
                    protected NStr(MemorySegment ptr) {
                        this.ptr = ptr;
                    }
                
                    public NStr(SegmentAllocator allocator, String s) {
                        this(allocator.allocateFrom(s, StandardCharsets.UTF_8));
                    }
                
                    public String get() {
                        return MemorySegment.NULL.address() == ptr.address() ? null : toString();
                    }
                
                    @Override
                    public int size() {
                        return (int) ptr.byteSize();
                    }
                
                    @Override
                    public String toString() {
                        return MemorySegment.NULL.address() == ptr.address()
                                ? "NStr{ptr=" + ptr + '}'
                                : ptr.getString(0, StandardCharsets.UTF_8);
                    }
                
                    @Override
                    public ArrayOpI<NStr, I8> operator() {
                        return new ArrayOpI<>() {
                            @Override
                            public Info.Operations<I8> elementOperation() {
                                return I8.OPERATIONS;
                            }
                
                            @Override
                            public NStr reinterpret(long length) {
                                return new NStr(ptr.reinterpret(length));
                            }
                
                            @Override
                            public %4$s<I8> pointerAt(long index) {
                                return new %4$s<>(ptr.asSlice(index, 1), I8.OPERATIONS);
                            }
                
                            @Override
                            public Info.Operations<NStr> getOperations() {
                                return OPERATIONS;
                            }
                
                            @Override
                            public I8 pointee() {
                                return get(0);
                            }
                
                            @Override
                            public MemorySegment value() {
                                return ptr;
                            }
                        };
                    }
                }
                """.formatted(packagePath.makePackage(), imports,
                CommonTypes.SpecificTypes.ArrayOp.typeName(TypeAttr.NameType.RAW),
                CommonTypes.BindTypes.Ptr.typeName(TypeAttr.NameType.RAW)));
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
                %1$s
                %2$s
                
                public interface %3$s<I> extends Value<%4$s> {
                    static <I> %3$s<I> of(%4$s value) {
                        return new %3$s<>() {
                            @Override
                            public ValueOp<%4$s> operator() {
                                return () -> value;
                            }
                
                            @Override
                            public String toString() {
                                return String.valueOf(value);
                            }
                        };
                    }
                }
                """.formatted(path.makePackage(), imports, type.typeName(TypeAttr.NameType.RAW),
                type.getPrimitive().getBoxedTypeName()));
    }

    private void genBindTypes(PackagePath path, CommonTypes.BindTypes bindTypes, String imports) {
        String str = """
                %1$s
                
                %2$s
                public class %3$s implements %5$s<%3$s>, Info<%3$s> {
                    public static final long BYTE_SIZE = %4$s.byteSize();
                    public static final Info.Operations<%3$s> OPERATIONS = new Info.Operations<>(
                            (param, offset) -> new %3$s(param.get(%4$s, offset)),
                            (source, dest, offset) -> dest.set(%4$s, offset, source.val), BYTE_SIZE);
                    private final %6$s val;
                
                    public %3$s(%6$s val) {
                        this.val = val;
                    }
                
                    Array<%3$s> list(SegmentAllocator allocator, int len) {
                        return new Array<>(allocator, OPERATIONS, len);
                    }
                
                    @Override
                    public %5$sI<%3$s> operator() {
                        return new %5$sI<>() {
                            @Override
                            public Info.Operations<%3$s> getOperations() {
                                return OPERATIONS;
                            }
                
                            @Override
                            public %7$s value() {
                                return val;
                            }
                        };
                    }
                
                    @Override
                    public String toString() {
                        return String.valueOf(val);
                    }
                }
                """.formatted(path.makePackage(), imports, bindTypes.getRawName(),
                bindTypes.getPrimitiveType().getMemoryLayout(), // 4
                bindTypes.getOperations().typeName(TypeAttr.NameType.RAW), // 5
                bindTypes.getPrimitiveType().getPrimitiveTypeName(),
                bindTypes.getPrimitiveType().getBoxedTypeName());
        if (bindTypes == CommonTypes.BindTypes.Ptr)
            str = """
                    %1$s
                    
                    %2$s
                    public class %3$s<E> implements %5$s<%3$s<E>, E>, Info<%3$s<E>> {
                        public static final int BYTE_SIZE = 8;
                    
                        public static <I> Info.Operations<%3$s<I>> makeStaticInfo(Info.Operations<I> operation) {
                            return new Info.Operations<>(
                                    (param, offset) -> new %3$s<>(param.get(ValueLayout.ADDRESS, offset), operation),
                                    (source, dest, offset1) -> dest.set(ValueLayout.ADDRESS, offset1, source.segment), BYTE_SIZE);
                        }
                        private final MemorySegment segment;
                        private final Info.Operations<E> operation;
                    
                        private MemorySegment fitByteSize(MemorySegment segment) {
                            return segment.byteSize() == operation.byteSize() ? segment : segment.reinterpret(operation.byteSize());
                        }
                    
                        public %3$s(MemorySegment segment, Info.Operations<E> operation) {
                            this.operation = operation;
                            this.segment = fitByteSize(segment);
                        }
                    
                        public %3$s(%6$s<?, E> arr) {
                            this.operation = arr.operator().elementOperation();
                            this.segment = fitByteSize(arr.operator().value());
                        }
                    
                        public %3$s(Info.Operations<E> operation, Value<MemorySegment> pointee) {
                            this.operation = operation;
                            this.segment = fitByteSize(pointee.operator().value());
                        }
                    
                        public %3$s(Info.Operations<E> operation, %4$s<E> pointee) {
                            this.operation = operation;
                            this.segment = fitByteSize(pointee.operator().value());
                        }
                    
                        @Override
                        public String toString() {
                            return "%3$s{" +
                                   "pointee=" + operator().pointee() +
                                   '}';
                        }
                    
                        public Info.Operations<E> getElementOperation() {
                            return operation;
                        }
                    
                        @Override
                        public %7$s<%3$s<E>, E> operator() {
                            return new %7$s<>() {
                                @Override
                                public Info.Operations<E> elementOperation() {
                                    return operation;
                                }
                    
                                @Override
                                public E pointee() {
                                    return operation.constructor().create(segment, 0);
                                }
                    
                                @Override
                                public Info.Operations<%3$s<E>> getOperations() {
                                    return makeStaticInfo(operation);
                                }
                    
                                @Override
                                public MemorySegment value() {
                                    return segment;
                                }
                            };
                        }
                    }
                    """.formatted(path.makePackage(), imports,
                    bindTypes.typeName(TypeAttr.NameType.RAW),
                    bindTypes.getOperations().getValue().typeName(TypeAttr.NameType.RAW), // 4
                    bindTypes.getOperations().typeName(TypeAttr.NameType.RAW),
                    CommonTypes.SpecificTypes.ArrayOp.typeName(TypeAttr.NameType.RAW), // 6
                    bindTypes.getOperations().operatorTypeName());// 7
        Utils.write(path.getFilePath(), str);
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
