package libclang.enumerates;

import libclang.common.Array;
import libclang.common.I32;
import libclang.common.I32Op;
import libclang.common.Info;
import libclang.common.Utils;


import java.lang.foreign.SegmentAllocator;

public final class CXCallingConv implements I32Op<CXCallingConv>, Info<CXCallingConv> {
    public static final Info.Operations<CXCallingConv> OPERATIONS = I32Op.makeOperations(CXCallingConv::new);
    public static final long BYTE_SIZE = OPERATIONS.byteSize();
    private final int val;

    public CXCallingConv(int val) {
        this.val = val;
    }

    public static Array<CXCallingConv> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public I32OpI<CXCallingConv> operator() {
        return new I32OpI<>() {
            @Override
            public Info.Operations<CXCallingConv> getOperations() {
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

    public static String enumToString(CXCallingConv e) {
        return Utils.enumToString(CXCallingConv.class, e);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXCallingConv that && that.val == val;
    }

    public static final CXCallingConv CXCallingConv_Default = new CXCallingConv(0);
    public static final CXCallingConv CXCallingConv_C = new CXCallingConv(1);
    public static final CXCallingConv CXCallingConv_X86StdCall = new CXCallingConv(2);
    public static final CXCallingConv CXCallingConv_X86FastCall = new CXCallingConv(3);
    public static final CXCallingConv CXCallingConv_X86ThisCall = new CXCallingConv(4);
    public static final CXCallingConv CXCallingConv_X86Pascal = new CXCallingConv(5);
    public static final CXCallingConv CXCallingConv_AAPCS = new CXCallingConv(6);
    public static final CXCallingConv CXCallingConv_AAPCS_VFP = new CXCallingConv(7);
    public static final CXCallingConv CXCallingConv_X86RegCall = new CXCallingConv(8);
    public static final CXCallingConv CXCallingConv_IntelOclBicc = new CXCallingConv(9);
    public static final CXCallingConv CXCallingConv_Win64 = new CXCallingConv(10);
    public static final CXCallingConv CXCallingConv_X86_64Win64 = new CXCallingConv(10);
    public static final CXCallingConv CXCallingConv_X86_64SysV = new CXCallingConv(11);
    public static final CXCallingConv CXCallingConv_X86VectorCall = new CXCallingConv(12);
    public static final CXCallingConv CXCallingConv_Swift = new CXCallingConv(13);
    public static final CXCallingConv CXCallingConv_PreserveMost = new CXCallingConv(14);
    public static final CXCallingConv CXCallingConv_PreserveAll = new CXCallingConv(15);
    public static final CXCallingConv CXCallingConv_AArch64VectorCall = new CXCallingConv(16);
    public static final CXCallingConv CXCallingConv_SwiftAsync = new CXCallingConv(17);
    public static final CXCallingConv CXCallingConv_AArch64SVEPCS = new CXCallingConv(18);
    public static final CXCallingConv CXCallingConv_Invalid = new CXCallingConv(100);
    public static final CXCallingConv CXCallingConv_Unexposed = new CXCallingConv(200);
}