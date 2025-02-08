package libclang.enumerates;

import libclang.common.Array;
import libclang.common.I32;
import libclang.common.I32Op;
import libclang.common.Info;
import libclang.common.Utils;


import java.lang.foreign.SegmentAllocator;

public final class CXObjCDeclQualifierKind implements I32Op<CXObjCDeclQualifierKind>, Info<CXObjCDeclQualifierKind> {
    public static final Info.Operations<CXObjCDeclQualifierKind> OPERATIONS = I32Op.makeOperations(CXObjCDeclQualifierKind::new);
    public static final long BYTE_SIZE = OPERATIONS.byteSize();
    private final int val;

    public CXObjCDeclQualifierKind(int val) {
        this.val = val;
    }

    public static Array<CXObjCDeclQualifierKind> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public I32OpI<CXObjCDeclQualifierKind> operator() {
        return new I32OpI<>() {
            @Override
            public Info.Operations<CXObjCDeclQualifierKind> getOperations() {
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

    public static String enumToString(CXObjCDeclQualifierKind e) {
        return Utils.enumToString(CXObjCDeclQualifierKind.class, e);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXObjCDeclQualifierKind that && that.val == val;
    }

    public static final CXObjCDeclQualifierKind CXObjCDeclQualifier_None = new CXObjCDeclQualifierKind(0);
    public static final CXObjCDeclQualifierKind CXObjCDeclQualifier_In = new CXObjCDeclQualifierKind(1);
    public static final CXObjCDeclQualifierKind CXObjCDeclQualifier_Inout = new CXObjCDeclQualifierKind(2);
    public static final CXObjCDeclQualifierKind CXObjCDeclQualifier_Out = new CXObjCDeclQualifierKind(4);
    public static final CXObjCDeclQualifierKind CXObjCDeclQualifier_Bycopy = new CXObjCDeclQualifierKind(8);
    public static final CXObjCDeclQualifierKind CXObjCDeclQualifier_Byref = new CXObjCDeclQualifierKind(16);
    public static final CXObjCDeclQualifierKind CXObjCDeclQualifier_Oneway = new CXObjCDeclQualifierKind(32);
}