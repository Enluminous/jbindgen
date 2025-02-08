package libclang.structs;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import libclang.common.Array;
import libclang.common.I32I;
import libclang.common.Info;
import libclang.common.MemoryUtils;
import libclang.common.Ptr;
import libclang.common.StructI;
import libclang.common.StructOp;
import libclang.enumerates.CXIdxAttrKind;
import libclang.structs.CXCursor;
import libclang.structs.CXIdxLoc;
public final class CXIdxAttrInfo implements StructOp<CXIdxAttrInfo>, Info<CXIdxAttrInfo> {
   public static final int BYTE_SIZE = 64;
   private final MemorySegment ms;
   public static final Operations<CXIdxAttrInfo> OPERATIONS = StructOp.makeOperations(CXIdxAttrInfo::new, BYTE_SIZE);

   public CXIdxAttrInfo(MemorySegment ms) {
       this.ms = ms;
   }

    public CXIdxAttrInfo(SegmentAllocator allocator) {
        this.ms = allocator.allocate(BYTE_SIZE);
    }

    public static Array<CXIdxAttrInfo> list(SegmentAllocator allocator, long len) {
        return new Array<>(allocator, CXIdxAttrInfo.OPERATIONS, len);
    }

   @Override
   public StructOpI<CXIdxAttrInfo> operator() {
       return new StructOpI<>() {
           @Override
           public CXIdxAttrInfo reinterpret() {
               return new CXIdxAttrInfo(ms.reinterpret(BYTE_SIZE));
           }

           @Override
           public Ptr<CXIdxAttrInfo> getPointer() {
               return new Ptr<>(ms, OPERATIONS);
           }

           @Override
           public Operations<CXIdxAttrInfo> getOperations() {
               return OPERATIONS;
           }

           @Override
           public MemorySegment value() {
               return ms;
           }
       };
   }

    public CXIdxAttrKind kind(){
        return new CXIdxAttrKind(MemoryUtils.getInt(ms, 0));
    }

    public CXIdxAttrInfo kind(I32I<? extends CXIdxAttrKind> kind){
        MemoryUtils.setInt(ms, 0, kind.operator().value());
        return this;
    }
    public CXCursor cursor(){
        return new CXCursor(ms.asSlice(8, 32));
    }

    public CXIdxAttrInfo cursor(StructI<? extends CXCursor> cursor){
        MemoryUtils.memcpy(ms, 8, cursor.operator().value(), 0, 32);
        return this;
    }
    public CXIdxLoc loc(){
        return new CXIdxLoc(ms.asSlice(40, 24));
    }

    public CXIdxAttrInfo loc(StructI<? extends CXIdxLoc> loc){
        MemoryUtils.memcpy(ms, 40, loc.operator().value(), 0, 24);
        return this;
    }
    @Override
    public String toString() {
        return ms.address() == 0 ? ms.toString()
                : "CXIdxAttrInfo{" +
                "kind=" + kind() +
                ", cursor=" + cursor() +
                ", loc=" + loc() +
                '}';
    }

}