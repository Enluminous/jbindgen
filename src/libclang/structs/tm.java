package libclang.structs;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import libclang.common.Array;
import libclang.common.I32;
import libclang.common.I32I;
import libclang.common.I64;
import libclang.common.I64I;
import libclang.common.I8;
import libclang.common.I8I;
import libclang.common.Info;
import libclang.common.MemoryUtils;
import libclang.common.Ptr;
import libclang.common.PtrI;
import libclang.common.StructOp;
public final class tm implements StructOp<tm>, Info<tm> {
   public static final int BYTE_SIZE = 56;
   private final MemorySegment ms;
   public static final Operations<tm> OPERATIONS = StructOp.makeOperations(tm::new, BYTE_SIZE);

   public tm(MemorySegment ms) {
       this.ms = ms;
   }

    public tm(SegmentAllocator allocator) {
        this.ms = allocator.allocate(BYTE_SIZE);
    }

    public static Array<tm> list(SegmentAllocator allocator, long len) {
        return new Array<>(allocator, tm.OPERATIONS, len);
    }

   @Override
   public StructOpI<tm> operator() {
       return new StructOpI<>() {
           @Override
           public tm reinterpret() {
               return new tm(ms.reinterpret(BYTE_SIZE));
           }

           @Override
           public Ptr<tm> getPointer() {
               return new Ptr<>(ms, OPERATIONS);
           }

           @Override
           public Operations<tm> getOperations() {
               return OPERATIONS;
           }

           @Override
           public MemorySegment value() {
               return ms;
           }
       };
   }

    public I32 tm_sec(){
        return new I32(MemoryUtils.getInt(ms, 0));
    }

    public tm tm_sec(I32I<?> tm_sec){
        MemoryUtils.setInt(ms, 0, tm_sec.operator().value());
        return this;
    }
    public I32 tm_min(){
        return new I32(MemoryUtils.getInt(ms, 4));
    }

    public tm tm_min(I32I<?> tm_min){
        MemoryUtils.setInt(ms, 4, tm_min.operator().value());
        return this;
    }
    public I32 tm_hour(){
        return new I32(MemoryUtils.getInt(ms, 8));
    }

    public tm tm_hour(I32I<?> tm_hour){
        MemoryUtils.setInt(ms, 8, tm_hour.operator().value());
        return this;
    }
    public I32 tm_mday(){
        return new I32(MemoryUtils.getInt(ms, 12));
    }

    public tm tm_mday(I32I<?> tm_mday){
        MemoryUtils.setInt(ms, 12, tm_mday.operator().value());
        return this;
    }
    public I32 tm_mon(){
        return new I32(MemoryUtils.getInt(ms, 16));
    }

    public tm tm_mon(I32I<?> tm_mon){
        MemoryUtils.setInt(ms, 16, tm_mon.operator().value());
        return this;
    }
    public I32 tm_year(){
        return new I32(MemoryUtils.getInt(ms, 20));
    }

    public tm tm_year(I32I<?> tm_year){
        MemoryUtils.setInt(ms, 20, tm_year.operator().value());
        return this;
    }
    public I32 tm_wday(){
        return new I32(MemoryUtils.getInt(ms, 24));
    }

    public tm tm_wday(I32I<?> tm_wday){
        MemoryUtils.setInt(ms, 24, tm_wday.operator().value());
        return this;
    }
    public I32 tm_yday(){
        return new I32(MemoryUtils.getInt(ms, 28));
    }

    public tm tm_yday(I32I<?> tm_yday){
        MemoryUtils.setInt(ms, 28, tm_yday.operator().value());
        return this;
    }
    public I32 tm_isdst(){
        return new I32(MemoryUtils.getInt(ms, 32));
    }

    public tm tm_isdst(I32I<?> tm_isdst){
        MemoryUtils.setInt(ms, 32, tm_isdst.operator().value());
        return this;
    }
    public I64 tm_gmtoff(){
        return new I64(MemoryUtils.getLong(ms, 40));
    }

    public tm tm_gmtoff(I64I<?> tm_gmtoff){
        MemoryUtils.setLong(ms, 40, tm_gmtoff.operator().value());
        return this;
    }
    public Ptr<I8> tm_zone(){
        return new Ptr<I8>(MemoryUtils.getAddr(ms, 48), I8.OPERATIONS);
    }

    public tm tm_zone(PtrI<? extends I8I<?>> tm_zone){
        MemoryUtils.setAddr(ms, 48, tm_zone.operator().value());
        return this;
    }
    @Override
    public String toString() {
        return ms.address() == 0 ? ms.toString()
                : "tm{" +
                "tm_sec=" + tm_sec() +
                ", tm_min=" + tm_min() +
                ", tm_hour=" + tm_hour() +
                ", tm_mday=" + tm_mday() +
                ", tm_mon=" + tm_mon() +
                ", tm_year=" + tm_year() +
                ", tm_wday=" + tm_wday() +
                ", tm_yday=" + tm_yday() +
                ", tm_isdst=" + tm_isdst() +
                ", tm_gmtoff=" + tm_gmtoff() +
                ", tm_zone=" + tm_zone() +
                '}';
    }

}