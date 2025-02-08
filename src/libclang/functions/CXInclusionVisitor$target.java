package libclang.functions;
import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import libclang.common.I32;
import libclang.common.I32I;
import libclang.common.Info;
import libclang.common.Operation;
import libclang.common.Ptr;
import libclang.common.PtrI;
import libclang.common.PtrOp;
import libclang.common.StructI;
import libclang.common.Utils;
import libclang.structs.CXSourceLocation;
import libclang.values.CXClientData;
import libclang.values.CXFile;
public class CXInclusionVisitor$target implements PtrOp<CXInclusionVisitor$target, CXInclusionVisitor$target.Function>, Info<CXInclusionVisitor$target> {
    public static final Operations<CXInclusionVisitor$target> OPERATIONS = PtrOp.makeOperations(CXInclusionVisitor$target::new);
    public static final FunctionDescriptor FUNCTIONDESCRIPTOR = FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.ADDRESS);

    public interface FunctionRaw {
        void CXInclusionVisitor$target(MemorySegment included_file, MemorySegment inclusion_stack, int include_len, MemorySegment client_data);
    }

    public interface Function {
        void CXInclusionVisitor$target(CXFile included_file, Ptr<CXSourceLocation> inclusion_stack, I32 include_len, CXClientData client_data);
    }

    private final MemorySegment funPtr;
    private final MethodHandle methodHandle;

    public CXInclusionVisitor$target(Arena funcLifeTime, FunctionRaw function) {
        try {
            methodHandle = MethodHandles.lookup().findVirtual(FunctionRaw.class,
                    "CXInclusionVisitor$target", FUNCTIONDESCRIPTOR.toMethodType()).bindTo(function);
            funPtr = Utils.upcallStub(funcLifeTime, methodHandle, FUNCTIONDESCRIPTOR);
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new Utils.SymbolNotFound(e);
        }
    }

    public CXInclusionVisitor$target(Arena funcLifeTime, Function function) {
        this(funcLifeTime, (FunctionRaw) (included_file, inclusion_stack, include_len, client_data)
                -> function.CXInclusionVisitor$target(new CXFile(included_file), new Ptr<CXSourceLocation>(inclusion_stack, CXSourceLocation.OPERATIONS), new I32(include_len), new CXClientData(client_data)));
    }


    public CXInclusionVisitor$target(MemorySegment funPtr) {
        this.funPtr = funPtr;
        methodHandle = funPtr.address() == 0 ? null : Utils.downcallHandle(funPtr, FUNCTIONDESCRIPTOR, true);
    }

    public void invokeRaw(MemorySegment included_file, MemorySegment inclusion_stack, int include_len, MemorySegment client_data) {
        try {
             methodHandle.invokeExact(included_file, inclusion_stack, include_len, client_data);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public void invoke(CXFile included_file, PtrI<? extends StructI<? extends CXSourceLocation>> inclusion_stack, I32I<?> include_len, CXClientData client_data) {
        invokeRaw(included_file.operator().value(), inclusion_stack.operator().value(), include_len.operator().value(), client_data.operator().value());
    }


    @Override
    public PtrOpI<CXInclusionVisitor$target, Function> operator() {
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
            public Operations<CXInclusionVisitor$target> getOperations() {
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

    @Override
    public String toString() {
        return "CXInclusionVisitor$target{" +
                "funPtr=" + funPtr +
                ", methodHandle=" + methodHandle +
                '}';
    }

}