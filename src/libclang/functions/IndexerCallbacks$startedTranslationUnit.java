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
public interface IndexerCallbacks$startedTranslationUnit {
    MemorySegment function(MemorySegment client_data, MemorySegment reserved);

    default VPointer<IndexerCallbacks$startedTranslationUnit> toVPointer(Arena arena) {
        FunctionDescriptor functionDescriptor = FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS);
        try {
            return new VPointer<>(FunctionUtils.toMemorySegment(arena, MethodHandles.lookup().findVirtual(IndexerCallbacks$startedTranslationUnit.class, "function", functionDescriptor.toMethodType()).bindTo(this) , functionDescriptor));
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new FunctionUtils.SymbolNotFound(e);
        }
   }


    @FunctionalInterface
    interface IndexerCallbacks$startedTranslationUnit$CXIdxClientContainer$0 extends IndexerCallbacks$startedTranslationUnit {
        CXIdxClientContainer function(CXClientData client_data, Pointer<?> reserved);

        @Override
        default MemorySegment function(MemorySegment client_data, MemorySegment reserved) {
            return function(new CXClientData(client_data), FunctionUtils.makePointer(reserved)).value();
        }
    }

    static IndexerCallbacks$startedTranslationUnit ofVPointer(VPointer<IndexerCallbacks$startedTranslationUnit> p) {
        MethodHandle methodHandle = FunctionUtils.toMethodHandle(p.value(), FunctionDescriptor.ofVoid(), false).orElseThrow();
        return new IndexerCallbacks$startedTranslationUnit() {
            @Override
            public MemorySegment function(MemorySegment client_data, MemorySegment reserved) {
                try {
                    return (MemorySegment) methodHandle.invokeExact(client_data, reserved);
                } catch (Throwable e) {
                    throw new FunctionUtils.InvokeException(e);
                }
            }

            @Override
            public VPointer<IndexerCallbacks$startedTranslationUnit> toVPointer(Arena arena) {
                return p;
            }
        };
    }
}