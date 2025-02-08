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
public final class CXIdxObjCProtocolRefInfo implements StructOp<CXIdxObjCProtocolRefInfo>, Info<CXIdxObjCProtocolRefInfo> {
   public static final int BYTE_SIZE = 64;
   private final MemorySegment ms;
   public static final Operations<CXIdxObjCProtocolRefInfo> OPERATIONS = StructOp.makeOperations(CXIdxObjCProtocolRefInfo::new, BYTE_SIZE);

   public CXIdxObjCProtocolRefInfo(MemorySegment ms) {
       this.ms = ms;
   }

    public CXIdxObjCProtocolRefInfo(SegmentAllocator allocator) {
        this.ms = allocator.allocate(BYTE_SIZE);
    }

    public static Array<CXIdxObjCProtocolRefInfo> list(SegmentAllocator allocator, long len) {
        return new Array<>(allocator, CXIdxObjCProtocolRefInfo.OPERATIONS, len);
    }

   @Override
   public StructOpI<CXIdxObjCProtocolRefInfo> operator() {
       return new StructOpI<>() {
           @Override
           public CXIdxObjCProtocolRefInfo reinterpret() {
               return new CXIdxObjCProtocolRefInfo(ms.reinterpret(BYTE_SIZE));
           }

           @Override
           public Ptr<CXIdxObjCProtocolRefInfo> getPointer() {
               return new Ptr<>(ms, OPERATIONS);
           }

           @Override
           public Operations<CXIdxObjCProtocolRefInfo> getOperations() {
               return OPERATIONS;
           }

           @Override
           public MemorySegment value() {
               return ms;
           }
       };
   }

    public Ptr<CXIdxEntityInfo> protocol(){
        return new Ptr<CXIdxEntityInfo>(MemoryUtils.getAddr(ms, 0), CXIdxEntityInfo.OPERATIONS);
    }

    public CXIdxObjCProtocolRefInfo protocol(PtrI<? extends StructI<? extends CXIdxEntityInfo>> protocol){
        MemoryUtils.setAddr(ms, 0, protocol.operator().value());
        return this;
    }
    public CXCursor cursor(){
        return new CXCursor(ms.asSlice(8, 32));
    }

    public CXIdxObjCProtocolRefInfo cursor(StructI<? extends CXCursor> cursor){
        MemoryUtils.memcpy(ms, 8, cursor.operator().value(), 0, 32);
        return this;
    }
    public CXIdxLoc loc(){
        return new CXIdxLoc(ms.asSlice(40, 24));
    }

    public CXIdxObjCProtocolRefInfo loc(StructI<? extends CXIdxLoc> loc){
        MemoryUtils.memcpy(ms, 40, loc.operator().value(), 0, 24);
        return this;
    }
    @Override
    public String toString() {
        return ms.address() == 0 ? ms.toString()
                : "CXIdxObjCProtocolRefInfo{" +
                "protocol=" + protocol() +
                ", cursor=" + cursor() +
                ", loc=" + loc() +
                '}';
    }

}