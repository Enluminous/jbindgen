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
import libclang.values.CXIdxClientContainer;
public class IndexerCallbacks$startedTranslationUnit implements PtrOp<IndexerCallbacks$startedTranslationUnit, IndexerCallbacks$startedTranslationUnit.Function>, Info<IndexerCallbacks$startedTranslationUnit> {
    public static final Operations<IndexerCallbacks$startedTranslationUnit> OPERATIONS = PtrOp.makeOperations(IndexerCallbacks$startedTranslationUnit::new);
    public static final FunctionDescriptor FUNCTIONDESCRIPTOR = FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS);

    public interface FunctionRaw {
        MemorySegment IndexerCallbacks$startedTranslationUnit(MemorySegment client_data, MemorySegment reserved);
    }

    public interface Function {
        CXIdxClientContainer IndexerCallbacks$startedTranslationUnit(CXClientData client_data, Ptr<Void> reserved);
    }

    private final MemorySegment funPtr;
    private final MethodHandle methodHandle;

    public IndexerCallbacks$startedTranslationUnit(Arena funcLifeTime, FunctionRaw function) {
        try {
            methodHandle = MethodHandles.lookup().findVirtual(FunctionRaw.class,
                    "IndexerCallbacks$startedTranslationUnit", FUNCTIONDESCRIPTOR.toMethodType()).bindTo(function);
            funPtr = Utils.upcallStub(funcLifeTime, methodHandle, FUNCTIONDESCRIPTOR);
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new Utils.SymbolNotFound(e);
        }
    }

    public IndexerCallbacks$startedTranslationUnit(Arena funcLifeTime, Function function) {
        this(funcLifeTime, (FunctionRaw) (client_data, reserved)
                -> function.IndexerCallbacks$startedTranslationUnit(new CXClientData(client_data), new Ptr<Void>(reserved, Info.makeOperations())).operator().value());
    }


    public IndexerCallbacks$startedTranslationUnit(MemorySegment funPtr) {
        this.funPtr = funPtr;
        methodHandle = funPtr.address() == 0 ? null : Utils.downcallHandle(funPtr, FUNCTIONDESCRIPTOR, true);
    }

    public MemorySegment invokeRaw(MemorySegment client_data, MemorySegment reserved) {
        try {
            return (MemorySegment)  methodHandle.invokeExact(client_data, reserved);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public CXIdxClientContainer invoke(CXClientData client_data, PtrI<?> reserved) {
        return new CXIdxClientContainer(invokeRaw(client_data.operator().value(), reserved.operator().value()));
    }


    @Override
    public PtrOpI<IndexerCallbacks$startedTranslationUnit, Function> operator() {
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
            public Operations<IndexerCallbacks$startedTranslationUnit> getOperations() {
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
        return "IndexerCallbacks$startedTranslationUnit{" +
                "funPtr=" + funPtr +
                ", methodHandle=" + methodHandle +
                '}';
    }

}