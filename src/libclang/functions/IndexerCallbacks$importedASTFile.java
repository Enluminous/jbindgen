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
public interface IndexerCallbacks$importedASTFile {
    MemorySegment function(MemorySegment client_data, MemorySegment para1);

    default VPointer<IndexerCallbacks$importedASTFile> toVPointer(Arena arena) {
        FunctionDescriptor functionDescriptor = FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS);
        try {
            return new VPointer<>(FunctionUtils.toMemorySegment(arena, MethodHandles.lookup().findVirtual(IndexerCallbacks$importedASTFile.class, "function", functionDescriptor.toMethodType()).bindTo(this) , functionDescriptor));
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new FunctionUtils.SymbolNotFound(e);
        }
   }


    @FunctionalInterface
    interface IndexerCallbacks$importedASTFile$CXIdxClientASTFile$0 extends IndexerCallbacks$importedASTFile {
        CXIdxClientASTFile function(CXClientData client_data, Pointer<CXIdxImportedASTFileInfo> para1);

        @Override
        default MemorySegment function(MemorySegment client_data, MemorySegment para1) {
            return function(new CXClientData(client_data), FunctionUtils.makePointer(para1)).value();
        }
    }

    static IndexerCallbacks$importedASTFile ofVPointer(VPointer<IndexerCallbacks$importedASTFile> p) {
        MethodHandle methodHandle = FunctionUtils.toMethodHandle(p.value(), FunctionDescriptor.ofVoid(), false).orElseThrow();
        return new IndexerCallbacks$importedASTFile() {
            @Override
            public MemorySegment function(MemorySegment client_data, MemorySegment para1) {
                try {
                    return (MemorySegment) methodHandle.invokeExact(client_data, para1);
                } catch (Throwable e) {
                    throw new FunctionUtils.InvokeException(e);
                }
            }

            @Override
            public VPointer<IndexerCallbacks$importedASTFile> toVPointer(Arena arena) {
                return p;
            }
        };
    }
}