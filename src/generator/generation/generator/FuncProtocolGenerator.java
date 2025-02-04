package generator.generation.generator;

import generator.Dependency;
import generator.Utils;
import generator.generation.FuncPointer;
import generator.types.CommonTypes;
import generator.types.FunctionPtrType;

public class FuncProtocolGenerator implements Generator {
    public static final String FUNCTION_TYPE_NAME = "Function";
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
                    public interface %6$sRaw {
                        %3$s %1$s(%2$s);
                    }
                
                    public interface %6$s {
                        %4$s %1$s(%5$s);
                    }
                """.formatted(className, FuncPtrUtils.makeDirectPara(type, true), FuncPtrUtils.makeDirectRetType(type),
                FuncPtrUtils.makeWrappedRetType(type), FuncPtrUtils.makeWrappedPara(type, true), FUNCTION_TYPE_NAME);// 6

        String constructors = """
                    public %1$s(Arena funcLifeTime, %4$sRaw function) {
                        try {
                            methodHandle = MethodHandles.lookup().findVirtual(%4$sRaw.class,
                                    "%1$s", FUNCTIONDESCRIPTOR.toMethodType()).bindTo(function);
                            funPtr = Utils.upcallStub(funcLifeTime, methodHandle, FUNCTIONDESCRIPTOR);
                        } catch (NoSuchMethodException | IllegalAccessException e) {
                            throw new Utils.SymbolNotFound(e);
                        }
                    }
                
                    public %1$s(Arena funcLifeTime, %4$s function) {
                        this(funcLifeTime, (%4$sRaw) (%2$s)
                                -> %3$s);
                    }
                """.formatted(className, FuncPtrUtils.makeInvokeStr(type),
                FuncPtrUtils.makeWrappedRetDestruct("function.%s(%s)"
                        .formatted(className, FuncPtrUtils.makeWrappedParaConstruct(type)), type),
                FUNCTION_TYPE_NAME);

        String invokes = """
                    public %1$s invokeRaw(%2$s) {
                        try {
                            %3$s methodHandle.invokeExact(%4$s);
                        } catch (Throwable e) {
                            throw new Utils.InvokeException(e);
                        }
                    }
                
                    public %5$s invoke(%6$s) {
                        %7$s;
                    }
                """.formatted(FuncPtrUtils.makeDirectRetType(type), FuncPtrUtils.makeDirectPara(type, false),
                FuncPtrUtils.makeStrBeforeInvoke(type), FuncPtrUtils.makeInvokeStr(type), // 4
                FuncPtrUtils.makeWrappedRetType(type), FuncPtrUtils.makeUpperWrappedPara(type, false), // 6
                FuncPtrUtils.makeStrForInvoke("invokeRaw(%s)".formatted(FuncPtrUtils.makeUpperWrappedParaDestruct(type)), type));
        String toString = """
                    @Override
                    public String toString() {
                        return "%1$s{" +
                                "funPtr=" + funPtr +
                                ", methodHandle=" + methodHandle +
                                '}';
                    }
                """.formatted(className);
        out += make(funcPointer, interfaces, constructors, invokes, toString);
        Utils.write(funcPointer.getTypePkg().packagePath().getFilePath(), out);
    }

    private String make(FuncPointer type, String interfaces, String constructors, String invokes, String ext) {
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
                
                %6$s
                }""".formatted(type.getTypePkg().packagePath().getClassName(),
                FuncPtrUtils.makeFuncDescriptor(type.getTypePkg().type()),
                interfaces, constructors, invokes, ext // 6
        );
    }
}
