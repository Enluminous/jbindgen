package libclang.enumerates;

import libclang.common.Array;
import libclang.common.I32;
import libclang.common.I32Op;
import libclang.common.Info;
import libclang.common.Utils;


import java.lang.foreign.SegmentAllocator;

public final class CXSymbolRole implements I32Op<CXSymbolRole>, Info<CXSymbolRole> {
    public static final Info.Operations<CXSymbolRole> OPERATIONS = I32Op.makeOperations(CXSymbolRole::new);
    public static final long BYTE_SIZE = OPERATIONS.byteSize();
    private final int val;

    public CXSymbolRole(int val) {
        this.val = val;
    }

    public static Array<CXSymbolRole> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public I32OpI<CXSymbolRole> operator() {
        return new I32OpI<>() {
            @Override
            public Info.Operations<CXSymbolRole> getOperations() {
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

    public static String enumToString(CXSymbolRole e) {
        return Utils.enumToString(CXSymbolRole.class, e);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXSymbolRole that && that.val == val;
    }

    public static final CXSymbolRole CXSymbolRole_None = new CXSymbolRole(0);
    public static final CXSymbolRole CXSymbolRole_Declaration = new CXSymbolRole(1);
    public static final CXSymbolRole CXSymbolRole_Definition = new CXSymbolRole(2);
    public static final CXSymbolRole CXSymbolRole_Reference = new CXSymbolRole(4);
    public static final CXSymbolRole CXSymbolRole_Read = new CXSymbolRole(8);
    public static final CXSymbolRole CXSymbolRole_Write = new CXSymbolRole(16);
    public static final CXSymbolRole CXSymbolRole_Call = new CXSymbolRole(32);
    public static final CXSymbolRole CXSymbolRole_Dynamic = new CXSymbolRole(64);
    public static final CXSymbolRole CXSymbolRole_AddressOf = new CXSymbolRole(128);
    public static final CXSymbolRole CXSymbolRole_Implicit = new CXSymbolRole(256);
}