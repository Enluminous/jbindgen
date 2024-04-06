package libclang.values;


import libclang.shared.Pointer;
import libclang.shared.Value;
import libclang.shared.VPointerList;
import libclang.shared.values.VPointerBasic;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.util.Collection;
import java.util.function.Consumer;

public class CXIndexAction extends VPointerBasic<CXIndexAction> {

    public static VPointerList<CXIndexAction> list(Pointer<CXIndexAction> ptr) {
        return new VPointerList<>(ptr, CXIndexAction::new);
    }

    public static VPointerList<CXIndexAction> list(Pointer<CXIndexAction> ptr, long length) {
        return new VPointerList<>(ptr, length, CXIndexAction::new);
    }

    public static VPointerList<CXIndexAction> list(SegmentAllocator allocator, long length) {
        return new VPointerList<>(allocator, length, CXIndexAction::new);
    }

    public static VPointerList<CXIndexAction> list(SegmentAllocator allocator, CXIndexAction[] c) {
        return new VPointerList<>(allocator, c, CXIndexAction::new);
    }

    public static VPointerList<CXIndexAction> list(SegmentAllocator allocator, Collection<CXIndexAction> c) {
        return new VPointerList<>(allocator, c, CXIndexAction::new);
    }

    public CXIndexAction(Pointer<CXIndexAction> ptr) {
        super(ptr);
    }

    public CXIndexAction(MemorySegment value) {
        super(value);
    }

    public CXIndexAction(Value<MemorySegment> value) {
        super(value);
    }

    public CXIndexAction(CXIndexAction value) {
        super(value);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXIndexAction that && that.value().equals(value());
    }
}
