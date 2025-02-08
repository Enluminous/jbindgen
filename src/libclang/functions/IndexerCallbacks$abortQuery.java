package libclang.functions;
import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import libclang.common.I32;
import libclang.common.Info;
import libclang.common.Operation;
import libclang.common.Ptr;
import libclang.common.PtrI;
import libclang.common.PtrOp;
import libclang.common.Utils;
import libclang.values.CXClientData;
public class IndexerCallbacks$abortQuery implements PtrOp<IndexerCallbacks$abortQuery, IndexerCallbacks$abortQuery.Function>, Info<IndexerCallbacks$abortQuery> {
    public static final Operations<IndexerCallbacks$abortQuery> OPERATIONS = PtrOp.makeOperations(IndexerCallbacks$abortQuery::new);
    public static final FunctionDescriptor FUNCTIONDESCRIPTOR = FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS);

    public interface FunctionRaw {
        int IndexerCallbacks$abortQuery(MemorySegment client_data, MemorySegment reserved);
    }

    public interface Function {
        I32 IndexerCallbacks$abortQuery(CXClientData client_data, Ptr<Void> reserved);
    }

    private final MemorySegment funPtr;
    private final MethodHandle methodHandle;

    public IndexerCallbacks$abortQuery(Arena funcLifeTime, FunctionRaw function) {
        try {
            methodHandle = MethodHandles.lookup().findVirtual(FunctionRaw.class,
                    "IndexerCallbacks$abortQuery", FUNCTIONDESCRIPTOR.toMethodType()).bindTo(function);
            funPtr = Utils.upcallStub(funcLifeTime, methodHandle, FUNCTIONDESCRIPTOR);
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new Utils.SymbolNotFound(e);
        }
    }

    public IndexerCallbacks$abortQuery(Arena funcLifeTime, Function function) {
        this(funcLifeTime, (FunctionRaw) (client_data, reserved)
                -> function.IndexerCallbacks$abortQuery(new CXClientData(client_data), new Ptr<Void>(reserved, Info.makeOperations())).operator().value());
    }


    public IndexerCallbacks$abortQuery(MemorySegment funPtr) {
        this.funPtr = funPtr;
        methodHandle = funPtr.address() == 0 ? null : Utils.downcallHandle(funPtr, FUNCTIONDESCRIPTOR, true);
    }

    public int invokeRaw(MemorySegment client_data, MemorySegment reserved) {
        try {
            return (int)  methodHandle.invokeExact(client_data, reserved);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public I32 invoke(CXClientData client_data, PtrI<?> reserved) {
        return new I32(invokeRaw(client_data.operator().value(), reserved.operator().value()));
    }


    @Override
    public PtrOpI<IndexerCallbacks$abortQuery, Function> operator() {
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
            public Operations<IndexerCallbacks$abortQuery> getOperations() {
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
        return "IndexerCallbacks$abortQuery{" +
                "funPtr=" + funPtr +
                ", methodHandle=" + methodHandle +
                '}';
    }

}