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
public interface clang_executeOnThread$fn {
    void function(MemorySegment para0);

    default VPointer<clang_executeOnThread$fn> toVPointer(Arena arena) {
        FunctionDescriptor functionDescriptor = FunctionDescriptor.ofVoid(ValueLayout.ADDRESS);
        try {
            return new VPointer<>(FunctionUtils.toMemorySegment(arena, MethodHandles.lookup().findVirtual(clang_executeOnThread$fn.class, "function", functionDescriptor.toMethodType()).bindTo(this) , functionDescriptor));
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new FunctionUtils.SymbolNotFound(e);
        }
   }


    @FunctionalInterface
    interface clang_executeOnThread$fn$0 extends clang_executeOnThread$fn {
        void function(Pointer<?> para0);

        @Override
        default void function(MemorySegment para0) {
            function(FunctionUtils.makePointer(para0));
        }
    }

    static clang_executeOnThread$fn ofVPointer(VPointer<clang_executeOnThread$fn> p) {
        MethodHandle methodHandle = FunctionUtils.toMethodHandle(p.value(), FunctionDescriptor.ofVoid(), false).orElseThrow();
        return new clang_executeOnThread$fn() {
            @Override
            public void function(MemorySegment para0) {
                try {
                    methodHandle.invokeExact(para0);
                } catch (Throwable e) {
                    throw new FunctionUtils.InvokeException(e);
                }
            }

            @Override
            public VPointer<clang_executeOnThread$fn> toVPointer(Arena arena) {
                return p;
            }
        };
    }
}