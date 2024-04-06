package libclang.values;


import libclang.shared.Pointer;
import libclang.shared.Value;
import libclang.shared.VPointerList;
import libclang.shared.values.VPointerBasic;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.util.Collection;
import java.util.function.Consumer;

public class CXFile extends VPointerBasic<CXFile> {

    public static VPointerList<CXFile> list(Pointer<CXFile> ptr) {
        return new VPointerList<>(ptr, CXFile::new);
    }

    public static VPointerList<CXFile> list(Pointer<CXFile> ptr, long length) {
        return new VPointerList<>(ptr, length, CXFile::new);
    }

    public static VPointerList<CXFile> list(SegmentAllocator allocator, long length) {
        return new VPointerList<>(allocator, length, CXFile::new);
    }

    public static VPointerList<CXFile> list(SegmentAllocator allocator, CXFile[] c) {
        return new VPointerList<>(allocator, c, CXFile::new);
    }

    public static VPointerList<CXFile> list(SegmentAllocator allocator, Collection<CXFile> c) {
        return new VPointerList<>(allocator, c, CXFile::new);
    }

    public CXFile(Pointer<CXFile> ptr) {
        super(ptr);
    }

    public CXFile(MemorySegment value) {
        super(value);
    }

    public CXFile(Value<MemorySegment> value) {
        super(value);
    }

    public CXFile(CXFile value) {
        super(value);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXFile that && that.value().equals(value());
    }
}
