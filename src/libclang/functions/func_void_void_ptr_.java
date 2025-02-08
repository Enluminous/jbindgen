package libclang.functions;
import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import libclang.common.Info;
import libclang.common.Operation;
import libclang.common.Ptr;
import libclang.common.PtrI;
import libclang.common.PtrOp;
import libclang.common.Utils;
public class func_void_void_ptr_ implements PtrOp<func_void_void_ptr_, func_void_void_ptr_.Function>, Info<func_void_void_ptr_> {
    public static final Operations<func_void_void_ptr_> OPERATIONS = PtrOp.makeOperations(func_void_void_ptr_::new);
    public static final FunctionDescriptor FUNCTIONDESCRIPTOR = FunctionDescriptor.ofVoid(ValueLayout.ADDRESS);

    public interface FunctionRaw {
        void func_void_void_ptr_(MemorySegment arg0);
    }

    public interface Function {
        void func_void_void_ptr_(Ptr<Void> arg0);
    }

    private final MemorySegment funPtr;
    private final MethodHandle methodHandle;

    public func_void_void_ptr_(Arena funcLifeTime, FunctionRaw function) {
        try {
            methodHandle = MethodHandles.lookup().findVirtual(FunctionRaw.class,
                    "func_void_void_ptr_", FUNCTIONDESCRIPTOR.toMethodType()).bindTo(function);
            funPtr = Utils.upcallStub(funcLifeTime, methodHandle, FUNCTIONDESCRIPTOR);
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new Utils.SymbolNotFound(e);
        }
    }

    public func_void_void_ptr_(Arena funcLifeTime, Function function) {
        this(funcLifeTime, (FunctionRaw) (arg0)
                -> function.func_void_void_ptr_(new Ptr<Void>(arg0, Info.makeOperations())));
    }


    public func_void_void_ptr_(MemorySegment funPtr) {
        this.funPtr = funPtr;
        methodHandle = funPtr.address() == 0 ? null : Utils.downcallHandle(funPtr, FUNCTIONDESCRIPTOR, true);
    }

    public void invokeRaw(MemorySegment arg0) {
        try {
             methodHandle.invokeExact(arg0);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public void invoke(PtrI<?> arg0) {
        invokeRaw(arg0.operator().value());
    }


    @Override
    public PtrOpI<func_void_void_ptr_, Function> operator() {
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
            public Operations<func_void_void_ptr_> getOperations() {
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
        return "func_void_void_ptr_{" +
                "funPtr=" + funPtr +
                ", methodHandle=" + methodHandle +
                '}';
    }

}