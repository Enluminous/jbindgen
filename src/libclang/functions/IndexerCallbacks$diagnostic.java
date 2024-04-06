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
public interface IndexerCallbacks$diagnostic {
    void function(MemorySegment client_data, MemorySegment para1, MemorySegment reserved);

    default VPointer<IndexerCallbacks$diagnostic> toVPointer(Arena arena) {
        FunctionDescriptor functionDescriptor = FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS);
        try {
            return new VPointer<>(FunctionUtils.toMemorySegment(arena, MethodHandles.lookup().findVirtual(IndexerCallbacks$diagnostic.class, "function", functionDescriptor.toMethodType()).bindTo(this) , functionDescriptor));
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new FunctionUtils.SymbolNotFound(e);
        }
   }


    @FunctionalInterface
    interface IndexerCallbacks$diagnostic$0 extends IndexerCallbacks$diagnostic {
        void function(CXClientData client_data, CXDiagnosticSet para1, Pointer<?> reserved);

        @Override
        default void function(MemorySegment client_data, MemorySegment para1, MemorySegment reserved) {
            function(new CXClientData(client_data), new CXDiagnosticSet(para1), FunctionUtils.makePointer(reserved));
        }
    }

    static IndexerCallbacks$diagnostic ofVPointer(VPointer<IndexerCallbacks$diagnostic> p) {
        MethodHandle methodHandle = FunctionUtils.toMethodHandle(p.value(), FunctionDescriptor.ofVoid(), false).orElseThrow();
        return new IndexerCallbacks$diagnostic() {
            @Override
            public void function(MemorySegment client_data, MemorySegment para1, MemorySegment reserved) {
                try {
                    methodHandle.invokeExact(client_data, para1, reserved);
                } catch (Throwable e) {
                    throw new FunctionUtils.InvokeException(e);
                }
            }

            @Override
            public VPointer<IndexerCallbacks$diagnostic> toVPointer(Arena arena) {
                return p;
            }
        };
    }
}