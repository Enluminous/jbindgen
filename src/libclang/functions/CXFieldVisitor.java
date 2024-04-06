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
public interface CXFieldVisitor {
    int function(MemorySegment C, MemorySegment client_data);

    default VPointer<CXFieldVisitor> toVPointer(Arena arena) {
        FunctionDescriptor functionDescriptor = FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS);
        try {
            return new VPointer<>(FunctionUtils.toMemorySegment(arena, MethodHandles.lookup().findVirtual(CXFieldVisitor.class, "function", functionDescriptor.toMethodType()).bindTo(this) , functionDescriptor));
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new FunctionUtils.SymbolNotFound(e);
        }
   }


    @FunctionalInterface
    interface CXFieldVisitor$CXVisitorResult$0 extends CXFieldVisitor {
        CXVisitorResult function(CXCursor C, CXClientData client_data);

        @Override
        default int function(MemorySegment C, MemorySegment client_data) {
            return function(new CXCursor(FunctionUtils.makePointer(C)), new CXClientData(client_data)).value();
        }
    }

    static CXFieldVisitor ofVPointer(VPointer<CXFieldVisitor> p) {
        MethodHandle methodHandle = FunctionUtils.toMethodHandle(p.value(), FunctionDescriptor.ofVoid(), false).orElseThrow();
        return new CXFieldVisitor() {
            @Override
            public int function(MemorySegment C, MemorySegment client_data) {
                try {
                    return (int) methodHandle.invokeExact(C, client_data);
                } catch (Throwable e) {
                    throw new FunctionUtils.InvokeException(e);
                }
            }

            @Override
            public VPointer<CXFieldVisitor> toVPointer(Arena arena) {
                return p;
            }
        };
    }
}