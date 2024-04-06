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


public final class CXIdxBaseClassInfo implements Pointer<CXIdxBaseClassInfo> {
    public static final MemoryLayout MEMORY_LAYOUT = MemoryLayout.structLayout(MemoryLayout.sequenceLayout(64, ValueLayout.JAVA_BYTE));
    public static final long BYTE_SIZE = MEMORY_LAYOUT.byteSize();

    public static NList<CXIdxBaseClassInfo> list(Pointer<CXIdxBaseClassInfo> ptr) {
        return new NList<>(ptr, CXIdxBaseClassInfo::new, BYTE_SIZE);
    }

    public static NList<CXIdxBaseClassInfo> list(Pointer<CXIdxBaseClassInfo> ptr, long length) {
        return new NList<>(ptr, length, CXIdxBaseClassInfo::new, BYTE_SIZE);
    }

    public static NList<CXIdxBaseClassInfo> list(SegmentAllocator allocator, long length) {
        return new NList<>(allocator, length, CXIdxBaseClassInfo::new, BYTE_SIZE);
    }

    private final MemorySegment ptr;

    public CXIdxBaseClassInfo(Pointer<CXIdxBaseClassInfo> ptr) {
        this.ptr = ptr.pointer();
    }

    public CXIdxBaseClassInfo(SegmentAllocator allocator) {
        ptr = allocator.allocate(BYTE_SIZE);
    }

    public CXIdxBaseClassInfo reinterpretSize() {
        return new CXIdxBaseClassInfo(FunctionUtils.makePointer(ptr.reinterpret(BYTE_SIZE)));
    }

    @Override
    public MemorySegment pointer() {
        return ptr;
    }

    public Pointer<CXIdxEntityInfo> base() {
        return FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS,0));
    }

    public NList<CXIdxEntityInfo> base(long length) {
        return CXIdxEntityInfo.list(FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS,0)), length);
    }

    public CXIdxBaseClassInfo base(Pointer<CXIdxEntityInfo> base) {
        ptr.set(ValueLayout.ADDRESS, 0, base.pointer());
        return this;
    }

    public CXCursor cursor() {
        return new CXCursor(FunctionUtils.makePointer(ptr.asSlice(8, 32)));
    }

    public CXIdxBaseClassInfo cursor(CXCursor cursor) {
        MemorySegment.copy(cursor.pointer(), 0,ptr, 8, Math.min(32,cursor.pointer().byteSize()));
        return this;
    }

    public CXIdxLoc loc() {
        return new CXIdxLoc(FunctionUtils.makePointer(ptr.asSlice(40, 24)));
    }

    public CXIdxBaseClassInfo loc(CXIdxLoc loc) {
        MemorySegment.copy(loc.pointer(), 0,ptr, 40, Math.min(24,loc.pointer().byteSize()));
        return this;
    }


    @Override
    public String toString() {
        if (MemorySegment.NULL.address() == ptr.address() || ptr.byteSize() < BYTE_SIZE)
            return STR."CXIdxBaseClassInfo{ptr=\{ptr}}";
        return STR."""
                CXIdxBaseClassInfo{\
                base=\{base()},\
                cursor=\{cursor()},\
                loc=\{loc()}}""";
    }
}