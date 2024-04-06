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


public final class CXIdxAttrInfo implements Pointer<CXIdxAttrInfo> {
    public static final MemoryLayout MEMORY_LAYOUT = MemoryLayout.structLayout(MemoryLayout.sequenceLayout(64, ValueLayout.JAVA_BYTE));
    public static final long BYTE_SIZE = MEMORY_LAYOUT.byteSize();

    public static NList<CXIdxAttrInfo> list(Pointer<CXIdxAttrInfo> ptr) {
        return new NList<>(ptr, CXIdxAttrInfo::new, BYTE_SIZE);
    }

    public static NList<CXIdxAttrInfo> list(Pointer<CXIdxAttrInfo> ptr, long length) {
        return new NList<>(ptr, length, CXIdxAttrInfo::new, BYTE_SIZE);
    }

    public static NList<CXIdxAttrInfo> list(SegmentAllocator allocator, long length) {
        return new NList<>(allocator, length, CXIdxAttrInfo::new, BYTE_SIZE);
    }

    private final MemorySegment ptr;

    public CXIdxAttrInfo(Pointer<CXIdxAttrInfo> ptr) {
        this.ptr = ptr.pointer();
    }

    public CXIdxAttrInfo(SegmentAllocator allocator) {
        ptr = allocator.allocate(BYTE_SIZE);
    }

    public CXIdxAttrInfo reinterpretSize() {
        return new CXIdxAttrInfo(FunctionUtils.makePointer(ptr.reinterpret(BYTE_SIZE)));
    }

    @Override
    public MemorySegment pointer() {
        return ptr;
    }

    public CXIdxAttrKind kind() {
        return new CXIdxAttrKind(FunctionUtils.makePointer(ptr.asSlice(0,4)));
    }

    public CXIdxAttrInfo kind(CXIdxAttrKind kind) {
        ptr.set(ValueLayout.JAVA_INT, 0, kind.value());
        return this;
    }

    public CXCursor cursor() {
        return new CXCursor(FunctionUtils.makePointer(ptr.asSlice(8, 32)));
    }

    public CXIdxAttrInfo cursor(CXCursor cursor) {
        MemorySegment.copy(cursor.pointer(), 0,ptr, 8, Math.min(32,cursor.pointer().byteSize()));
        return this;
    }

    public CXIdxLoc loc() {
        return new CXIdxLoc(FunctionUtils.makePointer(ptr.asSlice(40, 24)));
    }

    public CXIdxAttrInfo loc(CXIdxLoc loc) {
        MemorySegment.copy(loc.pointer(), 0,ptr, 40, Math.min(24,loc.pointer().byteSize()));
        return this;
    }


    @Override
    public String toString() {
        if (MemorySegment.NULL.address() == ptr.address() || ptr.byteSize() < BYTE_SIZE)
            return STR."CXIdxAttrInfo{ptr=\{ptr}}";
        return STR."""
                CXIdxAttrInfo{\
                kind=\{kind()},\
                cursor=\{cursor()},\
                loc=\{loc()}}""";
    }
}