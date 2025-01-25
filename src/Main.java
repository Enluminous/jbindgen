import analyser.Analyser;
import libclang.LibclangSymbols;
import preprocessor.Preprocessor;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;
import java.util.List;
import java.util.Optional;

public class Main {

    private static void disableClangCrashRecovery() {
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

    public static void main(String[] args) {
        disableClangCrashRecovery();

        LibclangSymbols.addSymbols(SymbolLookup.libraryLookup("libclang-17.so.1", Arena.global()));
        var analyser = new Analyser("test/miniaudio.h", List.of("-I", "/usr/include"));

        new Preprocessor(analyser.getFunctions(), analyser.getMacros(), analyser.getVarDeclares());

        analyser.close();
        System.err.println("Hello world!");
    }
}