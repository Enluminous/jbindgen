package utils;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

public class CheckedArena extends AutoCloseableChecker.CheckedAutoCloseable implements Arena {
    public static CheckedArena ofConfined() {
        return new CheckedArena(Arena.ofConfined());
    }

    public static CheckedArena ofShared() {
        return new CheckedArena(Arena.ofShared());
    }

    @Override
    public MemorySegment allocate(long byteSize, long byteAlignment) {
        return arena.allocate(byteSize, byteAlignment);
    }

    @Override
    public MemorySegment.Scope scope() {
        return arena.scope();
    }

    private final Arena arena;

    private CheckedArena(Arena arena) {
        this.arena = arena;
    }

    @Override
    public void close() {
        super.close();
        arena.close();
    }
}
