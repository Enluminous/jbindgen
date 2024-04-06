package libclang.values;


import libclang.shared.Pointer;
import libclang.shared.Value;
import libclang.shared.VPointerList;
import libclang.shared.values.VPointerBasic;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.util.Collection;
import java.util.function.Consumer;

public class CXDiagnostic extends VPointerBasic<CXDiagnostic> {

    public static VPointerList<CXDiagnostic> list(Pointer<CXDiagnostic> ptr) {
        return new VPointerList<>(ptr, CXDiagnostic::new);
    }

    public static VPointerList<CXDiagnostic> list(Pointer<CXDiagnostic> ptr, long length) {
        return new VPointerList<>(ptr, length, CXDiagnostic::new);
    }

    public static VPointerList<CXDiagnostic> list(SegmentAllocator allocator, long length) {
        return new VPointerList<>(allocator, length, CXDiagnostic::new);
    }

    public static VPointerList<CXDiagnostic> list(SegmentAllocator allocator, CXDiagnostic[] c) {
        return new VPointerList<>(allocator, c, CXDiagnostic::new);
    }

    public static VPointerList<CXDiagnostic> list(SegmentAllocator allocator, Collection<CXDiagnostic> c) {
        return new VPointerList<>(allocator, c, CXDiagnostic::new);
    }

    public CXDiagnostic(Pointer<CXDiagnostic> ptr) {
        super(ptr);
    }

    public CXDiagnostic(MemorySegment value) {
        super(value);
    }

    public CXDiagnostic(Value<MemorySegment> value) {
        super(value);
    }

    public CXDiagnostic(CXDiagnostic value) {
        super(value);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXDiagnostic that && that.value().equals(value());
    }
}
