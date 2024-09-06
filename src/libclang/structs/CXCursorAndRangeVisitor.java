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


public final class CXCursorAndRangeVisitor implements Pointer<CXCursorAndRangeVisitor> {
    public static final MemoryLayout MEMORY_LAYOUT = MemoryLayout.structLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_BYTE));
    public static final long BYTE_SIZE = MEMORY_LAYOUT.byteSize();

    public static NList<CXCursorAndRangeVisitor> list(Pointer<CXCursorAndRangeVisitor> ptr) {
        return new NList<>(ptr, CXCursorAndRangeVisitor::new, BYTE_SIZE);
    }

    public static NList<CXCursorAndRangeVisitor> list(Pointer<CXCursorAndRangeVisitor> ptr, long length) {
        return new NList<>(ptr, length, CXCursorAndRangeVisitor::new, BYTE_SIZE);
    }

    public static NList<CXCursorAndRangeVisitor> list(SegmentAllocator allocator, long length) {
        return new NList<>(allocator, length, CXCursorAndRangeVisitor::new, BYTE_SIZE);
    }

    private final MemorySegment ptr;

    public CXCursorAndRangeVisitor(Pointer<CXCursorAndRangeVisitor> ptr) {
        this.ptr = ptr.pointer();
    }

    public CXCursorAndRangeVisitor(SegmentAllocator allocator) {
        ptr = allocator.allocate(BYTE_SIZE);
    }

    public CXCursorAndRangeVisitor reinterpretSize() {
        return new CXCursorAndRangeVisitor(FunctionUtils.makePointer(ptr.reinterpret(BYTE_SIZE)));
    }

    @Override
    public MemorySegment pointer() {
        return ptr;
    }

    public Pointer<?> context() {
        return FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS, 0));
    }

    public CXCursorAndRangeVisitor context(Pointer<?> context) {
        ptr.set(ValueLayout.ADDRESS, 0, context.pointer());
        return this;
    }

    public VPointer<CXCursorAndRangeVisitor$visit> visit() {
        return new VPointer<>(ptr.get(ValueLayout.ADDRESS, 8));
    }

    public CXCursorAndRangeVisitor visit(Pointer<CXCursorAndRangeVisitor$visit> visit) {
        ptr.set(ValueLayout.ADDRESS, 8, visit.pointer());
        return this;
    }


    @Override
    public String toString() {
        if (MemorySegment.NULL.address() == ptr.address() || ptr.byteSize() < BYTE_SIZE)
            return "CXCursorAndRangeVisitor{ptr=" + ptr;
        return "CXCursorAndRangeVisitor{" +
                "context=" + context() +
                "visit=" + visit() + "}";
    }
}