package libclang.values;


import libclang.shared.Pointer;
import libclang.shared.Value;
import libclang.shared.VPointerList;
import libclang.shared.values.VPointerBasic;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.util.Collection;
import java.util.function.Consumer;

public class CXVirtualFileOverlay extends VPointerBasic<CXVirtualFileOverlay> {

    public static VPointerList<CXVirtualFileOverlay> list(Pointer<CXVirtualFileOverlay> ptr) {
        return new VPointerList<>(ptr, CXVirtualFileOverlay::new);
    }

    public static VPointerList<CXVirtualFileOverlay> list(Pointer<CXVirtualFileOverlay> ptr, long length) {
        return new VPointerList<>(ptr, length, CXVirtualFileOverlay::new);
    }

    public static VPointerList<CXVirtualFileOverlay> list(SegmentAllocator allocator, long length) {
        return new VPointerList<>(allocator, length, CXVirtualFileOverlay::new);
    }

    public static VPointerList<CXVirtualFileOverlay> list(SegmentAllocator allocator, CXVirtualFileOverlay[] c) {
        return new VPointerList<>(allocator, c, CXVirtualFileOverlay::new);
    }

    public static VPointerList<CXVirtualFileOverlay> list(SegmentAllocator allocator, Collection<CXVirtualFileOverlay> c) {
        return new VPointerList<>(allocator, c, CXVirtualFileOverlay::new);
    }

    public CXVirtualFileOverlay(Pointer<CXVirtualFileOverlay> ptr) {
        super(ptr);
    }

    public CXVirtualFileOverlay(MemorySegment value) {
        super(value);
    }

    public CXVirtualFileOverlay(Value<MemorySegment> value) {
        super(value);
    }

    public CXVirtualFileOverlay(CXVirtualFileOverlay value) {
        super(value);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXVirtualFileOverlay that && that.value().equals(value());
    }
}
