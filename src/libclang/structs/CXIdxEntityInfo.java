package libclang.structs;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import libclang.common.Array;
import libclang.common.I32;
import libclang.common.I32I;
import libclang.common.I8;
import libclang.common.I8I;
import libclang.common.Info;
import libclang.common.MemoryUtils;
import libclang.common.Ptr;
import libclang.common.PtrI;
import libclang.common.StructI;
import libclang.common.StructOp;
import libclang.enumerates.CXIdxEntityCXXTemplateKind;
import libclang.enumerates.CXIdxEntityKind;
import libclang.enumerates.CXIdxEntityLanguage;
import libclang.structs.CXCursor;
import libclang.structs.CXIdxAttrInfo;
public final class CXIdxEntityInfo implements StructOp<CXIdxEntityInfo>, Info<CXIdxEntityInfo> {
   public static final int BYTE_SIZE = 80;
   private final MemorySegment ms;
   public static final Operations<CXIdxEntityInfo> OPERATIONS = StructOp.makeOperations(CXIdxEntityInfo::new, BYTE_SIZE);

   public CXIdxEntityInfo(MemorySegment ms) {
       this.ms = ms;
   }

    public CXIdxEntityInfo(SegmentAllocator allocator) {
        this.ms = allocator.allocate(BYTE_SIZE);
    }

    public static Array<CXIdxEntityInfo> list(SegmentAllocator allocator, long len) {
        return new Array<>(allocator, CXIdxEntityInfo.OPERATIONS, len);
    }

   @Override
   public StructOpI<CXIdxEntityInfo> operator() {
       return new StructOpI<>() {
           @Override
           public CXIdxEntityInfo reinterpret() {
               return new CXIdxEntityInfo(ms.reinterpret(BYTE_SIZE));
           }

           @Override
           public Ptr<CXIdxEntityInfo> getPointer() {
               return new Ptr<>(ms, OPERATIONS);
           }

           @Override
           public Operations<CXIdxEntityInfo> getOperations() {
               return OPERATIONS;
           }

           @Override
           public MemorySegment value() {
               return ms;
           }
       };
   }

    public CXIdxEntityKind kind(){
        return new CXIdxEntityKind(MemoryUtils.getInt(ms, 0));
    }

    public CXIdxEntityInfo kind(I32I<? extends CXIdxEntityKind> kind){
        MemoryUtils.setInt(ms, 0, kind.operator().value());
        return this;
    }
    public CXIdxEntityCXXTemplateKind templateKind(){
        return new CXIdxEntityCXXTemplateKind(MemoryUtils.getInt(ms, 4));
    }

    public CXIdxEntityInfo templateKind(I32I<? extends CXIdxEntityCXXTemplateKind> templateKind){
        MemoryUtils.setInt(ms, 4, templateKind.operator().value());
        return this;
    }
    public CXIdxEntityLanguage lang(){
        return new CXIdxEntityLanguage(MemoryUtils.getInt(ms, 8));
    }

    public CXIdxEntityInfo lang(I32I<? extends CXIdxEntityLanguage> lang){
        MemoryUtils.setInt(ms, 8, lang.operator().value());
        return this;
    }
    public Ptr<I8> name(){
        return new Ptr<I8>(MemoryUtils.getAddr(ms, 16), I8.OPERATIONS);
    }

    public CXIdxEntityInfo name(PtrI<? extends I8I<?>> name){
        MemoryUtils.setAddr(ms, 16, name.operator().value());
        return this;
    }
    public Ptr<I8> USR(){
        return new Ptr<I8>(MemoryUtils.getAddr(ms, 24), I8.OPERATIONS);
    }

    public CXIdxEntityInfo USR(PtrI<? extends I8I<?>> USR){
        MemoryUtils.setAddr(ms, 24, USR.operator().value());
        return this;
    }
    public CXCursor cursor(){
        return new CXCursor(ms.asSlice(32, 32));
    }

    public CXIdxEntityInfo cursor(StructI<? extends CXCursor> cursor){
        MemoryUtils.memcpy(ms, 32, cursor.operator().value(), 0, 32);
        return this;
    }
    public Ptr<Ptr<CXIdxAttrInfo>> attributes(){
        return new Ptr<Ptr<CXIdxAttrInfo>>(MemoryUtils.getAddr(ms, 64), Ptr.makeOperations(CXIdxAttrInfo.OPERATIONS));
    }

    public CXIdxEntityInfo attributes(PtrI<? extends PtrI<? extends StructI<? extends CXIdxAttrInfo>>> attributes){
        MemoryUtils.setAddr(ms, 64, attributes.operator().value());
        return this;
    }
    public I32 numAttributes(){
        return new I32(MemoryUtils.getInt(ms, 72));
    }

    public CXIdxEntityInfo numAttributes(I32I<?> numAttributes){
        MemoryUtils.setInt(ms, 72, numAttributes.operator().value());
        return this;
    }
    @Override
    public String toString() {
        return ms.address() == 0 ? ms.toString()
                : "CXIdxEntityInfo{" +
                "kind=" + kind() +
                ", templateKind=" + templateKind() +
                ", lang=" + lang() +
                ", name=" + name() +
                ", USR=" + USR() +
                ", cursor=" + cursor() +
                ", attributes=" + attributes() +
                ", numAttributes=" + numAttributes() +
                '}';
    }

}