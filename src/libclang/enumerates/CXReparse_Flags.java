package libclang.enumerates;

import libclang.common.Array;
import libclang.common.I32;
import libclang.common.I32Op;
import libclang.common.Info;
import libclang.common.Utils;


import java.lang.foreign.SegmentAllocator;

public final class CXReparse_Flags implements I32Op<CXReparse_Flags>, Info<CXReparse_Flags> {
    public static final Info.Operations<CXReparse_Flags> OPERATIONS = I32Op.makeOperations(CXReparse_Flags::new);
    public static final long BYTE_SIZE = OPERATIONS.byteSize();
    private final int val;

    public CXReparse_Flags(int val) {
        this.val = val;
    }

    public static Array<CXReparse_Flags> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public I32OpI<CXReparse_Flags> operator() {
        return new I32OpI<>() {
            @Override
            public Info.Operations<CXReparse_Flags> getOperations() {
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

    public static String enumToString(CXReparse_Flags e) {
        return Utils.enumToString(CXReparse_Flags.class, e);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXReparse_Flags that && that.val == val;
    }

    public static final CXReparse_Flags CXReparse_None = new CXReparse_Flags(0);
}