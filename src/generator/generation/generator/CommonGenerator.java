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
                        case NString -> genNstr(packagePath, imports);
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
                    %3$ss<T> operator();
                
                    interface %3$ss<T> extends Info.InfoOp<T>, Value.ValueOp<%5$s> {
                
                    }
                }
                """.formatted(path.makePackage(), imports,
                btOp.typeName(TypeAttr.NamedType.NameType.RAW), // 3
                btOp.getValue().typeName(TypeAttr.NamedType.NameType.RAW),
                btOp.getValue().getPrimitive().getBoxedTypeName());
        if (btOp == CommonTypes.BindTypeOperations.PtrOp)
            str = """
                    %1$s
                    
                    %2$s
                    public interface %3$s<S extends Info<S>, E> extends Pointer<E>, Pointee<E> {
                        @Override
                        %3$ss<S, E> operator();
                    
                        interface %3$ss<S, E> extends Info.InfoOp<S>, Value.ValueOp<MemorySegment>, PointeeOp<E> {
                            Info.Operations<E> elementInfo();
                        }
                    }
                    """.formatted(path.makePackage(), imports, btOp.typeName(TypeAttr.NamedType.NameType.RAW));
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
                
                public interface ArrayOperation<A extends Info<A>, E> extends Value<MemorySegment>, PointerOperation<A, E>, List<E> {
                    interface ArrayOp<A, E> extends Value.ValueOp<MemorySegment>, Info.InfoOp<A>, PointerOp<A, E> {
                        A reinterpret(long length);
                
                        Pointer<E> pointerAt(long index);
                    }
                
                    ArrayOp<A, E> operator();
                }""".formatted(path.makePackage(), imports));
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
                
                public interface Pointee<E> extends Operation {
                    interface PointeeOp<E> extends Value.ValueOp<MemorySegment> {
                        E pointee();
                    }
                
                    @Override
                    PointeeOp<E> operator();
                }""".formatted(path.makePackage(), imports));
    }

    private void genInfo(PackagePath path, String imports) {
        Utils.write(path.getFilePath(), """
                %s
                
                %s
                
                public interface Info<T> extends Operation {
                    interface InfoOp<T> {
                        Operations<T> getStaticInfo();
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
                """.formatted(path.makePackage(), imports));
    }

    private void genArray(PackagePath path, String imports) {
        Utils.write(path.getFilePath(), """
                %s
                
                %s
                import java.util.AbstractList;
                import java.util.Collection;
                import java.util.Objects;
                import java.util.RandomAccess;
                
                public class Array<E> extends AbstractList<E> implements ArrayOperation<Array<E>, E>, Info<Array<E>>, RandomAccess {
                    public static <I> Operations<Array<I>> makeOperations(Operations<I> operation, long byteSize) {
                        return new Operations<>((param, offset) -> new Array<>(param.get(ValueLayout.ADDRESS, offset),
                                operation), (source, dest, offset) -> dest.asSlice(offset).copyFrom(source.ptr), byteSize);
                    }
                
                    public static <I> Operations<Array<I>> makeOperations(Operations<I> operation) {
                        return makeOperations(operation, 0L);
                    }
                
                    protected final MemorySegment ptr;
                    protected final Operations<E> operations;
                
                    public Array(Pointer<E> ptr, Info<E> info) {
                        this(ptr, info.operator().getStaticInfo());
                    }
                
                    public Array(attr.Pointer<E> ptr) {
                        this(ptr, ptr.operator().elementInfo());
                    }
                
                    public Array(Pointer<E> ptr, Operations<E> operations) {
                        this.ptr = ptr.operator().value();
                        this.operations = operations;
                    }
                
                    public Array(PointerOperation<?, E> ptr) {
                        this.ptr = ptr.operator().value();
                        this.operations = ptr.operator().elementInfo();
                    }
                
                    public Array(MemorySegment ptr, Operations<E> operations) {
                        this.ptr = ptr;
                        this.operations = operations;
                    }
                
                    public Array(SegmentAllocator allocator, Collection<E> elements, Info<E> staticInfo) {
                        this(allocator, elements, staticInfo.operator().getStaticInfo());
                    }
                
                    public Array(SegmentAllocator allocator, Collection<E> elements, Operations<E> operations) {
                        this.operations = operations;
                        this.ptr = allocator.allocate(elements.size() * operations.byteSize());
                        int i = 0;
                        for (E element : elements) {
                            operations.copy().copyTo(element, ptr, operations.byteSize() * i);
                            i++;
                        }
                    }
                
                    public Array(SegmentAllocator allocator, Info<E> staticInfo, long len) {
                        this(allocator, staticInfo.operator().getStaticInfo(), len);
                    }
                
                    public Array(SegmentAllocator allocator, Operations<E> operations, long len) {
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
                    public ArrayOp<Array<E>, E> operator() {
                        return new ArrayOp<>() {
                            @Override
                            public Operations<E> elementInfo() {
                                return operations;
                            }
                
                            @Override
                            public Array<E> reinterpret(long length) {
                                return new Array<>(ptr.reinterpret(length * operations.byteSize()), operations);
                            }
                
                            @Override
                            public Pointer<E> pointerAt(long index) {
                                Objects.checkIndex(index, size());
                                return Pointer.of(ptr.asSlice(index * operations.byteSize(), operations.byteSize()));
                            }
                
                            @Override
                            public Operations<Array<E>> getStaticInfo() {
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
                
                    public attr.Pointer<E> pointerAt(long index) {
                        Objects.checkIndex(index, size());
                        return new attr.Pointer<>(ptr.asSlice(index * operations.byteSize(), operations.byteSize()), operations);
                    }
                
                    @Override
                    public int size() {
                        return (int) (ptr.byteSize() / operations.byteSize());
                    }
                }
                """.formatted(path.makePackage(), imports));
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
                %s
                
                %s
                package attr;
                
                import attr.opeations.ArrayOperation;
                import attr.opeations.basic.Info;
                
                import java.lang.foreign.MemorySegment;
                import java.lang.foreign.SegmentAllocator;
                import java.lang.foreign.ValueLayout;
                import java.nio.charset.StandardCharsets;
                import java.util.AbstractList;
                import java.util.Arrays;
                import java.util.Collection;
                import java.util.List;
                import java.util.stream.Stream;
                
                public class NStr extends AbstractList<I8> implements ArrayOperation<NStr, I8>, Info<NStr> {
                    public static final long BYTE_SIZE = ValueLayout.ADDRESS.byteSize();
                    public static final Operations<NStr> OPERATIONS = new Operations<>((param, offset) -> new NStr(fitByteSize(param.get(ValueLayout.ADDRESS, offset))),
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
                    public ArrayOp<NStr, I8> operator() {
                        return new ArrayOp<>() {
                            @Override
                            public Operations<I8> elementInfo() {
                                return I8.OPERATIONS;
                            }
                
                            @Override
                            public NStr reinterpret(long length) {
                                return new NStr(ptr.reinterpret(length));
                            }
                
                            @Override
                            public attr.opeations.basic.Pointer<I8> pointerAt(long index) {
                                return attr.opeations.basic.Pointer.of(ptr.asSlice(index, 1));
                            }
                
                            @Override
                            public Operations<NStr> getStaticInfo() {
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
                """.formatted(path.makePackage(), imports, type.typeName(TypeAttr.NamedType.NameType.RAW),
                type.getPrimitive().getBoxedTypeName()));
    }

    private void genBindTypes(PackagePath path, CommonTypes.BindTypes bindTypes, String imports) {
        String str = """
                %1$s
                
                %2$s
                public class %3$s implements %5$s<%3$s>, Info<%3$s> {
                    public static final long BYTE_SIZE = %4$s.byteSize();
                    public static final Operations<%3$s> OPERATIONS = new Operations<>(
                            (param, offset) -> new I8(param.get(%4$s, offset)),
                            (source, dest, offset) -> dest.set(%4$s, offset, source.val), BYTE_SIZE);
                    private final %6$s val;
                
                    public %3$s(%6$s val) {
                        this.val = val;
                    }
                
                    Array<%3$s> list(SegmentAllocator allocator, int len) {
                        return new Array<>(allocator, OPERATIONS, len);
                    }
                
                    @Override
                    public %5$s<%3$s> operator() {
                        return new %5$s<>() {
                            @Override
                            public Operations<%3$s> getStaticInfo() {
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
                bindTypes.getOperations().typeName(TypeAttr.NamedType.NameType.RAW), // 5
                bindTypes.getPrimitiveType().getPrimitiveTypeName(),
                bindTypes.getPrimitiveType().getBoxedTypeName());
        if (bindTypes == CommonTypes.BindTypes.Pointer)
            str = """
                    %s
                    
                    %s
                    public class Pointer<E> implements PointerOperation<Pointer<E>, E>, Info<Pointer<E>> {
                        public static final int BYTE_SIZE = 8;
                    
                        public static <I> Operations<Pointer<I>> makeStaticInfo(Operations<I> operation) {
                            return new Operations<>(
                                    (param, offset) -> new Pointer<>(param.get(ValueLayout.ADDRESS, offset), operation),
                                    (source, dest, offset1) -> dest.set(ValueLayout.ADDRESS, offset1, source.segment), BYTE_SIZE);
                        }
                        private final MemorySegment segment;
                        private final Operations<E> operation;
                    
                        private MemorySegment fitByteSize(MemorySegment segment) {
                            return segment.byteSize() == operation.byteSize() ? segment : segment.reinterpret(operation.byteSize());
                        }
                    
                        public Pointer(MemorySegment segment, Operations<E> operation) {
                            this.operation = operation;
                            this.segment = fitByteSize(segment);
                        }
                    
                        public Pointer(ArrayOperation<?, E> arrayOperation) {
                            this.operation = arrayOperation.operator().elementInfo();
                            this.segment = fitByteSize(arrayOperation.operator().value());
                        }
                    
                        public Pointer(Operations<E> operation, Value<MemorySegment> pointee) {
                            this.operation = operation;
                            this.segment = fitByteSize(pointee.operator().value());
                        }
                    
                        public Pointer(Operations<E> operation, attr.opeations.basic.Pointer<E> pointee) {
                            this.operation = operation;
                            this.segment = fitByteSize(pointee.operator().value());
                        }
                    
                        @Override
                        public String toString() {
                            return "Pointer{" +
                                   "pointee=" + operator().pointee() +
                                   '}';
                        }
                    
                        public Operations<E> getElementOperation() {
                            return operation;
                        }
                    
                        @Override
                        public PointerOp<Pointer<E>, E> operator() {
                            return new PointerOp<>() {
                                @Override
                                public Operations<E> elementInfo() {
                                    return operation;
                                }
                    
                                @Override
                                public E pointee() {
                                    return operation.constructor().create(segment, 0);
                                }
                    
                                @Override
                                public Operations<Pointer<E>> getStaticInfo() {
                                    return makeStaticInfo(operation);
                                }
                    
                                @Override
                                public MemorySegment value() {
                                    return segment;
                                }
                            };
                        }
                    }
                    """.formatted(path.makePackage(), imports);
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
