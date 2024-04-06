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
public interface IndexerCallbacks$indexDeclaration {
    void function(MemorySegment client_data, MemorySegment para1);

    default VPointer<IndexerCallbacks$indexDeclaration> toVPointer(Arena arena) {
        FunctionDescriptor functionDescriptor = FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS);
        try {
            return new VPointer<>(FunctionUtils.toMemorySegment(arena, MethodHandles.lookup().findVirtual(IndexerCallbacks$indexDeclaration.class, "function", functionDescriptor.toMethodType()).bindTo(this) , functionDescriptor));
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new FunctionUtils.SymbolNotFound(e);
        }
   }


    @FunctionalInterface
    interface IndexerCallbacks$indexDeclaration$0 extends IndexerCallbacks$indexDeclaration {
        void function(CXClientData client_data, Pointer<CXIdxDeclInfo> para1);

        @Override
        default void function(MemorySegment client_data, MemorySegment para1) {
            function(new CXClientData(client_data), FunctionUtils.makePointer(para1));
        }
    }

    static IndexerCallbacks$indexDeclaration ofVPointer(VPointer<IndexerCallbacks$indexDeclaration> p) {
        MethodHandle methodHandle = FunctionUtils.toMethodHandle(p.value(), FunctionDescriptor.ofVoid(), false).orElseThrow();
        return new IndexerCallbacks$indexDeclaration() {
            @Override
            public void function(MemorySegment client_data, MemorySegment para1) {
                try {
                    methodHandle.invokeExact(client_data, para1);
                } catch (Throwable e) {
                    throw new FunctionUtils.InvokeException(e);
                }
            }

            @Override
            public VPointer<IndexerCallbacks$indexDeclaration> toVPointer(Arena arena) {
                return p;
            }
        };
    }
}