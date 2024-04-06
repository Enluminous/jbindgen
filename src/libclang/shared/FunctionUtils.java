package libclang.shared;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.Objects;
import java.util.Optional;

public class FunctionUtils {
    public static class InvokeException extends RuntimeException {
        public InvokeException(Throwable cause) {
            super(cause);
        }
    }

    public static class SymbolNotFound extends RuntimeException {
        public SymbolNotFound(String cause) {
            super(cause);
        }

        public SymbolNotFound(Throwable t) {
            super(t);
        }

        public SymbolNotFound() {
        }
    }

    private FunctionUtils() {
        throw new UnsupportedOperationException();
    }

    public static <T> Pointer<T> makePointer(MemorySegment ms) {
        return new Pointer<>() {
            @Override
            public String toString() {
                return String.valueOf(ms);
            }

            @Override
            public MemorySegment pointer() {
                return ms;
            }
        };
    }

    public static MemorySegment toMemorySegment(Arena arena, MethodHandle methodHandle, FunctionDescriptor functionDescriptor) {
        return Linker.nativeLinker().upcallStub(methodHandle, functionDescriptor, arena);
    }

    public static MemorySegment toMemorySegment(MethodHandles.Lookup lookup, Arena arena, FunctionDescriptor functionDescriptor, Object function, String functionName) {
        var handle = toMethodHandle(lookup, functionDescriptor, function, functionName);
        return toMemorySegment(arena, handle, functionDescriptor);
    }

    public static MethodHandle toMethodHandle(MethodHandles.Lookup lookup, FunctionDescriptor functionDescriptor, Object function, String functionName) {
        try {
            return lookup.findVirtual(function.getClass(), functionName, functionDescriptor.toMethodType()).bindTo(function);
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static Optional<MethodHandle> toMethodHandle(SymbolLookup lookup, String functionName, FunctionDescriptor functionDescriptor, boolean critical) {
        return Objects.requireNonNull(lookup).find(Objects.requireNonNull(functionName)).map(ms -> critical ?
                Linker.nativeLinker().downcallHandle(ms, functionDescriptor, Linker.Option.critical(true))
                : Linker.nativeLinker().downcallHandle(ms, functionDescriptor));
    }

    public static Optional<MethodHandle> toMethodHandle(MemorySegment memorySegment, FunctionDescriptor functionDescriptor, boolean critical) {
        return toMethodHandle(Optional.ofNullable(memorySegment), functionDescriptor, critical);
    }

    public static Optional<MethodHandle> toMethodHandle(Optional<MemorySegment> memorySegment, FunctionDescriptor functionDescriptor, boolean critical) {
        return memorySegment.map(ms -> critical ?
                Linker.nativeLinker().downcallHandle(ms, functionDescriptor, Linker.Option.critical(true))
                : Linker.nativeLinker().downcallHandle(ms, functionDescriptor));
    }
}
