package libclang.structs;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import libclang.common.Array;
import libclang.common.I64I;
import libclang.common.Info;
import libclang.common.MemoryUtils;
import libclang.common.Ptr;
import libclang.common.StructOp;
import libclang.values.__syscall_slong_t;
import libclang.values.__time_t;
public final class timespec implements StructOp<timespec>, Info<timespec> {
   public static final int BYTE_SIZE = 16;
   private final MemorySegment ms;
   public static final Operations<timespec> OPERATIONS = StructOp.makeOperations(timespec::new, BYTE_SIZE);

   public timespec(MemorySegment ms) {
       this.ms = ms;
   }

    public timespec(SegmentAllocator allocator) {
        this.ms = allocator.allocate(BYTE_SIZE);
    }

    public static Array<timespec> list(SegmentAllocator allocator, long len) {
        return new Array<>(allocator, timespec.OPERATIONS, len);
    }

   @Override
   public StructOpI<timespec> operator() {
       return new StructOpI<>() {
           @Override
           public timespec reinterpret() {
               return new timespec(ms.reinterpret(BYTE_SIZE));
           }

           @Override
           public Ptr<timespec> getPointer() {
               return new Ptr<>(ms, OPERATIONS);
           }

           @Override
           public Operations<timespec> getOperations() {
               return OPERATIONS;
           }

           @Override
           public MemorySegment value() {
               return ms;
           }
       };
   }

    public __time_t tv_sec(){
        return new __time_t(MemoryUtils.getLong(ms, 0));
    }

    public timespec tv_sec(I64I<? extends __time_t> tv_sec){
        MemoryUtils.setLong(ms, 0, tv_sec.operator().value());
        return this;
    }
    public __syscall_slong_t tv_nsec(){
        return new __syscall_slong_t(MemoryUtils.getLong(ms, 8));
    }

    public timespec tv_nsec(I64I<? extends __syscall_slong_t> tv_nsec){
        MemoryUtils.setLong(ms, 8, tv_nsec.operator().value());
        return this;
    }
    @Override
    public String toString() {
        return ms.address() == 0 ? ms.toString()
                : "timespec{" +
                "tv_sec=" + tv_sec() +
                ", tv_nsec=" + tv_nsec() +
                '}';
    }

}