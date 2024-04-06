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


public final class CXIdxDeclInfo implements Pointer<CXIdxDeclInfo> {
    public static final MemoryLayout MEMORY_LAYOUT = MemoryLayout.structLayout(MemoryLayout.sequenceLayout(128, ValueLayout.JAVA_BYTE));
    public static final long BYTE_SIZE = MEMORY_LAYOUT.byteSize();

    public static NList<CXIdxDeclInfo> list(Pointer<CXIdxDeclInfo> ptr) {
        return new NList<>(ptr, CXIdxDeclInfo::new, BYTE_SIZE);
    }

    public static NList<CXIdxDeclInfo> list(Pointer<CXIdxDeclInfo> ptr, long length) {
        return new NList<>(ptr, length, CXIdxDeclInfo::new, BYTE_SIZE);
    }

    public static NList<CXIdxDeclInfo> list(SegmentAllocator allocator, long length) {
        return new NList<>(allocator, length, CXIdxDeclInfo::new, BYTE_SIZE);
    }

    private final MemorySegment ptr;

    public CXIdxDeclInfo(Pointer<CXIdxDeclInfo> ptr) {
        this.ptr = ptr.pointer();
    }

    public CXIdxDeclInfo(SegmentAllocator allocator) {
        ptr = allocator.allocate(BYTE_SIZE);
    }

    public CXIdxDeclInfo reinterpretSize() {
        return new CXIdxDeclInfo(FunctionUtils.makePointer(ptr.reinterpret(BYTE_SIZE)));
    }

    @Override
    public MemorySegment pointer() {
        return ptr;
    }

    public Pointer<CXIdxEntityInfo> entityInfo() {
        return FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS,0));
    }

    public NList<CXIdxEntityInfo> entityInfo(long length) {
        return CXIdxEntityInfo.list(FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS,0)), length);
    }

    public CXIdxDeclInfo entityInfo(Pointer<CXIdxEntityInfo> entityInfo) {
        ptr.set(ValueLayout.ADDRESS, 0, entityInfo.pointer());
        return this;
    }

    public CXCursor cursor() {
        return new CXCursor(FunctionUtils.makePointer(ptr.asSlice(8, 32)));
    }

    public CXIdxDeclInfo cursor(CXCursor cursor) {
        MemorySegment.copy(cursor.pointer(), 0,ptr, 8, Math.min(32,cursor.pointer().byteSize()));
        return this;
    }

    public CXIdxLoc loc() {
        return new CXIdxLoc(FunctionUtils.makePointer(ptr.asSlice(40, 24)));
    }

    public CXIdxDeclInfo loc(CXIdxLoc loc) {
        MemorySegment.copy(loc.pointer(), 0,ptr, 40, Math.min(24,loc.pointer().byteSize()));
        return this;
    }

    public Pointer<CXIdxContainerInfo> semanticContainer() {
        return FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS,64));
    }

    public NList<CXIdxContainerInfo> semanticContainer(long length) {
        return CXIdxContainerInfo.list(FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS,64)), length);
    }

    public CXIdxDeclInfo semanticContainer(Pointer<CXIdxContainerInfo> semanticContainer) {
        ptr.set(ValueLayout.ADDRESS, 64, semanticContainer.pointer());
        return this;
    }

    public Pointer<CXIdxContainerInfo> lexicalContainer() {
        return FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS,72));
    }

    public NList<CXIdxContainerInfo> lexicalContainer(long length) {
        return CXIdxContainerInfo.list(FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS,72)), length);
    }

    public CXIdxDeclInfo lexicalContainer(Pointer<CXIdxContainerInfo> lexicalContainer) {
        ptr.set(ValueLayout.ADDRESS, 72, lexicalContainer.pointer());
        return this;
    }

    public int isRedeclaration() {
        return ptr.get(ValueLayout.JAVA_INT, 80);
    }

    public CXIdxDeclInfo isRedeclaration(int isRedeclaration) {
        ptr.set(ValueLayout.JAVA_INT, 80, isRedeclaration);
        return this;
    }

    public int isDefinition() {
        return ptr.get(ValueLayout.JAVA_INT, 84);
    }

    public CXIdxDeclInfo isDefinition(int isDefinition) {
        ptr.set(ValueLayout.JAVA_INT, 84, isDefinition);
        return this;
    }

    public int isContainer() {
        return ptr.get(ValueLayout.JAVA_INT, 88);
    }

    public CXIdxDeclInfo isContainer(int isContainer) {
        ptr.set(ValueLayout.JAVA_INT, 88, isContainer);
        return this;
    }

    public Pointer<CXIdxContainerInfo> declAsContainer() {
        return FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS,96));
    }

    public NList<CXIdxContainerInfo> declAsContainer(long length) {
        return CXIdxContainerInfo.list(FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS,96)), length);
    }

    public CXIdxDeclInfo declAsContainer(Pointer<CXIdxContainerInfo> declAsContainer) {
        ptr.set(ValueLayout.ADDRESS, 96, declAsContainer.pointer());
        return this;
    }

    public int isImplicit() {
        return ptr.get(ValueLayout.JAVA_INT, 104);
    }

    public CXIdxDeclInfo isImplicit(int isImplicit) {
        ptr.set(ValueLayout.JAVA_INT, 104, isImplicit);
        return this;
    }

    public Pointer<Pointer<CXIdxAttrInfo>> attributes() {
        return FunctionUtils.makePointer(ptr.get(ValueLayout.ADDRESS,112));
    }

    public CXIdxDeclInfo attributes(Pointer<Pointer<CXIdxAttrInfo>> attributes) {
        ptr.set(ValueLayout.ADDRESS, 112, attributes.pointer());
        return this;
    }

    public int numAttributes() {
        return ptr.get(ValueLayout.JAVA_INT, 120);
    }

    public CXIdxDeclInfo numAttributes(int numAttributes) {
        ptr.set(ValueLayout.JAVA_INT, 120, numAttributes);
        return this;
    }

    public int flags() {
        return ptr.get(ValueLayout.JAVA_INT, 124);
    }

    public CXIdxDeclInfo flags(int flags) {
        ptr.set(ValueLayout.JAVA_INT, 124, flags);
        return this;
    }


    @Override
    public String toString() {
        if (MemorySegment.NULL.address() == ptr.address() || ptr.byteSize() < BYTE_SIZE)
            return STR."CXIdxDeclInfo{ptr=\{ptr}}";
        return STR."""
                CXIdxDeclInfo{\
                entityInfo=\{entityInfo()},\
                cursor=\{cursor()},\
                loc=\{loc()},\
                semanticContainer=\{semanticContainer()},\
                lexicalContainer=\{lexicalContainer()},\
                isRedeclaration=\{isRedeclaration()},\
                isDefinition=\{isDefinition()},\
                isContainer=\{isContainer()},\
                declAsContainer=\{declAsContainer()},\
                isImplicit=\{isImplicit()},\
                attributes=\{attributes()},\
                numAttributes=\{numAttributes()},\
                flags=\{flags()}}""";
    }
}