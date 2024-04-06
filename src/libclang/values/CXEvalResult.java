package libclang.values;


import libclang.shared.Pointer;
import libclang.shared.Value;
import libclang.shared.VPointerList;
import libclang.shared.values.VPointerBasic;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.util.Collection;
import java.util.function.Consumer;

public class CXEvalResult extends VPointerBasic<CXEvalResult> {

    public static VPointerList<CXEvalResult> list(Pointer<CXEvalResult> ptr) {
        return new VPointerList<>(ptr, CXEvalResult::new);
    }

    public static VPointerList<CXEvalResult> list(Pointer<CXEvalResult> ptr, long length) {
        return new VPointerList<>(ptr, length, CXEvalResult::new);
    }

    public static VPointerList<CXEvalResult> list(SegmentAllocator allocator, long length) {
        return new VPointerList<>(allocator, length, CXEvalResult::new);
    }

    public static VPointerList<CXEvalResult> list(SegmentAllocator allocator, CXEvalResult[] c) {
        return new VPointerList<>(allocator, c, CXEvalResult::new);
    }

    public static VPointerList<CXEvalResult> list(SegmentAllocator allocator, Collection<CXEvalResult> c) {
        return new VPointerList<>(allocator, c, CXEvalResult::new);
    }

    public CXEvalResult(Pointer<CXEvalResult> ptr) {
        super(ptr);
    }

    public CXEvalResult(MemorySegment value) {
        super(value);
    }

    public CXEvalResult(Value<MemorySegment> value) {
        super(value);
    }

    public CXEvalResult(CXEvalResult value) {
        super(value);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXEvalResult that && that.value().equals(value());
    }
}
