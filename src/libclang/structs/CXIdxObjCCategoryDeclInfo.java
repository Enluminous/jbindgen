package libclang.structs;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import libclang.common.Array;
import libclang.common.Info;
import libclang.common.MemoryUtils;
import libclang.common.Ptr;
import libclang.common.PtrI;
import libclang.common.StructI;
import libclang.common.StructOp;
import libclang.structs.CXCursor;
import libclang.structs.CXIdxEntityInfo;
import libclang.structs.CXIdxLoc;
import libclang.structs.CXIdxObjCContainerDeclInfo;
import libclang.structs.CXIdxObjCProtocolRefListInfo;
public final class CXIdxObjCCategoryDeclInfo implements StructOp<CXIdxObjCCategoryDeclInfo>, Info<CXIdxObjCCategoryDeclInfo> {
   public static final int BYTE_SIZE = 80;
   private final MemorySegment ms;
   public static final Operations<CXIdxObjCCategoryDeclInfo> OPERATIONS = StructOp.makeOperations(CXIdxObjCCategoryDeclInfo::new, BYTE_SIZE);

   public CXIdxObjCCategoryDeclInfo(MemorySegment ms) {
       this.ms = ms;
   }

    public CXIdxObjCCategoryDeclInfo(SegmentAllocator allocator) {
        this.ms = allocator.allocate(BYTE_SIZE);
    }

    public static Array<CXIdxObjCCategoryDeclInfo> list(SegmentAllocator allocator, long len) {
        return new Array<>(allocator, CXIdxObjCCategoryDeclInfo.OPERATIONS, len);
    }

   @Override
   public StructOpI<CXIdxObjCCategoryDeclInfo> operator() {
       return new StructOpI<>() {
           @Override
           public CXIdxObjCCategoryDeclInfo reinterpret() {
               return new CXIdxObjCCategoryDeclInfo(ms.reinterpret(BYTE_SIZE));
           }

           @Override
           public Ptr<CXIdxObjCCategoryDeclInfo> getPointer() {
               return new Ptr<>(ms, OPERATIONS);
           }

           @Override
           public Operations<CXIdxObjCCategoryDeclInfo> getOperations() {
               return OPERATIONS;
           }

           @Override
           public MemorySegment value() {
               return ms;
           }
       };
   }

    public Ptr<CXIdxObjCContainerDeclInfo> containerInfo(){
        return new Ptr<CXIdxObjCContainerDeclInfo>(MemoryUtils.getAddr(ms, 0), CXIdxObjCContainerDeclInfo.OPERATIONS);
    }

    public CXIdxObjCCategoryDeclInfo containerInfo(PtrI<? extends StructI<? extends CXIdxObjCContainerDeclInfo>> containerInfo){
        MemoryUtils.setAddr(ms, 0, containerInfo.operator().value());
        return this;
    }
    public Ptr<CXIdxEntityInfo> objcClass(){
        return new Ptr<CXIdxEntityInfo>(MemoryUtils.getAddr(ms, 8), CXIdxEntityInfo.OPERATIONS);
    }

    public CXIdxObjCCategoryDeclInfo objcClass(PtrI<? extends StructI<? extends CXIdxEntityInfo>> objcClass){
        MemoryUtils.setAddr(ms, 8, objcClass.operator().value());
        return this;
    }
    public CXCursor classCursor(){
        return new CXCursor(ms.asSlice(16, 32));
    }

    public CXIdxObjCCategoryDeclInfo classCursor(StructI<? extends CXCursor> classCursor){
        MemoryUtils.memcpy(ms, 16, classCursor.operator().value(), 0, 32);
        return this;
    }
    public CXIdxLoc classLoc(){
        return new CXIdxLoc(ms.asSlice(48, 24));
    }

    public CXIdxObjCCategoryDeclInfo classLoc(StructI<? extends CXIdxLoc> classLoc){
        MemoryUtils.memcpy(ms, 48, classLoc.operator().value(), 0, 24);
        return this;
    }
    public Ptr<CXIdxObjCProtocolRefListInfo> protocols(){
        return new Ptr<CXIdxObjCProtocolRefListInfo>(MemoryUtils.getAddr(ms, 72), CXIdxObjCProtocolRefListInfo.OPERATIONS);
    }

    public CXIdxObjCCategoryDeclInfo protocols(PtrI<? extends StructI<? extends CXIdxObjCProtocolRefListInfo>> protocols){
        MemoryUtils.setAddr(ms, 72, protocols.operator().value());
        return this;
    }
    @Override
    public String toString() {
        return ms.address() == 0 ? ms.toString()
                : "CXIdxObjCCategoryDeclInfo{" +
                "containerInfo=" + containerInfo() +
                ", objcClass=" + objcClass() +
                ", classCursor=" + classCursor() +
                ", classLoc=" + classLoc() +
                ", protocols=" + protocols() +
                '}';
    }

}