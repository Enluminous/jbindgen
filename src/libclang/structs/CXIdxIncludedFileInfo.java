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
import libclang.structs.CXIdxLoc;
import libclang.values.CXFile;
public final class CXIdxIncludedFileInfo implements StructOp<CXIdxIncludedFileInfo>, Info<CXIdxIncludedFileInfo> {
   public static final int BYTE_SIZE = 56;
   private final MemorySegment ms;
   public static final Operations<CXIdxIncludedFileInfo> OPERATIONS = StructOp.makeOperations(CXIdxIncludedFileInfo::new, BYTE_SIZE);

   public CXIdxIncludedFileInfo(MemorySegment ms) {
       this.ms = ms;
   }

    public CXIdxIncludedFileInfo(SegmentAllocator allocator) {
        this.ms = allocator.allocate(BYTE_SIZE);
    }

    public static Array<CXIdxIncludedFileInfo> list(SegmentAllocator allocator, long len) {
        return new Array<>(allocator, CXIdxIncludedFileInfo.OPERATIONS, len);
    }

   @Override
   public StructOpI<CXIdxIncludedFileInfo> operator() {
       return new StructOpI<>() {
           @Override
           public CXIdxIncludedFileInfo reinterpret() {
               return new CXIdxIncludedFileInfo(ms.reinterpret(BYTE_SIZE));
           }

           @Override
           public Ptr<CXIdxIncludedFileInfo> getPointer() {
               return new Ptr<>(ms, OPERATIONS);
           }

           @Override
           public Operations<CXIdxIncludedFileInfo> getOperations() {
               return OPERATIONS;
           }

           @Override
           public MemorySegment value() {
               return ms;
           }
       };
   }

    public CXIdxLoc hashLoc(){
        return new CXIdxLoc(ms.asSlice(0, 24));
    }

    public CXIdxIncludedFileInfo hashLoc(StructI<? extends CXIdxLoc> hashLoc){
        MemoryUtils.memcpy(ms, 0, hashLoc.operator().value(), 0, 24);
        return this;
    }
    public Ptr<I8> filename(){
        return new Ptr<I8>(MemoryUtils.getAddr(ms, 24), I8.OPERATIONS);
    }

    public CXIdxIncludedFileInfo filename(PtrI<? extends I8I<?>> filename){
        MemoryUtils.setAddr(ms, 24, filename.operator().value());
        return this;
    }
    public CXFile file(){
        return new CXFile(MemoryUtils.getAddr(ms, 32));
    }

    public CXIdxIncludedFileInfo file(CXFile file){
        MemoryUtils.setAddr(ms, 32, file.operator().value());
        return this;
    }
    public I32 isImport(){
        return new I32(MemoryUtils.getInt(ms, 40));
    }

    public CXIdxIncludedFileInfo isImport(I32I<?> isImport){
        MemoryUtils.setInt(ms, 40, isImport.operator().value());
        return this;
    }
    public I32 isAngled(){
        return new I32(MemoryUtils.getInt(ms, 44));
    }

    public CXIdxIncludedFileInfo isAngled(I32I<?> isAngled){
        MemoryUtils.setInt(ms, 44, isAngled.operator().value());
        return this;
    }
    public I32 isModuleImport(){
        return new I32(MemoryUtils.getInt(ms, 48));
    }

    public CXIdxIncludedFileInfo isModuleImport(I32I<?> isModuleImport){
        MemoryUtils.setInt(ms, 48, isModuleImport.operator().value());
        return this;
    }
    @Override
    public String toString() {
        return ms.address() == 0 ? ms.toString()
                : "CXIdxIncludedFileInfo{" +
                "hashLoc=" + hashLoc() +
                ", filename=" + filename() +
                ", file=" + file() +
                ", isImport=" + isImport() +
                ", isAngled=" + isAngled() +
                ", isModuleImport=" + isModuleImport() +
                '}';
    }

}