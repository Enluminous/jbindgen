package generator.generation.generator;

import generator.Dependency;
import generator.Utils;
import generator.generation.Structure;
import generator.types.CommonTypes;
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
            stringBuilder.append(getterAndSetter.getter).append(System.lineSeparator()).append(getterAndSetter.setter);
        }
        String out = structure.getTypePkg().packagePath().makePackage();
        out += Generator.extractImports(structure, dependency);
        out += getMain(Generator.getTypeName(structType), structType.getByteSize(), stringBuilder.toString());

        Utils.write(structure.getTypePkg().packagePath().getFilePath(), out);
    }

    record GetterAndSetter(String getter, String setter) {
    }

    private static GetterAndSetter getterAndSetter(String thisName, StructType.Member member) {
        OperationAttr.Operation operation = ((TypeAttr.OperationType) member.type()).getOperation();
        String memberName = member.name();
        MemoryOperation.Getter getter = operation.getMemoryOperation().getter("ms", member.offset() / 8);
        MemoryOperation.Setter setter = operation.getMemoryOperation().setter("ms", member.offset() / 8, memberName);
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
                public final class %1$s implements %5$s<%1$s> {
                   public static final int BYTE_SIZE = %2$s;
                   private final MemorySegment ms;
                   private static final Operations<%1$s> OPERATIONS = %5$s.makeOperations(%1$s::new, BYTE_SIZE);
                
                   public %1$s(MemorySegment ms) {
                       this.ms = ms;
                   }
                
                   @Override
                   public StructOpI<%1$s> operator() {
                       return new StructOpI<>() {
                           @Override
                           public %1$s reinterpret() {
                               return new %1$s(ms.reinterpret(BYTE_SIZE));
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
                }""".formatted(className, byteSize, ext,
                CommonTypes.BindTypes.Ptr.typeName(TypeAttr.NameType.RAW), CommonTypes.SpecificTypes.StructOp.typeName(TypeAttr.NameType.RAW));
    }
}
