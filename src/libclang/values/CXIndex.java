package libclang.values;


import libclang.shared.Pointer;
import libclang.shared.Value;
import libclang.shared.VPointerList;
import libclang.shared.values.VPointerBasic;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.util.Collection;
import java.util.function.Consumer;

public class CXIndex extends VPointerBasic<CXIndex> {

    public static VPointerList<CXIndex> list(Pointer<CXIndex> ptr) {
        return new VPointerList<>(ptr, CXIndex::new);
    }

    public static VPointerList<CXIndex> list(Pointer<CXIndex> ptr, long length) {
        return new VPointerList<>(ptr, length, CXIndex::new);
    }

    public static VPointerList<CXIndex> list(SegmentAllocator allocator, long length) {
        return new VPointerList<>(allocator, length, CXIndex::new);
    }

    public static VPointerList<CXIndex> list(SegmentAllocator allocator, CXIndex[] c) {
        return new VPointerList<>(allocator, c, CXIndex::new);
    }

    public static VPointerList<CXIndex> list(SegmentAllocator allocator, Collection<CXIndex> c) {
        return new VPointerList<>(allocator, c, CXIndex::new);
    }

    public CXIndex(Pointer<CXIndex> ptr) {
        super(ptr);
    }

    public CXIndex(MemorySegment value) {
        super(value);
    }

    public CXIndex(Value<MemorySegment> value) {
        super(value);
    }

    public CXIndex(CXIndex value) {
        super(value);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXIndex that && that.value().equals(value());
    }
}
