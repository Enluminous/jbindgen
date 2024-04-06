package libclang.values;


import libclang.shared.Pointer;
import libclang.shared.Value;
import libclang.shared.VPointerList;
import libclang.shared.values.VPointerBasic;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.util.Collection;
import java.util.function.Consumer;

public class CXPrintingPolicy extends VPointerBasic<CXPrintingPolicy> {

    public static VPointerList<CXPrintingPolicy> list(Pointer<CXPrintingPolicy> ptr) {
        return new VPointerList<>(ptr, CXPrintingPolicy::new);
    }

    public static VPointerList<CXPrintingPolicy> list(Pointer<CXPrintingPolicy> ptr, long length) {
        return new VPointerList<>(ptr, length, CXPrintingPolicy::new);
    }

    public static VPointerList<CXPrintingPolicy> list(SegmentAllocator allocator, long length) {
        return new VPointerList<>(allocator, length, CXPrintingPolicy::new);
    }

    public static VPointerList<CXPrintingPolicy> list(SegmentAllocator allocator, CXPrintingPolicy[] c) {
        return new VPointerList<>(allocator, c, CXPrintingPolicy::new);
    }

    public static VPointerList<CXPrintingPolicy> list(SegmentAllocator allocator, Collection<CXPrintingPolicy> c) {
        return new VPointerList<>(allocator, c, CXPrintingPolicy::new);
    }

    public CXPrintingPolicy(Pointer<CXPrintingPolicy> ptr) {
        super(ptr);
    }

    public CXPrintingPolicy(MemorySegment value) {
        super(value);
    }

    public CXPrintingPolicy(Value<MemorySegment> value) {
        super(value);
    }

    public CXPrintingPolicy(CXPrintingPolicy value) {
        super(value);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXPrintingPolicy that && that.value().equals(value());
    }
}
