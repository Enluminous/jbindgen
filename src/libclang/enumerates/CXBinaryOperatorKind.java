package libclang.enumerates;

import libclang.common.Array;
import libclang.common.I32;
import libclang.common.I32Op;
import libclang.common.Info;
import libclang.common.Utils;


import java.lang.foreign.SegmentAllocator;

public final class CXBinaryOperatorKind implements I32Op<CXBinaryOperatorKind>, Info<CXBinaryOperatorKind> {
    public static final Info.Operations<CXBinaryOperatorKind> OPERATIONS = I32Op.makeOperations(CXBinaryOperatorKind::new);
    public static final long BYTE_SIZE = OPERATIONS.byteSize();
    private final int val;

    public CXBinaryOperatorKind(int val) {
        this.val = val;
    }

    public static Array<CXBinaryOperatorKind> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public I32OpI<CXBinaryOperatorKind> operator() {
        return new I32OpI<>() {
            @Override
            public Info.Operations<CXBinaryOperatorKind> getOperations() {
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

    public static String enumToString(CXBinaryOperatorKind e) {
        return Utils.enumToString(CXBinaryOperatorKind.class, e);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXBinaryOperatorKind that && that.val == val;
    }

    public static final CXBinaryOperatorKind CXBinaryOperator_Invalid = new CXBinaryOperatorKind(0);
    public static final CXBinaryOperatorKind CXBinaryOperator_PtrMemD = new CXBinaryOperatorKind(1);
    public static final CXBinaryOperatorKind CXBinaryOperator_PtrMemI = new CXBinaryOperatorKind(2);
    public static final CXBinaryOperatorKind CXBinaryOperator_Mul = new CXBinaryOperatorKind(3);
    public static final CXBinaryOperatorKind CXBinaryOperator_Div = new CXBinaryOperatorKind(4);
    public static final CXBinaryOperatorKind CXBinaryOperator_Rem = new CXBinaryOperatorKind(5);
    public static final CXBinaryOperatorKind CXBinaryOperator_Add = new CXBinaryOperatorKind(6);
    public static final CXBinaryOperatorKind CXBinaryOperator_Sub = new CXBinaryOperatorKind(7);
    public static final CXBinaryOperatorKind CXBinaryOperator_Shl = new CXBinaryOperatorKind(8);
    public static final CXBinaryOperatorKind CXBinaryOperator_Shr = new CXBinaryOperatorKind(9);
    public static final CXBinaryOperatorKind CXBinaryOperator_Cmp = new CXBinaryOperatorKind(10);
    public static final CXBinaryOperatorKind CXBinaryOperator_LT = new CXBinaryOperatorKind(11);
    public static final CXBinaryOperatorKind CXBinaryOperator_GT = new CXBinaryOperatorKind(12);
    public static final CXBinaryOperatorKind CXBinaryOperator_LE = new CXBinaryOperatorKind(13);
    public static final CXBinaryOperatorKind CXBinaryOperator_GE = new CXBinaryOperatorKind(14);
    public static final CXBinaryOperatorKind CXBinaryOperator_EQ = new CXBinaryOperatorKind(15);
    public static final CXBinaryOperatorKind CXBinaryOperator_NE = new CXBinaryOperatorKind(16);
    public static final CXBinaryOperatorKind CXBinaryOperator_And = new CXBinaryOperatorKind(17);
    public static final CXBinaryOperatorKind CXBinaryOperator_Xor = new CXBinaryOperatorKind(18);
    public static final CXBinaryOperatorKind CXBinaryOperator_Or = new CXBinaryOperatorKind(19);
    public static final CXBinaryOperatorKind CXBinaryOperator_LAnd = new CXBinaryOperatorKind(20);
    public static final CXBinaryOperatorKind CXBinaryOperator_LOr = new CXBinaryOperatorKind(21);
    public static final CXBinaryOperatorKind CXBinaryOperator_Assign = new CXBinaryOperatorKind(22);
    public static final CXBinaryOperatorKind CXBinaryOperator_MulAssign = new CXBinaryOperatorKind(23);
    public static final CXBinaryOperatorKind CXBinaryOperator_DivAssign = new CXBinaryOperatorKind(24);
    public static final CXBinaryOperatorKind CXBinaryOperator_RemAssign = new CXBinaryOperatorKind(25);
    public static final CXBinaryOperatorKind CXBinaryOperator_AddAssign = new CXBinaryOperatorKind(26);
    public static final CXBinaryOperatorKind CXBinaryOperator_SubAssign = new CXBinaryOperatorKind(27);
    public static final CXBinaryOperatorKind CXBinaryOperator_ShlAssign = new CXBinaryOperatorKind(28);
    public static final CXBinaryOperatorKind CXBinaryOperator_ShrAssign = new CXBinaryOperatorKind(29);
    public static final CXBinaryOperatorKind CXBinaryOperator_AndAssign = new CXBinaryOperatorKind(30);
    public static final CXBinaryOperatorKind CXBinaryOperator_XorAssign = new CXBinaryOperatorKind(31);
    public static final CXBinaryOperatorKind CXBinaryOperator_OrAssign = new CXBinaryOperatorKind(32);
    public static final CXBinaryOperatorKind CXBinaryOperator_Comma = new CXBinaryOperatorKind(33);
}