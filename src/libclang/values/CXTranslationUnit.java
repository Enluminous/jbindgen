package libclang.values;


import libclang.shared.Pointer;
import libclang.shared.Value;
import libclang.shared.VPointerList;
import libclang.shared.values.VPointerBasic;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.util.Collection;
import java.util.function.Consumer;

public class CXTranslationUnit extends VPointerBasic<CXTranslationUnit> {

    public static VPointerList<CXTranslationUnit> list(Pointer<CXTranslationUnit> ptr) {
        return new VPointerList<>(ptr, CXTranslationUnit::new);
    }

    public static VPointerList<CXTranslationUnit> list(Pointer<CXTranslationUnit> ptr, long length) {
        return new VPointerList<>(ptr, length, CXTranslationUnit::new);
    }

    public static VPointerList<CXTranslationUnit> list(SegmentAllocator allocator, long length) {
        return new VPointerList<>(allocator, length, CXTranslationUnit::new);
    }

    public static VPointerList<CXTranslationUnit> list(SegmentAllocator allocator, CXTranslationUnit[] c) {
        return new VPointerList<>(allocator, c, CXTranslationUnit::new);
    }

    public static VPointerList<CXTranslationUnit> list(SegmentAllocator allocator, Collection<CXTranslationUnit> c) {
        return new VPointerList<>(allocator, c, CXTranslationUnit::new);
    }

    public CXTranslationUnit(Pointer<CXTranslationUnit> ptr) {
        super(ptr);
    }

    public CXTranslationUnit(MemorySegment value) {
        super(value);
    }

    public CXTranslationUnit(Value<MemorySegment> value) {
        super(value);
    }

    public CXTranslationUnit(CXTranslationUnit value) {
        super(value);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXTranslationUnit that && that.value().equals(value());
    }
}
