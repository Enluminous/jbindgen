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
                    genBindTypes(packagePath, bindTypes, imports);
                }
                case CommonTypes.BindTypeOperations btOp -> {
                    genBindTypeOp(packagePath, btOp, imports);
                }
                case CommonTypes.ValueInterface v -> {
                    genValueInterface(packagePath, v, imports);
                }
                case CommonTypes.SpecificTypes specificTypes -> {
                    switch (specificTypes) {
                        case Array -> genArray(packagePath, imports);
                        case FlatArray -> genFlatArray(packagePath, imports);
                        case Str -> genNstr(packagePath, imports);
                        case FunctionUtils -> genFunctionUtils(packagePath);
                        case ArrayOp -> genArrayOp(packagePath, imports);
                        case FlatArrayOp -> genFlatArrayOp(packagePath, imports);
                        case StructOp -> genStructOp(packagePath, imports);
                        case MemoryUtils -> genMemoryUtils(packagePath, imports);
                    }
                }
                case CommonTypes.FFMTypes FFMTypes -> {

                }
                case CommonTypes.BasicOperations ext -> {
                    switch (ext) {
                        case Operation -> genOperation(packagePath);
                        case Info -> genInfo(packagePath, imports);
                        case Value -> genValue(packagePath, imports);
                        case PteI -> genPointee(packagePath, imports);
                        case ArrayI -> genArrayI(packagePath, imports);
                        case StructI -> genStructI(packagePath, imports);
                    }
                }
            }
        }
    }

    private void genStructI(PackagePath path, String imports) {
        Utils.write(path, """
                %s
                
                %s
                
                public interface StructI<E> extends Value<MemorySegment> {
                    static <I> StructI<I> of(MemorySegment value) {
                        return new StructI<>() {
                            @Override
                            public ValueOp<MemorySegment> operator() {
                                return () -> value;
                            }
                
                            @Override
                            public String toString() {
                                return String.valueOf(value);
                            }
                        };
                    }
                
                    static <I> StructI<I> of(StructI<?> value) {
                        return of(value.operator().value());
                    }
                }
                """.formatted(path.makePackage(), imports));
    }

    private void genArrayI(PackagePath path, String imports) {
        Utils.write(path, """
                %s
                
                %s
                
                public interface ArrayI<E> extends Value<MemorySegment> {
                    static <I> ArrayI<I> of(MemorySegment value) {
                        return new ArrayI<>() {
                            @Override
                            public ValueOp<MemorySegment> operator() {
                                return () -> value;
                            }
                
                            @Override
                            public String toString() {
                                return String.valueOf(value);
                            }
                        };
                    }
                
                    static <I> ArrayI<I> of(ArrayI<?> value) {
                        return of(value.operator().value());
                    }
                }
                """.formatted(path.makePackage(), imports));
    }

    private void genBindTypeOp(PackagePath path, CommonTypes.BindTypeOperations btOp, String imports) {
        if (btOp.getValue().getPrimitive().noJavaPrimitive()) {
            var str = """
                    %1$s
                    
                    %2$s
                    import java.lang.foreign.MemorySegment;
                    
                    public interface %3$s<T extends Info<T>> extends %4$s<T> {
                        @Override
                        %3$sI<T> operator();
                    
                        interface %3$sI<T> extends Info.InfoOp<T>, Value.ValueOp<MemorySegment> {
                    
                        }
                    }
                    """.formatted(path.makePackage(), imports, btOp.typeName(TypeAttr.NameType.RAW), // 3
                    btOp.getValue().typeName(TypeAttr.NameType.RAW));
            Utils.write(path, str);
            return;
        }
        String str = """
                %1$s
                
                %2$s
                import java.util.function.Function;
                
                public interface %3$s<T extends Info<T>> extends %4$s<T> {
                    @Override
                    %6$s<T> operator();
                
                    interface %6$s<T> extends Info.InfoOp<T>, Value.ValueOp<%5$s> {
                
                    }
                
                    static <T extends %4$s<?>> Info.Operations<T> makeOperations(Function<%5$s, T> constructor) {
                        return new Info.Operations<>(
                                (param, offset) -> constructor.apply(MemoryUtils.get%8$s(param, offset)),
                                (source, dest, offset) -> MemoryUtils.set%8$s(dest, offset, source.operator().value()),
                                %7$s.byteSize());
                    }
                }
                """.formatted(path.makePackage(), imports,
                btOp.typeName(TypeAttr.NameType.RAW), // 3
                btOp.getValue().typeName(TypeAttr.NameType.RAW),
                btOp.getValue().getPrimitive().getBoxedTypeName(), // 5
                btOp.operatorTypeName(),
                btOp.getValue().getPrimitive().getMemoryLayout(),
                btOp.getValue().getPrimitive().getMemoryUtilName()); // 7
        if (btOp == CommonTypes.BindTypeOperations.PtrOp)
            str = """
                    %1$s
                    
                    %2$s
                    import java.lang.foreign.MemorySegment;
                    import java.util.function.Function;
                    
                    public interface %3$s<S extends Info<S>, E> extends %5$s<E>, %6$s<E> {
                        @Override
                        %4$s<S, E> operator();
                    
                        interface %4$s<S, E> extends Info.InfoOp<S>, Value.ValueOp<MemorySegment>, PointeeOp<E> {
                            Info.Operations<E> elementOperation();
                    
                            void setPointee(E pointee);
                        }
                    
                        static <T extends %5$s<?>> Info.Operations<T> makeOperations(Function<MemorySegment, T> constructor) {
                            return new Info.Operations<>(
                                        (param, offset) -> constructor.apply(MemoryUtils.getAddr(param, offset)),
                                        (source, dest, offset) -> MemoryUtils.setAddr(dest, offset, source.operator().value()),
                                        ValueLayout.ADDRESS.byteSize());
                        }
                    }
                    """.formatted(path.makePackage(), imports,
                    btOp.typeName(TypeAttr.NameType.RAW),
                    btOp.operatorTypeName(),
                    btOp.getValue().typeName(TypeAttr.NameType.RAW),
                    CommonTypes.BasicOperations.PteI.typeName(TypeAttr.NameType.RAW));// 4
        Utils.write(path, str);
    }

    private void genOperation(PackagePath path) {
        Utils.write(path, """
                %s
                
                public interface Operation {
                    Object operator();
                }
                """.formatted(path.makePackage()));
    }

    private void genValue(PackagePath path, String imports) {
        Utils.write(path, """
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
        Utils.write(path, """
                %s
                
                %s
                import java.util.AbstractList;
                import java.util.List;
                import java.util.RandomAccess;
                
                public interface %s<A extends Info<A>, E> extends %7$s<E>, %4$s<A, E>, List<E> {
                    interface ArrayOpI<A, E> extends Value.ValueOp<MemorySegment>, Info.InfoOp<A>, %5$s<A, E> {
                        A reinterpret(long length);
                
                        %6$s<E> pointerAt(long index);
                
                        List<%6$s<E>> pointerList();
                    }
                
                    abstract class AbstractRandomAccessList<E> extends AbstractList<E> implements RandomAccess {
                    }
                
                    interface FixedArrayOpI<A, E> extends ArrayOpI<A, E> {
                        A reinterpret();
                    }
                
                    ArrayOpI<A, E> operator();
                }""".formatted(path.makePackage(), imports,
                CommonTypes.SpecificTypes.ArrayOp.typeName(TypeAttr.NameType.RAW),
                CommonTypes.BindTypeOperations.PtrOp.typeName(TypeAttr.NameType.RAW), // 4
                CommonTypes.BindTypeOperations.PtrOp.operatorTypeName(),
                CommonTypes.BindTypes.Ptr.typeName(TypeAttr.NameType.RAW),
                CommonTypes.BasicOperations.ArrayI.typeName(TypeAttr.NameType.RAW))); // 7
    }

    private void genFlatArrayOp(PackagePath path, String imports) {
        Utils.write(path, """
                %s
                
                %s
                import java.util.AbstractList;
                import java.util.List;
                import java.util.RandomAccess;
                
                public interface %s<A extends Info<A>, E> extends %7$s<E>, List<E> {
                    interface FlatArrayOpI<A, E> extends Value.ValueOp<MemorySegment>, Info.InfoOp<A> {
                        A reinterpret(long length);
                
                        %6$s<E> pointerAt(long index);
                
                        List<%6$s<E>> pointerList();
                
                        Info.Operations<E> elementOperation();
                    }
                
                    abstract class AbstractRandomAccessList<E> extends AbstractList<E> implements RandomAccess {
                    }
                
                    interface FixedFlatArrayOpI<A, E> extends FlatArrayOpI<A, E> {
                        A reinterpret();
                    }
                
                    FlatArrayOpI<A, E> operator();
                }""".formatted(path.makePackage(), imports,
                CommonTypes.SpecificTypes.FlatArrayOp.typeName(TypeAttr.NameType.RAW),
                CommonTypes.BindTypeOperations.PtrOp.typeName(TypeAttr.NameType.RAW), // 4
                CommonTypes.BindTypeOperations.PtrOp.operatorTypeName(),
                CommonTypes.BindTypes.Ptr.typeName(TypeAttr.NameType.RAW),
                CommonTypes.BasicOperations.ArrayI.typeName(TypeAttr.NameType.RAW))); // 7
    }

    private void genStructOp(PackagePath path, String imports) {
        Utils.write(path, """
                %1$s
                
                %2$s
                import java.util.function.Function;
                
                public interface StructOp<E extends Info<? extends E>> extends %4$s<E> {
                
                    @Override
                    StructOpI<E> operator();
                
                    interface StructOpI<E extends Info<? extends E>> extends Info.InfoOp<E>, Value.ValueOp<MemorySegment> {
                        E reinterpret();
                
                        %3$s<E> getPointer();
                    }
                
                    static <E extends StructOp<?>> Info.Operations<E> makeOperations(Function<MemorySegment, E> constructor, long byteSize) {
                        return new Info.Operations<>(
                                (param, offset) -> constructor.apply(param.asSlice(offset, byteSize)),
                                (source, dest, offset) -> dest.asSlice(offset).copyFrom(source.operator().value()), byteSize);
                    }
                }""".formatted(path.makePackage(), imports, CommonTypes.BindTypes.Ptr.typeName(TypeAttr.NameType.RAW),
                CommonTypes.BasicOperations.StructI.typeName(TypeAttr.NameType.RAW)));
    }

    private void genPointee(PackagePath path, String imports) {
        Utils.write(path, """
                %s
                
                %s
                
                public interface %3$s<E> extends Operation {
                    interface PointeeOp<E> extends Value.ValueOp<MemorySegment> {
                        E pointee();
                    }
                
                    @Override
                    PointeeOp<E> operator();
                }""".formatted(path.makePackage(), imports, CommonTypes.BasicOperations.PteI.typeName(TypeAttr.NameType.RAW)));
    }

    private void genInfo(PackagePath path, String imports) {
        Utils.write(path, """
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
                
                    static <I> Operations<I> makeOperations() {
                        return new Operations<>((_, _) -> {
                            throw new UnsupportedOperationException();
                        }, (_, _, _) -> {
                            throw new UnsupportedOperationException();
                        }, 0);
                    }
                }
                """.formatted(path.makePackage(), imports));
    }

    public static final String ARRAY_MAKE_OPERATION_METHOD = "makeOperations";

    private void genArray(PackagePath path, String imports) {
        Utils.write(path, """
                %1$s
                
                %2$s
                import java.util.*;
                
                public class Array<E> extends %3$s.AbstractRandomAccessList<E> implements %3$s<Array<E>, E>, Info<Array<E>> {
                    public static final long BYTE_SIZE = ValueLayout.ADDRESS.byteSize();
                
                    public static <I> Info.Operations<Array<I>> makeOperations(Operations<I> operation, long len) {
                        return new Info.Operations<>((param, offset) -> new Array<>(MemoryUtils.getAddr(param, offset).reinterpret(len * operation.byteSize()),
                                operation), (source, dest, offset) -> MemoryUtils.setAddr(dest, offset, source.ptr), BYTE_SIZE);
                    }
                
                    public static <I> Info.Operations<Array<I>> makeOperations(Operations<I> operation) {
                        return new Info.Operations<>((param, offset) -> new Array<>(MemoryUtils.getAddr(param, offset),
                                operation), (source, dest, offset) -> MemoryUtils.setAddr(dest, offset, source.ptr), BYTE_SIZE);
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
                
                    public Array(SegmentAllocator allocator, Info<E> info, Collection<E> elements) {
                        this(allocator, info.operator().getOperations(), elements);
                    }
                
                    public Array(SegmentAllocator allocator, Info.Operations<E> operations, Collection<E> elements) {
                        this.operations = operations;
                        this.ptr = allocator.allocate(elements.size() * operations.byteSize());
                        int i = 0;
                        for (E element : elements) {
                            operations.copy().copyTo(element, ptr, operations.byteSize() * i);
                            i++;
                        }
                    }
                
                    public Array(SegmentAllocator allocator, Info<E> info, long len) {
                        this(allocator, info.operator().getOperations(), len);
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
                            public void setPointee(E pointee) {
                                set(0, pointee);
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
                            public List<%6$s<E>> pointerList() {
                                return new %3$s.AbstractRandomAccessList<>() {
                                    @Override
                                    public %6$s<E> get(int index) {
                                        return pointerAt(index);
                                    }
                
                                    @Override
                                    public int size() {
                                        return Array.this.size();
                                    }
                                };
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
                
                    public List<%6$s<E>> pointerList() {
                        return operator().pointerList();
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

    private void genFlatArray(PackagePath path, String imports) {
        Utils.write(path, """
                %1$s
                
                %2$s
                import java.util.*;
                
                public class FlatArray<E> extends %3$s.AbstractRandomAccessList<E> implements %3$s<FlatArray<E>, E>, Info<FlatArray<E>> {
                    public static <I> Operations<FlatArray<I>> makeOperations(Operations<I> operation, long len) {
                        return new Operations<>((param, offset) -> new FlatArray<>(param.asSlice(offset, len * operation.byteSize()),
                                operation), (source, dest, offset) -> MemoryUtils.memcpy(source.ptr, 0, dest, offset, len * operation.byteSize()),
                                len * operation.byteSize());
                    }
                
                    protected final MemorySegment ptr;
                    protected final Info.Operations<E> operations;
                
                    public FlatArray(%5$s<E> ptr, Info<E> info) {
                        this(ptr, info.operator().getOperations());
                    }
                
                    public FlatArray(%6$s<E> ptr) {
                        this(ptr, ptr.operator().elementOperation());
                    }
                
                    public FlatArray(%5$s<E> ptr, Info.Operations<E> operations) {
                        this.ptr = ptr.operator().value();
                        this.operations = operations;
                    }
                
                    public FlatArray(%4$s<?, E> ptr) {
                        this.ptr = ptr.operator().value();
                        this.operations = ptr.operator().elementOperation();
                    }
                
                    public FlatArray(MemorySegment ptr, Info.Operations<E> operations) {
                        this.ptr = ptr;
                        this.operations = operations;
                    }
                
                    public FlatArray(SegmentAllocator allocator, Info<E> info, Collection<E> elements) {
                        this(allocator, info.operator().getOperations(), elements);
                    }
                
                    public FlatArray(SegmentAllocator allocator, Info.Operations<E> operations, Collection<E> elements) {
                        this.operations = operations;
                        this.ptr = allocator.allocate(elements.size() * operations.byteSize());
                        int i = 0;
                        for (E element : elements) {
                            operations.copy().copyTo(element, ptr, operations.byteSize() * i);
                            i++;
                        }
                    }
                
                    public FlatArray(SegmentAllocator allocator, Info<E> info, long len) {
                        this(allocator, info.operator().getOperations(), len);
                    }
                
                    public FlatArray(SegmentAllocator allocator, Info.Operations<E> operations, long len) {
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
                    public FlatArrayOpI<FlatArray<E>, E> operator() {
                        return new FlatArrayOpI<>() {
                            @Override
                            public Info.Operations<E> elementOperation() {
                                return operations;
                            }
                
                            @Override
                            public FlatArray<E> reinterpret(long length) {
                                return new FlatArray<>(ptr.reinterpret(length * operations.byteSize()), operations);
                            }
                
                            @Override
                            public %6$s<E> pointerAt(long index) {
                                Objects.checkIndex(index, size());
                                return new %6$s<>(ptr.asSlice(index * operations.byteSize(), operations.byteSize()), operations);
                            }
                
                            @Override
                            public List<%6$s<E>> pointerList() {
                                return new %3$s.AbstractRandomAccessList<>() {
                                    @Override
                                    public %6$s<E> get(int index) {
                                        return pointerAt(index);
                                    }
                
                                    @Override
                                    public int size() {
                                        return FlatArray.this.size();
                                    }
                                };
                            }
                
                            @Override
                            public Info.Operations<FlatArray<E>> getOperations() {
                                return makeOperations(operations, ptr.byteSize());
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
                
                    public List<%6$s<E>> pointerList() {
                        return operator().pointerList();
                    }
                
                    @Override
                    public int size() {
                        return (int) (ptr.byteSize() / operations.byteSize());
                    }
                }
                """.formatted(path.makePackage(), imports,
                CommonTypes.SpecificTypes.FlatArrayOp.typeName(TypeAttr.NameType.RAW),
                CommonTypes.BindTypeOperations.PtrOp.typeName(TypeAttr.NameType.RAW),
                CommonTypes.ValueInterface.PtrI.typeName(TypeAttr.NameType.RAW), // 5
                CommonTypes.BindTypes.Ptr.typeName(TypeAttr.NameType.RAW)));
    }

    private static void genNstr(PackagePath packagePath, String imports) {
        Utils.write(packagePath, """
                %1$s
                
                %2$s
                import java.lang.foreign.MemorySegment;
                import java.lang.foreign.SegmentAllocator;
                import java.lang.foreign.ValueLayout;
                import java.nio.charset.StandardCharsets;
                import java.util.Arrays;
                import java.util.Collection;
                import java.util.List;
                import java.util.stream.Stream;
                
                public class %5$s extends %3$s.AbstractRandomAccessList<I8> implements %3$s<%5$s, I8>, Info<%5$s> {
                    public static final long BYTE_SIZE = ValueLayout.ADDRESS.byteSize();
                    public static final Info.Operations<%5$s> OPERATIONS = new Info.Operations<>(
                            (param, offset) -> new %5$s(fitByteSize(param.get(ValueLayout.ADDRESS, offset))),
                            (source, dest, offset) -> dest.set(ValueLayout.ADDRESS, offset, source.ptr), BYTE_SIZE);
                    private final MemorySegment ptr;
                
                    @Override
                    public I8 get(int index) {
                        return new I8(ptr.getAtIndex(ValueLayout.JAVA_BYTE, index));
                    }
                
                    private static Array<%5$s> makeArray(SegmentAllocator allocator, Stream<String> ss) {
                        List<%5$s> list = ss.map(s -> new %5$s(allocator, s)).toList();
                        return new Array<>(allocator, list.getFirst(), list);
                    }
                
                    private static final long HIMAGIC_FOR_BYTES = 0x8080_8080_8080_8080L;
                    private static final long LOMAGIC_FOR_BYTES = 0x0101_0101_0101_0101L;
                
                    private static boolean containZeroByte(long l) {
                        return ((l - LOMAGIC_FOR_BYTES) & (~l) & HIMAGIC_FOR_BYTES) != 0;
                    }
                
                    private static int strlen(MemorySegment segment) {
                        int count = 0;
                        while (!containZeroByte(segment.get(ValueLayout.JAVA_LONG_UNALIGNED, count))) {
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
                
                
                    public static Array<%5$s> list(SegmentAllocator allocator, String[] strings) {
                        return makeArray(allocator, Arrays.stream(strings));
                    }
                
                    public static Array<%5$s> list(SegmentAllocator allocator, Collection<String> strings) {
                        return makeArray(allocator, strings.stream());
                    }
                
                    protected %5$s(MemorySegment ptr) {
                        this.ptr = ptr;
                    }
                
                    public %5$s(%6$s<? extends %7$s<?>> ptr) {
                        this(fitByteSize(ptr.operator().value()));
                    }
                
                    public %5$s(SegmentAllocator allocator, String s) {
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
                                ? "%5$s{ptr=" + ptr + '}'
                                : ptr.getString(0, StandardCharsets.UTF_8);
                    }
                
                    @Override
                    public ArrayOpI<%5$s, I8> operator() {
                        return new ArrayOpI<>() {
                            @Override
                            public Info.Operations<I8> elementOperation() {
                                return I8.OPERATIONS;
                            }
                
                            @Override
                            public %5$s reinterpret(long length) {
                                return new %5$s(ptr.reinterpret(length));
                            }
                
                            @Override
                            public %4$s<I8> pointerAt(long index) {
                                return new %4$s<>(ptr.asSlice(index, 1), I8.OPERATIONS);
                            }
                
                            @Override
                            public Info.Operations<%5$s> getOperations() {
                                return OPERATIONS;
                            }
                
                            @Override
                            public I8 pointee() {
                                return get(0);
                            }
                
                            @Override
                            public void setPointee(I8 pointee) {
                                set(0, pointee);
                            }
                
                            @Override
                            public List<%4$s<I8>> pointerList() {
                                return new %3$s.AbstractRandomAccessList<>() {
                                    @Override
                                    public %4$s<I8> get(int index) {
                                        return pointerAt(index);
                                    }
                
                                    @Override
                                    public int size() {
                                        return %5$s.this.size();
                                    }
                                };
                            }
                
                            @Override
                            public MemorySegment value() {
                                return ptr;
                            }
                        };
                    }
                }
                """.formatted(packagePath.makePackage(), imports,
                CommonTypes.SpecificTypes.ArrayOp.typeName(TypeAttr.NameType.RAW),// 3
                CommonTypes.BindTypes.Ptr.typeName(TypeAttr.NameType.RAW),
                CommonTypes.SpecificTypes.Str.typeName(TypeAttr.NameType.RAW),// 5
                CommonTypes.ValueInterface.PtrI.typeName(TypeAttr.NameType.RAW),
                CommonTypes.ValueInterface.I8I.typeName(TypeAttr.NameType.RAW)));// 7
    }

    private void genValueInterface(PackagePath path, CommonTypes.ValueInterface type, String imports) {
        if (type.getPrimitive().noJavaPrimitive()) {
            Utils.write(path, """
                    %1$s
                    %2$s
                    import java.lang.foreign.MemorySegment;
                    
                    public interface %3$s<I> {
                        Value.ValueOp<MemorySegment> operator();
                    }
                    """.formatted(path.makePackage(), imports, type.typeName(TypeAttr.NameType.RAW))); // 3
            return;
        }
        Utils.write(path, """
                %1$s
                %2$s
                
                public interface %3$s<I> extends Value<%4$s> {
                    static <I> %3$s<I> of(%5$s value) {
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
                
                    static <I> %3$s<I> of(%3$s<?> value) {
                        return of(value.operator().value());
                    }
                }
                """.formatted(path.makePackage(), imports, type.typeName(TypeAttr.NameType.RAW),
                type.getPrimitive().getBoxedTypeName(), type.getPrimitive().getPrimitiveTypeName())); // 5
    }

    public static final String PTR_MAKE_OPERATION_METHOD = "makeOperations";

    private void genBindTypes(PackagePath path, CommonTypes.BindTypes bindTypes, String imports) {
        if (bindTypes.getOperations().getValue().getPrimitive().noJavaPrimitive()) {
            Assert(bindTypes.getOperations().getValue().getPrimitive().getByteSize() == 16, " sizeof %s must be 16".formatted(bindTypes));
            var str = """
                    %1$s
                    
                    %2$s
                    import java.lang.foreign.MemorySegment;
                    import java.lang.foreign.SegmentAllocator;
                    import java.nio.ByteOrder;
                    
                    public class %3$s implements %4$s<%3$s>, Info<%3$s> {
                        public static final long BYTE_SIZE = 16;
                        public static final Operations<%3$s> OPERATIONS = new Operations<>((param, offset) -> new %3$s(param.asSlice(offset)),
                                (source, dest, offset) -> MemoryUtils.memcpy(source.val, 0, dest, offset, BYTE_SIZE), BYTE_SIZE);
                        private final MemorySegment val;
                    
                        public %3$s(MemorySegment val) {
                            this.val = val;
                        }
                    
                        public %3$s(long low, long high) {
                            this.val = MemorySegment.ofArray(new long[2]);
                            val.asByteBuffer().order(ByteOrder.nativeOrder()).putLong(low).putLong(high);
                        }
                    
                        public static Array<%3$s> list(SegmentAllocator allocator, int len) {
                            return new Array<>(allocator, OPERATIONS, len);
                        }
                    
                        @Override
                        public %4$sI<%3$s> operator() {
                            return new %4$sI<>() {
                                @Override
                                public Operations<%3$s> getOperations() {
                                    return OPERATIONS;
                                }
                    
                                @Override
                                public MemorySegment value() {
                                    return val;
                                }
                            };
                        }
                    
                        @Override
                        public String toString() {
                            return String.valueOf(val);
                        }
                    }
                    """.formatted(path.makePackage(), imports, bindTypes.typeName(TypeAttr.NameType.RAW), // 3
                    bindTypes.getOperations().typeName(TypeAttr.NameType.RAW));
            Utils.write(path, str);
            return;
        }
        if (bindTypes != CommonTypes.BindTypes.Ptr) {
            genValueBasedTypes(path, bindTypes, imports, bindTypes.typeName(TypeAttr.NameType.RAW));
            return;
        }
        var str = """
                %1$s
                
                %2$s
                public class %3$s<E> implements %5$s<%3$s<E>, E>, Info<%3$s<E>> {
                    public static final long BYTE_SIZE = ValueLayout.ADDRESS.byteSize();
                    public static <I> Info.Operations<%3$s<I>> makeOperations(Info.Operations<I> operation) {
                        return new Info.Operations<>(
                                (param, offset) -> new %3$s<>(param.get(ValueLayout.ADDRESS, offset), operation),
                                (source, dest, offset) -> dest.set(ValueLayout.ADDRESS, offset, source.segment), BYTE_SIZE);
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
                
                    public %3$s(%4$s<E> pointee, Info.Operations<E> operation) {
                        this.operation = operation;
                        this.segment = fitByteSize(pointee.operator().value());
                    }
                
                    @Override
                    public String toString() {
                        return "%3$s{" +
                                "segment=" + segment +
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
                            public void setPointee(E pointee) {
                                operation.copy().copyTo(pointee, segment, 0);
                            }
                
                            @Override
                            public E pointee() {
                                return operation.constructor().create(segment, 0);
                            }
                
                            @Override
                            public Info.Operations<%3$s<E>> getOperations() {
                                return makeOperations(operation);
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
        Utils.write(path, str);
    }

    static void genValueBasedTypes(PackagePath path, CommonTypes.BindTypes bindTypes, String imports, String typeName) {
        var str = """
                %1$s
                
                %2$s
                public class %3$s implements %5$s<%3$s>, Info<%3$s> {
                    public static final Info.Operations<%3$s> OPERATIONS = %5$s.makeOperations(%3$s::new);;
                    public static final long BYTE_SIZE = OPERATIONS.byteSize();
                    private final %6$s val;
                
                    public %3$s(%6$s val) {
                        this.val = val;
                    }
                
                    public static Array<%3$s> list(SegmentAllocator allocator, int len) {
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
                """.formatted(path.makePackage(), imports, typeName,
                bindTypes.getPrimitiveType().getMemoryLayout(), // 4
                bindTypes.getOperations().typeName(TypeAttr.NameType.RAW), // 5
                bindTypes.getPrimitiveType().getPrimitiveTypeName(),
                bindTypes.getPrimitiveType().getBoxedTypeName());
        Utils.write(path, str);
    }

    private void genFunctionUtils(PackagePath path) {
        Utils.write(path, """
                %s
                
                import java.lang.foreign.Arena;
                import java.lang.foreign.FunctionDescriptor;
                import java.lang.foreign.Linker;
                import java.lang.foreign.MemorySegment;
                import java.lang.invoke.MethodHandle;
                import java.lang.reflect.Modifier;
                import java.util.Optional;
                
                public class %s {
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
                
                    public static <E> String enumToString(Class<?> klass, E e) {
                        for (var field : klass.getFields()) {
                            try {
                                if (Modifier.isStatic(field.getModifiers()) && e.equals(field.get(null))) {
                                    return field.getName();
                                }
                            } catch (IllegalAccessException _) {
                            }
                        }
                        return null;
                    }
                
                    public sealed interface Symbol {
                        MemorySegment getSymbol();
                    }
                
                    public record FunctionSymbol(MemorySegment ms, boolean critical) implements Symbol {
                        public FunctionSymbol(MemorySegment ms) {
                            this(ms, false);
                        }
                
                        @Override
                        public MemorySegment getSymbol() {
                            return ms;
                        }
                    }
                
                    public record VariableSymbol(MemorySegment ms) implements Symbol {
                        @Override
                        public MemorySegment getSymbol() {
                            return ms;
                        }
                    }
                
                
                    public interface SymbolProvider {
                        Optional<Symbol> provide(String name);
                    }
                
                    public static MemorySegment upcallStub(Arena arena, MethodHandle methodHandle, FunctionDescriptor functionDescriptor) {
                        return Linker.nativeLinker().upcallStub(methodHandle, functionDescriptor, arena);
                    }
                
                    public static MethodHandle downcallHandle(MemorySegment ms, FunctionDescriptor fd, boolean critical) {
                        return critical ?
                                Linker.nativeLinker().downcallHandle(ms, fd, Linker.Option.critical(true))
                                : Linker.nativeLinker().downcallHandle(ms, fd);
                    }
                }
                """.formatted(path.makePackage(), CommonTypes.SpecificTypes.FunctionUtils.getRawName()));
    }

    private void genMemoryUtils(PackagePath path, String imports) {
        Utils.write(path, """
                %1$s
                
                %3$s
                import java.util.Objects;
                
                public final class %2$s {
                    public interface MemorySupport {
                        void setByte(MemorySegment ms, long offset, byte val);
                
                        void setShort(MemorySegment ms, long offset, short val);
                
                        void setInt(MemorySegment ms, long offset, int val);
                
                        void setLong(MemorySegment ms, long offset, long val);
                
                        void setAddr(MemorySegment ms, long offset, MemorySegment val);
                
                        void setFloat(MemorySegment ms, long offset, float val);
                
                        void setDouble(MemorySegment ms, long offset, double val);
                
                        byte getByte(MemorySegment ms, long offset);
                
                        short getShort(MemorySegment ms, long offset);
                
                        int getInt(MemorySegment ms, long offset);
                
                        long getLong(MemorySegment ms, long offset);
                
                        MemorySegment getAddr(MemorySegment ms, long offset);
                
                        float getFloat(MemorySegment ms, long offset);
                
                        double getDouble(MemorySegment ms, long offset);
                
                        void memcpy(MemorySegment src, long srcOffset, MemorySegment dest, long destOffset, long byteSize);
                    }
                
                    public static MemorySupport ms = new MemorySupport() {
                        @Override
                        public void setByte(MemorySegment ms, long offset, byte val) {
                            ms.set(ValueLayout.JAVA_BYTE, offset, val);
                        }
                
                        @Override
                        public void setShort(MemorySegment ms, long offset, short val) {
                            ms.set(ValueLayout.JAVA_SHORT, offset, val);
                        }
                
                        @Override
                        public void setInt(MemorySegment ms, long offset, int val) {
                            ms.set(ValueLayout.JAVA_INT, offset, val);
                        }
                
                        @Override
                        public void setLong(MemorySegment ms, long offset, long val) {
                            ms.set(ValueLayout.JAVA_LONG, offset, val);
                        }
                
                        @Override
                        public void setAddr(MemorySegment ms, long offset, MemorySegment val) {
                            ms.set(ValueLayout.ADDRESS, offset, val);
                        }
                
                        @Override
                        public void setFloat(MemorySegment ms, long offset, float val) {
                            ms.set(ValueLayout.JAVA_FLOAT, offset, val);
                        }
                
                        @Override
                        public void setDouble(MemorySegment ms, long offset, double val) {
                            ms.set(ValueLayout.JAVA_DOUBLE, offset, val);
                        }
                
                        @Override
                        public byte getByte(MemorySegment ms, long offset) {
                            return ms.get(ValueLayout.JAVA_BYTE, offset);
                        }
                
                        @Override
                        public short getShort(MemorySegment ms, long offset) {
                            return ms.get(ValueLayout.JAVA_SHORT, offset);
                        }
                
                        @Override
                        public int getInt(MemorySegment ms, long offset) {
                            return ms.get(ValueLayout.JAVA_INT, offset);
                        }
                
                        @Override
                        public long getLong(MemorySegment ms, long offset) {
                            return ms.get(ValueLayout.JAVA_LONG, offset);
                        }
                
                        @Override
                        public MemorySegment getAddr(MemorySegment ms, long offset) {
                            return ms.get(ValueLayout.ADDRESS, offset);
                        }
                
                        @Override
                        public float getFloat(MemorySegment ms, long offset) {
                            return ms.get(ValueLayout.JAVA_FLOAT, offset);
                        }
                
                        @Override
                        public double getDouble(MemorySegment ms, long offset) {
                            return ms.get(ValueLayout.JAVA_DOUBLE, offset);
                        }
                
                        @Override
                        public void memcpy(MemorySegment src, long srcOffset, MemorySegment dest, long destOffset, long byteSize) {
                            MemorySegment.copy(src, srcOffset, dest, destOffset, byteSize);
                        }
                    };
                
                    private static final class MemorySupportHolder {
                        private static final MemorySupport MEMORY_SUPPORT = Objects.requireNonNull(ms);
                    }
                
                    public static void setByte(MemorySegment ms, long offset, byte val) {
                        MemorySupportHolder.MEMORY_SUPPORT.setByte(ms, offset, val);
                    }
                
                    public static void setShort(MemorySegment ms, long offset, short val) {
                        MemorySupportHolder.MEMORY_SUPPORT.setShort(ms, offset, val);
                    }
                
                    public static void setInt(MemorySegment ms, long offset, int val) {
                        MemorySupportHolder.MEMORY_SUPPORT.setInt(ms, offset, val);
                    }
                
                    public static void setLong(MemorySegment ms, long offset, long val) {
                        MemorySupportHolder.MEMORY_SUPPORT.setLong(ms, offset, val);
                    }
                
                    public static void setAddr(MemorySegment ms, long offset, MemorySegment val) {
                        MemorySupportHolder.MEMORY_SUPPORT.setAddr(ms, offset, val);
                    }
                
                    public static void setFloat(MemorySegment ms, long offset, float val) {
                        MemorySupportHolder.MEMORY_SUPPORT.setFloat(ms, offset, val);
                    }
                
                    public static void setDouble(MemorySegment ms, long offset, double val) {
                        MemorySupportHolder.MEMORY_SUPPORT.setDouble(ms, offset, val);
                    }
                
                    public static byte getByte(MemorySegment ms, long offset) {
                        return MemorySupportHolder.MEMORY_SUPPORT.getByte(ms, offset);
                    }
                
                    public static short getShort(MemorySegment ms, long offset) {
                        return MemorySupportHolder.MEMORY_SUPPORT.getShort(ms, offset);
                    }
                
                    public static int getInt(MemorySegment ms, long offset) {
                        return MemorySupportHolder.MEMORY_SUPPORT.getInt(ms, offset);
                    }
                
                    public static long getLong(MemorySegment ms, long offset) {
                        return MemorySupportHolder.MEMORY_SUPPORT.getLong(ms, offset);
                    }
                
                    public static MemorySegment getAddr(MemorySegment ms, long offset) {
                        return MemorySupportHolder.MEMORY_SUPPORT.getAddr(ms, offset);
                    }
                
                    public static float getFloat(MemorySegment ms, long offset) {
                        return MemorySupportHolder.MEMORY_SUPPORT.getFloat(ms, offset);
                    }
                
                    public static double getDouble(MemorySegment ms, long offset) {
                        return MemorySupportHolder.MEMORY_SUPPORT.getDouble(ms, offset);
                    }
                
                    public static void memcpy(MemorySegment src, long srcOffset, MemorySegment dest, long destOffset, long byteSize) {
                        MemorySupportHolder.MEMORY_SUPPORT.memcpy(src, srcOffset, dest, destOffset, byteSize);
                    }
                }
                """.formatted(path.makePackage(), path.getClassName(), imports));
    }
}
