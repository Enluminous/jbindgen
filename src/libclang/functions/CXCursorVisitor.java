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
public interface CXCursorVisitor {
    int function(MemorySegment cursor, MemorySegment parent, MemorySegment client_data);

    default VPointer<CXCursorVisitor> toVPointer(Arena arena) {
        FunctionDescriptor functionDescriptor = FunctionDescriptor.of(ValueLayout.JAVA_INT, MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), MemoryLayout.structLayout(MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE)), ValueLayout.ADDRESS);
        try {
            return new VPointer<>(FunctionUtils.toMemorySegment(arena, MethodHandles.lookup().findVirtual(CXCursorVisitor.class, "function", functionDescriptor.toMethodType()).bindTo(this) , functionDescriptor));
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new FunctionUtils.SymbolNotFound(e);
        }
   }


    @FunctionalInterface
    interface CXCursorVisitor$CXChildVisitResult$0 extends CXCursorVisitor {
        CXChildVisitResult function(CXCursor cursor, CXCursor parent, CXClientData client_data);

        @Override
        default int function(MemorySegment cursor, MemorySegment parent, MemorySegment client_data) {
            return function(new CXCursor(FunctionUtils.makePointer(cursor)), new CXCursor(FunctionUtils.makePointer(parent)), new CXClientData(client_data)).value();
        }
    }

    static CXCursorVisitor ofVPointer(VPointer<CXCursorVisitor> p) {
        MethodHandle methodHandle = FunctionUtils.toMethodHandle(p.value(), FunctionDescriptor.ofVoid(), false).orElseThrow();
        return new CXCursorVisitor() {
            @Override
            public int function(MemorySegment cursor, MemorySegment parent, MemorySegment client_data) {
                try {
                    return (int) methodHandle.invokeExact(cursor, parent, client_data);
                } catch (Throwable e) {
                    throw new FunctionUtils.InvokeException(e);
                }
            }

            @Override
            public VPointer<CXCursorVisitor> toVPointer(Arena arena) {
                return p;
            }
        };
    }
}