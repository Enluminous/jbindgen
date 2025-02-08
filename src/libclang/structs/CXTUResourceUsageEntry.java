package libclang.structs;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import libclang.common.Array;
import libclang.common.I32I;
import libclang.common.I64;
import libclang.common.I64I;
import libclang.common.Info;
import libclang.common.MemoryUtils;
import libclang.common.Ptr;
import libclang.common.StructOp;
import libclang.enumerates.CXTUResourceUsageKind;
public final class CXTUResourceUsageEntry implements StructOp<CXTUResourceUsageEntry>, Info<CXTUResourceUsageEntry> {
   public static final int BYTE_SIZE = 16;
   private final MemorySegment ms;
   public static final Operations<CXTUResourceUsageEntry> OPERATIONS = StructOp.makeOperations(CXTUResourceUsageEntry::new, BYTE_SIZE);

   public CXTUResourceUsageEntry(MemorySegment ms) {
       this.ms = ms;
   }

    public CXTUResourceUsageEntry(SegmentAllocator allocator) {
        this.ms = allocator.allocate(BYTE_SIZE);
    }

    public static Array<CXTUResourceUsageEntry> list(SegmentAllocator allocator, long len) {
        return new Array<>(allocator, CXTUResourceUsageEntry.OPERATIONS, len);
    }

   @Override
   public StructOpI<CXTUResourceUsageEntry> operator() {
       return new StructOpI<>() {
           @Override
           public CXTUResourceUsageEntry reinterpret() {
               return new CXTUResourceUsageEntry(ms.reinterpret(BYTE_SIZE));
           }

           @Override
           public Ptr<CXTUResourceUsageEntry> getPointer() {
               return new Ptr<>(ms, OPERATIONS);
           }

           @Override
           public Operations<CXTUResourceUsageEntry> getOperations() {
               return OPERATIONS;
           }

           @Override
           public MemorySegment value() {
               return ms;
           }
       };
   }

    public CXTUResourceUsageKind kind(){
        return new CXTUResourceUsageKind(MemoryUtils.getInt(ms, 0));
    }

    public CXTUResourceUsageEntry kind(I32I<? extends CXTUResourceUsageKind> kind){
        MemoryUtils.setInt(ms, 0, kind.operator().value());
        return this;
    }
    public I64 amount(){
        return new I64(MemoryUtils.getLong(ms, 8));
    }

    public CXTUResourceUsageEntry amount(I64I<?> amount){
        MemoryUtils.setLong(ms, 8, amount.operator().value());
        return this;
    }
    @Override
    public String toString() {
        return ms.address() == 0 ? ms.toString()
                : "CXTUResourceUsageEntry{" +
                "kind=" + kind() +
                ", amount=" + amount() +
                '}';
    }

}