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
public interface IndexerCallbacks$enteredMainFile {
    MemorySegment function(MemorySegment client_data, MemorySegment mainFile, MemorySegment reserved);

    default VPointer<IndexerCallbacks$enteredMainFile> toVPointer(Arena arena) {
        FunctionDescriptor functionDescriptor = FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS);
        try {
            return new VPointer<>(FunctionUtils.toMemorySegment(arena, MethodHandles.lookup().findVirtual(IndexerCallbacks$enteredMainFile.class, "function", functionDescriptor.toMethodType()).bindTo(this) , functionDescriptor));
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new FunctionUtils.SymbolNotFound(e);
        }
   }


    @FunctionalInterface
    interface IndexerCallbacks$enteredMainFile$CXIdxClientFile$0 extends IndexerCallbacks$enteredMainFile {
        CXIdxClientFile function(CXClientData client_data, CXFile mainFile, Pointer<?> reserved);

        @Override
        default MemorySegment function(MemorySegment client_data, MemorySegment mainFile, MemorySegment reserved) {
            return function(new CXClientData(client_data), new CXFile(mainFile), FunctionUtils.makePointer(reserved)).value();
        }
    }

    static IndexerCallbacks$enteredMainFile ofVPointer(VPointer<IndexerCallbacks$enteredMainFile> p) {
        MethodHandle methodHandle = FunctionUtils.toMethodHandle(p.value(), FunctionDescriptor.ofVoid(), false).orElseThrow();
        return new IndexerCallbacks$enteredMainFile() {
            @Override
            public MemorySegment function(MemorySegment client_data, MemorySegment mainFile, MemorySegment reserved) {
                try {
                    return (MemorySegment) methodHandle.invokeExact(client_data, mainFile, reserved);
                } catch (Throwable e) {
                    throw new FunctionUtils.InvokeException(e);
                }
            }

            @Override
            public VPointer<IndexerCallbacks$enteredMainFile> toVPointer(Arena arena) {
                return p;
            }
        };
    }
}