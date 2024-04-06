package libclang.functions;

import libclang.structs.*;
import libclang.LibclangEnums.*;
import libclang.functions.*;
import libclang.values.*;
import libclang.shared.values.*;
import libclang.shared.*;
import libclang.shared.natives.*;
import libclang.shared.Value;
import libclang.shared.Pointer;
import libclang.shared.FunctionUtils;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;

@FunctionalInterface
public interface CXCursorAndRangeVisitor$visit {
    int function(MemorySegment context, MemorySegment para1, MemorySegment para2);

    default VPointer<CXCursorAndRangeVisitor$visit> toVPointer(Arena arena) {
        FunctionDescriptor functionDescriptor = FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE)));
        try {
            return new VPointer<>(FunctionUtils.toMemorySegment(arena, MethodHandles.lookup().findVirtual(CXCursorAndRangeVisitor$visit.class, "function", functionDescriptor.toMethodType()).bindTo(this) , functionDescriptor));
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new FunctionUtils.SymbolNotFound(e);
        }
   }


    @FunctionalInterface
    interface CXCursorAndRangeVisitor$visit$CXVisitorResult$0 extends CXCursorAndRangeVisitor$visit {
        CXVisitorResult function(Pointer<?> context, CXCursor para1, CXSourceRange para2);

        @Override
        default int function(MemorySegment context, MemorySegment para1, MemorySegment para2) {
            return function(FunctionUtils.makePointer(context), new CXCursor(FunctionUtils.makePointer(para1)), new CXSourceRange(FunctionUtils.makePointer(para2))).value();
        }
    }

    static CXCursorAndRangeVisitor$visit ofVPointer(VPointer<CXCursorAndRangeVisitor$visit> p) {
        MethodHandle methodHandle = FunctionUtils.toMethodHandle(p.value(), FunctionDescriptor.ofVoid(), false).orElseThrow();
        return new CXCursorAndRangeVisitor$visit() {
            @Override
            public int function(MemorySegment context, MemorySegment para1, MemorySegment para2) {
                try {
                    return (int) methodHandle.invokeExact(context, para1, para2);
                } catch (Throwable e) {
                    throw new FunctionUtils.InvokeException(e);
                }
            }

            @Override
            public VPointer<CXCursorAndRangeVisitor$visit> toVPointer(Arena arena) {
                return p;
            }
        };
    }
}