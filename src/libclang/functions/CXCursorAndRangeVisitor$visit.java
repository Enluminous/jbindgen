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
import libclang.enumerates.CXVisitorResult;
import libclang.structs.CXCursor;
import libclang.structs.CXSourceRange;
public class CXCursorAndRangeVisitor$visit implements PtrOp<CXCursorAndRangeVisitor$visit, CXCursorAndRangeVisitor$visit.Function>, Info<CXCursorAndRangeVisitor$visit> {
    public static final Operations<CXCursorAndRangeVisitor$visit> OPERATIONS = PtrOp.makeOperations(CXCursorAndRangeVisitor$visit::new);
    public static final FunctionDescriptor FUNCTIONDESCRIPTOR = FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)));

    public interface FunctionRaw {
        int CXCursorAndRangeVisitor$visit(MemorySegment context, MemorySegment arg1, MemorySegment arg2);
    }

    public interface Function {
        CXVisitorResult CXCursorAndRangeVisitor$visit(Ptr<Void> context, CXCursor arg1, CXSourceRange arg2);
    }

    private final MemorySegment funPtr;
    private final MethodHandle methodHandle;

    public CXCursorAndRangeVisitor$visit(Arena funcLifeTime, FunctionRaw function) {
        try {
            methodHandle = MethodHandles.lookup().findVirtual(FunctionRaw.class,
                    "CXCursorAndRangeVisitor$visit", FUNCTIONDESCRIPTOR.toMethodType()).bindTo(function);
            funPtr = Utils.upcallStub(funcLifeTime, methodHandle, FUNCTIONDESCRIPTOR);
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new Utils.SymbolNotFound(e);
        }
    }

    public CXCursorAndRangeVisitor$visit(Arena funcLifeTime, Function function) {
        this(funcLifeTime, (FunctionRaw) (context, arg1, arg2)
                -> function.CXCursorAndRangeVisitor$visit(new Ptr<Void>(context, Info.makeOperations()), new CXCursor(arg1), new CXSourceRange(arg2)).operator().value());
    }


    public CXCursorAndRangeVisitor$visit(MemorySegment funPtr) {
        this.funPtr = funPtr;
        methodHandle = funPtr.address() == 0 ? null : Utils.downcallHandle(funPtr, FUNCTIONDESCRIPTOR, true);
    }

    public int invokeRaw(MemorySegment context, MemorySegment arg1, MemorySegment arg2) {
        try {
            return (int)  methodHandle.invokeExact(context, arg1, arg2);
        } catch (Throwable e) {
            throw new Utils.InvokeException(e);
        }
    }

    public CXVisitorResult invoke(PtrI<?> context, StructI<? extends CXCursor> arg1, StructI<? extends CXSourceRange> arg2) {
        return new CXVisitorResult(invokeRaw(context.operator().value(), arg1.operator().value(), arg2.operator().value()));
    }


    @Override
    public PtrOpI<CXCursorAndRangeVisitor$visit, Function> operator() {
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
            public Operations<CXCursorAndRangeVisitor$visit> getOperations() {
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
        return "CXCursorAndRangeVisitor$visit{" +
                "funPtr=" + funPtr +
                ", methodHandle=" + methodHandle +
                '}';
    }

}