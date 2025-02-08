package libclang.structs;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import libclang.common.Array;
import libclang.common.I32I;
import libclang.common.Info;
import libclang.common.MemoryUtils;
import libclang.common.Ptr;
import libclang.common.PtrI;
import libclang.common.StructI;
import libclang.common.StructOp;
import libclang.enumerates.CXIdxEntityRefKind;
import libclang.enumerates.CXSymbolRole;
import libclang.structs.CXCursor;
import libclang.structs.CXIdxContainerInfo;
import libclang.structs.CXIdxEntityInfo;
import libclang.structs.CXIdxLoc;
public final class CXIdxEntityRefInfo implements StructOp<CXIdxEntityRefInfo>, Info<CXIdxEntityRefInfo> {
   public static final int BYTE_SIZE = 96;
   private final MemorySegment ms;
   public static final Operations<CXIdxEntityRefInfo> OPERATIONS = StructOp.makeOperations(CXIdxEntityRefInfo::new, BYTE_SIZE);

   public CXIdxEntityRefInfo(MemorySegment ms) {
       this.ms = ms;
   }

    public CXIdxEntityRefInfo(SegmentAllocator allocator) {
        this.ms = allocator.allocate(BYTE_SIZE);
    }

    public static Array<CXIdxEntityRefInfo> list(SegmentAllocator allocator, long len) {
        return new Array<>(allocator, CXIdxEntityRefInfo.OPERATIONS, len);
    }

   @Override
   public StructOpI<CXIdxEntityRefInfo> operator() {
       return new StructOpI<>() {
           @Override
           public CXIdxEntityRefInfo reinterpret() {
               return new CXIdxEntityRefInfo(ms.reinterpret(BYTE_SIZE));
           }

           @Override
           public Ptr<CXIdxEntityRefInfo> getPointer() {
               return new Ptr<>(ms, OPERATIONS);
           }

           @Override
           public Operations<CXIdxEntityRefInfo> getOperations() {
               return OPERATIONS;
           }

           @Override
           public MemorySegment value() {
               return ms;
           }
       };
   }

    public CXIdxEntityRefKind kind(){
        return new CXIdxEntityRefKind(MemoryUtils.getInt(ms, 0));
    }

    public CXIdxEntityRefInfo kind(I32I<? extends CXIdxEntityRefKind> kind){
        MemoryUtils.setInt(ms, 0, kind.operator().value());
        return this;
    }
    public CXCursor cursor(){
        return new CXCursor(ms.asSlice(8, 32));
    }

    public CXIdxEntityRefInfo cursor(StructI<? extends CXCursor> cursor){
        MemoryUtils.memcpy(ms, 8, cursor.operator().value(), 0, 32);
        return this;
    }
    public CXIdxLoc loc(){
        return new CXIdxLoc(ms.asSlice(40, 24));
    }

    public CXIdxEntityRefInfo loc(StructI<? extends CXIdxLoc> loc){
        MemoryUtils.memcpy(ms, 40, loc.operator().value(), 0, 24);
        return this;
    }
    public Ptr<CXIdxEntityInfo> referencedEntity(){
        return new Ptr<CXIdxEntityInfo>(MemoryUtils.getAddr(ms, 64), CXIdxEntityInfo.OPERATIONS);
    }

    public CXIdxEntityRefInfo referencedEntity(PtrI<? extends StructI<? extends CXIdxEntityInfo>> referencedEntity){
        MemoryUtils.setAddr(ms, 64, referencedEntity.operator().value());
        return this;
    }
    public Ptr<CXIdxEntityInfo> parentEntity(){
        return new Ptr<CXIdxEntityInfo>(MemoryUtils.getAddr(ms, 72), CXIdxEntityInfo.OPERATIONS);
    }

    public CXIdxEntityRefInfo parentEntity(PtrI<? extends StructI<? extends CXIdxEntityInfo>> parentEntity){
        MemoryUtils.setAddr(ms, 72, parentEntity.operator().value());
        return this;
    }
    public Ptr<CXIdxContainerInfo> container(){
        return new Ptr<CXIdxContainerInfo>(MemoryUtils.getAddr(ms, 80), CXIdxContainerInfo.OPERATIONS);
    }

    public CXIdxEntityRefInfo container(PtrI<? extends StructI<? extends CXIdxContainerInfo>> container){
        MemoryUtils.setAddr(ms, 80, container.operator().value());
        return this;
    }
    public CXSymbolRole role(){
        return new CXSymbolRole(MemoryUtils.getInt(ms, 88));
    }

    public CXIdxEntityRefInfo role(I32I<? extends CXSymbolRole> role){
        MemoryUtils.setInt(ms, 88, role.operator().value());
        return this;
    }
    @Override
    public String toString() {
        return ms.address() == 0 ? ms.toString()
                : "CXIdxEntityRefInfo{" +
                "kind=" + kind() +
                ", cursor=" + cursor() +
                ", loc=" + loc() +
                ", referencedEntity=" + referencedEntity() +
                ", parentEntity=" + parentEntity() +
                ", container=" + container() +
                ", role=" + role() +
                '}';
    }

}