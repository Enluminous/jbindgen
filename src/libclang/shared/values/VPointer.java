package libclang.shared.values;

import libclang.shared.Pointer;
import libclang.shared.Value;
import libclang.shared.VPointerList;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.util.Collection;
import java.util.function.Consumer;

public class VPointer<T> extends VPointerBasic<T> {

    public static <T> VPointerList<VPointer<T>> list(Pointer<VPointer<T>> ptr) {
        return new VPointerList<>(ptr, VPointer::new);
    }

    public static <T> VPointerList<VPointer<T>> list(Pointer<VPointer<T>> ptr, long length) {
        return new VPointerList<>(ptr, length, VPointer::new);
    }

    public static <T> VPointerList<VPointer<T>> list(SegmentAllocator allocator, long length) {
        return new VPointerList<>(allocator, length, VPointer::new);
    }

    public static <T> VPointerList<VPointer<T>> list(SegmentAllocator allocator, VPointer<T>[] c) {
        return new VPointerList<>(allocator, c, VPointer::new);
    }

    public static <T> VPointerList<VPointer<T>> list(SegmentAllocator allocator, Collection<VPointer<T>> c) {
        return new VPointerList<>(allocator, c, VPointer::new);
    }

    public VPointer(Pointer<? extends VPointer<T>> ptr) {
        super(ptr);
    }

    public VPointer(MemorySegment value) {
        super(value);
    }

    public VPointer(Value<MemorySegment> value) {
        super(value);
    }

    public VPointer(VPointer<T> value) {
        super(value);
    }
}
