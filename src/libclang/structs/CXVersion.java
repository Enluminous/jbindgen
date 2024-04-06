package libclang.structs;


import libclang.structs.*;
import libclang.LibclangEnums.*;
import libclang.functions.*;
import libclang.values.*;
import libclang.shared.values.*;
import libclang.shared.*;
import libclang.shared.natives.*;
import libclang.shared.Value;
import libclang.shared.Pointer;
import libclang.shared.FunctionUtils;

import java.lang.foreign.*;
import java.util.function.Consumer;


public final class CXVersion implements Pointer<CXVersion> {
    public static final MemoryLayout MEMORY_LAYOUT = MemoryLayout.structLayout(MemoryLayout.sequenceLayout(12, ValueLayout.JAVA_BYTE));
    public static final long BYTE_SIZE = MEMORY_LAYOUT.byteSize();

    public static NList<CXVersion> list(Pointer<CXVersion> ptr) {
        return new NList<>(ptr, CXVersion::new, BYTE_SIZE);
    }

    public static NList<CXVersion> list(Pointer<CXVersion> ptr, long length) {
        return new NList<>(ptr, length, CXVersion::new, BYTE_SIZE);
    }

    public static NList<CXVersion> list(SegmentAllocator allocator, long length) {
        return new NList<>(allocator, length, CXVersion::new, BYTE_SIZE);
    }

    private final MemorySegment ptr;

    public CXVersion(Pointer<CXVersion> ptr) {
        this.ptr = ptr.pointer();
    }

    public CXVersion(SegmentAllocator allocator) {
        ptr = allocator.allocate(BYTE_SIZE);
    }

    public CXVersion reinterpretSize() {
        return new CXVersion(FunctionUtils.makePointer(ptr.reinterpret(BYTE_SIZE)));
    }

    @Override
    public MemorySegment pointer() {
        return ptr;
    }

    public int Major() {
        return ptr.get(ValueLayout.JAVA_INT, 0);
    }

    public CXVersion Major(int Major) {
        ptr.set(ValueLayout.JAVA_INT, 0, Major);
        return this;
    }

    public int Minor() {
        return ptr.get(ValueLayout.JAVA_INT, 4);
    }

    public CXVersion Minor(int Minor) {
        ptr.set(ValueLayout.JAVA_INT, 4, Minor);
        return this;
    }

    public int Subminor() {
        return ptr.get(ValueLayout.JAVA_INT, 8);
    }

    public CXVersion Subminor(int Subminor) {
        ptr.set(ValueLayout.JAVA_INT, 8, Subminor);
        return this;
    }


    @Override
    public String toString() {
        if (MemorySegment.NULL.address() == ptr.address() || ptr.byteSize() < BYTE_SIZE)
            return STR."CXVersion{ptr=\{ptr}}";
        return STR."""
                CXVersion{\
                Major=\{Major()},\
                Minor=\{Minor()},\
                Subminor=\{Subminor()}}""";
    }
}