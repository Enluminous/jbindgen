package libclang.values;


import libclang.shared.Pointer;
import libclang.shared.Value;
import libclang.shared.VPointerList;
import libclang.shared.values.VPointerBasic;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.util.Collection;
import java.util.function.Consumer;

public class CXIdxClientEntity extends VPointerBasic<CXIdxClientEntity> {

    public static VPointerList<CXIdxClientEntity> list(Pointer<CXIdxClientEntity> ptr) {
        return new VPointerList<>(ptr, CXIdxClientEntity::new);
    }

    public static VPointerList<CXIdxClientEntity> list(Pointer<CXIdxClientEntity> ptr, long length) {
        return new VPointerList<>(ptr, length, CXIdxClientEntity::new);
    }

    public static VPointerList<CXIdxClientEntity> list(SegmentAllocator allocator, long length) {
        return new VPointerList<>(allocator, length, CXIdxClientEntity::new);
    }

    public static VPointerList<CXIdxClientEntity> list(SegmentAllocator allocator, CXIdxClientEntity[] c) {
        return new VPointerList<>(allocator, c, CXIdxClientEntity::new);
    }

    public static VPointerList<CXIdxClientEntity> list(SegmentAllocator allocator, Collection<CXIdxClientEntity> c) {
        return new VPointerList<>(allocator, c, CXIdxClientEntity::new);
    }

    public CXIdxClientEntity(Pointer<CXIdxClientEntity> ptr) {
        super(ptr);
    }

    public CXIdxClientEntity(MemorySegment value) {
        super(value);
    }

    public CXIdxClientEntity(Value<MemorySegment> value) {
        super(value);
    }

    public CXIdxClientEntity(CXIdxClientEntity value) {
        super(value);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXIdxClientEntity that && that.value().equals(value());
    }
}
