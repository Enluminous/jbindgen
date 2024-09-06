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


public final class CXCompletionResult implements Pointer<CXCompletionResult> {
    public static final MemoryLayout MEMORY_LAYOUT = MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE));
    public static final long BYTE_SIZE = MEMORY_LAYOUT.byteSize();

    public static NList<CXCompletionResult> list(Pointer<CXCompletionResult> ptr) {
        return new NList<>(ptr, CXCompletionResult::new, BYTE_SIZE);
    }

    public static NList<CXCompletionResult> list(Pointer<CXCompletionResult> ptr, long length) {
        return new NList<>(ptr, length, CXCompletionResult::new, BYTE_SIZE);
    }

    public static NList<CXCompletionResult> list(SegmentAllocator allocator, long length) {
        return new NList<>(allocator, length, CXCompletionResult::new, BYTE_SIZE);
    }

    private final MemorySegment ptr;

    public CXCompletionResult(Pointer<CXCompletionResult> ptr) {
        this.ptr = ptr.pointer();
    }

    public CXCompletionResult(SegmentAllocator allocator) {
        ptr = allocator.allocate(BYTE_SIZE);
    }

    public CXCompletionResult reinterpretSize() {
        return new CXCompletionResult(FunctionUtils.makePointer(ptr.reinterpret(BYTE_SIZE)));
    }

    @Override
    public MemorySegment pointer() {
        return ptr;
    }

    public CXCursorKind CursorKind() {
        return new CXCursorKind(FunctionUtils.makePointer(ptr.asSlice(0, 4)));
    }

    public CXCompletionResult CursorKind(CXCursorKind CursorKind) {
        ptr.set(ValueLayout.JAVA_INT, 0, CursorKind.value());
        return this;
    }

    public CXCompletionString CompletionString() {
        return new CXCompletionString(ptr.get(ValueLayout.ADDRESS, 8));
    }

    public CXCompletionResult CompletionString(CXCompletionString CompletionString) {
        ptr.set(ValueLayout.ADDRESS, 8, CompletionString.value());
        return this;
    }


    @Override
    public String toString() {
        if (MemorySegment.NULL.address() == ptr.address() || ptr.byteSize() < BYTE_SIZE)
            return "CXCompletionResult{ptr=" + ptr;
        return "CXCompletionResult{" +
                "CursorKind=" + CursorKind() +
                "CompletionString=" + CompletionString() + "}";
    }
}