package libclang.values;


import libclang.shared.Pointer;
import libclang.shared.Value;
import libclang.shared.VPointerList;
import libclang.shared.values.VPointerBasic;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.util.Collection;
import java.util.function.Consumer;

public class CXIdxClientASTFile extends VPointerBasic<CXIdxClientASTFile> {

    public static VPointerList<CXIdxClientASTFile> list(Pointer<CXIdxClientASTFile> ptr) {
        return new VPointerList<>(ptr, CXIdxClientASTFile::new);
    }

    public static VPointerList<CXIdxClientASTFile> list(Pointer<CXIdxClientASTFile> ptr, long length) {
        return new VPointerList<>(ptr, length, CXIdxClientASTFile::new);
    }

    public static VPointerList<CXIdxClientASTFile> list(SegmentAllocator allocator, long length) {
        return new VPointerList<>(allocator, length, CXIdxClientASTFile::new);
    }

    public static VPointerList<CXIdxClientASTFile> list(SegmentAllocator allocator, CXIdxClientASTFile[] c) {
        return new VPointerList<>(allocator, c, CXIdxClientASTFile::new);
    }

    public static VPointerList<CXIdxClientASTFile> list(SegmentAllocator allocator, Collection<CXIdxClientASTFile> c) {
        return new VPointerList<>(allocator, c, CXIdxClientASTFile::new);
    }

    public CXIdxClientASTFile(Pointer<CXIdxClientASTFile> ptr) {
        super(ptr);
    }

    public CXIdxClientASTFile(MemorySegment value) {
        super(value);
    }

    public CXIdxClientASTFile(Value<MemorySegment> value) {
        super(value);
    }

    public CXIdxClientASTFile(CXIdxClientASTFile value) {
        super(value);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXIdxClientASTFile that && that.value().equals(value());
    }
}
