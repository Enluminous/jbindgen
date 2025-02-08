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
import libclang.values.CXClientData;
import libclang.values.CXDiagnosticSet;
public class IndexerCallbacks$diagnostic implements PtrOp<IndexerCallbacks$diagnostic, IndexerCallbacks$diagnostic.Function>, Info<IndexerCallbacks$diagnostic> {
    public static final Operations<IndexerCallbacks$diagnostic> OPERATIONS = PtrOp.makeOperations(IndexerCallbacks$diagnostic::new);
    public static final FunctionDescriptor FUNCTIONDESCRIPTOR = FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS);

    public interface FunctionRaw {
        void IndexerCallbacks$diagnostic(MemorySegment client_data, MemorySegment arg1, MemorySegment reserved);
    }

    public interface Function {
        void IndexerCallbacks$diagnostic(CXClientData client_data, CXDiagnosticSet arg1, Ptr<Void> reserved);
    }

    private final MemorySegment funPtr;
    private final MethodHandle methodHandle;

    public IndexerCallbacks$diagnostic(Arena funcLifeTime, FunctionRaw function) {
        try {
            methodHandle = MethodHandles.lookup().findVirtual(FunctionRaw.class,
                    "IndexerCallbacks$diagnostic", FUNCTIONDESCRIPTOR.toMethodType()).bindTo(function);
            funPtr = Utils.upcallStub(funcLifeTime, methodHandle, FUNCTIONDESCRIPTOR);
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new Utils.SymbolNotFound(e);
        }
    }

    public IndexerCallbacks$diagnostic(Arena funcLifeTime, Function function) {
        this(funcLifeTime, (FunctionRaw) (client_data, arg1, reserved)
                -> function.IndexerCallbacks$diagnostic(new CXClientData(client_data), new CXDiagnosticSet(arg1), new Ptr<Void>(reserved, Info.makeOperations())));
    }


    public IndexerCallbacks$diagnostic(MemorySegment funPtr) {
        this.funPtr = funPtr;
        methodHandle = funPtr.address() == 0 ? null : Utils.downcallHandle(funPtr, FUNCTIONDESCRIPTOR, true);
    }

    public void invokeRaw(MemorySegment client_data, MemorySegment arg1, MemorySegment reserved) {
        try {
             methodHandle.invokeExact(client_data, arg1, reserved);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public void invoke(CXClientData client_data, CXDiagnosticSet arg1, PtrI<?> reserved) {
        invokeRaw(client_data.operator().value(), arg1.operator().value(), reserved.operator().value());
    }


    @Override
    public PtrOpI<IndexerCallbacks$diagnostic, Function> operator() {
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
            public Operations<IndexerCallbacks$diagnostic> getOperations() {
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
        return "IndexerCallbacks$diagnostic{" +
                "funPtr=" + funPtr +
                ", methodHandle=" + methodHandle +
                '}';
    }

}