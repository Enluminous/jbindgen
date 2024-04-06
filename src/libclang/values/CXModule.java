package libclang.values;


import libclang.shared.Pointer;
import libclang.shared.Value;
import libclang.shared.VPointerList;
import libclang.shared.values.VPointerBasic;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.util.Collection;
import java.util.function.Consumer;

public class CXModule extends VPointerBasic<CXModule> {

    public static VPointerList<CXModule> list(Pointer<CXModule> ptr) {
        return new VPointerList<>(ptr, CXModule::new);
    }

    public static VPointerList<CXModule> list(Pointer<CXModule> ptr, long length) {
        return new VPointerList<>(ptr, length, CXModule::new);
    }

    public static VPointerList<CXModule> list(SegmentAllocator allocator, long length) {
        return new VPointerList<>(allocator, length, CXModule::new);
    }

    public static VPointerList<CXModule> list(SegmentAllocator allocator, CXModule[] c) {
        return new VPointerList<>(allocator, c, CXModule::new);
    }

    public static VPointerList<CXModule> list(SegmentAllocator allocator, Collection<CXModule> c) {
        return new VPointerList<>(allocator, c, CXModule::new);
    }

    public CXModule(Pointer<CXModule> ptr) {
        super(ptr);
    }

    public CXModule(MemorySegment value) {
        super(value);
    }

    public CXModule(Value<MemorySegment> value) {
        super(value);
    }

    public CXModule(CXModule value) {
        super(value);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXModule that && that.value().equals(value());
    }
}
