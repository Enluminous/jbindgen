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


public final class CXIdxEntityRefInfo implements Pointer<CXIdxEntityRefInfo> {
    public static final MemoryLayout MEMORY_LAYOUT = MemoryLayout.structLayout(MemoryLayout.sequenceLayout(96, ValueLayout.JAVA_BYTE));
    public static final long BYTE_SIZE = MEMORY_LAYOUT.byteSize();

    public static NList<CXIdxEntityRefInfo> list(Pointer<CXIdxEntityRefInfo> ptr) {
        return new NList<>(ptr, CXIdxEntityRefInfo::new, BYTE_SIZE);
    }

    public static NList<CXIdxEntityRefInfo> list(Pointer<CXIdxEntityRefInfo> ptr, long length) {
        return new NList<>(ptr, length, CXIdxEntityRefInfo::new, BYTE_SIZE);
    }

    public static NList<CXIdxEntityRefInfo> list(SegmentAllocator allocator, long length) {
        return new NList<>(allocator, length, CXIdxEntityRefInfo::new, BYTE_SIZE);
    }

    private final MemorySegment ptr;

    public CXIdxEntityRefInfo(Pointer<CXIdxEntityRefInfo> ptr) {
        this.ptr = ptr.pointer();
    }

    public CXIdxEntityRefInfo(SegmentAllocator allocator) {
        ptr = allocator.allocate(BYTE_SIZE);
    }

    public CXIdxEntityRefInfo reinterpretSize() {
        return new CXIdxEntityRefInfo(FunctionUtils.makePointer(ptr.reinterpret(BYTE_SIZE)));
    }

    @Override
    public MemorySegment pointer() {
        return ptr;
    }

    public CXIdxEntityRefKind kind() {
        return new CXIdxEntityRefKind(FunctionUtils.makePointer(ptr.asSlice(0, 4)));
    }

    public CXIdxEntityRefInfo kind(CXIdxEntityRefKind kind) {
        ptr.set(ValueLayout.JAVA_INT, 0, kind.value());
        return this;
    }

    public CXCursor cursor() {
        return new CXCursor(FunctionUtils.makePointer(ptr.asSlice(8, 32)));
    }

    public CXIdxEntityRefInfo cursor(CXCursor cursor) {
        MemorySegment.copy(cursor.pointer(), 0, ptr, 8, Math.min(32, cursor.pointer().byteSize()));
        return this;
    }

    public CXIdxLoc loc() {
        return new CXIdxLoc(FunctionUtils.makePointer(ptr.asSlice(40, 24)));
    }

    public CXIdxEntityRefInfo loc(CXIdxLoc loc) {
        MemorySegment.copy(loc.pointer(), 0, ptr, 40, Math.min(24, loc.pointer().byteSize()));
        return this;
    }

    public Pointer<CXIdxEntityInfo> referencedEntity() {
        return FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS, 64));
    }

    public NList<CXIdxEntityInfo> referencedEntity(long length) {
        return CXIdxEntityInfo.list(FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS, 64)), length);
    }

    public CXIdxEntityRefInfo referencedEntity(Pointer<CXIdxEntityInfo> referencedEntity) {
        ptr.set(ValueLayout.ADDRESS, 64, referencedEntity.pointer());
        return this;
    }

    public Pointer<CXIdxEntityInfo> parentEntity() {
        return FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS, 72));
    }

    public NList<CXIdxEntityInfo> parentEntity(long length) {
        return CXIdxEntityInfo.list(FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS, 72)), length);
    }

    public CXIdxEntityRefInfo parentEntity(Pointer<CXIdxEntityInfo> parentEntity) {
        ptr.set(ValueLayout.ADDRESS, 72, parentEntity.pointer());
        return this;
    }

    public Pointer<CXIdxContainerInfo> container() {
        return FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS, 80));
    }

    public NList<CXIdxContainerInfo> container(long length) {
        return CXIdxContainerInfo.list(FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS, 80)), length);
    }

    public CXIdxEntityRefInfo container(Pointer<CXIdxContainerInfo> container) {
        ptr.set(ValueLayout.ADDRESS, 80, container.pointer());
        return this;
    }

    public CXSymbolRole role() {
        return new CXSymbolRole(FunctionUtils.makePointer(ptr.asSlice(88, 4)));
    }

    public CXIdxEntityRefInfo role(CXSymbolRole role) {
        ptr.set(ValueLayout.JAVA_INT, 88, role.value());
        return this;
    }


    @Override
    public String toString() {
        if (MemorySegment.NULL.address() == ptr.address() || ptr.byteSize() < BYTE_SIZE)
            return "CXIdxEntityRefInfo{ptr=" + ptr + "}";
//        return STR."""
//                CXIdxEntityRefInfo{\
//                kind=\{kind()},\
//                cursor=\{cursor()},\
//                loc=\{loc()},\
//                referencedEntity=\{referencedEntity()},\
//                parentEntity=\{parentEntity()},\
//                container=\{container()},\
//                role=\{role()}}""";
        return "";
    }
}