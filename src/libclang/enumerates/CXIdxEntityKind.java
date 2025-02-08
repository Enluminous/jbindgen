package libclang.enumerates;

import libclang.common.Array;
import libclang.common.I32;
import libclang.common.I32Op;
import libclang.common.Info;
import libclang.common.Utils;


import java.lang.foreign.SegmentAllocator;

public final class CXIdxEntityKind implements I32Op<CXIdxEntityKind>, Info<CXIdxEntityKind> {
    public static final Info.Operations<CXIdxEntityKind> OPERATIONS = I32Op.makeOperations(CXIdxEntityKind::new);
    public static final long BYTE_SIZE = OPERATIONS.byteSize();
    private final int val;

    public CXIdxEntityKind(int val) {
        this.val = val;
    }

    public static Array<CXIdxEntityKind> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public I32OpI<CXIdxEntityKind> operator() {
        return new I32OpI<>() {
            @Override
            public Info.Operations<CXIdxEntityKind> getOperations() {
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

    public static String enumToString(CXIdxEntityKind e) {
        return Utils.enumToString(CXIdxEntityKind.class, e);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXIdxEntityKind that && that.val == val;
    }

    public static final CXIdxEntityKind CXIdxEntity_Unexposed = new CXIdxEntityKind(0);
    public static final CXIdxEntityKind CXIdxEntity_Typedef = new CXIdxEntityKind(1);
    public static final CXIdxEntityKind CXIdxEntity_Function = new CXIdxEntityKind(2);
    public static final CXIdxEntityKind CXIdxEntity_Variable = new CXIdxEntityKind(3);
    public static final CXIdxEntityKind CXIdxEntity_Field = new CXIdxEntityKind(4);
    public static final CXIdxEntityKind CXIdxEntity_EnumConstant = new CXIdxEntityKind(5);
    public static final CXIdxEntityKind CXIdxEntity_ObjCClass = new CXIdxEntityKind(6);
    public static final CXIdxEntityKind CXIdxEntity_ObjCProtocol = new CXIdxEntityKind(7);
    public static final CXIdxEntityKind CXIdxEntity_ObjCCategory = new CXIdxEntityKind(8);
    public static final CXIdxEntityKind CXIdxEntity_ObjCInstanceMethod = new CXIdxEntityKind(9);
    public static final CXIdxEntityKind CXIdxEntity_ObjCClassMethod = new CXIdxEntityKind(10);
    public static final CXIdxEntityKind CXIdxEntity_ObjCProperty = new CXIdxEntityKind(11);
    public static final CXIdxEntityKind CXIdxEntity_ObjCIvar = new CXIdxEntityKind(12);
    public static final CXIdxEntityKind CXIdxEntity_Enum = new CXIdxEntityKind(13);
    public static final CXIdxEntityKind CXIdxEntity_Struct = new CXIdxEntityKind(14);
    public static final CXIdxEntityKind CXIdxEntity_Union = new CXIdxEntityKind(15);
    public static final CXIdxEntityKind CXIdxEntity_CXXClass = new CXIdxEntityKind(16);
    public static final CXIdxEntityKind CXIdxEntity_CXXNamespace = new CXIdxEntityKind(17);
    public static final CXIdxEntityKind CXIdxEntity_CXXNamespaceAlias = new CXIdxEntityKind(18);
    public static final CXIdxEntityKind CXIdxEntity_CXXStaticVariable = new CXIdxEntityKind(19);
    public static final CXIdxEntityKind CXIdxEntity_CXXStaticMethod = new CXIdxEntityKind(20);
    public static final CXIdxEntityKind CXIdxEntity_CXXInstanceMethod = new CXIdxEntityKind(21);
    public static final CXIdxEntityKind CXIdxEntity_CXXConstructor = new CXIdxEntityKind(22);
    public static final CXIdxEntityKind CXIdxEntity_CXXDestructor = new CXIdxEntityKind(23);
    public static final CXIdxEntityKind CXIdxEntity_CXXConversionFunction = new CXIdxEntityKind(24);
    public static final CXIdxEntityKind CXIdxEntity_CXXTypeAlias = new CXIdxEntityKind(25);
    public static final CXIdxEntityKind CXIdxEntity_CXXInterface = new CXIdxEntityKind(26);
    public static final CXIdxEntityKind CXIdxEntity_CXXConcept = new CXIdxEntityKind(27);
}