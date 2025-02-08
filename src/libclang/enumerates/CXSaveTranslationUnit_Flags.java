package libclang.enumerates;

import libclang.common.Array;
import libclang.common.I32;
import libclang.common.I32Op;
import libclang.common.Info;
import libclang.common.Utils;


import java.lang.foreign.SegmentAllocator;

public final class CXSaveTranslationUnit_Flags implements I32Op<CXSaveTranslationUnit_Flags>, Info<CXSaveTranslationUnit_Flags> {
    public static final Info.Operations<CXSaveTranslationUnit_Flags> OPERATIONS = I32Op.makeOperations(CXSaveTranslationUnit_Flags::new);
    public static final long BYTE_SIZE = OPERATIONS.byteSize();
    private final int val;

    public CXSaveTranslationUnit_Flags(int val) {
        this.val = val;
    }

    public static Array<CXSaveTranslationUnit_Flags> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public I32OpI<CXSaveTranslationUnit_Flags> operator() {
        return new I32OpI<>() {
            @Override
            public Info.Operations<CXSaveTranslationUnit_Flags> getOperations() {
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

    public static String enumToString(CXSaveTranslationUnit_Flags e) {
        return Utils.enumToString(CXSaveTranslationUnit_Flags.class, e);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXSaveTranslationUnit_Flags that && that.val == val;
    }

    public static final CXSaveTranslationUnit_Flags CXSaveTranslationUnit_None = new CXSaveTranslationUnit_Flags(0);
}