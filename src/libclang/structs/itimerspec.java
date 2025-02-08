package libclang.structs;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import libclang.common.Array;
import libclang.common.Info;
import libclang.common.MemoryUtils;
import libclang.common.Ptr;
import libclang.common.StructI;
import libclang.common.StructOp;
import libclang.structs.timespec;
public final class itimerspec implements StructOp<itimerspec>, Info<itimerspec> {
   public static final int BYTE_SIZE = 32;
   private final MemorySegment ms;
   public static final Operations<itimerspec> OPERATIONS = StructOp.makeOperations(itimerspec::new, BYTE_SIZE);

   public itimerspec(MemorySegment ms) {
       this.ms = ms;
   }

    public itimerspec(SegmentAllocator allocator) {
        this.ms = allocator.allocate(BYTE_SIZE);
    }

    public static Array<itimerspec> list(SegmentAllocator allocator, long len) {
        return new Array<>(allocator, itimerspec.OPERATIONS, len);
    }

   @Override
   public StructOpI<itimerspec> operator() {
       return new StructOpI<>() {
           @Override
           public itimerspec reinterpret() {
               return new itimerspec(ms.reinterpret(BYTE_SIZE));
           }

           @Override
           public Ptr<itimerspec> getPointer() {
               return new Ptr<>(ms, OPERATIONS);
           }

           @Override
           public Operations<itimerspec> getOperations() {
               return OPERATIONS;
           }

           @Override
           public MemorySegment value() {
               return ms;
           }
       };
   }

    public timespec it_interval(){
        return new timespec(ms.asSlice(0, 16));
    }

    public itimerspec it_interval(StructI<? extends timespec> it_interval){
        MemoryUtils.memcpy(ms, 0, it_interval.operator().value(), 0, 16);
        return this;
    }
    public timespec it_value(){
        return new timespec(ms.asSlice(16, 16));
    }

    public itimerspec it_value(StructI<? extends timespec> it_value){
        MemoryUtils.memcpy(ms, 16, it_value.operator().value(), 0, 16);
        return this;
    }
    @Override
    public String toString() {
        return ms.address() == 0 ? ms.toString()
                : "itimerspec{" +
                "it_interval=" + it_interval() +
                ", it_value=" + it_value() +
                '}';
    }

}