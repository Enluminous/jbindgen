package utils;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;
import java.util.Optional;

public class CommonUtils {
    public static void Assert(boolean bool, String msg) {
        if (!bool)
            throw new AssertionError(msg);
    }

    public static void Assert(boolean bool) {
        if (!bool)
            throw new AssertionError();
    }

    private static final boolean IS_WINDOWS = System.getProperty("os.name").startsWith("Windows");

    public static void disableClangCrashRecovery() {
        Linker linker = Linker.nativeLinker();
        try (Arena mem = Arena.ofConfined()) {
            MemorySegment argv1 = mem.allocateFrom("LIBCLANG_DISABLE_CRASH_RECOVERY");
            MemorySegment argv2 = mem.allocateFrom("1");

            if (IS_WINDOWS) {
                Optional<MemorySegment> putenvHandle = linker.defaultLookup().find("_putenv_s");
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

    public static String getLibClangName() {
        if (IS_WINDOWS)
            return "libclang";
        return "libclang-17.so.1";
    }


    private CommonUtils() {
        throw new UnsupportedOperationException();
    }
}
