package libclang.values;


import libclang.shared.Pointer;
import libclang.shared.Value;
import libclang.shared.VPointerList;
import libclang.shared.values.VPointerBasic;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.util.Collection;
import java.util.function.Consumer;

public class CXClientData extends VPointerBasic<CXClientData> {

    public static VPointerList<CXClientData> list(Pointer<CXClientData> ptr) {
        return new VPointerList<>(ptr, CXClientData::new);
    }

    public static VPointerList<CXClientData> list(Pointer<CXClientData> ptr, long length) {
        return new VPointerList<>(ptr, length, CXClientData::new);
    }

    public static VPointerList<CXClientData> list(SegmentAllocator allocator, long length) {
        return new VPointerList<>(allocator, length, CXClientData::new);
    }

    public static VPointerList<CXClientData> list(SegmentAllocator allocator, CXClientData[] c) {
        return new VPointerList<>(allocator, c, CXClientData::new);
    }

    public static VPointerList<CXClientData> list(SegmentAllocator allocator, Collection<CXClientData> c) {
        return new VPointerList<>(allocator, c, CXClientData::new);
    }

    public CXClientData(Pointer<CXClientData> ptr) {
        super(ptr);
    }

    public CXClientData(MemorySegment value) {
        super(value);
    }

    public CXClientData(Value<MemorySegment> value) {
        super(value);
    }

    public CXClientData(CXClientData value) {
        super(value);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXClientData that && that.value().equals(value());
    }
}
