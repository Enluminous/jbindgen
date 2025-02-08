package libclang.enumerates;

import libclang.common.Array;
import libclang.common.I32;
import libclang.common.I32Op;
import libclang.common.Info;
import libclang.common.Utils;


import java.lang.foreign.SegmentAllocator;

public final class CXIdxEntityLanguage implements I32Op<CXIdxEntityLanguage>, Info<CXIdxEntityLanguage> {
    public static final Info.Operations<CXIdxEntityLanguage> OPERATIONS = I32Op.makeOperations(CXIdxEntityLanguage::new);
    public static final long BYTE_SIZE = OPERATIONS.byteSize();
    private final int val;

    public CXIdxEntityLanguage(int val) {
        this.val = val;
    }

    public static Array<CXIdxEntityLanguage> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public I32OpI<CXIdxEntityLanguage> operator() {
        return new I32OpI<>() {
            @Override
            public Info.Operations<CXIdxEntityLanguage> getOperations() {
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

    public static String enumToString(CXIdxEntityLanguage e) {
        return Utils.enumToString(CXIdxEntityLanguage.class, e);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXIdxEntityLanguage that && that.val == val;
    }

    public static final CXIdxEntityLanguage CXIdxEntityLang_None = new CXIdxEntityLanguage(0);
    public static final CXIdxEntityLanguage CXIdxEntityLang_C = new CXIdxEntityLanguage(1);
    public static final CXIdxEntityLanguage CXIdxEntityLang_ObjC = new CXIdxEntityLanguage(2);
    public static final CXIdxEntityLanguage CXIdxEntityLang_CXX = new CXIdxEntityLanguage(3);
    public static final CXIdxEntityLanguage CXIdxEntityLang_Swift = new CXIdxEntityLanguage(4);
}