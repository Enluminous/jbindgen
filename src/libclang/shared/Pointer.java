package libclang.shared;

import java.lang.foreign.MemorySegment;

public interface Pointer<P> {
    MemorySegment pointer();
}
