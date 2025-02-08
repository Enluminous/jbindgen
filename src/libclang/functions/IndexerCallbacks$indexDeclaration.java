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
import libclang.structs.CXIdxDeclInfo;
import libclang.values.CXClientData;
public class IndexerCallbacks$indexDeclaration implements PtrOp<IndexerCallbacks$indexDeclaration, IndexerCallbacks$indexDeclaration.Function>, Info<IndexerCallbacks$indexDeclaration> {
    public static final Operations<IndexerCallbacks$indexDeclaration> OPERATIONS = PtrOp.makeOperations(IndexerCallbacks$indexDeclaration::new);
    public static final FunctionDescriptor FUNCTIONDESCRIPTOR = FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS);

    public interface FunctionRaw {
        void IndexerCallbacks$indexDeclaration(MemorySegment client_data, MemorySegment arg1);
    }

    public interface Function {
        void IndexerCallbacks$indexDeclaration(CXClientData client_data, Ptr<CXIdxDeclInfo> arg1);
    }

    private final MemorySegment funPtr;
    private final MethodHandle methodHandle;

    public IndexerCallbacks$indexDeclaration(Arena funcLifeTime, FunctionRaw function) {
        try {
            methodHandle = MethodHandles.lookup().findVirtual(FunctionRaw.class,
                    "IndexerCallbacks$indexDeclaration", FUNCTIONDESCRIPTOR.toMethodType()).bindTo(function);
            funPtr = Utils.upcallStub(funcLifeTime, methodHandle, FUNCTIONDESCRIPTOR);
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new Utils.SymbolNotFound(e);
        }
    }

    public IndexerCallbacks$indexDeclaration(Arena funcLifeTime, Function function) {
        this(funcLifeTime, (FunctionRaw) (client_data, arg1)
                -> function.IndexerCallbacks$indexDeclaration(new CXClientData(client_data), new Ptr<CXIdxDeclInfo>(arg1, CXIdxDeclInfo.OPERATIONS)));
    }


    public IndexerCallbacks$indexDeclaration(MemorySegment funPtr) {
        this.funPtr = funPtr;
        methodHandle = funPtr.address() == 0 ? null : Utils.downcallHandle(funPtr, FUNCTIONDESCRIPTOR, true);
    }

    public void invokeRaw(MemorySegment client_data, MemorySegment arg1) {
        try {
             methodHandle.invokeExact(client_data, arg1);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public void invoke(CXClientData client_data, PtrI<? extends StructI<? extends CXIdxDeclInfo>> arg1) {
        invokeRaw(client_data.operator().value(), arg1.operator().value());
    }


    @Override
    public PtrOpI<IndexerCallbacks$indexDeclaration, Function> operator() {
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
            public Operations<IndexerCallbacks$indexDeclaration> getOperations() {
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
        return "IndexerCallbacks$indexDeclaration{" +
                "funPtr=" + funPtr +
                ", methodHandle=" + methodHandle +
                '}';
    }

}