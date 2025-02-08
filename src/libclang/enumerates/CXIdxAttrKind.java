package libclang.enumerates;

import libclang.common.Array;
import libclang.common.I32;
import libclang.common.I32Op;
import libclang.common.Info;
import libclang.common.Utils;


import java.lang.foreign.SegmentAllocator;

public final class CXIdxAttrKind implements I32Op<CXIdxAttrKind>, Info<CXIdxAttrKind> {
    public static final Info.Operations<CXIdxAttrKind> OPERATIONS = I32Op.makeOperations(CXIdxAttrKind::new);
    public static final long BYTE_SIZE = OPERATIONS.byteSize();
    private final int val;

    public CXIdxAttrKind(int val) {
        this.val = val;
    }

    public static Array<CXIdxAttrKind> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public I32OpI<CXIdxAttrKind> operator() {
        return new I32OpI<>() {
            @Override
            public Info.Operations<CXIdxAttrKind> getOperations() {
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

    public static String enumToString(CXIdxAttrKind e) {
        return Utils.enumToString(CXIdxAttrKind.class, e);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXIdxAttrKind that && that.val == val;
    }

    public static final CXIdxAttrKind CXIdxAttr_Unexposed = new CXIdxAttrKind(0);
    public static final CXIdxAttrKind CXIdxAttr_IBAction = new CXIdxAttrKind(1);
    public static final CXIdxAttrKind CXIdxAttr_IBOutlet = new CXIdxAttrKind(2);
    public static final CXIdxAttrKind CXIdxAttr_IBOutletCollection = new CXIdxAttrKind(3);
}