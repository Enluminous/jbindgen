package libclang.values;


import libclang.shared.Pointer;
import libclang.shared.Value;
import libclang.shared.VPointerList;
import libclang.shared.values.VPointerBasic;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.util.Collection;
import java.util.function.Consumer;

public class CXIdxClientContainer extends VPointerBasic<CXIdxClientContainer> {

    public static VPointerList<CXIdxClientContainer> list(Pointer<CXIdxClientContainer> ptr) {
        return new VPointerList<>(ptr, CXIdxClientContainer::new);
    }

    public static VPointerList<CXIdxClientContainer> list(Pointer<CXIdxClientContainer> ptr, long length) {
        return new VPointerList<>(ptr, length, CXIdxClientContainer::new);
    }

    public static VPointerList<CXIdxClientContainer> list(SegmentAllocator allocator, long length) {
        return new VPointerList<>(allocator, length, CXIdxClientContainer::new);
    }

    public static VPointerList<CXIdxClientContainer> list(SegmentAllocator allocator, CXIdxClientContainer[] c) {
        return new VPointerList<>(allocator, c, CXIdxClientContainer::new);
    }

    public static VPointerList<CXIdxClientContainer> list(SegmentAllocator allocator, Collection<CXIdxClientContainer> c) {
        return new VPointerList<>(allocator, c, CXIdxClientContainer::new);
    }

    public CXIdxClientContainer(Pointer<CXIdxClientContainer> ptr) {
        super(ptr);
    }

    public CXIdxClientContainer(MemorySegment value) {
        super(value);
    }

    public CXIdxClientContainer(Value<MemorySegment> value) {
        super(value);
    }

    public CXIdxClientContainer(CXIdxClientContainer value) {
        super(value);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXIdxClientContainer that && that.value().equals(value());
    }
}
