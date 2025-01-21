package generator.generation.generator;

import generator.Dependency;
import generator.Utils;
import generator.generation.Structure;
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
        StructType structType = structure.getStructType().type();
        for (StructType.Member member : structType.getMembers()) {
            GetterAndSetter getterAndSetter = getterAndSetter(Generator.getTypeName(structType), member);
            stringBuilder.append(getterAndSetter.getter).append(System.lineSeparator()).append(getterAndSetter.setter);
        }
        String out = structure.getTypePkg().packagePath().makePackage();
        out += Generator.extractImports(structure, dependency);
        out += getMain(Generator.getTypeName(structType), structType.getByteSize(), stringBuilder.toString());

        Utils.write(structure.getStructType().packagePath().getFilePath(), out);
    }

    record GetterAndSetter(String getter, String setter) {
    }

    private static GetterAndSetter getterAndSetter(String thisName, StructType.Member member) {
        OperationAttr.Operation operation = ((TypeAttr.OperationType) member.type()).getOperation();
        String memberName = member.name();
        MemoryOperation.Getter getter = operation.getMemoryOperation().getter("ptr", member.offset() / 8);
        MemoryOperation.Setter setter = operation.getMemoryOperation().setter("ptr", member.offset() / 8, memberName);
        return new GetterAndSetter("""
                    %s %s(%s){
                        return %s;
                    }
                """.formatted(getter.ret(), memberName, getter.para(), getter.codeSegment()),
                """
                            %s %s(%s){
                                %s;
                                return this;
                            }
                        """.formatted(thisName, memberName, setter.para(), setter.codeSegment()));
    }

    private static String getMain(String className, long byteSize, String ext) {
        return """
                public final class %1$s implements Pointer<%1$s> {
                    public static final MemoryLayout MEMORY_LAYOUT =  MemoryLayout.structLayout(MemoryLayout.sequenceLayout(%2$s, AddressLayout.JAVA_BYTE));;
                    public static final long BYTE_SIZE = MEMORY_LAYOUT.byteSize();
                
                    public static NList<%1$s> list(Pointer<%1$s> ptr) {
                        return new NList<>(ptr, %1$s::new, BYTE_SIZE);
                    }
                
                    public static NList<%1$s> list(Pointer<%1$s> ptr, long length) {
                        return new NList<>(ptr, length, %1$s::new, BYTE_SIZE);
                    }
                
                    public static NList<%1$s> list(SegmentAllocator allocator, long length) {
                        return new NList<>(allocator, length, %1$s::new, BYTE_SIZE);
                    }
                
                    private final MemorySegment ptr;
                
                    public %1$s(Pointer<%1$s> ptr) {
                        this.ptr = ptr.value();
                    }
                
                    public %1$s(MemorySegment ms) {
                        this.ptr = ms;
                    }
                
                    public %1$s(SegmentAllocator allocator) {
                        ptr = allocator.allocate(BYTE_SIZE);
                    }
                
                    public %1$s reinterpretSize() {
                        return new %1$s(FunctionUtils.makePointer(ptr.reinterpret(BYTE_SIZE)));
                    }
                
                    @Override
                    public MemorySegment value() {
                        return ptr;
                    }
                
                %3$s
                }""".formatted(className, byteSize, ext);
    }
}
