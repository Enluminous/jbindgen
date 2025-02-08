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
import libclang.common.StructI;
import libclang.common.Utils;
import libclang.structs.CXIdxImportedASTFileInfo;
import libclang.values.CXClientData;
import libclang.values.CXIdxClientASTFile;
public class IndexerCallbacks$importedASTFile implements PtrOp<IndexerCallbacks$importedASTFile, IndexerCallbacks$importedASTFile.Function>, Info<IndexerCallbacks$importedASTFile> {
    public static final Operations<IndexerCallbacks$importedASTFile> OPERATIONS = PtrOp.makeOperations(IndexerCallbacks$importedASTFile::new);
    public static final FunctionDescriptor FUNCTIONDESCRIPTOR = FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS);

    public interface FunctionRaw {
        MemorySegment IndexerCallbacks$importedASTFile(MemorySegment client_data, MemorySegment arg1);
    }

    public interface Function {
        CXIdxClientASTFile IndexerCallbacks$importedASTFile(CXClientData client_data, Ptr<CXIdxImportedASTFileInfo> arg1);
    }

    private final MemorySegment funPtr;
    private final MethodHandle methodHandle;

    public IndexerCallbacks$importedASTFile(Arena funcLifeTime, FunctionRaw function) {
        try {
            methodHandle = MethodHandles.lookup().findVirtual(FunctionRaw.class,
                    "IndexerCallbacks$importedASTFile", FUNCTIONDESCRIPTOR.toMethodType()).bindTo(function);
            funPtr = Utils.upcallStub(funcLifeTime, methodHandle, FUNCTIONDESCRIPTOR);
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new Utils.SymbolNotFound(e);
        }
    }

    public IndexerCallbacks$importedASTFile(Arena funcLifeTime, Function function) {
        this(funcLifeTime, (FunctionRaw) (client_data, arg1)
                -> function.IndexerCallbacks$importedASTFile(new CXClientData(client_data), new Ptr<CXIdxImportedASTFileInfo>(arg1, CXIdxImportedASTFileInfo.OPERATIONS)).operator().value());
    }


    public IndexerCallbacks$importedASTFile(MemorySegment funPtr) {
        this.funPtr = funPtr;
        methodHandle = funPtr.address() == 0 ? null : Utils.downcallHandle(funPtr, FUNCTIONDESCRIPTOR, true);
    }

    public MemorySegment invokeRaw(MemorySegment client_data, MemorySegment arg1) {
        try {
            return (MemorySegment)  methodHandle.invokeExact(client_data, arg1);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public CXIdxClientASTFile invoke(CXClientData client_data, PtrI<? extends StructI<? extends CXIdxImportedASTFileInfo>> arg1) {
        return new CXIdxClientASTFile(invokeRaw(client_data.operator().value(), arg1.operator().value()));
    }


    @Override
    public PtrOpI<IndexerCallbacks$importedASTFile, Function> operator() {
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
            public Operations<IndexerCallbacks$importedASTFile> getOperations() {
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
        return "IndexerCallbacks$importedASTFile{" +
                "funPtr=" + funPtr +
                ", methodHandle=" + methodHandle +
                '}';
    }

}