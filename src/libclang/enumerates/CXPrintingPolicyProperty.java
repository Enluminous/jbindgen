package libclang.enumerates;

import libclang.common.Array;
import libclang.common.I32;
import libclang.common.I32Op;
import libclang.common.Info;
import libclang.common.Utils;


import java.lang.foreign.SegmentAllocator;

public final class CXPrintingPolicyProperty implements I32Op<CXPrintingPolicyProperty>, Info<CXPrintingPolicyProperty> {
    public static final Info.Operations<CXPrintingPolicyProperty> OPERATIONS = I32Op.makeOperations(CXPrintingPolicyProperty::new);
    public static final long BYTE_SIZE = OPERATIONS.byteSize();
    private final int val;

    public CXPrintingPolicyProperty(int val) {
        this.val = val;
    }

    public static Array<CXPrintingPolicyProperty> list(SegmentAllocator allocator, int len) {
        return new Array<>(allocator, OPERATIONS, len);
    }

    @Override
    public I32OpI<CXPrintingPolicyProperty> operator() {
        return new I32OpI<>() {
            @Override
            public Info.Operations<CXPrintingPolicyProperty> getOperations() {
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

    public static String enumToString(CXPrintingPolicyProperty e) {
        return Utils.enumToString(CXPrintingPolicyProperty.class, e);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CXPrintingPolicyProperty that && that.val == val;
    }

    public static final CXPrintingPolicyProperty CXPrintingPolicy_Indentation = new CXPrintingPolicyProperty(0);
    public static final CXPrintingPolicyProperty CXPrintingPolicy_SuppressSpecifiers = new CXPrintingPolicyProperty(1);
    public static final CXPrintingPolicyProperty CXPrintingPolicy_SuppressTagKeyword = new CXPrintingPolicyProperty(2);
    public static final CXPrintingPolicyProperty CXPrintingPolicy_IncludeTagDefinition = new CXPrintingPolicyProperty(3);
    public static final CXPrintingPolicyProperty CXPrintingPolicy_SuppressScope = new CXPrintingPolicyProperty(4);
    public static final CXPrintingPolicyProperty CXPrintingPolicy_SuppressUnwrittenScope = new CXPrintingPolicyProperty(5);
    public static final CXPrintingPolicyProperty CXPrintingPolicy_SuppressInitializers = new CXPrintingPolicyProperty(6);
    public static final CXPrintingPolicyProperty CXPrintingPolicy_ConstantArraySizeAsWritten = new CXPrintingPolicyProperty(7);
    public static final CXPrintingPolicyProperty CXPrintingPolicy_AnonymousTagLocations = new CXPrintingPolicyProperty(8);
    public static final CXPrintingPolicyProperty CXPrintingPolicy_SuppressStrongLifetime = new CXPrintingPolicyProperty(9);
    public static final CXPrintingPolicyProperty CXPrintingPolicy_SuppressLifetimeQualifiers = new CXPrintingPolicyProperty(10);
    public static final CXPrintingPolicyProperty CXPrintingPolicy_SuppressTemplateArgsInCXXConstructors = new CXPrintingPolicyProperty(11);
    public static final CXPrintingPolicyProperty CXPrintingPolicy_Bool = new CXPrintingPolicyProperty(12);
    public static final CXPrintingPolicyProperty CXPrintingPolicy_Restrict = new CXPrintingPolicyProperty(13);
    public static final CXPrintingPolicyProperty CXPrintingPolicy_Alignof = new CXPrintingPolicyProperty(14);
    public static final CXPrintingPolicyProperty CXPrintingPolicy_UnderscoreAlignof = new CXPrintingPolicyProperty(15);
    public static final CXPrintingPolicyProperty CXPrintingPolicy_UseVoidForZeroParams = new CXPrintingPolicyProperty(16);
    public static final CXPrintingPolicyProperty CXPrintingPolicy_TerseOutput = new CXPrintingPolicyProperty(17);
    public static final CXPrintingPolicyProperty CXPrintingPolicy_PolishForDeclaration = new CXPrintingPolicyProperty(18);
    public static final CXPrintingPolicyProperty CXPrintingPolicy_Half = new CXPrintingPolicyProperty(19);
    public static final CXPrintingPolicyProperty CXPrintingPolicy_MSWChar = new CXPrintingPolicyProperty(20);
    public static final CXPrintingPolicyProperty CXPrintingPolicy_IncludeNewlines = new CXPrintingPolicyProperty(21);
    public static final CXPrintingPolicyProperty CXPrintingPolicy_MSVCFormatting = new CXPrintingPolicyProperty(22);
    public static final CXPrintingPolicyProperty CXPrintingPolicy_ConstantsAsWritten = new CXPrintingPolicyProperty(23);
    public static final CXPrintingPolicyProperty CXPrintingPolicy_SuppressImplicitBase = new CXPrintingPolicyProperty(24);
    public static final CXPrintingPolicyProperty CXPrintingPolicy_FullyQualifiedName = new CXPrintingPolicyProperty(25);
    public static final CXPrintingPolicyProperty CXPrintingPolicy_LastProperty = new CXPrintingPolicyProperty(25);
}