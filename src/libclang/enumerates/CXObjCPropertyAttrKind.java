package libclang.enumerates;

import libclang.common.Array;
import libclang.common.I32;
import libclang.common.I32Op;
import libclang.common.Info;
import libclang.common.Utils;


import java.lang.foreign.SegmentAllocator;

public final class CXObjCPropertyAttrKind implements I32Op<CXObjCPropertyAttrKind>, Info<CXObjCPropertyAttrKind> {
    public static final Info.Operations<CXObjCPropertyAttrKind> OPERATIONS = I32Op.makeOperations(CXObjCPropertyAttrKind::new);
    public static final long BYTE_SIZE = OPERATIONS.byteSize();
    private final int val;

    public CXObjCPropertyAttrKind(int val) {
        this.val = val;
    }

    public static Array<CXObjCPropertyAttrKind> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public I32OpI<CXObjCPropertyAttrKind> operator() {
        return new I32OpI<>() {
            @Override
            public Info.Operations<CXObjCPropertyAttrKind> getOperations() {
                return OPERATIONS;
            }

            @Override
            public Integer value() {
                return val;
            }
        };
    }

    private String str;

    @Override
    public String toString() {
        if (str == null) {
            str = enumToString(this);
            if (str == null) str = String.valueOf(val);
        }
        return str;
    }

    public static String enumToString(CXObjCPropertyAttrKind e) {
        return Utils.enumToString(CXObjCPropertyAttrKind.class, e);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXObjCPropertyAttrKind that && that.val == val;
    }

    public static final CXObjCPropertyAttrKind CXObjCPropertyAttr_noattr = new CXObjCPropertyAttrKind(0);
    public static final CXObjCPropertyAttrKind CXObjCPropertyAttr_readonly = new CXObjCPropertyAttrKind(1);
    public static final CXObjCPropertyAttrKind CXObjCPropertyAttr_getter = new CXObjCPropertyAttrKind(2);
    public static final CXObjCPropertyAttrKind CXObjCPropertyAttr_assign = new CXObjCPropertyAttrKind(4);
    public static final CXObjCPropertyAttrKind CXObjCPropertyAttr_readwrite = new CXObjCPropertyAttrKind(8);
    public static final CXObjCPropertyAttrKind CXObjCPropertyAttr_retain = new CXObjCPropertyAttrKind(16);
    public static final CXObjCPropertyAttrKind CXObjCPropertyAttr_copy = new CXObjCPropertyAttrKind(32);
    public static final CXObjCPropertyAttrKind CXObjCPropertyAttr_nonatomic = new CXObjCPropertyAttrKind(64);
    public static final CXObjCPropertyAttrKind CXObjCPropertyAttr_setter = new CXObjCPropertyAttrKind(128);
    public static final CXObjCPropertyAttrKind CXObjCPropertyAttr_atomic = new CXObjCPropertyAttrKind(256);
    public static final CXObjCPropertyAttrKind CXObjCPropertyAttr_weak = new CXObjCPropertyAttrKind(512);
    public static final CXObjCPropertyAttrKind CXObjCPropertyAttr_strong = new CXObjCPropertyAttrKind(1024);
    public static final CXObjCPropertyAttrKind CXObjCPropertyAttr_unsafe_unretained = new CXObjCPropertyAttrKind(2048);
    public static final CXObjCPropertyAttrKind CXObjCPropertyAttr_class = new CXObjCPropertyAttrKind(4096);
}