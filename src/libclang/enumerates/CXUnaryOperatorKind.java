package libclang.enumerates;

import libclang.common.Array;
import libclang.common.I32;
import libclang.common.I32Op;
import libclang.common.Info;
import libclang.common.Utils;


import java.lang.foreign.SegmentAllocator;

public final class CXUnaryOperatorKind implements I32Op<CXUnaryOperatorKind>, Info<CXUnaryOperatorKind> {
    public static final Info.Operations<CXUnaryOperatorKind> OPERATIONS = I32Op.makeOperations(CXUnaryOperatorKind::new);
    public static final long BYTE_SIZE = OPERATIONS.byteSize();
    private final int val;

    public CXUnaryOperatorKind(int val) {
        this.val = val;
    }

    public static Array<CXUnaryOperatorKind> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public I32OpI<CXUnaryOperatorKind> operator() {
        return new I32OpI<>() {
            @Override
            public Info.Operations<CXUnaryOperatorKind> getOperations() {
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

    public static String enumToString(CXUnaryOperatorKind e) {
        return Utils.enumToString(CXUnaryOperatorKind.class, e);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXUnaryOperatorKind that && that.val == val;
    }

    public static final CXUnaryOperatorKind CXUnaryOperator_Invalid = new CXUnaryOperatorKind(0);
    public static final CXUnaryOperatorKind CXUnaryOperator_PostInc = new CXUnaryOperatorKind(1);
    public static final CXUnaryOperatorKind CXUnaryOperator_PostDec = new CXUnaryOperatorKind(2);
    public static final CXUnaryOperatorKind CXUnaryOperator_PreInc = new CXUnaryOperatorKind(3);
    public static final CXUnaryOperatorKind CXUnaryOperator_PreDec = new CXUnaryOperatorKind(4);
    public static final CXUnaryOperatorKind CXUnaryOperator_AddrOf = new CXUnaryOperatorKind(5);
    public static final CXUnaryOperatorKind CXUnaryOperator_Deref = new CXUnaryOperatorKind(6);
    public static final CXUnaryOperatorKind CXUnaryOperator_Plus = new CXUnaryOperatorKind(7);
    public static final CXUnaryOperatorKind CXUnaryOperator_Minus = new CXUnaryOperatorKind(8);
    public static final CXUnaryOperatorKind CXUnaryOperator_Not = new CXUnaryOperatorKind(9);
    public static final CXUnaryOperatorKind CXUnaryOperator_LNot = new CXUnaryOperatorKind(10);
    public static final CXUnaryOperatorKind CXUnaryOperator_Real = new CXUnaryOperatorKind(11);
    public static final CXUnaryOperatorKind CXUnaryOperator_Imag = new CXUnaryOperatorKind(12);
    public static final CXUnaryOperatorKind CXUnaryOperator_Extension = new CXUnaryOperatorKind(13);
    public static final CXUnaryOperatorKind CXUnaryOperator_Coawait = new CXUnaryOperatorKind(14);
}