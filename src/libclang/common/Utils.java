package libclang.common;


import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class Utils {
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

    public static class InvokeException extends RuntimeException {
        public InvokeException(Throwable cause) {
            super(cause);
        }
    }

    public static <E> String enumToString(Class<?> klass, E e) {
        for (var field : klass.getFields()) {
            try {
                if (Modifier.isStatic(field.getModifiers()) && e.equals(field.get(null))) {
                    return field.getName();
                }
            } catch (IllegalAccessException _) {
            }
        }
        return null;
    }

    public static MemorySegment upcallStub(Arena arena, MethodHandle methodHandle, FunctionDescriptor functionDescriptor) {
        return Linker.nativeLinker().upcallStub(methodHandle, functionDescriptor, arena);
    }

    public static Optional<MethodHandle> downcallHandle(SymbolLookup lookup, String functionName, FunctionDescriptor fd, boolean critical) {
        return Objects.requireNonNull(lookup).find(Objects.requireNonNull(functionName)).map(ms -> critical ?
                Linker.nativeLinker().downcallHandle(ms, fd, Linker.Option.critical(true))
                : Linker.nativeLinker().downcallHandle(ms, fd));
    }

    public static MethodHandle downcallHandle(MemorySegment ms, FunctionDescriptor fd, boolean critical) {
        return critical ?
                Linker.nativeLinker().downcallHandle(ms, fd, Linker.Option.critical(true))
                : Linker.nativeLinker().downcallHandle(ms, fd);
    }
}
