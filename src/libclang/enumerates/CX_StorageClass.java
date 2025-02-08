package libclang.enumerates;

import libclang.common.Array;
import libclang.common.I32;
import libclang.common.I32Op;
import libclang.common.Info;
import libclang.common.Utils;


import java.lang.foreign.SegmentAllocator;

public final class CX_StorageClass implements I32Op<CX_StorageClass>, Info<CX_StorageClass> {
    public static final Info.Operations<CX_StorageClass> OPERATIONS = I32Op.makeOperations(CX_StorageClass::new);
    public static final long BYTE_SIZE = OPERATIONS.byteSize();
    private final int val;

    public CX_StorageClass(int val) {
        this.val = val;
    }

    public static Array<CX_StorageClass> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public I32OpI<CX_StorageClass> operator() {
        return new I32OpI<>() {
            @Override
            public Info.Operations<CX_StorageClass> getOperations() {
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

    public static String enumToString(CX_StorageClass e) {
        return Utils.enumToString(CX_StorageClass.class, e);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CX_StorageClass that && that.val == val;
    }

    public static final CX_StorageClass CX_SC_Invalid = new CX_StorageClass(0);
    public static final CX_StorageClass CX_SC_None = new CX_StorageClass(1);
    public static final CX_StorageClass CX_SC_Extern = new CX_StorageClass(2);
    public static final CX_StorageClass CX_SC_Static = new CX_StorageClass(3);
    public static final CX_StorageClass CX_SC_PrivateExtern = new CX_StorageClass(4);
    public static final CX_StorageClass CX_SC_OpenCLWorkGroupLocal = new CX_StorageClass(5);
    public static final CX_StorageClass CX_SC_Auto = new CX_StorageClass(6);
    public static final CX_StorageClass CX_SC_Register = new CX_StorageClass(7);
}