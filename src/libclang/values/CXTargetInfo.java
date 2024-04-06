package libclang.values;


import libclang.shared.Pointer;
import libclang.shared.Value;
import libclang.shared.VPointerList;
import libclang.shared.values.VPointerBasic;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.util.Collection;
import java.util.function.Consumer;

public class CXTargetInfo extends VPointerBasic<CXTargetInfo> {

    public static VPointerList<CXTargetInfo> list(Pointer<CXTargetInfo> ptr) {
        return new VPointerList<>(ptr, CXTargetInfo::new);
    }

    public static VPointerList<CXTargetInfo> list(Pointer<CXTargetInfo> ptr, long length) {
        return new VPointerList<>(ptr, length, CXTargetInfo::new);
    }

    public static VPointerList<CXTargetInfo> list(SegmentAllocator allocator, long length) {
        return new VPointerList<>(allocator, length, CXTargetInfo::new);
    }

    public static VPointerList<CXTargetInfo> list(SegmentAllocator allocator, CXTargetInfo[] c) {
        return new VPointerList<>(allocator, c, CXTargetInfo::new);
    }

    public static VPointerList<CXTargetInfo> list(SegmentAllocator allocator, Collection<CXTargetInfo> c) {
        return new VPointerList<>(allocator, c, CXTargetInfo::new);
    }

    public CXTargetInfo(Pointer<CXTargetInfo> ptr) {
        super(ptr);
    }

    public CXTargetInfo(MemorySegment value) {
        super(value);
    }

    public CXTargetInfo(Value<MemorySegment> value) {
        super(value);
    }

    public CXTargetInfo(CXTargetInfo value) {
        super(value);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXTargetInfo that && that.value().equals(value());
    }
}
