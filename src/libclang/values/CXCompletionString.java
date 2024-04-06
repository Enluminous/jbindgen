package libclang.values;


import libclang.shared.Pointer;
import libclang.shared.Value;
import libclang.shared.VPointerList;
import libclang.shared.values.VPointerBasic;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.util.Collection;
import java.util.function.Consumer;

public class CXCompletionString extends VPointerBasic<CXCompletionString> {

    public static VPointerList<CXCompletionString> list(Pointer<CXCompletionString> ptr) {
        return new VPointerList<>(ptr, CXCompletionString::new);
    }

    public static VPointerList<CXCompletionString> list(Pointer<CXCompletionString> ptr, long length) {
        return new VPointerList<>(ptr, length, CXCompletionString::new);
    }

    public static VPointerList<CXCompletionString> list(SegmentAllocator allocator, long length) {
        return new VPointerList<>(allocator, length, CXCompletionString::new);
    }

    public static VPointerList<CXCompletionString> list(SegmentAllocator allocator, CXCompletionString[] c) {
        return new VPointerList<>(allocator, c, CXCompletionString::new);
    }

    public static VPointerList<CXCompletionString> list(SegmentAllocator allocator, Collection<CXCompletionString> c) {
        return new VPointerList<>(allocator, c, CXCompletionString::new);
    }

    public CXCompletionString(Pointer<CXCompletionString> ptr) {
        super(ptr);
    }

    public CXCompletionString(MemorySegment value) {
        super(value);
    }

    public CXCompletionString(Value<MemorySegment> value) {
        super(value);
    }

    public CXCompletionString(CXCompletionString value) {
        super(value);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXCompletionString that && that.value().equals(value());
    }
}
