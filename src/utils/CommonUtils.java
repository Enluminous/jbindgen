package utils;

import libclang.shared.FunctionUtils;
import libclang.shared.Pointer;
import libclang.shared.values.VPointerBasic;

import java.io.File;
import java.lang.foreign.MemorySegment;
import java.util.function.Function;

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

    public static String requireFileExists(String file) {
        if (new File(file).exists())
            return file;
        else
            throw new RuntimeException("required file not exits: " + file);
    }

    public static File requireFileExists(File file) {
        if (file.exists())
            return file;
        else
            throw new RuntimeException("required file not exist: " + file.getPath());
    }

    public static RuntimeException shouldNotReachHere() {
        throw new RuntimeException("should not reach here");
    }

    public static RuntimeException shouldNotReachHere(String msg) {
        throw new RuntimeException("should not reach here: " + msg);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Throwable> void throwCheckedException(Throwable toThrow) throws T {
        throw (T) toThrow;
    }

    public static String getStackString() {
        StringBuilder builder = new StringBuilder();
        StackWalker.getInstance().forEach(stackFrame -> builder.append(stackFrame.toString()).append("\n"));
        return builder.toString();
    }

    public static <T> Pointer<T> makeOnHeap(long byteSize) {
        Assert(byteSize < Integer.MAX_VALUE, "byteSize: " + byteSize + " bigger than Integer.MAX_VALUE");
        return FunctionUtils.makePointer(MemorySegment.ofArray(new int[(int) Math.ceilDiv(byteSize, 4)]));
    }

    public static <T extends Pointer<T>> T makeOnHeap(T t, Function<Pointer<T>, T> constructor) {
        Pointer<T> onHeap = makeOnHeap(t.pointer().byteSize());
        onHeap.pointer().copyFrom(t.pointer());
        return constructor.apply(onHeap);
    }

    public static <T> T requireNonNull(T obj) {
        switch (obj) {
            case null -> throw new NullPointerException();
            case Pointer<?> pointer -> requireNonNull(pointer.pointer());
            case MemorySegment ms when isNull(ms) -> throw new NullPointerException();
            case VPointerBasic<?> pointer -> requireNonNull(pointer.value());
            default -> {
            }
        }
        return obj;
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
