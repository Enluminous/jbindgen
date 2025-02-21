package generator.generation.generator;

import generator.Dependency;
import generator.Utils;
import generator.generation.Structure;
import generator.types.CommonTypes;
import generator.types.MemoryLayouts;
import generator.types.StructType;
import generator.types.TypeAttr;
import generator.types.operations.MemoryOperation;
import generator.types.operations.OperationAttr;

public class StructGenerator implements Generator {
    private final Structure structure;
    private final Dependency dependency;

    public StructGenerator(Structure struct, Dependency dependency) {
        this.structure = struct;
        this.dependency = dependency;
    }

    @Override
    public void generate() {
        StringBuilder stringBuilder = new StringBuilder();
        StructType structType = structure.getTypePkg().type();
        for (StructType.Member member : structType.getMembers()) {
            GetterAndSetter getterAndSetter = getterAndSetter(Generator.getTypeName(structType), member);
            stringBuilder.append(getterAndSetter.getter)
                    .append(System.lineSeparator())
                    .append(getterAndSetter.setter);
        }
        String out = structure.getTypePkg().packagePath().makePackage();
        out += Generator.extractImports(structure, dependency);
        out += getMain(Generator.getTypeName(structType), structType.getMemoryLayout(),
                stringBuilder + toString(structType));
        Utils.write(structure.getTypePkg().packagePath(), out);
    }

    record GetterAndSetter(String getter, String setter) {
    }

    private static String toString(StructType s) {
        var ss = s.getMembers().stream().filter(member -> !member.bitField()).map(member -> """
                %s=" + %s() +
                """.formatted(member.name(), member.name())).toList();
        return """
                    @Override
                    public String toString() {
                        return ms.address() == 0 ? ms.toString()
                                : "%s{" +
                                %s                '}';
                    }
                """.formatted(Generator.getTypeName(s), ss.isEmpty() ? "" : "\"" + String.join("                \", ", ss));
    }

    private static GetterAndSetter getterAndSetter(String thisName, StructType.Member member) {
        if (member.bitField()) {
            return new GetterAndSetter("", "");
        }
        OperationAttr.Operation operation = ((TypeAttr.OperationType) member.type()).getOperation();
        String memberName = member.name();
        MemoryOperation.Getter getter = operation.getMemoryOperation().getter("ms", member.offset() / 8);
        MemoryOperation.Setter setter = operation.getMemoryOperation().setter("ms", member.offset() / 8, memberName);
        return new GetterAndSetter("""
                    public %s %s(%s) {
                        return %s;
                    }
                """.formatted(getter.ret(), memberName, getter.para(), getter.codeSegment()),
                """
                            public %s %s(%s) {
                                %s;
                                return this;
                            }
                        """.formatted(thisName, memberName, setter.para(), setter.codeSegment()));
    }

    private static String getMain(String className, MemoryLayouts layout, String ext) {
        return """
                public final class %1$s implements %5$s<%1$s>, Info<%1$s> {
                    private final MemorySegment ms;
                    public static final Operations<%1$s> OPERATIONS = %5$s.makeOperations(%1$s::new, %2$s);
                
                    public %1$s(MemorySegment ms) {
                        this.ms = ms;
                    }
                
                    public %1$s(SegmentAllocator allocator) {
                        this.ms = allocator.allocate(OPERATIONS.memoryLayout().byteSize());
                    }
                
                    public static Array<%1$s> list(SegmentAllocator allocator, long len) {
                        return new Array<>(allocator, %1$s.OPERATIONS, len);
                    }
                
                    @Override
                    public StructOpI<%1$s> operator() {
                        return new StructOpI<>() {
                            @Override
                            public %1$s reinterpret() {
                                return new %1$s(ms.reinterpret(OPERATIONS.memoryLayout().byteSize()));
                            }
                
                            @Override
                            public %4$s<%1$s> getPointer() {
                                return new %4$s<>(ms, OPERATIONS);
                            }
                
                            @Override
                            public Operations<%1$s> getOperations() {
                                return OPERATIONS;
                            }
                
                            @Override
                            public MemorySegment value() {
                                return ms;
                            }
                        };
                    }
                
                %3$s
                }""".formatted(className, layout.getMemoryLayout(), ext,
                CommonTypes.BindTypes.Ptr.typeName(TypeAttr.NameType.RAW), CommonTypes.SpecificTypes.StructOp.typeName(TypeAttr.NameType.RAW));
    }
}
