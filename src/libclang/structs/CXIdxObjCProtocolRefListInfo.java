package libclang.structs;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import libclang.common.Array;
import libclang.common.I32;
import libclang.common.I32I;
import libclang.common.Info;
import libclang.common.MemoryUtils;
import libclang.common.Ptr;
import libclang.common.PtrI;
import libclang.common.StructI;
import libclang.common.StructOp;
import libclang.structs.CXIdxObjCProtocolRefInfo;
public final class CXIdxObjCProtocolRefListInfo implements StructOp<CXIdxObjCProtocolRefListInfo>, Info<CXIdxObjCProtocolRefListInfo> {
   public static final int BYTE_SIZE = 16;
   private final MemorySegment ms;
   public static final Operations<CXIdxObjCProtocolRefListInfo> OPERATIONS = StructOp.makeOperations(CXIdxObjCProtocolRefListInfo::new, BYTE_SIZE);

   public CXIdxObjCProtocolRefListInfo(MemorySegment ms) {
       this.ms = ms;
   }

    public CXIdxObjCProtocolRefListInfo(SegmentAllocator allocator) {
        this.ms = allocator.allocate(BYTE_SIZE);
    }

    public static Array<CXIdxObjCProtocolRefListInfo> list(SegmentAllocator allocator, long len) {
        return new Array<>(allocator, CXIdxObjCProtocolRefListInfo.OPERATIONS, len);
    }

   @Override
   public StructOpI<CXIdxObjCProtocolRefListInfo> operator() {
       return new StructOpI<>() {
           @Override
           public CXIdxObjCProtocolRefListInfo reinterpret() {
               return new CXIdxObjCProtocolRefListInfo(ms.reinterpret(BYTE_SIZE));
           }

           @Override
           public Ptr<CXIdxObjCProtocolRefListInfo> getPointer() {
               return new Ptr<>(ms, OPERATIONS);
           }

           @Override
           public Operations<CXIdxObjCProtocolRefListInfo> getOperations() {
               return OPERATIONS;
           }

           @Override
           public MemorySegment value() {
               return ms;
           }
       };
   }

    public Ptr<Ptr<CXIdxObjCProtocolRefInfo>> protocols(){
        return new Ptr<Ptr<CXIdxObjCProtocolRefInfo>>(MemoryUtils.getAddr(ms, 0), Ptr.makeOperations(CXIdxObjCProtocolRefInfo.OPERATIONS));
    }

    public CXIdxObjCProtocolRefListInfo protocols(PtrI<? extends PtrI<? extends StructI<? extends CXIdxObjCProtocolRefInfo>>> protocols){
        MemoryUtils.setAddr(ms, 0, protocols.operator().value());
        return this;
    }
    public I32 numProtocols(){
        return new I32(MemoryUtils.getInt(ms, 8));
    }

    public CXIdxObjCProtocolRefListInfo numProtocols(I32I<?> numProtocols){
        MemoryUtils.setInt(ms, 8, numProtocols.operator().value());
        return this;
    }
    @Override
    public String toString() {
        return ms.address() == 0 ? ms.toString()
                : "CXIdxObjCProtocolRefListInfo{" +
                "protocols=" + protocols() +
                ", numProtocols=" + numProtocols() +
                '}';
    }

}