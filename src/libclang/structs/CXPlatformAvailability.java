package libclang.structs;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import libclang.common.Array;
import libclang.common.I32;
import libclang.common.I32I;
import libclang.common.Info;
import libclang.common.MemoryUtils;
import libclang.common.Ptr;
import libclang.common.StructI;
import libclang.common.StructOp;
import libclang.structs.CXString;
import libclang.structs.CXVersion;
public final class CXPlatformAvailability implements StructOp<CXPlatformAvailability>, Info<CXPlatformAvailability> {
   public static final int BYTE_SIZE = 72;
   private final MemorySegment ms;
   public static final Operations<CXPlatformAvailability> OPERATIONS = StructOp.makeOperations(CXPlatformAvailability::new, BYTE_SIZE);

   public CXPlatformAvailability(MemorySegment ms) {
       this.ms = ms;
   }

    public CXPlatformAvailability(SegmentAllocator allocator) {
        this.ms = allocator.allocate(BYTE_SIZE);
    }

    public static Array<CXPlatformAvailability> list(SegmentAllocator allocator, long len) {
        return new Array<>(allocator, CXPlatformAvailability.OPERATIONS, len);
    }

   @Override
   public StructOpI<CXPlatformAvailability> operator() {
       return new StructOpI<>() {
           @Override
           public CXPlatformAvailability reinterpret() {
               return new CXPlatformAvailability(ms.reinterpret(BYTE_SIZE));
           }

           @Override
           public Ptr<CXPlatformAvailability> getPointer() {
               return new Ptr<>(ms, OPERATIONS);
           }

           @Override
           public Operations<CXPlatformAvailability> getOperations() {
               return OPERATIONS;
           }

           @Override
           public MemorySegment value() {
               return ms;
           }
       };
   }

    public CXString Platform(){
        return new CXString(ms.asSlice(0, 16));
    }

    public CXPlatformAvailability Platform(StructI<? extends CXString> Platform){
        MemoryUtils.memcpy(ms, 0, Platform.operator().value(), 0, 16);
        return this;
    }
    public CXVersion Introduced(){
        return new CXVersion(ms.asSlice(16, 12));
    }

    public CXPlatformAvailability Introduced(StructI<? extends CXVersion> Introduced){
        MemoryUtils.memcpy(ms, 16, Introduced.operator().value(), 0, 12);
        return this;
    }
    public CXVersion Deprecated(){
        return new CXVersion(ms.asSlice(28, 12));
    }

    public CXPlatformAvailability Deprecated(StructI<? extends CXVersion> Deprecated){
        MemoryUtils.memcpy(ms, 28, Deprecated.operator().value(), 0, 12);
        return this;
    }
    public CXVersion Obsoleted(){
        return new CXVersion(ms.asSlice(40, 12));
    }

    public CXPlatformAvailability Obsoleted(StructI<? extends CXVersion> Obsoleted){
        MemoryUtils.memcpy(ms, 40, Obsoleted.operator().value(), 0, 12);
        return this;
    }
    public I32 Unavailable(){
        return new I32(MemoryUtils.getInt(ms, 52));
    }

    public CXPlatformAvailability Unavailable(I32I<?> Unavailable){
        MemoryUtils.setInt(ms, 52, Unavailable.operator().value());
        return this;
    }
    public CXString Message(){
        return new CXString(ms.asSlice(56, 16));
    }

    public CXPlatformAvailability Message(StructI<? extends CXString> Message){
        MemoryUtils.memcpy(ms, 56, Message.operator().value(), 0, 16);
        return this;
    }
    @Override
    public String toString() {
        return ms.address() == 0 ? ms.toString()
                : "CXPlatformAvailability{" +
                "Platform=" + Platform() +
                ", Introduced=" + Introduced() +
                ", Deprecated=" + Deprecated() +
                ", Obsoleted=" + Obsoleted() +
                ", Unavailable=" + Unavailable() +
                ", Message=" + Message() +
                '}';
    }

}