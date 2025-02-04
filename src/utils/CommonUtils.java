package utils;

import libclang.shared.Pointer;
import libclang.shared.values.VPointerBasic;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;
import java.util.Optional;

import static java.lang.foreign.MemorySegment.NULL;

public class CommonUtils {
    public static void Assert(boolean bool, String msg) {
        if (!bool)
            throw new AssertionError(msg);
    }

    public static void Assert(boolean bool) {
        if (!bool)
            throw new AssertionError();
    }

    public static void disableClangCrashRecovery() {
        Linker linker = Linker.nativeLinker();
        try (Arena mem = Arena.ofConfined()) {
            MemorySegment argv1 = mem.allocateFrom("LIBCLANG_DISABLE_CRASH_RECOVERY");
            MemorySegment argv2 = mem.allocateFrom("1");

            if (System.getProperty("os.name").startsWith("Windows")) {
                Optional<MemorySegment> putenvHandle = linker.defaultLookup().find("SetEnvironmentVariable");
                MethodHandle putenvFunc = linker.downcallHandle(putenvHandle.orElseThrow(), FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS));
                int _ = (int) putenvFunc.invokeExact(argv1, argv2);
            } else {
                Optional<MemorySegment> putenvHandle = linker.defaultLookup().find("setenv");
                MethodHandle putenvFunc = linker.downcallHandle(putenvHandle.orElseThrow(), FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_INT));
                int _ = (int) putenvFunc.invokeExact(argv1, argv2, 1);
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static String getStackString() {
        StringBuilder builder = new StringBuilder();
        StackWalker.getInstance().forEach(stackFrame -> builder.append(stackFrame.toString()).append("\n"));
        return builder.toString();
    }

    public static <T> Pointer<T> nullptr() {
        return () -> NULL;
    }

    public static <T> boolean isNull(T t) {
        switch (t) {
            case Pointer<?> p -> {
                return isNull(p.pointer());
            }
            case MemorySegment m -> {
                return m.address() == MemorySegment.NULL.address();
            }
            case VPointerBasic<?> vPointer -> {
                return isNull(vPointer.value());
            }
            case null -> {
                return true;
            }
            default -> throw new IllegalStateException("Unexpected value: " + t);
        }
    }


    private CommonUtils() {
        throw new UnsupportedOperationException();
    }
}
