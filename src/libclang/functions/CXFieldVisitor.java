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
import libclang.common.PtrOp;
import libclang.common.StructI;
import libclang.common.Utils;
import libclang.enumerates.CXVisitorResult;
import libclang.structs.CXCursor;
import libclang.values.CXClientData;
public class CXFieldVisitor implements PtrOp<CXFieldVisitor, CXFieldVisitor.Function>, Info<CXFieldVisitor> {
    public static final Operations<CXFieldVisitor> OPERATIONS = PtrOp.makeOperations(CXFieldVisitor::new);
    public static final FunctionDescriptor FUNCTIONDESCRIPTOR = FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS);

    public interface FunctionRaw {
        int CXFieldVisitor(MemorySegment C, MemorySegment client_data);
    }

    public interface Function {
        CXVisitorResult CXFieldVisitor(CXCursor C, CXClientData client_data);
    }

    private final MemorySegment funPtr;
    private final MethodHandle methodHandle;

    public CXFieldVisitor(Arena funcLifeTime, FunctionRaw function) {
        try {
            methodHandle = MethodHandles.lookup().findVirtual(FunctionRaw.class,
                    "CXFieldVisitor", FUNCTIONDESCRIPTOR.toMethodType()).bindTo(function);
            funPtr = Utils.upcallStub(funcLifeTime, methodHandle, FUNCTIONDESCRIPTOR);
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new Utils.SymbolNotFound(e);
        }
    }

    public CXFieldVisitor(Arena funcLifeTime, Function function) {
        this(funcLifeTime, (FunctionRaw) (C, client_data)
                -> function.CXFieldVisitor(new CXCursor(C), new CXClientData(client_data)).operator().value());
    }


    public CXFieldVisitor(MemorySegment funPtr) {
        this.funPtr = funPtr;
        methodHandle = funPtr.address() == 0 ? null : Utils.downcallHandle(funPtr, FUNCTIONDESCRIPTOR, true);
    }

    public int invokeRaw(MemorySegment C, MemorySegment client_data) {
        try {
            return (int)  methodHandle.invokeExact(C, client_data);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public CXVisitorResult invoke(StructI<? extends CXCursor> C, CXClientData client_data) {
        return new CXVisitorResult(invokeRaw(C.operator().value(), client_data.operator().value()));
    }


    @Override
    public PtrOpI<CXFieldVisitor, Function> operator() {
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
            public Operations<CXFieldVisitor> getOperations() {
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
        return "CXFieldVisitor{" +
                "funPtr=" + funPtr +
                ", methodHandle=" + methodHandle +
                '}';
    }

}