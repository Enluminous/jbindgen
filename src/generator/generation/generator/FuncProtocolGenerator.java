package generator.generation.generator;

import generator.Dependency;
import generator.Utils;
import generator.generation.FuncPointer;
import generator.types.CommonTypes;
import generator.types.FunctionPtrType;

import static utils.CommonUtils.Assert;

public class FuncProtocolGenerator implements Generator {
    private final FuncPointer funcPointer;
    private final Dependency dependency;
    private final String utilsClassName;

    public FuncProtocolGenerator(FuncPointer funcPointer, Dependency dependency) {
        this.funcPointer = funcPointer;
        this.dependency = dependency;
        utilsClassName = dependency.getTypePackagePath(CommonTypes.SpecificTypes.Utils).getClassName();
    }

    @Override
    public void generate() {
        String out = funcPointer.getTypePkg().packagePath().makePackage();
        out += Generator.extractImports(funcPointer, dependency);
        FunctionPtrType type = funcPointer.getTypePkg().type();
        out += makeDirectCall(Generator.getTypeName(type), FuncPtrUtils.makeRetType(type), FuncPtrUtils.makeFuncDescriptor(type),
                utilsClassName, FuncPtrUtils.makeDirectPara(type));
        Utils.write(funcPointer.getTypePkg().packagePath().getFilePath(), out);
    }

    private static String makeDirectCall(String funcName, String retType, String funcDescriptor,
                                         String utilsClassName, String para) {
        return """
                @FunctionalInterface
                public interface %1$s {
                    %2$s function(%3$s);
                
                    static Pointer<%1$s> toPointer(%1$s func,Arena arena) throws Utils.SymbolNotFound {
                        return () -> {
                            try {
                                FunctionDescriptor functionDescriptor = %4$s;
                                return %5$s.toMemorySegment(arena, MethodHandles.lookup().findVirtual(%1$s.class, "function", functionDescriptor.toMethodType()).bindTo(func), functionDescriptor);
                            } catch (NoSuchMethodException | IllegalAccessException e) {
                                throw new %5$s.SymbolNotFound(e);
                            }
                        };
                    }
                }""".formatted(funcName, retType, para, funcDescriptor, utilsClassName);
    }
}
