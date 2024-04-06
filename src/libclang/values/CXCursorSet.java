package libclang.values;


import libclang.shared.Pointer;
import libclang.shared.Value;
import libclang.shared.VPointerList;
import libclang.shared.values.VPointerBasic;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.util.Collection;
import java.util.function.Consumer;

public class CXCursorSet extends VPointerBasic<CXCursorSet> {

    public static VPointerList<CXCursorSet> list(Pointer<CXCursorSet> ptr) {
        return new VPointerList<>(ptr, CXCursorSet::new);
    }

    public static VPointerList<CXCursorSet> list(Pointer<CXCursorSet> ptr, long length) {
        return new VPointerList<>(ptr, length, CXCursorSet::new);
    }

    public static VPointerList<CXCursorSet> list(SegmentAllocator allocator, long length) {
        return new VPointerList<>(allocator, length, CXCursorSet::new);
    }

    public static VPointerList<CXCursorSet> list(SegmentAllocator allocator, CXCursorSet[] c) {
        return new VPointerList<>(allocator, c, CXCursorSet::new);
    }

    public static VPointerList<CXCursorSet> list(SegmentAllocator allocator, Collection<CXCursorSet> c) {
        return new VPointerList<>(allocator, c, CXCursorSet::new);
    }

    public CXCursorSet(Pointer<CXCursorSet> ptr) {
        super(ptr);
    }

    public CXCursorSet(MemorySegment value) {
        super(value);
    }

    public CXCursorSet(Value<MemorySegment> value) {
        super(value);
    }

    public CXCursorSet(CXCursorSet value) {
        super(value);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXCursorSet that && that.value().equals(value());
    }
}
