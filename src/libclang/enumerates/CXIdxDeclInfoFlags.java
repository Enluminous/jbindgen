package libclang.enumerates;

import libclang.common.Array;
import libclang.common.I32;
import libclang.common.I32Op;
import libclang.common.Info;
import libclang.common.Utils;


import java.lang.foreign.SegmentAllocator;

public final class CXIdxDeclInfoFlags implements I32Op<CXIdxDeclInfoFlags>, Info<CXIdxDeclInfoFlags> {
    public static final Info.Operations<CXIdxDeclInfoFlags> OPERATIONS = I32Op.makeOperations(CXIdxDeclInfoFlags::new);
    public static final long BYTE_SIZE = OPERATIONS.byteSize();
    private final int val;

    public CXIdxDeclInfoFlags(int val) {
        this.val = val;
    }

    public static Array<CXIdxDeclInfoFlags> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public I32OpI<CXIdxDeclInfoFlags> operator() {
        return new I32OpI<>() {
            @Override
            public Info.Operations<CXIdxDeclInfoFlags> getOperations() {
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

    public static String enumToString(CXIdxDeclInfoFlags e) {
        return Utils.enumToString(CXIdxDeclInfoFlags.class, e);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXIdxDeclInfoFlags that && that.val == val;
    }

    public static final CXIdxDeclInfoFlags CXIdxDeclFlag_Skipped = new CXIdxDeclInfoFlags(1);
}