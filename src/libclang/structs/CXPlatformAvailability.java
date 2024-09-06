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


public final class CXPlatformAvailability implements Pointer<CXPlatformAvailability> {
    public static final MemoryLayout MEMORY_LAYOUT = MemoryLayout.structLayout(MemoryLayout.sequenceLayout(72, ValueLayout.JAVA_BYTE));
    public static final long BYTE_SIZE = MEMORY_LAYOUT.byteSize();

    public static NList<CXPlatformAvailability> list(Pointer<CXPlatformAvailability> ptr) {
        return new NList<>(ptr, CXPlatformAvailability::new, BYTE_SIZE);
    }

    public static NList<CXPlatformAvailability> list(Pointer<CXPlatformAvailability> ptr, long length) {
        return new NList<>(ptr, length, CXPlatformAvailability::new, BYTE_SIZE);
    }

    public static NList<CXPlatformAvailability> list(SegmentAllocator allocator, long length) {
        return new NList<>(allocator, length, CXPlatformAvailability::new, BYTE_SIZE);
    }

    private final MemorySegment ptr;

    public CXPlatformAvailability(Pointer<CXPlatformAvailability> ptr) {
        this.ptr = ptr.pointer();
    }

    public CXPlatformAvailability(SegmentAllocator allocator) {
        ptr = allocator.allocate(BYTE_SIZE);
    }

    public CXPlatformAvailability reinterpretSize() {
        return new CXPlatformAvailability(FunctionUtils.makePointer(ptr.reinterpret(BYTE_SIZE)));
    }

    @Override
    public MemorySegment pointer() {
        return ptr;
    }

    public CXString Platform() {
        return new CXString(FunctionUtils.makePointer(ptr.asSlice(0, 16)));
    }

    public CXPlatformAvailability Platform(CXString Platform) {
        MemorySegment.copy(Platform.pointer(), 0, ptr, 0, Math.min(16, Platform.pointer().byteSize()));
        return this;
    }

    public CXVersion Introduced() {
        return new CXVersion(FunctionUtils.makePointer(ptr.asSlice(16, 12)));
    }

    public CXPlatformAvailability Introduced(CXVersion Introduced) {
        MemorySegment.copy(Introduced.pointer(), 0, ptr, 16, Math.min(12, Introduced.pointer().byteSize()));
        return this;
    }

    public CXVersion Deprecated() {
        return new CXVersion(FunctionUtils.makePointer(ptr.asSlice(28, 12)));
    }

    public CXPlatformAvailability Deprecated(CXVersion Deprecated) {
        MemorySegment.copy(Deprecated.pointer(), 0, ptr, 28, Math.min(12, Deprecated.pointer().byteSize()));
        return this;
    }

    public CXVersion Obsoleted() {
        return new CXVersion(FunctionUtils.makePointer(ptr.asSlice(40, 12)));
    }

    public CXPlatformAvailability Obsoleted(CXVersion Obsoleted) {
        MemorySegment.copy(Obsoleted.pointer(), 0, ptr, 40, Math.min(12, Obsoleted.pointer().byteSize()));
        return this;
    }

    public int Unavailable() {
        return ptr.get(ValueLayout.JAVA_INT, 52);
    }

    public CXPlatformAvailability Unavailable(int Unavailable) {
        ptr.set(ValueLayout.JAVA_INT, 52, Unavailable);
        return this;
    }

    public CXString Message() {
        return new CXString(FunctionUtils.makePointer(ptr.asSlice(56, 16)));
    }

    public CXPlatformAvailability Message(CXString Message) {
        MemorySegment.copy(Message.pointer(), 0, ptr, 56, Math.min(16, Message.pointer().byteSize()));
        return this;
    }


    @Override
    public String toString() {
        if (MemorySegment.NULL.address() == ptr.address() || ptr.byteSize() < BYTE_SIZE)
            return "CXPlatformAvailability{ptr=" + ptr + "}";
//        return STR."""
//                CXPlatformAvailability{\
//                Platform=\{Platform()},\
//                Introduced=\{Introduced()},\
//                Deprecated=\{Deprecated()},\
//                Obsoleted=\{Obsoleted()},\
//                Unavailable=\{Unavailable()},\
//                Message=\{Message()}}""";
        return "";
    }
}