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


public final class CXUnsavedFile implements Pointer<CXUnsavedFile> {
    public static final MemoryLayout MEMORY_LAYOUT = MemoryLayout.structLayout(MemoryLayout.sequenceLayout(24, ValueLayout.JAVA_BYTE));
    public static final long BYTE_SIZE = MEMORY_LAYOUT.byteSize();

    public static NList<CXUnsavedFile> list(Pointer<CXUnsavedFile> ptr) {
        return new NList<>(ptr, CXUnsavedFile::new, BYTE_SIZE);
    }

    public static NList<CXUnsavedFile> list(Pointer<CXUnsavedFile> ptr, long length) {
        return new NList<>(ptr, length, CXUnsavedFile::new, BYTE_SIZE);
    }

    public static NList<CXUnsavedFile> list(SegmentAllocator allocator, long length) {
        return new NList<>(allocator, length, CXUnsavedFile::new, BYTE_SIZE);
    }

    private final MemorySegment ptr;

    public CXUnsavedFile(Pointer<CXUnsavedFile> ptr) {
        this.ptr = ptr.pointer();
    }

    public CXUnsavedFile(SegmentAllocator allocator) {
        ptr = allocator.allocate(BYTE_SIZE);
    }

    public CXUnsavedFile reinterpretSize() {
        return new CXUnsavedFile(FunctionUtils.makePointer(ptr.reinterpret(BYTE_SIZE)));
    }

    @Override
    public MemorySegment pointer() {
        return ptr;
    }

    public VI8List<VI8<Byte>> Filename(long length) {
        return VI8.list(FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS, 0)), length);
    }

    public Pointer<VI8<Byte>> Filename() {
        return FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS, 0));
    }

    public CXUnsavedFile Filename(Pointer<VI8<Byte>> Filename) {
        ptr.set(ValueLayout.ADDRESS, 0, Filename.pointer());
        return this;
    }

    public CXUnsavedFile Filename(NI8 Filename) {
        ptr.set(ValueLayout.ADDRESS, 0, Filename.pointer());
        return this;
    }

    public VI8List<VI8<Byte>> Contents(long length) {
        return VI8.list(FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS, 8)), length);
    }

    public Pointer<VI8<Byte>> Contents() {
        return FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS, 8));
    }

    public CXUnsavedFile Contents(Pointer<VI8<Byte>> Contents) {
        ptr.set(ValueLayout.ADDRESS, 8, Contents.pointer());
        return this;
    }

    public CXUnsavedFile Contents(NI8 Contents) {
        ptr.set(ValueLayout.ADDRESS, 8, Contents.pointer());
        return this;
    }

    public long Length() {
        return ptr.get(ValueLayout.JAVA_LONG, 16);
    }

    public CXUnsavedFile Length(long Length) {
        ptr.set(ValueLayout.JAVA_LONG, 16, Length);
        return this;
    }


    @Override
    public String toString() {
        if (MemorySegment.NULL.address() == ptr.address() || ptr.byteSize() < BYTE_SIZE)
            return "CXUnsavedFile{ptr=" + ptr + "}";
//        return STR."""
//                CXUnsavedFile{\
//                Filename=\{Filename()},\
//                Contents=\{Contents()},\
//                Length=\{Length()}}""";
        return "";
    }
}