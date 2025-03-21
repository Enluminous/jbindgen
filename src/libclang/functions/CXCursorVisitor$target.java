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
import libclang.enumerates.CXChildVisitResult;
import libclang.structs.CXCursor;
import libclang.values.CXClientData;
public class CXCursorVisitor$target implements PtrOp<CXCursorVisitor$target, CXCursorVisitor$target.Function>, Info<CXCursorVisitor$target> {
    public static final Operations<CXCursorVisitor$target> OPERATIONS = PtrOp.makeOperations(CXCursorVisitor$target::new);
    public static final FunctionDescriptor FUNCTIONDESCRIPTOR = FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS);

    public interface FunctionRaw {
        int CXCursorVisitor$target(MemorySegment cursor, MemorySegment parent, MemorySegment client_data);
    }

    public interface Function {
        CXChildVisitResult CXCursorVisitor$target(CXCursor cursor, CXCursor parent, CXClientData client_data);
    }

    private final MemorySegment funPtr;
    private final MethodHandle methodHandle;

    public CXCursorVisitor$target(Arena funcLifeTime, FunctionRaw function) {
        try {
            methodHandle = MethodHandles.lookup().findVirtual(FunctionRaw.class,
                    "CXCursorVisitor$target", FUNCTIONDESCRIPTOR.toMethodType()).bindTo(function);
            funPtr = Utils.upcallStub(funcLifeTime, methodHandle, FUNCTIONDESCRIPTOR);
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new Utils.SymbolNotFound(e);
        }
    }

    public CXCursorVisitor$target(Arena funcLifeTime, Function function) {
        this(funcLifeTime, (FunctionRaw) (cursor, parent, client_data)
                -> function.CXCursorVisitor$target(new CXCursor(cursor), new CXCursor(parent), new CXClientData(client_data)).operator().value());
    }


    public CXCursorVisitor$target(MemorySegment funPtr) {
        this.funPtr = funPtr;
        methodHandle = funPtr.address() == 0 ? null : Utils.downcallHandle(funPtr, FUNCTIONDESCRIPTOR, true);
    }

    public int invokeRaw(MemorySegment cursor, MemorySegment parent, MemorySegment client_data) {
        try {
            return (int)  methodHandle.invokeExact(cursor, parent, client_data);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public CXChildVisitResult invoke(StructI<? extends CXCursor> cursor, StructI<? extends CXCursor> parent, CXClientData client_data) {
        return new CXChildVisitResult(invokeRaw(cursor.operator().value(), parent.operator().value(), client_data.operator().value()));
    }


    @Override
    public PtrOpI<CXCursorVisitor$target, Function> operator() {
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
            public Operations<CXCursorVisitor$target> getOperations() {
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
        return "CXCursorVisitor$target{" +
                "funPtr=" + funPtr +
                ", methodHandle=" + methodHandle +
                '}';
    }

}