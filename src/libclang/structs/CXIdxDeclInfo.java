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
import libclang.structs.CXCursor;
import libclang.structs.CXIdxAttrInfo;
import libclang.structs.CXIdxContainerInfo;
import libclang.structs.CXIdxEntityInfo;
import libclang.structs.CXIdxLoc;
public final class CXIdxDeclInfo implements StructOp<CXIdxDeclInfo>, Info<CXIdxDeclInfo> {
   public static final int BYTE_SIZE = 128;
   private final MemorySegment ms;
   public static final Operations<CXIdxDeclInfo> OPERATIONS = StructOp.makeOperations(CXIdxDeclInfo::new, BYTE_SIZE);

   public CXIdxDeclInfo(MemorySegment ms) {
       this.ms = ms;
   }

    public CXIdxDeclInfo(SegmentAllocator allocator) {
        this.ms = allocator.allocate(BYTE_SIZE);
    }

    public static Array<CXIdxDeclInfo> list(SegmentAllocator allocator, long len) {
        return new Array<>(allocator, CXIdxDeclInfo.OPERATIONS, len);
    }

   @Override
   public StructOpI<CXIdxDeclInfo> operator() {
       return new StructOpI<>() {
           @Override
           public CXIdxDeclInfo reinterpret() {
               return new CXIdxDeclInfo(ms.reinterpret(BYTE_SIZE));
           }

           @Override
           public Ptr<CXIdxDeclInfo> getPointer() {
               return new Ptr<>(ms, OPERATIONS);
           }

           @Override
           public Operations<CXIdxDeclInfo> getOperations() {
               return OPERATIONS;
           }

           @Override
           public MemorySegment value() {
               return ms;
           }
       };
   }

    public Ptr<CXIdxEntityInfo> entityInfo(){
        return new Ptr<CXIdxEntityInfo>(MemoryUtils.getAddr(ms, 0), CXIdxEntityInfo.OPERATIONS);
    }

    public CXIdxDeclInfo entityInfo(PtrI<? extends StructI<? extends CXIdxEntityInfo>> entityInfo){
        MemoryUtils.setAddr(ms, 0, entityInfo.operator().value());
        return this;
    }
    public CXCursor cursor(){
        return new CXCursor(ms.asSlice(8, 32));
    }

    public CXIdxDeclInfo cursor(StructI<? extends CXCursor> cursor){
        MemoryUtils.memcpy(ms, 8, cursor.operator().value(), 0, 32);
        return this;
    }
    public CXIdxLoc loc(){
        return new CXIdxLoc(ms.asSlice(40, 24));
    }

    public CXIdxDeclInfo loc(StructI<? extends CXIdxLoc> loc){
        MemoryUtils.memcpy(ms, 40, loc.operator().value(), 0, 24);
        return this;
    }
    public Ptr<CXIdxContainerInfo> semanticContainer(){
        return new Ptr<CXIdxContainerInfo>(MemoryUtils.getAddr(ms, 64), CXIdxContainerInfo.OPERATIONS);
    }

    public CXIdxDeclInfo semanticContainer(PtrI<? extends StructI<? extends CXIdxContainerInfo>> semanticContainer){
        MemoryUtils.setAddr(ms, 64, semanticContainer.operator().value());
        return this;
    }
    public Ptr<CXIdxContainerInfo> lexicalContainer(){
        return new Ptr<CXIdxContainerInfo>(MemoryUtils.getAddr(ms, 72), CXIdxContainerInfo.OPERATIONS);
    }

    public CXIdxDeclInfo lexicalContainer(PtrI<? extends StructI<? extends CXIdxContainerInfo>> lexicalContainer){
        MemoryUtils.setAddr(ms, 72, lexicalContainer.operator().value());
        return this;
    }
    public I32 isRedeclaration(){
        return new I32(MemoryUtils.getInt(ms, 80));
    }

    public CXIdxDeclInfo isRedeclaration(I32I<?> isRedeclaration){
        MemoryUtils.setInt(ms, 80, isRedeclaration.operator().value());
        return this;
    }
    public I32 isDefinition(){
        return new I32(MemoryUtils.getInt(ms, 84));
    }

    public CXIdxDeclInfo isDefinition(I32I<?> isDefinition){
        MemoryUtils.setInt(ms, 84, isDefinition.operator().value());
        return this;
    }
    public I32 isContainer(){
        return new I32(MemoryUtils.getInt(ms, 88));
    }

    public CXIdxDeclInfo isContainer(I32I<?> isContainer){
        MemoryUtils.setInt(ms, 88, isContainer.operator().value());
        return this;
    }
    public Ptr<CXIdxContainerInfo> declAsContainer(){
        return new Ptr<CXIdxContainerInfo>(MemoryUtils.getAddr(ms, 96), CXIdxContainerInfo.OPERATIONS);
    }

    public CXIdxDeclInfo declAsContainer(PtrI<? extends StructI<? extends CXIdxContainerInfo>> declAsContainer){
        MemoryUtils.setAddr(ms, 96, declAsContainer.operator().value());
        return this;
    }
    public I32 isImplicit(){
        return new I32(MemoryUtils.getInt(ms, 104));
    }

    public CXIdxDeclInfo isImplicit(I32I<?> isImplicit){
        MemoryUtils.setInt(ms, 104, isImplicit.operator().value());
        return this;
    }
    public Ptr<Ptr<CXIdxAttrInfo>> attributes(){
        return new Ptr<Ptr<CXIdxAttrInfo>>(MemoryUtils.getAddr(ms, 112), Ptr.makeOperations(CXIdxAttrInfo.OPERATIONS));
    }

    public CXIdxDeclInfo attributes(PtrI<? extends PtrI<? extends StructI<? extends CXIdxAttrInfo>>> attributes){
        MemoryUtils.setAddr(ms, 112, attributes.operator().value());
        return this;
    }
    public I32 numAttributes(){
        return new I32(MemoryUtils.getInt(ms, 120));
    }

    public CXIdxDeclInfo numAttributes(I32I<?> numAttributes){
        MemoryUtils.setInt(ms, 120, numAttributes.operator().value());
        return this;
    }
    public I32 flags(){
        return new I32(MemoryUtils.getInt(ms, 124));
    }

    public CXIdxDeclInfo flags(I32I<?> flags){
        MemoryUtils.setInt(ms, 124, flags.operator().value());
        return this;
    }
    @Override
    public String toString() {
        return ms.address() == 0 ? ms.toString()
                : "CXIdxDeclInfo{" +
                "entityInfo=" + entityInfo() +
                ", cursor=" + cursor() +
                ", loc=" + loc() +
                ", semanticContainer=" + semanticContainer() +
                ", lexicalContainer=" + lexicalContainer() +
                ", isRedeclaration=" + isRedeclaration() +
                ", isDefinition=" + isDefinition() +
                ", isContainer=" + isContainer() +
                ", declAsContainer=" + declAsContainer() +
                ", isImplicit=" + isImplicit() +
                ", attributes=" + attributes() +
                ", numAttributes=" + numAttributes() +
                ", flags=" + flags() +
                '}';
    }

}