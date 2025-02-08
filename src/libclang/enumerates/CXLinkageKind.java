package libclang.enumerates;

import libclang.common.Array;
import libclang.common.I32;
import libclang.common.I32Op;
import libclang.common.Info;
import libclang.common.Utils;


import java.lang.foreign.SegmentAllocator;

public final class CXLinkageKind implements I32Op<CXLinkageKind>, Info<CXLinkageKind> {
    public static final Info.Operations<CXLinkageKind> OPERATIONS = I32Op.makeOperations(CXLinkageKind::new);
    public static final long BYTE_SIZE = OPERATIONS.byteSize();
    private final int val;

    public CXLinkageKind(int val) {
        this.val = val;
    }

    public static Array<CXLinkageKind> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public I32OpI<CXLinkageKind> operator() {
        return new I32OpI<>() {
            @Override
            public Info.Operations<CXLinkageKind> getOperations() {
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

    public static String enumToString(CXLinkageKind e) {
        return Utils.enumToString(CXLinkageKind.class, e);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXLinkageKind that && that.val == val;
    }

    public static final CXLinkageKind CXLinkage_Invalid = new CXLinkageKind(0);
    public static final CXLinkageKind CXLinkage_NoLinkage = new CXLinkageKind(1);
    public static final CXLinkageKind CXLinkage_Internal = new CXLinkageKind(2);
    public static final CXLinkageKind CXLinkage_UniqueExternal = new CXLinkageKind(3);
    public static final CXLinkageKind CXLinkage_External = new CXLinkageKind(4);
}