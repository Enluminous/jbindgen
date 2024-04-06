package libclang.values;


import libclang.shared.Pointer;
import libclang.shared.Value;
import libclang.shared.VPointerList;
import libclang.shared.values.VPointerBasic;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.util.Collection;
import java.util.function.Consumer;

public class CXModuleMapDescriptor extends VPointerBasic<CXModuleMapDescriptor> {

    public static VPointerList<CXModuleMapDescriptor> list(Pointer<CXModuleMapDescriptor> ptr) {
        return new VPointerList<>(ptr, CXModuleMapDescriptor::new);
    }

    public static VPointerList<CXModuleMapDescriptor> list(Pointer<CXModuleMapDescriptor> ptr, long length) {
        return new VPointerList<>(ptr, length, CXModuleMapDescriptor::new);
    }

    public static VPointerList<CXModuleMapDescriptor> list(SegmentAllocator allocator, long length) {
        return new VPointerList<>(allocator, length, CXModuleMapDescriptor::new);
    }

    public static VPointerList<CXModuleMapDescriptor> list(SegmentAllocator allocator, CXModuleMapDescriptor[] c) {
        return new VPointerList<>(allocator, c, CXModuleMapDescriptor::new);
    }

    public static VPointerList<CXModuleMapDescriptor> list(SegmentAllocator allocator, Collection<CXModuleMapDescriptor> c) {
        return new VPointerList<>(allocator, c, CXModuleMapDescriptor::new);
    }

    public CXModuleMapDescriptor(Pointer<CXModuleMapDescriptor> ptr) {
        super(ptr);
    }

    public CXModuleMapDescriptor(MemorySegment value) {
        super(value);
    }

    public CXModuleMapDescriptor(Value<MemorySegment> value) {
        super(value);
    }

    public CXModuleMapDescriptor(CXModuleMapDescriptor value) {
        super(value);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXModuleMapDescriptor that && that.value().equals(value());
    }
}
