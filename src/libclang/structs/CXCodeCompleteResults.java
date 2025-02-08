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
import libclang.structs.CXCompletionResult;
public final class CXCodeCompleteResults implements StructOp<CXCodeCompleteResults>, Info<CXCodeCompleteResults> {
   public static final int BYTE_SIZE = 16;
   private final MemorySegment ms;
   public static final Operations<CXCodeCompleteResults> OPERATIONS = StructOp.makeOperations(CXCodeCompleteResults::new, BYTE_SIZE);

   public CXCodeCompleteResults(MemorySegment ms) {
       this.ms = ms;
   }

    public CXCodeCompleteResults(SegmentAllocator allocator) {
        this.ms = allocator.allocate(BYTE_SIZE);
    }

    public static Array<CXCodeCompleteResults> list(SegmentAllocator allocator, long len) {
        return new Array<>(allocator, CXCodeCompleteResults.OPERATIONS, len);
    }

   @Override
   public StructOpI<CXCodeCompleteResults> operator() {
       return new StructOpI<>() {
           @Override
           public CXCodeCompleteResults reinterpret() {
               return new CXCodeCompleteResults(ms.reinterpret(BYTE_SIZE));
           }

           @Override
           public Ptr<CXCodeCompleteResults> getPointer() {
               return new Ptr<>(ms, OPERATIONS);
           }

           @Override
           public Operations<CXCodeCompleteResults> getOperations() {
               return OPERATIONS;
           }

           @Override
           public MemorySegment value() {
               return ms;
           }
       };
   }

    public Ptr<CXCompletionResult> Results(){
        return new Ptr<CXCompletionResult>(MemoryUtils.getAddr(ms, 0), CXCompletionResult.OPERATIONS);
    }

    public CXCodeCompleteResults Results(PtrI<? extends StructI<? extends CXCompletionResult>> Results){
        MemoryUtils.setAddr(ms, 0, Results.operator().value());
        return this;
    }
    public I32 NumResults(){
        return new I32(MemoryUtils.getInt(ms, 8));
    }

    public CXCodeCompleteResults NumResults(I32I<?> NumResults){
        MemoryUtils.setInt(ms, 8, NumResults.operator().value());
        return this;
    }
    @Override
    public String toString() {
        return ms.address() == 0 ? ms.toString()
                : "CXCodeCompleteResults{" +
                "Results=" + Results() +
                ", NumResults=" + NumResults() +
                '}';
    }

}