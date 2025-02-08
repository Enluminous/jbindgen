package libclang.structs;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import libclang.common.Array;
import libclang.common.I32I;
import libclang.common.Info;
import libclang.common.MemoryUtils;
import libclang.common.Ptr;
import libclang.common.StructOp;
import libclang.enumerates.CXCursorKind;
import libclang.values.CXCompletionString;
public final class CXCompletionResult implements StructOp<CXCompletionResult>, Info<CXCompletionResult> {
   public static final int BYTE_SIZE = 16;
   private final MemorySegment ms;
   public static final Operations<CXCompletionResult> OPERATIONS = StructOp.makeOperations(CXCompletionResult::new, BYTE_SIZE);

   public CXCompletionResult(MemorySegment ms) {
       this.ms = ms;
   }

    public CXCompletionResult(SegmentAllocator allocator) {
        this.ms = allocator.allocate(BYTE_SIZE);
    }

    public static Array<CXCompletionResult> list(SegmentAllocator allocator, long len) {
        return new Array<>(allocator, CXCompletionResult.OPERATIONS, len);
    }

   @Override
   public StructOpI<CXCompletionResult> operator() {
       return new StructOpI<>() {
           @Override
           public CXCompletionResult reinterpret() {
               return new CXCompletionResult(ms.reinterpret(BYTE_SIZE));
           }

           @Override
           public Ptr<CXCompletionResult> getPointer() {
               return new Ptr<>(ms, OPERATIONS);
           }

           @Override
           public Operations<CXCompletionResult> getOperations() {
               return OPERATIONS;
           }

           @Override
           public MemorySegment value() {
               return ms;
           }
       };
   }

    public CXCursorKind CursorKind(){
        return new CXCursorKind(MemoryUtils.getInt(ms, 0));
    }

    public CXCompletionResult CursorKind(I32I<? extends CXCursorKind> CursorKind){
        MemoryUtils.setInt(ms, 0, CursorKind.operator().value());
        return this;
    }
    public CXCompletionString CompletionString(){
        return new CXCompletionString(MemoryUtils.getAddr(ms, 8));
    }

    public CXCompletionResult CompletionString(CXCompletionString CompletionString){
        MemoryUtils.setAddr(ms, 8, CompletionString.operator().value());
        return this;
    }
    @Override
    public String toString() {
        return ms.address() == 0 ? ms.toString()
                : "CXCompletionResult{" +
                "CursorKind=" + CursorKind() +
                ", CompletionString=" + CompletionString() +
                '}';
    }

}