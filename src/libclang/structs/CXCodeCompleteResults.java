package libclang.structs;


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
import java.util.function.Consumer;


public final class CXCodeCompleteResults implements Pointer<CXCodeCompleteResults> {
    public static final MemoryLayout MEMORY_LAYOUT = MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE));
    public static final long BYTE_SIZE = MEMORY_LAYOUT.byteSize();

    public static NList<CXCodeCompleteResults> list(Pointer<CXCodeCompleteResults> ptr) {
        return new NList<>(ptr, CXCodeCompleteResults::new, BYTE_SIZE);
    }

    public static NList<CXCodeCompleteResults> list(Pointer<CXCodeCompleteResults> ptr, long length) {
        return new NList<>(ptr, length, CXCodeCompleteResults::new, BYTE_SIZE);
    }

    public static NList<CXCodeCompleteResults> list(SegmentAllocator allocator, long length) {
        return new NList<>(allocator, length, CXCodeCompleteResults::new, BYTE_SIZE);
    }

    private final MemorySegment ptr;

    public CXCodeCompleteResults(Pointer<CXCodeCompleteResults> ptr) {
        this.ptr = ptr.pointer();
    }

    public CXCodeCompleteResults(SegmentAllocator allocator) {
        ptr = allocator.allocate(BYTE_SIZE);
    }

    public CXCodeCompleteResults reinterpretSize() {
        return new CXCodeCompleteResults(FunctionUtils.makePointer(ptr.reinterpret(BYTE_SIZE)));
    }

    @Override
    public MemorySegment pointer() {
        return ptr;
    }

    public Pointer<CXCompletionResult> Results() {
        return FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS, 0));
    }

    public NList<CXCompletionResult> Results(long length) {
        return CXCompletionResult.list(FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS, 0)), length);
    }

    public CXCodeCompleteResults Results(Pointer<CXCompletionResult> Results) {
        ptr.set(ValueLayout.ADDRESS, 0, Results.pointer());
        return this;
    }

    public int NumResults() {
        return ptr.get(ValueLayout.JAVA_INT, 8);
    }

    public CXCodeCompleteResults NumResults(int NumResults) {
        ptr.set(ValueLayout.JAVA_INT, 8, NumResults);
        return this;
    }


    @Override
    public String toString() {
        if (MemorySegment.NULL.address() == ptr.address() || ptr.byteSize() < BYTE_SIZE)
            return "CXCodeCompleteResults{ptr=" + ptr;
        return "CXCodeCompleteResults{" +
                "Results=" + Results() +
                "NumResults=" + NumResults() + "}";
    }
}