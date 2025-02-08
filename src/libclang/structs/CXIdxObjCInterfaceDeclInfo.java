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
import libclang.structs.CXIdxBaseClassInfo;
import libclang.structs.CXIdxObjCContainerDeclInfo;
import libclang.structs.CXIdxObjCProtocolRefListInfo;
public final class CXIdxObjCInterfaceDeclInfo implements StructOp<CXIdxObjCInterfaceDeclInfo>, Info<CXIdxObjCInterfaceDeclInfo> {
   public static final int BYTE_SIZE = 24;
   private final MemorySegment ms;
   public static final Operations<CXIdxObjCInterfaceDeclInfo> OPERATIONS = StructOp.makeOperations(CXIdxObjCInterfaceDeclInfo::new, BYTE_SIZE);

   public CXIdxObjCInterfaceDeclInfo(MemorySegment ms) {
       this.ms = ms;
   }

    public CXIdxObjCInterfaceDeclInfo(SegmentAllocator allocator) {
        this.ms = allocator.allocate(BYTE_SIZE);
    }

    public static Array<CXIdxObjCInterfaceDeclInfo> list(SegmentAllocator allocator, long len) {
        return new Array<>(allocator, CXIdxObjCInterfaceDeclInfo.OPERATIONS, len);
    }

   @Override
   public StructOpI<CXIdxObjCInterfaceDeclInfo> operator() {
       return new StructOpI<>() {
           @Override
           public CXIdxObjCInterfaceDeclInfo reinterpret() {
               return new CXIdxObjCInterfaceDeclInfo(ms.reinterpret(BYTE_SIZE));
           }

           @Override
           public Ptr<CXIdxObjCInterfaceDeclInfo> getPointer() {
               return new Ptr<>(ms, OPERATIONS);
           }

           @Override
           public Operations<CXIdxObjCInterfaceDeclInfo> getOperations() {
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

    public CXIdxObjCInterfaceDeclInfo containerInfo(PtrI<? extends StructI<? extends CXIdxObjCContainerDeclInfo>> containerInfo){
        MemoryUtils.setAddr(ms, 0, containerInfo.operator().value());
        return this;
    }
    public Ptr<CXIdxBaseClassInfo> superInfo(){
        return new Ptr<CXIdxBaseClassInfo>(MemoryUtils.getAddr(ms, 8), CXIdxBaseClassInfo.OPERATIONS);
    }

    public CXIdxObjCInterfaceDeclInfo superInfo(PtrI<? extends StructI<? extends CXIdxBaseClassInfo>> superInfo){
        MemoryUtils.setAddr(ms, 8, superInfo.operator().value());
        return this;
    }
    public Ptr<CXIdxObjCProtocolRefListInfo> protocols(){
        return new Ptr<CXIdxObjCProtocolRefListInfo>(MemoryUtils.getAddr(ms, 16), CXIdxObjCProtocolRefListInfo.OPERATIONS);
    }

    public CXIdxObjCInterfaceDeclInfo protocols(PtrI<? extends StructI<? extends CXIdxObjCProtocolRefListInfo>> protocols){
        MemoryUtils.setAddr(ms, 16, protocols.operator().value());
        return this;
    }
    @Override
    public String toString() {
        return ms.address() == 0 ? ms.toString()
                : "CXIdxObjCInterfaceDeclInfo{" +
                "containerInfo=" + containerInfo() +
                ", superInfo=" + superInfo() +
                ", protocols=" + protocols() +
                '}';
    }

}