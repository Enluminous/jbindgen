package libclang.values;


import libclang.shared.Pointer;
import libclang.shared.Value;
import libclang.shared.VPointerList;
import libclang.shared.values.VPointerBasic;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.util.Collection;
import java.util.function.Consumer;

public class CXRemapping extends VPointerBasic<CXRemapping> {

    public static VPointerList<CXRemapping> list(Pointer<CXRemapping> ptr) {
        return new VPointerList<>(ptr, CXRemapping::new);
    }

    public static VPointerList<CXRemapping> list(Pointer<CXRemapping> ptr, long length) {
        return new VPointerList<>(ptr, length, CXRemapping::new);
    }

    public static VPointerList<CXRemapping> list(SegmentAllocator allocator, long length) {
        return new VPointerList<>(allocator, length, CXRemapping::new);
    }

    public static VPointerList<CXRemapping> list(SegmentAllocator allocator, CXRemapping[] c) {
        return new VPointerList<>(allocator, c, CXRemapping::new);
    }

    public static VPointerList<CXRemapping> list(SegmentAllocator allocator, Collection<CXRemapping> c) {
        return new VPointerList<>(allocator, c, CXRemapping::new);
    }

    public CXRemapping(Pointer<CXRemapping> ptr) {
        super(ptr);
    }

    public CXRemapping(MemorySegment value) {
        super(value);
    }

    public CXRemapping(Value<MemorySegment> value) {
        super(value);
    }

    public CXRemapping(CXRemapping value) {
        super(value);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXRemapping that && that.value().equals(value());
    }
}
