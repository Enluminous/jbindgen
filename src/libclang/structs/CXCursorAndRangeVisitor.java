package libclang.structs;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import libclang.common.Array;
import libclang.common.Info;
import libclang.common.MemoryUtils;
import libclang.common.Ptr;
import libclang.common.PtrI;
import libclang.common.StructOp;
import libclang.functions.CXCursorAndRangeVisitor$visit;
public final class CXCursorAndRangeVisitor implements StructOp<CXCursorAndRangeVisitor>, Info<CXCursorAndRangeVisitor> {
   public static final int BYTE_SIZE = 16;
   private final MemorySegment ms;
   public static final Operations<CXCursorAndRangeVisitor> OPERATIONS = StructOp.makeOperations(CXCursorAndRangeVisitor::new, BYTE_SIZE);

   public CXCursorAndRangeVisitor(MemorySegment ms) {
       this.ms = ms;
   }

    public CXCursorAndRangeVisitor(SegmentAllocator allocator) {
        this.ms = allocator.allocate(BYTE_SIZE);
    }

    public static Array<CXCursorAndRangeVisitor> list(SegmentAllocator allocator, long len) {
        return new Array<>(allocator, CXCursorAndRangeVisitor.OPERATIONS, len);
    }

   @Override
   public StructOpI<CXCursorAndRangeVisitor> operator() {
       return new StructOpI<>() {
           @Override
           public CXCursorAndRangeVisitor reinterpret() {
               return new CXCursorAndRangeVisitor(ms.reinterpret(BYTE_SIZE));
           }

           @Override
           public Ptr<CXCursorAndRangeVisitor> getPointer() {
               return new Ptr<>(ms, OPERATIONS);
           }

           @Override
           public Operations<CXCursorAndRangeVisitor> getOperations() {
               return OPERATIONS;
           }

           @Override
           public MemorySegment value() {
               return ms;
           }
       };
   }

    public Ptr<Void> context(){
        return new Ptr<Void>(MemoryUtils.getAddr(ms, 0), Info.makeOperations());
    }

    public CXCursorAndRangeVisitor context(PtrI<?> context){
        MemoryUtils.setAddr(ms, 0, context.operator().value());
        return this;
    }
    public CXCursorAndRangeVisitor$visit visit(){
        return new CXCursorAndRangeVisitor$visit(MemoryUtils.getAddr(ms, 8));
    }

    public CXCursorAndRangeVisitor visit(PtrI<? extends CXCursorAndRangeVisitor$visit.Function> visit){
        MemoryUtils.setAddr(ms, 8, visit.operator().value());
        return this;
    }
    @Override
    public String toString() {
        return ms.address() == 0 ? ms.toString()
                : "CXCursorAndRangeVisitor{" +
                "context=" + context() +
                ", visit=" + visit() +
                '}';
    }

}