package libclang.enumerates;

import libclang.common.Array;
import libclang.common.I32;
import libclang.common.I32Op;
import libclang.common.Info;
import libclang.common.Utils;


import java.lang.foreign.SegmentAllocator;

public final class CXTLSKind implements I32Op<CXTLSKind>, Info<CXTLSKind> {
    public static final Info.Operations<CXTLSKind> OPERATIONS = I32Op.makeOperations(CXTLSKind::new);
    public static final long BYTE_SIZE = OPERATIONS.byteSize();
    private final int val;

    public CXTLSKind(int val) {
        this.val = val;
    }

    public static Array<CXTLSKind> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public I32OpI<CXTLSKind> operator() {
        return new I32OpI<>() {
            @Override
            public Info.Operations<CXTLSKind> getOperations() {
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

    public static String enumToString(CXTLSKind e) {
        return Utils.enumToString(CXTLSKind.class, e);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXTLSKind that && that.val == val;
    }

    public static final CXTLSKind CXTLS_None = new CXTLSKind(0);
    public static final CXTLSKind CXTLS_Dynamic = new CXTLSKind(1);
    public static final CXTLSKind CXTLS_Static = new CXTLSKind(2);
}