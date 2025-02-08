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
import libclang.values.CXFile;
import libclang.values.CXIdxClientFile;
public class IndexerCallbacks$enteredMainFile implements PtrOp<IndexerCallbacks$enteredMainFile, IndexerCallbacks$enteredMainFile.Function>, Info<IndexerCallbacks$enteredMainFile> {
    public static final Operations<IndexerCallbacks$enteredMainFile> OPERATIONS = PtrOp.makeOperations(IndexerCallbacks$enteredMainFile::new);
    public static final FunctionDescriptor FUNCTIONDESCRIPTOR = FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS);

    public interface FunctionRaw {
        MemorySegment IndexerCallbacks$enteredMainFile(MemorySegment client_data, MemorySegment mainFile, MemorySegment reserved);
    }

    public interface Function {
        CXIdxClientFile IndexerCallbacks$enteredMainFile(CXClientData client_data, CXFile mainFile, Ptr<Void> reserved);
    }

    private final MemorySegment funPtr;
    private final MethodHandle methodHandle;

    public IndexerCallbacks$enteredMainFile(Arena funcLifeTime, FunctionRaw function) {
        try {
            methodHandle = MethodHandles.lookup().findVirtual(FunctionRaw.class,
                    "IndexerCallbacks$enteredMainFile", FUNCTIONDESCRIPTOR.toMethodType()).bindTo(function);
            funPtr = Utils.upcallStub(funcLifeTime, methodHandle, FUNCTIONDESCRIPTOR);
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new Utils.SymbolNotFound(e);
        }
    }

    public IndexerCallbacks$enteredMainFile(Arena funcLifeTime, Function function) {
        this(funcLifeTime, (FunctionRaw) (client_data, mainFile, reserved)
                -> function.IndexerCallbacks$enteredMainFile(new CXClientData(client_data), new CXFile(mainFile), new Ptr<Void>(reserved, Info.makeOperations())).operator().value());
    }


    public IndexerCallbacks$enteredMainFile(MemorySegment funPtr) {
        this.funPtr = funPtr;
        methodHandle = funPtr.address() == 0 ? null : Utils.downcallHandle(funPtr, FUNCTIONDESCRIPTOR, true);
    }

    public MemorySegment invokeRaw(MemorySegment client_data, MemorySegment mainFile, MemorySegment reserved) {
        try {
            return (MemorySegment)  methodHandle.invokeExact(client_data, mainFile, reserved);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public CXIdxClientFile invoke(CXClientData client_data, CXFile mainFile, PtrI<?> reserved) {
        return new CXIdxClientFile(invokeRaw(client_data.operator().value(), mainFile.operator().value(), reserved.operator().value()));
    }


    @Override
    public PtrOpI<IndexerCallbacks$enteredMainFile, Function> operator() {
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
            public Operations<IndexerCallbacks$enteredMainFile> getOperations() {
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
        return "IndexerCallbacks$enteredMainFile{" +
                "funPtr=" + funPtr +
                ", methodHandle=" + methodHandle +
                '}';
    }

}