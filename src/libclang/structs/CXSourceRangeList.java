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
import libclang.structs.CXSourceRange;
public final class CXSourceRangeList implements StructOp<CXSourceRangeList>, Info<CXSourceRangeList> {
   public static final int BYTE_SIZE = 16;
   private final MemorySegment ms;
   public static final Operations<CXSourceRangeList> OPERATIONS = StructOp.makeOperations(CXSourceRangeList::new, BYTE_SIZE);

   public CXSourceRangeList(MemorySegment ms) {
       this.ms = ms;
   }

    public CXSourceRangeList(SegmentAllocator allocator) {
        this.ms = allocator.allocate(BYTE_SIZE);
    }

    public static Array<CXSourceRangeList> list(SegmentAllocator allocator, long len) {
        return new Array<>(allocator, CXSourceRangeList.OPERATIONS, len);
    }

   @Override
   public StructOpI<CXSourceRangeList> operator() {
       return new StructOpI<>() {
           @Override
           public CXSourceRangeList reinterpret() {
               return new CXSourceRangeList(ms.reinterpret(BYTE_SIZE));
           }

           @Override
           public Ptr<CXSourceRangeList> getPointer() {
               return new Ptr<>(ms, OPERATIONS);
           }

           @Override
           public Operations<CXSourceRangeList> getOperations() {
               return OPERATIONS;
           }

           @Override
           public MemorySegment value() {
               return ms;
           }
       };
   }

    public I32 count(){
        return new I32(MemoryUtils.getInt(ms, 0));
    }

    public CXSourceRangeList count(I32I<?> count){
        MemoryUtils.setInt(ms, 0, count.operator().value());
        return this;
    }
    public Ptr<CXSourceRange> ranges(){
        return new Ptr<CXSourceRange>(MemoryUtils.getAddr(ms, 8), CXSourceRange.OPERATIONS);
    }

    public CXSourceRangeList ranges(PtrI<? extends StructI<? extends CXSourceRange>> ranges){
        MemoryUtils.setAddr(ms, 8, ranges.operator().value());
        return this;
    }
    @Override
    public String toString() {
        return ms.address() == 0 ? ms.toString()
                : "CXSourceRangeList{" +
                "count=" + count() +
                ", ranges=" + ranges() +
                '}';
    }

}