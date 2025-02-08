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
import libclang.structs.CXTUResourceUsageEntry;
public final class CXTUResourceUsage implements StructOp<CXTUResourceUsage>, Info<CXTUResourceUsage> {
   public static final int BYTE_SIZE = 24;
   private final MemorySegment ms;
   public static final Operations<CXTUResourceUsage> OPERATIONS = StructOp.makeOperations(CXTUResourceUsage::new, BYTE_SIZE);

   public CXTUResourceUsage(MemorySegment ms) {
       this.ms = ms;
   }

    public CXTUResourceUsage(SegmentAllocator allocator) {
        this.ms = allocator.allocate(BYTE_SIZE);
    }

    public static Array<CXTUResourceUsage> list(SegmentAllocator allocator, long len) {
        return new Array<>(allocator, CXTUResourceUsage.OPERATIONS, len);
    }

   @Override
   public StructOpI<CXTUResourceUsage> operator() {
       return new StructOpI<>() {
           @Override
           public CXTUResourceUsage reinterpret() {
               return new CXTUResourceUsage(ms.reinterpret(BYTE_SIZE));
           }

           @Override
           public Ptr<CXTUResourceUsage> getPointer() {
               return new Ptr<>(ms, OPERATIONS);
           }

           @Override
           public Operations<CXTUResourceUsage> getOperations() {
               return OPERATIONS;
           }

           @Override
           public MemorySegment value() {
               return ms;
           }
       };
   }

    public Ptr<Void> data(){
        return new Ptr<Void>(MemoryUtils.getAddr(ms, 0), Info.makeOperations());
    }

    public CXTUResourceUsage data(PtrI<?> data){
        MemoryUtils.setAddr(ms, 0, data.operator().value());
        return this;
    }
    public I32 numEntries(){
        return new I32(MemoryUtils.getInt(ms, 8));
    }

    public CXTUResourceUsage numEntries(I32I<?> numEntries){
        MemoryUtils.setInt(ms, 8, numEntries.operator().value());
        return this;
    }
    public Ptr<CXTUResourceUsageEntry> entries(){
        return new Ptr<CXTUResourceUsageEntry>(MemoryUtils.getAddr(ms, 16), CXTUResourceUsageEntry.OPERATIONS);
    }

    public CXTUResourceUsage entries(PtrI<? extends StructI<? extends CXTUResourceUsageEntry>> entries){
        MemoryUtils.setAddr(ms, 16, entries.operator().value());
        return this;
    }
    @Override
    public String toString() {
        return ms.address() == 0 ? ms.toString()
                : "CXTUResourceUsage{" +
                "data=" + data() +
                ", numEntries=" + numEntries() +
                ", entries=" + entries() +
                '}';
    }

}