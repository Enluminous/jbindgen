package analyser;

public sealed interface PrimitiveTypes permits PrimitiveTypes.CType, PrimitiveTypes.JType {
    enum CType implements PrimitiveTypes {
        C_VOID(0, false),

        C_I8(1, true),

        C_I16(2, true),

        C_I32(4, true),

        C_I64(8, true),

        C_FP32(4, false),

        C_FP64(8, false),

        C_FP128(16, false);

        private final long byteSize;
        private final boolean integer;

        CType(long byteSize, boolean integer) {
            this.byteSize = byteSize;
            this.integer = integer;
        }

        public long getByteSize() {
            return byteSize;
        }

        public static CType getPrimitiveType(long byteSize, boolean integer) {
            for (CType type : CType.values()) {
                if (type.byteSize == byteSize && type.integer == integer) {
                    return type;
                }
            }
            throw new RuntimeException();
        }
    }

    enum JType implements PrimitiveTypes {
        J_String,
    }
}
