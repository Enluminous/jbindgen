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
public interface IndexerCallbacks$abortQuery {
    int function(MemorySegment client_data, MemorySegment reserved);

    default VPointer<IndexerCallbacks$abortQuery> toVPointer(Arena arena) {
        FunctionDescriptor functionDescriptor = FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS);
        try {
            return new VPointer<>(FunctionUtils.toMemorySegment(arena, MethodHandles.lookup().findVirtual(IndexerCallbacks$abortQuery.class, "function", functionDescriptor.toMethodType()).bindTo(this) , functionDescriptor));
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new FunctionUtils.SymbolNotFound(e);
        }
   }


    @FunctionalInterface
    interface IndexerCallbacks$abortQuery$int$0 extends IndexerCallbacks$abortQuery {
        int function(CXClientData client_data, Pointer<?> reserved);

        @Override
        default int function(MemorySegment client_data, MemorySegment reserved) {
            return function(new CXClientData(client_data), FunctionUtils.makePointer(reserved));
        }
    }

    static IndexerCallbacks$abortQuery ofVPointer(VPointer<IndexerCallbacks$abortQuery> p) {
        MethodHandle methodHandle = FunctionUtils.toMethodHandle(p.value(), FunctionDescriptor.ofVoid(), false).orElseThrow();
        return new IndexerCallbacks$abortQuery() {
            @Override
            public int function(MemorySegment client_data, MemorySegment reserved) {
                try {
                    return (int) methodHandle.invokeExact(client_data, reserved);
                } catch (Throwable e) {
                    throw new FunctionUtils.InvokeException(e);
                }
            }

            @Override
            public VPointer<IndexerCallbacks$abortQuery> toVPointer(Arena arena) {
                return p;
            }
        };
    }
}