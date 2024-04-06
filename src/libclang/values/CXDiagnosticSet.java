package libclang.values;


import libclang.shared.Pointer;
import libclang.shared.Value;
import libclang.shared.VPointerList;
import libclang.shared.values.VPointerBasic;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.util.Collection;
import java.util.function.Consumer;

public class CXDiagnosticSet extends VPointerBasic<CXDiagnosticSet> {

    public static VPointerList<CXDiagnosticSet> list(Pointer<CXDiagnosticSet> ptr) {
        return new VPointerList<>(ptr, CXDiagnosticSet::new);
    }

    public static VPointerList<CXDiagnosticSet> list(Pointer<CXDiagnosticSet> ptr, long length) {
        return new VPointerList<>(ptr, length, CXDiagnosticSet::new);
    }

    public static VPointerList<CXDiagnosticSet> list(SegmentAllocator allocator, long length) {
        return new VPointerList<>(allocator, length, CXDiagnosticSet::new);
    }

    public static VPointerList<CXDiagnosticSet> list(SegmentAllocator allocator, CXDiagnosticSet[] c) {
        return new VPointerList<>(allocator, c, CXDiagnosticSet::new);
    }

    public static VPointerList<CXDiagnosticSet> list(SegmentAllocator allocator, Collection<CXDiagnosticSet> c) {
        return new VPointerList<>(allocator, c, CXDiagnosticSet::new);
    }

    public CXDiagnosticSet(Pointer<CXDiagnosticSet> ptr) {
        super(ptr);
    }

    public CXDiagnosticSet(MemorySegment value) {
        super(value);
    }

    public CXDiagnosticSet(Value<MemorySegment> value) {
        super(value);
    }

    public CXDiagnosticSet(CXDiagnosticSet value) {
        super(value);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXDiagnosticSet that && that.value().equals(value());
    }
}
