package generator.generation.generator;

import generator.Dependency;
import generator.PackagePath;
import generator.Utils;
import generator.generation.ValueBased;
import generator.types.CommonTypes;
import generator.types.PointerType;
import generator.types.TypeAttr;
import generator.types.ValueBasedType;

public class ValueBasedGenerator implements Generator {
    private final Dependency dependency;
    private final ValueBased valueBased;

    public ValueBasedGenerator(ValueBased v, Dependency dependency) {
        this.dependency = dependency;
        this.valueBased = v;
    }

    @Override
    public void generate() {
        makeValue(valueBased.getTypePkg().packagePath(), valueBased.getTypePkg().type(), Generator.extractImports(valueBased, dependency));
    }

    private static void makeValue(PackagePath path, ValueBasedType type, String imports) {
        String typeName = Generator.getTypeName(type);
        CommonTypes.BindTypes bindTypes = type.getBindTypes();
        if (bindTypes != CommonTypes.BindTypes.Ptr) {
            CommonGenerator.genValueBasedTypes(path, type.getBindTypes(), imports, type.typeName(TypeAttr.NameType.RAW));
            return;
        }
        PointerType pointerType = type.getPointerType().orElseThrow();
        var pointee = ((TypeAttr.OperationType) pointerType.pointee());
        String pointeeName = Generator.getTypeName(pointerType.pointee());
        Utils.write(path.getFilePath(), """
                %1$s
                
                %2$s
                import java.lang.foreign.MemorySegment;
                
                public class %3$s implements %5$s<%3$s, %4$s>, Info<%3$s> {
                    public static final Operations<%4$s> ELEMENT_OPERATIONS = %6$s;
                    public static final Operations<%3$s> OPERATIONS = %5$s.makeOperations(%3$s::new);
                    public static final long BYTE_SIZE = OPERATIONS.byteSize();
                
                    private final MemorySegment segment;
                
                    private MemorySegment fitByteSize(MemorySegment segment) {
                        return segment.byteSize() == ELEMENT_OPERATIONS.byteSize() ? segment : segment.reinterpret(ELEMENT_OPERATIONS.byteSize());
                    }
                
                    public %3$s(MemorySegment segment) {
                        this.segment = fitByteSize(segment);
                    }
                
                    public %3$s(%9$s<?, %4$s> arrayOperation) {
                        this.segment = fitByteSize(arrayOperation.operator().value());
                    }
                
                    public %3$s(Value<MemorySegment> pointee) {
                        this.segment = fitByteSize(pointee.operator().value());
                    }
                
                    public %3$s(%7$s<%4$s> pointee) {
                        this.segment = fitByteSize(pointee.operator().value());
                    }
                
                    @Override
                    public String toString() {
                        return "%3$s{" +
                               "pointee=" + operator().pointee() +
                               '}';
                    }
                
                    @Override
                    public %8$s<%3$s, %4$s> operator() {
                        return new %8$s<>() {
                            @Override
                            public MemorySegment value() {
                                return segment;
                            }
                
                            @Override
                            public %4$s pointee() {
                                return ELEMENT_OPERATIONS.constructor().create(segment, 0);
                            }
                
                            @Override
                            public Operations<%3$s> getOperations() {
                                return OPERATIONS;
                            }
                
                            @Override
                            public Operations<%4$s> elementOperation() {
                                return ELEMENT_OPERATIONS;
                            }
                
                            @Override
                            public void setPointee(%4$s pointee) {
                                ELEMENT_OPERATIONS.copy().copyTo(pointee, segment, 0);
                            }
                        };
                    }
                }
                """.formatted(path.makePackage(), imports, typeName, pointeeName,
                CommonTypes.BindTypeOperations.PtrOp.typeName(TypeAttr.NameType.RAW), // 5
                pointee.getOperation().getCommonOperation().makeOperation(),
                CommonTypes.ValueInterface.PtrI.typeName(TypeAttr.NameType.RAW), // 7
                CommonTypes.BindTypeOperations.PtrOp.operatorTypeName(), // 8
                CommonTypes.SpecificTypes.ArrayOp.typeName(TypeAttr.NameType.RAW) // 8
        ));
    }
}
