package libclang.values;


import libclang.shared.Pointer;
import libclang.shared.Value;
import libclang.shared.VPointerList;
import libclang.shared.values.VPointerBasic;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.util.Collection;
import java.util.function.Consumer;

public class CXIdxClientFile extends VPointerBasic<CXIdxClientFile> {

    public static VPointerList<CXIdxClientFile> list(Pointer<CXIdxClientFile> ptr) {
        return new VPointerList<>(ptr, CXIdxClientFile::new);
    }

    public static VPointerList<CXIdxClientFile> list(Pointer<CXIdxClientFile> ptr, long length) {
        return new VPointerList<>(ptr, length, CXIdxClientFile::new);
    }

    public static VPointerList<CXIdxClientFile> list(SegmentAllocator allocator, long length) {
        return new VPointerList<>(allocator, length, CXIdxClientFile::new);
    }

    public static VPointerList<CXIdxClientFile> list(SegmentAllocator allocator, CXIdxClientFile[] c) {
        return new VPointerList<>(allocator, c, CXIdxClientFile::new);
    }

    public static VPointerList<CXIdxClientFile> list(SegmentAllocator allocator, Collection<CXIdxClientFile> c) {
        return new VPointerList<>(allocator, c, CXIdxClientFile::new);
    }

    public CXIdxClientFile(Pointer<CXIdxClientFile> ptr) {
        super(ptr);
    }

    public CXIdxClientFile(MemorySegment value) {
        super(value);
    }

    public CXIdxClientFile(Value<MemorySegment> value) {
        super(value);
    }

    public CXIdxClientFile(CXIdxClientFile value) {
        super(value);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXIdxClientFile that && that.value().equals(value());
    }
}
