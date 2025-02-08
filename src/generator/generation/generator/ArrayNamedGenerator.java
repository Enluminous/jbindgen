package generator.generation.generator;

import generator.Dependency;
import generator.PackagePath;
import generator.Utils;
import generator.generation.ArrayNamed;
import generator.types.ArrayTypeNamed;
import generator.types.TypeAttr;

public class ArrayNamedGenerator implements Generator {
    private final Dependency dependency;
    private final ArrayNamed arrayNamed;

    public ArrayNamedGenerator(ArrayNamed v, Dependency dependency) {
        this.dependency = dependency;
        this.arrayNamed = v;
    }

    @Override
    public void generate() {
        PackagePath packagePath = arrayNamed.getTypePkg().packagePath();
        String out = packagePath.makePackage();
        out += Generator.extractImports(arrayNamed, dependency);
        out += makeValue(packagePath, arrayNamed.getTypePkg().type());
        Utils.write(packagePath, out);
    }

    private String makeValue(PackagePath packagePath, ArrayTypeNamed type) {
        return """
                import java.util.List;
                import java.util.Objects;
                
                public class %1$s extends ArrayOp.AbstractRandomAccessList<%2$s> implements ArrayOp<%1$s, %2$s>, Info<%1$s> {
                    public static final long BYTE_SIZE = ValueLayout.ADDRESS.byteSize();
                    public static final Operations<%2$s> ELE_OPERATIONS = %3$s;
                    public static final long LENGTH = %5$s;
                    public static final Operations<%1$s> OPERATIONS = new Operations<>(
                            (param, offset) -> new %1$s(MemoryUtils.getAddr(param, offset).reinterpret(LENGTH * ELE_OPERATIONS.byteSize())),
                            (source, dest, offset) -> MemoryUtils.setAddr(dest, offset, source.ms),
                            BYTE_SIZE
                    );
                
                    private final MemorySegment ms;
                
                    public %1$s(MemorySegment ms) {
                        this.ms = ms;
                    }
                
                    @Override
                    public FixedArrayOp<%1$s, %2$s> operator() {
                        return new FixedArrayOp<>() {
                            @Override
                            public %1$s reinterpret(long length) {
                                return new %1$s(ms.reinterpret(length * ELE_OPERATIONS.byteSize()));
                            }
                
                            @Override
                            public %1$s reinterpret() {
                                return new %1$s(ms.reinterpret(BYTE_SIZE));
                            }
                
                            @Override
                            public Ptr<%2$s> pointerAt(long index) {
                                Objects.checkIndex(index, size());
                                return new Ptr<>(ms.asSlice(index * ELE_OPERATIONS.byteSize(), ELE_OPERATIONS.byteSize()), ELE_OPERATIONS);
                            }
                
                            @Override
                            public List<Ptr<%2$s>> pointerList() {
                                return new AbstractRandomAccessList<>() {
                                    @Override
                                    public Ptr<%2$s> get(int index) {
                                        return pointerAt(index);
                                    }
                
                                    @Override
                                    public int size() {
                                        return %1$s.this.size();
                                    }
                                };
                            }
                
                            @Override
                            public Operations<%2$s> elementOperation() {
                                return ELE_OPERATIONS;
                            }
                
                            @Override
                            public void setPointee(%2$s pointee) {
                                set(0, pointee);
                            }
                
                            @Override
                            public Operations<%1$s> getOperations() {
                                return OPERATIONS;
                            }
                
                            @Override
                            public %2$s pointee() {
                                return get(0);
                            }
                
                            @Override
                            public MemorySegment value() {
                                return ms;
                            }
                        };
                    }
                
                    @Override
                    public %2$s set(int index, %2$s element) {
                        Objects.checkIndex(index, size());
                        ELE_OPERATIONS.copy().copyTo(element, ms, index * ELE_OPERATIONS.byteSize());
                        return element;
                    }
                
                    @Override
                    public %2$s get(int index) {
                        Objects.checkIndex(index, size());
                        return ELE_OPERATIONS.constructor().create(ms, index * ELE_OPERATIONS.byteSize());
                    }
                
                    @Override
                    public int size() {
                        return (int) (ms.byteSize() / ELE_OPERATIONS.byteSize());
                    }
                }""".formatted(packagePath.getClassName(), ((TypeAttr.NamedType) type.element()).typeName(TypeAttr.NameType.RAW),
                ((TypeAttr.OperationType) type.element()).getOperation().getCommonOperation().makeOperation().str(), // 3
                type.getByteSize(), type.length());
    }
}
