package generator.generation.generator;

import generator.Dependency;
import generator.Utils;
import generator.generation.FuncPointer;
import generator.types.CommonTypes;
import generator.types.FunctionPtrType;

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
        FunctionPtrType type = funcPointer.getTypePkg().type();
        String className = funcPointer.getTypePkg().packagePath().getClassName();

        String out = funcPointer.getTypePkg().packagePath().makePackage();
        out += Generator.extractImports(funcPointer, dependency);
        String interfaces = """
                    public interface Function {
                        %3$s %1$s(%2$s);
                    }
                
                    public interface FunctionWrapped {
                        %4$s %1$s(%5$s);
                    }
                """.formatted(className, FuncPtrUtils.makeDirectPara(type, true), FuncPtrUtils.makeDirectRetType(type),
                FuncPtrUtils.makeWrappedRetType(type), FuncPtrUtils.makeWrappedPara(type, true));

        String constructors = """
                    public %1$s(Arena funcLifeTime, Function function) {
                        try {
                            methodHandle = MethodHandles.lookup().findVirtual(Function.class,
                                    "%1$s", FUNCTIONDESCRIPTOR.toMethodType()).bindTo(function);
                            funPtr = Utils.upcallStub(funcLifeTime, methodHandle, FUNCTIONDESCRIPTOR);
                        } catch (NoSuchMethodException | IllegalAccessException e) {
                            throw new Utils.SymbolNotFound(e);
                        }
                    }
                
                    public %1$s(Arena funcLifeTime, FunctionWrapped function) {
                        this(funcLifeTime, (Function) (%2$s)
                                -> %3$s);
                    }
                """.formatted(className, FuncPtrUtils.makeInvokeStr(type),
                FuncPtrUtils.makeWrappedRetDestruct("function.%s(%s)"
                        .formatted(className, FuncPtrUtils.makeWrappedParaConstruct(type)), type));

        String invokes = """
                    public %1$s invoke(%2$s) {
                        try {
                            %3$s methodHandle.invokeExact(%4$s);
                        } catch (Throwable e) {
                            throw new Utils.InvokeException(e);
                        }
                    }
                
                    public %5$s invokeWrapped(%6$s) {
                        %7$s;
                    }
                """.formatted(FuncPtrUtils.makeDirectRetType(type), FuncPtrUtils.makeDirectPara(type, false),
                FuncPtrUtils.makeStrBeforeInvoke(type), FuncPtrUtils.makeInvokeStr(type), // 4
                FuncPtrUtils.makeWrappedRetType(type), FuncPtrUtils.makeWrappedPara(type, false), // 6
                FuncPtrUtils.makeStrForInvoke("invoke(%s)".formatted(FuncPtrUtils.makeWrappedParaDestruct(type)), type));

        out += make(funcPointer, interfaces, constructors, invokes);
        Utils.write(funcPointer.getTypePkg().packagePath().getFilePath(), out);
    }

    private String make(FuncPointer type, String interfaces, String constructors, String invokes) {
        return """
                public class %1$s implements PtrOp<%1$s, %1$s.Function>, Info<%1$s> {
                    public static final Operations<%1$s> OPERATIONS = PtrOp.makeOperations(%1$s::new);
                    public static final FunctionDescriptor FUNCTIONDESCRIPTOR = %2$s;
                %3$s
                
                    private final MemorySegment funPtr;
                    private final MethodHandle methodHandle;
                
                %4$s
                
                    public %1$s(MemorySegment funPtr) {
                        this.funPtr = funPtr;
                        methodHandle = Utils.downcallHandle(funPtr, FUNCTIONDESCRIPTOR, true);
                    }
                
                %5$s
                
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
                }""".formatted(type.getTypePkg().packagePath().getClassName(),
                FuncPtrUtils.makeFuncDescriptor(type.getTypePkg().type()),
                interfaces, constructors, invokes
        );
    }
}
