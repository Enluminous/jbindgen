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
public interface CXInclusionVisitor {
    void function(MemorySegment included_file, MemorySegment inclusion_stack, int include_len, MemorySegment client_data);

    default VPointer<CXInclusionVisitor> toVPointer(Arena arena) {
        FunctionDescriptor functionDescriptor = FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.ADDRESS);
        try {
            return new VPointer<>(FunctionUtils.toMemorySegment(arena, MethodHandles.lookup().findVirtual(CXInclusionVisitor.class, "function", functionDescriptor.toMethodType()).bindTo(this) , functionDescriptor));
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new FunctionUtils.SymbolNotFound(e);
        }
   }


    @FunctionalInterface
    interface CXInclusionVisitor$0 extends CXInclusionVisitor {
        void function(CXFile included_file, Pointer<CXSourceLocation> inclusion_stack, int include_len, CXClientData client_data);

        @Override
        default void function(MemorySegment included_file, MemorySegment inclusion_stack, int include_len, MemorySegment client_data) {
            function(new CXFile(included_file), FunctionUtils.makePointer(inclusion_stack), include_len, new CXClientData(client_data));
        }
    }

    static CXInclusionVisitor ofVPointer(VPointer<CXInclusionVisitor> p) {
        MethodHandle methodHandle = FunctionUtils.toMethodHandle(p.value(), FunctionDescriptor.ofVoid(), false).orElseThrow();
        return new CXInclusionVisitor() {
            @Override
            public void function(MemorySegment included_file, MemorySegment inclusion_stack, int include_len, MemorySegment client_data) {
                try {
                    methodHandle.invokeExact(included_file, inclusion_stack, include_len, client_data);
                } catch (Throwable e) {
                    throw new FunctionUtils.InvokeException(e);
                }
            }

            @Override
            public VPointer<CXInclusionVisitor> toVPointer(Arena arena) {
                return p;
            }
        };
    }
}