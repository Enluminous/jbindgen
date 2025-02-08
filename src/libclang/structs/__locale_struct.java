package libclang.structs;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import libclang.common.Array;
import libclang.common.ArrayI;
import libclang.common.I16;
import libclang.common.I16I;
import libclang.common.I32;
import libclang.common.I32I;
import libclang.common.I8;
import libclang.common.I8I;
import libclang.common.Info;
import libclang.common.MemoryUtils;
import libclang.common.Ptr;
import libclang.common.PtrI;
import libclang.common.StructOp;
import libclang.opaques.__locale_data;
public final class __locale_struct implements StructOp<__locale_struct>, Info<__locale_struct> {
   public static final int BYTE_SIZE = 232;
   private final MemorySegment ms;
   public static final Operations<__locale_struct> OPERATIONS = StructOp.makeOperations(__locale_struct::new, BYTE_SIZE);

   public __locale_struct(MemorySegment ms) {
       this.ms = ms;
   }

    public __locale_struct(SegmentAllocator allocator) {
        this.ms = allocator.allocate(BYTE_SIZE);
    }

    public static Array<__locale_struct> list(SegmentAllocator allocator, long len) {
        return new Array<>(allocator, __locale_struct.OPERATIONS, len);
    }

   @Override
   public StructOpI<__locale_struct> operator() {
       return new StructOpI<>() {
           @Override
           public __locale_struct reinterpret() {
               return new __locale_struct(ms.reinterpret(BYTE_SIZE));
           }

           @Override
           public Ptr<__locale_struct> getPointer() {
               return new Ptr<>(ms, OPERATIONS);
           }

           @Override
           public Operations<__locale_struct> getOperations() {
               return OPERATIONS;
           }

           @Override
           public MemorySegment value() {
               return ms;
           }
       };
   }

    public Array<Ptr<__locale_data>> __locales(){
        return new Array<Ptr<__locale_data>>(ms.asSlice(0, 104), Ptr.makeOperations(__locale_data.OPERATIONS));
    }

    public __locale_struct __locales(ArrayI<? extends PtrI<? extends __locale_data>> __locales){
        MemoryUtils.memcpy(ms, 0, __locales.operator().value(), 0, 104);
        return this;
    }
    public Ptr<I16> __ctype_b(){
        return new Ptr<I16>(MemoryUtils.getAddr(ms, 104), I16.OPERATIONS);
    }

    public __locale_struct __ctype_b(PtrI<? extends I16I<?>> __ctype_b){
        MemoryUtils.setAddr(ms, 104, __ctype_b.operator().value());
        return this;
    }
    public Ptr<I32> __ctype_tolower(){
        return new Ptr<I32>(MemoryUtils.getAddr(ms, 112), I32.OPERATIONS);
    }

    public __locale_struct __ctype_tolower(PtrI<? extends I32I<?>> __ctype_tolower){
        MemoryUtils.setAddr(ms, 112, __ctype_tolower.operator().value());
        return this;
    }
    public Ptr<I32> __ctype_toupper(){
        return new Ptr<I32>(MemoryUtils.getAddr(ms, 120), I32.OPERATIONS);
    }

    public __locale_struct __ctype_toupper(PtrI<? extends I32I<?>> __ctype_toupper){
        MemoryUtils.setAddr(ms, 120, __ctype_toupper.operator().value());
        return this;
    }
    public Array<Ptr<I8>> __names(){
        return new Array<Ptr<I8>>(ms.asSlice(128, 104), Ptr.makeOperations(I8.OPERATIONS));
    }

    public __locale_struct __names(ArrayI<? extends PtrI<? extends I8I<?>>> __names){
        MemoryUtils.memcpy(ms, 128, __names.operator().value(), 0, 104);
        return this;
    }
    @Override
    public String toString() {
        return ms.address() == 0 ? ms.toString()
                : "__locale_struct{" +
                "__locales=" + __locales() +
                ", __ctype_b=" + __ctype_b() +
                ", __ctype_tolower=" + __ctype_tolower() +
                ", __ctype_toupper=" + __ctype_toupper() +
                ", __names=" + __names() +
                '}';
    }

}