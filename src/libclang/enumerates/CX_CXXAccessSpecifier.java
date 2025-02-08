package libclang.enumerates;

import libclang.common.Array;
import libclang.common.I32;
import libclang.common.I32Op;
import libclang.common.Info;
import libclang.common.Utils;


import java.lang.foreign.SegmentAllocator;

public final class CX_CXXAccessSpecifier implements I32Op<CX_CXXAccessSpecifier>, Info<CX_CXXAccessSpecifier> {
    public static final Info.Operations<CX_CXXAccessSpecifier> OPERATIONS = I32Op.makeOperations(CX_CXXAccessSpecifier::new);
    public static final long BYTE_SIZE = OPERATIONS.byteSize();
    private final int val;

    public CX_CXXAccessSpecifier(int val) {
        this.val = val;
    }

    public static Array<CX_CXXAccessSpecifier> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public I32OpI<CX_CXXAccessSpecifier> operator() {
        return new I32OpI<>() {
            @Override
            public Info.Operations<CX_CXXAccessSpecifier> getOperations() {
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

    public static String enumToString(CX_CXXAccessSpecifier e) {
        return Utils.enumToString(CX_CXXAccessSpecifier.class, e);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CX_CXXAccessSpecifier that && that.val == val;
    }

    public static final CX_CXXAccessSpecifier CX_CXXInvalidAccessSpecifier = new CX_CXXAccessSpecifier(0);
    public static final CX_CXXAccessSpecifier CX_CXXPublic = new CX_CXXAccessSpecifier(1);
    public static final CX_CXXAccessSpecifier CX_CXXProtected = new CX_CXXAccessSpecifier(2);
    public static final CX_CXXAccessSpecifier CX_CXXPrivate = new CX_CXXAccessSpecifier(3);
}