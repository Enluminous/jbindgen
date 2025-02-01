package generator.generation.generator;

import generator.Dependency;
import generator.Utils;
import generator.generation.FuncPointer;
import generator.types.CommonTypes;

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
        out += makeMain(funcPointer);
        Utils.write(funcPointer.getTypePkg().packagePath().getFilePath(), out);
    }

    private String makeMain(FuncPointer type) {
        return """
                public class %1$s implements PtrOp<%1$s, %1$s.Function>, Info<%1$s> {
                    public static final Operations<%1$s> OPERATIONS = PtrOp.makeOperations(%1$s::new);
                    public static final FunctionDescriptor FUNCTIONDESCRIPTOR = %2$s;
                
                    public interface Function {
                        %3$s %1$s(%4$s);
                    }
                
                    private final MemorySegment funPtr;
                    private final MethodHandle methodHandle;
                
                    public %1$s(Arena funcLifeTime, Function function) {
                        try {
                            methodHandle = MethodHandles.lookup().findVirtual(Function.class,
                                    "%1$s", FUNCTIONDESCRIPTOR.toMethodType()).bindTo(function);
                            funPtr = Utils.upcallStub(funcLifeTime, methodHandle, FUNCTIONDESCRIPTOR);
                        } catch (NoSuchMethodException | IllegalAccessException e) {
                            throw new Utils.SymbolNotFound(e);
                        }
                    }
                
                    public %1$s(MemorySegment funPtr) {
                        this.funPtr = funPtr;
                        methodHandle = Utils.downcallHandle(funPtr, FUNCTIONDESCRIPTOR, true);
                    }
                
                    public %3$s invoke(%4$s) {
                        try {
                            %5$s methodHandle.invokeExact(%6$s);
                        } catch (Throwable e) {
                            throw new Utils.InvokeException(e);
                        }
                    }
                
                    @Override
                    public PtrOpI<%1$s, Function> operator() {
                        return new PtrOpI<>() {
                            @Override
                            public Operations<Function> elementOperation() {
                                throw new UnsupportedOperationException();
                            }
                
                            @Override
                            public void setPointee(Function pointee) {
                                throw new UnsupportedOperationException();
                            }
                
                            @Override
                            public Operations<%1$s> getOperations() {
                                return OPERATIONS;
                            }
                
                            @Override
                            public Function pointee() {
                                throw new UnsupportedOperationException();
                            }
                
                            @Override
                            public MemorySegment value() {
                                return funPtr;
                            }
                        };
                    }
                }""".formatted(type.getTypePkg().packagePath().getClassName(), // 1
                FuncPtrUtils.makeFuncDescriptor(type.getTypePkg().type()),     // 2
                FuncPtrUtils.makeRetType(type.getTypePkg().type()),            // 3
                FuncPtrUtils.makeDirectPara(type.getTypePkg().type()),         // 4
                FuncPtrUtils.makeStrBeforeInvoke(type.getTypePkg().type()),     // 5
                FuncPtrUtils.makeInvokeStr(type.getTypePkg().type())             // 6
        );
    }
}
