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


public final class CXIdxEntityInfo implements Pointer<CXIdxEntityInfo> {
    public static final MemoryLayout MEMORY_LAYOUT = MemoryLayout.structLayout(MemoryLayout.sequenceLayout(80, ValueLayout.JAVA_BYTE));
    public static final long BYTE_SIZE = MEMORY_LAYOUT.byteSize();

    public static NList<CXIdxEntityInfo> list(Pointer<CXIdxEntityInfo> ptr) {
        return new NList<>(ptr, CXIdxEntityInfo::new, BYTE_SIZE);
    }

    public static NList<CXIdxEntityInfo> list(Pointer<CXIdxEntityInfo> ptr, long length) {
        return new NList<>(ptr, length, CXIdxEntityInfo::new, BYTE_SIZE);
    }

    public static NList<CXIdxEntityInfo> list(SegmentAllocator allocator, long length) {
        return new NList<>(allocator, length, CXIdxEntityInfo::new, BYTE_SIZE);
    }

    private final MemorySegment ptr;

    public CXIdxEntityInfo(Pointer<CXIdxEntityInfo> ptr) {
        this.ptr = ptr.pointer();
    }

    public CXIdxEntityInfo(SegmentAllocator allocator) {
        ptr = allocator.allocate(BYTE_SIZE);
    }

    public CXIdxEntityInfo reinterpretSize() {
        return new CXIdxEntityInfo(FunctionUtils.makePointer(ptr.reinterpret(BYTE_SIZE)));
    }

    @Override
    public MemorySegment pointer() {
        return ptr;
    }

    public CXIdxEntityKind kind() {
        return new CXIdxEntityKind(FunctionUtils.makePointer(ptr.asSlice(0,4)));
    }

    public CXIdxEntityInfo kind(CXIdxEntityKind kind) {
        ptr.set(ValueLayout.JAVA_INT, 0, kind.value());
        return this;
    }

    public CXIdxEntityCXXTemplateKind templateKind() {
        return new CXIdxEntityCXXTemplateKind(FunctionUtils.makePointer(ptr.asSlice(4,4)));
    }

    public CXIdxEntityInfo templateKind(CXIdxEntityCXXTemplateKind templateKind) {
        ptr.set(ValueLayout.JAVA_INT, 4, templateKind.value());
        return this;
    }

    public CXIdxEntityLanguage lang() {
        return new CXIdxEntityLanguage(FunctionUtils.makePointer(ptr.asSlice(8,4)));
    }

    public CXIdxEntityInfo lang(CXIdxEntityLanguage lang) {
        ptr.set(ValueLayout.JAVA_INT, 8, lang.value());
        return this;
    }

    public VI8List<VI8<Byte>> name(long length) {
        return VI8.list(FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS,16)), length);
    }

    public Pointer<VI8<Byte>> name() {
        return FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS,16));
    }

    public CXIdxEntityInfo name(Pointer<VI8<Byte>> name) {
        ptr.set(ValueLayout.ADDRESS, 16, name.pointer());
        return this;
    }

    public CXIdxEntityInfo name(NI8 name) {
        ptr.set(ValueLayout.ADDRESS, 16, name.pointer());
        return this;
    }

    public VI8List<VI8<Byte>> USR(long length) {
        return VI8.list(FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS,24)), length);
    }

    public Pointer<VI8<Byte>> USR() {
        return FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS,24));
    }

    public CXIdxEntityInfo USR(Pointer<VI8<Byte>> USR) {
        ptr.set(ValueLayout.ADDRESS, 24, USR.pointer());
        return this;
    }

    public CXIdxEntityInfo USR(NI8 USR) {
        ptr.set(ValueLayout.ADDRESS, 24, USR.pointer());
        return this;
    }

    public CXCursor cursor() {
        return new CXCursor(FunctionUtils.makePointer(ptr.asSlice(32, 32)));
    }

    public CXIdxEntityInfo cursor(CXCursor cursor) {
        MemorySegment.copy(cursor.pointer(), 0,ptr, 32, Math.min(32,cursor.pointer().byteSize()));
        return this;
    }

    public Pointer<Pointer<CXIdxAttrInfo>> attributes() {
        return FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS,64));
    }

    public CXIdxEntityInfo attributes(Pointer<Pointer<CXIdxAttrInfo>> attributes) {
        ptr.set(ValueLayout.ADDRESS, 64, attributes.pointer());
        return this;
    }

    public int numAttributes() {
        return ptr.get(ValueLayout.JAVA_INT, 72);
    }

    public CXIdxEntityInfo numAttributes(int numAttributes) {
        ptr.set(ValueLayout.JAVA_INT, 72, numAttributes);
        return this;
    }


    @Override
    public String toString() {
        if (MemorySegment.NULL.address() == ptr.address() || ptr.byteSize() < BYTE_SIZE)
            return STR."CXIdxEntityInfo{ptr=\{ptr}}";
        return STR."""
                CXIdxEntityInfo{\
                kind=\{kind()},\
                templateKind=\{templateKind()},\
                lang=\{lang()},\
                name=\{name()},\
                USR=\{USR()},\
                cursor=\{cursor()},\
                attributes=\{attributes()},\
                numAttributes=\{numAttributes()}}""";
    }
}