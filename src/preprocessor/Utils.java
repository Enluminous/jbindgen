package preprocessor;

import analyser.PrimitiveTypes;
import generator.types.CommonTypes;

public class Utils {
    public static CommonTypes.BindTypes conv2BindTypes(PrimitiveTypes.CType type) {
        switch (type) {
            case C_I8 -> {
                return CommonTypes.BindTypes.I8;
            }
            case C_I16 -> {
                return CommonTypes.BindTypes.I16;
            }
            case C_I32 -> {
                return CommonTypes.BindTypes.I32;
            }
            case C_I64 -> {
                return CommonTypes.BindTypes.I64;
            }
            case C_FP32 -> {
                return CommonTypes.BindTypes.FP32;
            }
            case C_FP64 -> {
                return CommonTypes.BindTypes.FP64;
            }
            default -> {
                throw new RuntimeException();
            }
        }
    }
}
