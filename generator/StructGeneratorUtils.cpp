//
// Created by snownf on 23-11-9.
//

#include <format>
#include "StructGeneratorUtils.h"

#include "FunctionGeneratorUtils.h"
#include "StructGenerator.h"

namespace jbindgen {
    using namespace value::jbasic;

    std::string
    StructGeneratorUtils::makeCore(const std::string &extraImported, const std::string &packageName,
                                   const std::string &structName, long byteSize,
                                   const std::string &toString, const std::string &getter_setter) {
        {
            const std::string &fakeValueLayout = generateFakeValueLayout(byteSize);
            std::string core = std::vformat(
                    "package {4};\n"
                    "\n"
                    "\n"
                    "{5}"
                    "\n"
                    "import java.lang.foreign.*;\n"
                    "import java.util.function.Consumer;\n"
                    "\n"
                    "\n"
                    "public final class {0} implements Pointer<{0}> {{\n"
                    "    public static final MemoryLayout MEMORY_LAYOUT = {1};\n"
                    "    public static final long BYTE_SIZE = MEMORY_LAYOUT.byteSize();\n"
                    "\n"
                    "    public static NList<{0}> list(Pointer<{0}> ptr) {{\n"
                    "        return new NList<>(ptr, {0}::new, BYTE_SIZE);\n"
                    "    }}\n"
                    "\n"
                    "    public static NList<{0}> list(Pointer<{0}> ptr, long length) {{\n"
                    "        return new NList<>(ptr, length, {0}::new, BYTE_SIZE);\n"
                    "    }}\n"
                    "\n"
                    "    public static NList<{0}> list(SegmentAllocator allocator, long length) {{\n"
                    "        return new NList<>(allocator, length, {0}::new, BYTE_SIZE);\n"
                    "    }}\n"
                    "\n"
                    "    private final MemorySegment ptr;\n"
                    "\n"
                    "    public {0}(Pointer<{0}> ptr) {{\n"
                    "        this.ptr = ptr.pointer();\n"
                    "    }}\n"
                    "\n"
                    "    public {0}(SegmentAllocator allocator) {{\n"
                    "        ptr = allocator.allocate(BYTE_SIZE);\n"
                    "    }}\n"
                    "\n"
                    "    public {0} reinterpretSize() {{\n"
                    "        return new {0}(FunctionUtils.makePointer(ptr.reinterpret(BYTE_SIZE)));\n"
                    "    }}\n"
                    "\n"
                    "    @Override\n"
                    "    public MemorySegment pointer() {{\n"
                    "        return ptr;\n"
                    "    }}\n"
                    "\n"
                    "{2}\n"
                    "{3}\n"
                    "}}", std::make_format_args(structName, fakeValueLayout, getter_setter,
                                                toString, packageName, extraImported));
            return core;
        }
    }

    std::vector<Setter>
    StructGeneratorUtils::defaultStructDecodeSetter(const StructMember &structMember, const Analyser &analyser,
                                                    const std::string &ptrName) {
        return get<1>(defaultStructDecodeShared(structMember, analyser, ptrName));
    }

    std::vector<Getter> StructGeneratorUtils::defaultStructDecodeGetter(const jbindgen::StructMember &structMember,
                                                                        const Analyser &analyser,
                                                                        const std::string &ptrName) {
        return get<0>(defaultStructDecodeShared(structMember, analyser, ptrName));
    }

    std::tuple<std::vector<Getter>, std::vector<Setter>>
    StructGeneratorUtils::defaultStructDecodeShared(const StructMember &structMember, const Analyser &analyser,
                                                    const std::string &ptrName) {
        auto copyResult = value::method::typeCopyWithResultType(structMember.var.type);
        switch (copyResult.copy) {
            case value::method::copy_by_primitive_j_int_call:
            case value::method::copy_by_primitive_j_long_call:
            case value::method::copy_by_primitive_j_float_call:
            case value::method::copy_by_primitive_j_double_call:
            case value::method::copy_by_primitive_j_short_call:
            case value::method::copy_by_primitive_j_byte_call:
#if NATIVE_UNSUPPORTED
            case value::method::copy_by_primitive_j_char_call:
            case value::method::copy_by_primitive_j_bool_call:
#endif
            {
                const value::jbasic::NativeType &ffmType = copy_method_2_native_type(copyResult.copy);
                assertAppend(ffmType.type != value::jbasic::type_other,
                             "type:" + toStringWithoutConst(copyResult.type));
                return {std::vector{(Getter) {
                        ffmType.primitive(), "",
                        ptrName + ".get(" + ffmType.value_layout() + ", " +
                        std::to_string(structMember.offsetOfBit / 8) +
                        ")"
                }}, std::vector{(Setter) {
                        ffmType.primitive() + " " + structMember.var.name,
                        ptrName + ".set(" + ffmType.value_layout() + ", " +
                        std::to_string(structMember.offsetOfBit / 8) + ", " + structMember.var.name +
                        ")"
                }}
                };
            }
            case value::method::copy_by_value_j_int_call:
            case value::method::copy_by_value_j_long_call:
            case value::method::copy_by_value_j_float_call:
            case value::method::copy_by_value_j_double_call:
            case value::method::copy_by_value_j_short_call:
            case value::method::copy_by_value_j_byte_call:
#if NATIVE_UNSUPPORTED
            case value::method::copy_by_value_j_char_call:
            case value::method::copy_by_value_j_bool_call:
#endif
            {
                auto value = copy_method_2_value_type(copyResult.copy);
                assertAppend(value.type != value::jbasic::type_other, "type:" + toStringWithoutConst(copyResult.type));
                auto name = toCXTypeName(copyResult.type, analyser);
                return {std::vector{(Getter) {
                        name, "",
                        "new " + name + "(FunctionUtils.makePointer(" + ptrName + ".asSlice(" +
                        std::to_string(structMember.offsetOfBit / 8) + "," +//offset
                        std::to_string(checkResultSize(structMember.var.byteSize)) +//size
                        ")))"}}, std::vector{(Setter) {
                        //setter
                        name + " " + structMember.var.name,
                        ptrName + ".set(" + value.value_layout() + ", " +
                        std::to_string(structMember.offsetOfBit / 8) + ", " +
                        structMember.var.name + ".value())"
                }}
                };
            }
            case value::method::copy_by_value_memory_segment_call: {
                auto name = toCXTypeName(copyResult.type, analyser);
                if (isTypedefFunction(copyResult.type)) {
                    return {{(Getter) {
                            value::makeValue(name, value::jext::VPointer), "",
                            "new VPointer<>(" + ptrName + ".get(" +
                            value::jext::VPointer.value_layout() + ", " +
                            std::to_string(structMember.offsetOfBit / 8) +//offset
                            "))"}},
                            {(Setter) {
                                    //setter
                                    value::makeValue(name, value::jext::VPointer) + " " + structMember.var.name,
                                    ptrName + ".set(" + value::jext::VPointer.value_layout() + ", " +
                                    std::to_string(structMember.offsetOfBit / 8) + ", " +
                                    structMember.var.name + ".value())"
                            }}
                    };
                }
                return {{(Getter) {
                        name, "",
                        "new " + name + "(" + ptrName + ".get(" +
                        value::jext::VPointer.value_layout() + ", " +
                        std::to_string(structMember.offsetOfBit / 8) +//offset
                        "))"}},
                        {(Setter) {
                                //setter
                                name + " " + structMember.var.name,
                                ptrName + ".set(" + value::jext::VPointer.value_layout() + ", " +
                                std::to_string(structMember.offsetOfBit / 8) + ", " +
                                structMember.var.name + ".value())"
                        }}
                };
            }
            case value::method::copy_by_ext_int128_call:
            case value::method::copy_by_ext_long_double_call: {
                auto ext = value::method::copy_method_2_ext_type(copyResult.copy);
                assertAppend(ext.type != value::jext::type_other, "type:" + toStringWithoutConst(copyResult.type));
                return {std::vector{(Getter) {
                        ext.native_wrapper, "",
                        "new " + ext.native_wrapper + "(FunctionUtils.makePointer(" + ptrName + ".asSlice(" +
                        std::to_string(structMember.offsetOfBit / 8) + ", " +
                        std::to_string(checkResultSize(structMember.var.byteSize)) + ")))"
                }}, std::vector{(Setter) {
                        //setter
                        ext.native_wrapper + " " + structMember.var.name,
                        //copy dest for ext type
                        "MemorySegment.copy(" + structMember.var.name + ".pointer(), 0," + ptrName + ", " +
                        std::to_string(structMember.offsetOfBit / 8) + ", Math.min(" +
                        std::to_string(checkResultSize(structMember.var.byteSize)) + "," + structMember.var.name +
                        ".pointer().byteSize()))"
                }}
                };
            }
            case value::method::copy_by_function_ptr_call: {
                auto name = toCXTypeFunctionPtrName(copyResult.type, analyser);
                return {{(Getter) {
                        value::makeValue(name, value::jext::VPointer), "",
                        "new VPointer<>(" + ptrName + ".get(" +
                        value::jext::VPointer.value_layout() + ", " +
                        std::to_string(structMember.offsetOfBit / 8) +//offset
                        "))"}},
                        {(Setter) {
                                //setter
                                value::makePointer(name) + " " + structMember.var.name,
                                ptrName + ".set(" + value::jext::VPointer.value_layout() + ", " +
                                std::to_string(structMember.offsetOfBit / 8) + ", " +
                                structMember.var.name + ".pointer())"
                        }}
                };
            }
            case value::method::copy_by_ptr_copy_call: {
                auto pointeeResult = value::method::typeCopyWithResultType(toPointeeType(copyResult.type));
                //special char*
                switch (pointeeResult.copy) {
                    case value::method::copy_by_primitive_j_int_call:
                    case value::method::copy_by_primitive_j_long_call:
#if NATIVE_UNSUPPORTED
                    case value::method::copy_by_primitive_j_bool_call:
                    case value::method::copy_by_primitive_j_char_call:
#endif
                    case value::method::copy_by_primitive_j_float_call:
                    case value::method::copy_by_primitive_j_double_call:
                    case value::method::copy_by_primitive_j_byte_call:
                    case value::method::copy_by_primitive_j_short_call: {
                        auto native = copy_method_2_native_type(pointeeResult.copy);
                        auto value = value::method::native_type_2_value_type(native);
                        Getter ptrGetter = (Getter) {
                                value::makePointer(value), "",
                                "FunctionUtils.makePointer(" + ptrName + ".get(ValueLayout.ADDRESS," +
                                std::to_string(structMember.offsetOfBit / 8) + "))"
                        };
                        return {{(Getter) {
                                value::makeVList(value),
                                "long length",
                                value.wrapper() + ".list(FunctionUtils.makePointer(" + ptrName + ".get(ValueLayout.ADDRESS," +
                                std::to_string(structMember.offsetOfBit / 8) + ")), length)"}, ptrGetter},
                                //setter
                                std::vector{(Setter) {
                                        value::makePointer(value)
                                        + " " + structMember.var.name,
                                        ptrName + ".set(ValueLayout.ADDRESS, " +
                                        std::to_string(structMember.offsetOfBit / 8) + ", " //offset
                                        + structMember.var.name + ".pointer()" + //value
                                        ")"
                                }, (Setter) {
                                        native.wrapper()
                                        + " " + structMember.var.name,
                                        ptrName + ".set(ValueLayout.ADDRESS, " +
                                        std::to_string(structMember.offsetOfBit / 8) + ", " //offset
                                        + structMember.var.name + ".pointer()" + //value
                                        ")"
                                }}
                        };
                    }
                    case value::method::copy_by_value_j_int_call:
                    case value::method::copy_by_value_j_long_call:
                    case value::method::copy_by_value_j_float_call:
#if NATIVE_UNSUPPORTED
                    case value::method::copy_by_value_j_char_call:
                    case value::method::copy_by_value_j_bool_call:
#endif
                    case value::method::copy_by_value_j_double_call:
                    case value::method::copy_by_value_j_short_call:
                    case value::method::copy_by_value_j_byte_call: {
                        auto value = copy_method_2_value_type(pointeeResult.copy);
                        auto pointerTypeName = toCXTypeName(pointeeResult.type, analyser);
                        Getter ptrGetter = (Getter) {
                                value::makePointer(pointerTypeName), "",
                                "FunctionUtils.makePointer(" + ptrName + ".get(ValueLayout.ADDRESS," +
                                std::to_string(structMember.offsetOfBit / 8) + "))"
                        };
                        return {{(Getter) {
                                value::makeVList(pointerTypeName, value),
                                "long length",
                                pointerTypeName + ".list(FunctionUtils.makePointer(" + ptrName + ".get(ValueLayout.ADDRESS," +
                                std::to_string(structMember.offsetOfBit / 8) + ")), length)"}, ptrGetter},
                                //setter
                                std::vector{(Setter) {
                                        value::makePointer(pointerTypeName)
                                        + " " + structMember.var.name,
                                        ptrName + ".set(ValueLayout.ADDRESS, " +
                                        std::to_string(structMember.offsetOfBit / 8) + ", " //offset
                                        + structMember.var.name + ".pointer()" + //value
                                        ")"
                                }}
                        };
                    }
                    case value::method::copy_by_value_memory_segment_call: {
                        auto pointerTypeName = toCXTypeName(pointeeResult.type, analyser);
                        Getter ptrGetter = (Getter) {
                                value::makePointer(pointerTypeName), "",
                                "FunctionUtils.makePointer(" + ptrName + ".get(ValueLayout.ADDRESS," +
                                std::to_string(structMember.offsetOfBit / 8) + "))"
                        };
                        jbindgen::Setter ptrSetter = {
                                value::makePointer(pointerTypeName) + " " + structMember.var.name,
                                ptrName + ".set(ValueLayout.ADDRESS, " +
                                std::to_string(structMember.offsetOfBit / 8) + ", " //offset
                                + structMember.var.name + ".pointer()" + //value
                                ")"
                        };
                        if (isTypedefFunction(pointeeResult.type)) {
                            return {{ptrGetter},
                                    //setter
                                    {ptrSetter}
                            };
                        }
                        return {{(Getter) {
                                value::makeVList(pointerTypeName, value::jext::VPointer),
                                "long length",
                                pointerTypeName + ".list(FunctionUtils.makePointer(" + ptrName + ".get(ValueLayout.ADDRESS," +
                                std::to_string(structMember.offsetOfBit / 8) + ")), length)"}, ptrGetter},
                                //setter
                                {ptrSetter}
                        };
                    }
                    case value::method::copy_by_function_ptr_call: {
                        auto pointerTypeName = toCXTypeFunctionPtrName(pointeeResult.type, analyser);
                        Getter ptrGetter = (Getter) {
                                value::makePointer(pointerTypeName), "",
                                "FunctionUtils.makePointer(" + ptrName + ".get(ValueLayout.ADDRESS," +
                                std::to_string(structMember.offsetOfBit / 8) + "))"
                        };
                        return {{ptrGetter},
                                //setter
                                std::vector{(Setter) {
                                        value::makePointer(pointerTypeName) + " " + structMember.var.name,
                                        ptrName + ".set(" + value::jext::VPointer.value_layout() + ", " +
                                        std::to_string(structMember.offsetOfBit / 8) + ", " +
                                        structMember.var.name + ".pointer())"
                                }}
                        };
                    }
                    case value::method::copy_by_array_call:
                    case value::method::copy_by_ptr_copy_call: {
                        auto [name, depth] = functiongenerator::depthName(pointeeResult.type, analyser);
                        depth++;
                        std::string jType;
                        std::string end;
                        for (int i = 0; i < depth; ++i) {
                            jType += "Pointer<";
                            end += ">";
                        }
                        return std::tuple{
                                std::vector{
                                        (Getter) {
                                                jType + name + end, "",
                                                "FunctionUtils.makePointer(" + ptrName + ".get(ValueLayout.ADDRESS," +
                                                std::to_string(structMember.offsetOfBit / 8) + "))"
                                        }
                                },
                                std::vector{
                                        (Setter) {
                                                jType + name + end + " " + structMember.var.name,
                                                ptrName + ".set(ValueLayout.ADDRESS, " +
                                                std::to_string(structMember.offsetOfBit / 8) + ", " //offset
                                                + structMember.var.name + ".pointer()" + //value
                                                ")"
                                        }
                                }
                        };
                    }
                    case value::method::copy_by_ptr_dest_copy_call:
                    case value::method::copy_by_ext_int128_call:
                    case value::method::copy_by_ext_long_double_call: {
                        std::string name;
                        if (value::method::copy_method_2_ext_type(pointeeResult.copy).type != value::jext::type_other) {
                            name = value::method::copy_method_2_ext_type(pointeeResult.copy).native_wrapper;
                        } else {
                            name = toCXTypeName(pointeeResult.type, analyser);
                        }
                        Getter nativeArrayGetter = (Getter) {
                                NList + "<" + name + ">", "long length",
                                name + ".list(FunctionUtils.makePointer(" + ptrName + ".get(ValueLayout.ADDRESS," +
                                std::to_string(structMember.offsetOfBit / 8) + ")), length)"};
                        Getter ptrGetter = (Getter) {
                                value::makePointer(name), "",
                                "FunctionUtils.makePointer(" + ptrName + ".get(ValueLayout.ADDRESS," +
                                std::to_string(structMember.offsetOfBit / 8) + "))"};
                        return std::tuple{std::vector{ptrGetter, nativeArrayGetter},
                                          std::vector{(Setter) {
                                                  value::makePointer(name) + " " + structMember.var.name,
                                                  ptrName + ".set(ValueLayout.ADDRESS, " +
                                                  std::to_string(structMember.offsetOfBit / 8) + ", " //offset
                                                  + structMember.var.name + ".pointer()" + //value
                                                  ")"
                                          }}};
                    }
                    case value::method::copy_target_void: {
                        auto name = toCXTypeName(pointeeResult.type, analyser);
                        return std::tuple{
                                std::vector{
                                        (Getter) {
                                                value::makePointer(name), "",
                                                "FunctionUtils.makePointer(" + ptrName + ".get(ValueLayout.ADDRESS," +
                                                std::to_string(structMember.offsetOfBit / 8) + "))"
                                        }
                                },
                                std::vector{
                                        (Setter) {
                                                value::makePointer(name) + " " + structMember.var.name,
                                                ptrName + ".set(ValueLayout.ADDRESS, " +
                                                std::to_string(structMember.offsetOfBit / 8) + ", " //offset
                                                + structMember.var.name + ".pointer()" + //value
                                                ")"
                                        }
                                }
                        };
                    }
                    case value::method::copy_internal_function_proto:
                    case value::method::copy_error: assertAppend(0, "meet copy_error || copy_internal_function_proto" +
                                                                    toStringWithoutConst(copyResult.type));;
                    case value::method::copy_void: {
                        return {std::vector{(Getter) {
                                "Pointer<?>", "",
                                "FunctionUtils.makePointer(" + ptrName + ".get(ValueLayout.ADDRESS," +
                                std::to_string(structMember.offsetOfBit / 8) + "))"
                        }
                        }, std::vector{(Setter) {
                                //setter
                                "Pointer<?> " + structMember.var.name,
                                ptrName + ".set(ValueLayout.ADDRESS, " +
                                std::to_string(structMember.offsetOfBit / 8) + ", " //offset
                                + structMember.var.name + ".pointer()" + //value
                                ")"
                        }
                        }};
                    }
                }
                assertAppend(0, "should not reach here");
            }

            case value::method::copy_by_ptr_dest_copy_call: {
                //mush have declaration
                const std::string &varName = toCXTypeDeclName(analyser, copyResult.type);
                return {std::vector{(Getter) {
                        varName, "",
                        "new " + varName + "(FunctionUtils.makePointer(" + ptrName + ".asSlice(" +
                        std::to_string(structMember.offsetOfBit / 8) + ", " +
                        std::to_string(checkResultSize(structMember.var.byteSize)) + ")))"
                }}, std::vector{(Setter) {
                        varName + " " + structMember.var.name,
                        "MemorySegment.copy(" + structMember.var.name + ".pointer(), 0," + ptrName + ", " +
                        std::to_string(structMember.offsetOfBit / 8) + ", Math.min(" +
                        std::to_string(checkResultSize(structMember.var.byteSize)) + "," + structMember.var.name +
                        ".pointer().byteSize()))"
                }
                }};
            }
            case value::method::copy_by_array_call: {
                auto element = value::method::typeCopyWithResultType(clang_getArrayElementType(copyResult.type));
                switch (element.copy) {
                    case value::method::copy_by_primitive_j_int_call:
#if NATIVE_UNSUPPORTED
                    case value::method::copy_by_primitive_j_bool_call:
                    case value::method::copy_by_primitive_j_char_call:
#endif
                    case value::method::copy_by_primitive_j_long_call:
                    case value::method::copy_by_primitive_j_float_call:
                    case value::method::copy_by_primitive_j_double_call:
                    case value::method::copy_by_primitive_j_byte_call:
                    case value::method::copy_by_primitive_j_short_call: {
                        auto native = value::method::copy_method_2_native_type(element.copy);
                        auto value = value::method::native_type_2_value_type(native);
                        return {{(Getter) {
                                value::makeVList(value), "",
                                value.wrapper() + ".list(FunctionUtils.makePointer(" + ptrName + ".asSlice(" +
                                std::to_string(structMember.offsetOfBit / 8) + ", " +
                                std::to_string(checkResultSize(structMember.var.byteSize)) + ")))"}},
                                //setter
                                std::vector{(Setter) {
                                        value::makeVList(value) + structMember.var.name,
                                        "MemorySegment.copy(" + structMember.var.name + ".pointer(), 0," + ptrName +
                                        ", " +
                                        std::to_string(structMember.offsetOfBit / 8) + ", Math.min(" +
                                        std::to_string(checkResultSize(structMember.var.byteSize)) + "," +
                                        structMember.var.name +
                                        ".pointer().byteSize()))"
                                }}
                        };
                    }
                    case value::method::copy_by_value_j_int_call:
#if NATIVE_UNSUPPORTED
                    case value::method::copy_by_value_j_char_call:
                    case value::method::copy_by_value_j_bool_call:
#endif
                    case value::method::copy_by_value_j_long_call:
                    case value::method::copy_by_value_j_float_call:
                    case value::method::copy_by_value_j_double_call:
                    case value::method::copy_by_value_j_short_call:
                    case value::method::copy_by_value_j_byte_call: {
                        auto value = copy_method_2_value_type(element.copy);
                        auto valueName = toCXTypeName(element.type, analyser);
                        return {{(Getter) {
                                value::makeVList(valueName, value), "",
                                valueName + ".list(FunctionUtils.makePointer(" + ptrName + ".asSlice(" +
                                std::to_string(structMember.offsetOfBit / 8) + ", " +
                                std::to_string(checkResultSize(structMember.var.byteSize)) + ")))"}},
                                //setter
                                std::vector{(Setter) {
                                        value::makeVList(valueName, value) + structMember.var.name,
                                        "MemorySegment.copy(" + structMember.var.name + ".pointer(), 0," + ptrName +
                                        ", " + std::to_string(structMember.offsetOfBit / 8) + ", Math.min(" +
                                        std::to_string(checkResultSize(structMember.var.byteSize)) + "," +
                                        structMember.var.name +
                                        ".pointer().byteSize()))"
                                }}
                        };
                    }
                    case value::method::copy_by_value_memory_segment_call: {
                        auto valueName = toCXTypeName(element.type, analyser);
                        if (isTypedefFunction(element.type)) {
                            return {{(Getter) {
                                    value::makePointer(valueName), "",
                                    "FunctionUtils.makePointer(" + valueName + "" + ptrName + ".asSlice(" +
                                    std::to_string(structMember.offsetOfBit / 8) + ", " +
                                    std::to_string(checkResultSize(structMember.var.byteSize)) + "))"}},
                                    //setter
                                    std::vector{(Setter) {
                                            value::makePointer(valueName) + " " + structMember.var.name,
                                            "MemorySegment.copy(" + structMember.var.name + ".pointer(), 0," + ptrName +
                                            ", " + std::to_string(structMember.offsetOfBit / 8) + ", Math.min(" +
                                            std::to_string(checkResultSize(structMember.var.byteSize)) + "," +
                                            structMember.var.name +
                                            ".pointer().byteSize()))"
                                    }}
                            };
                        }
                        return {{(Getter) {
                                value::makeVList(valueName, value::jext::VPointer), "",
                                valueName + ".list(FunctionUtils.makePointer(" + ptrName + ".asSlice(" +
                                std::to_string(structMember.offsetOfBit / 8) + ", " +
                                std::to_string(checkResultSize(structMember.var.byteSize)) + ")))"}},
                                //setter
                                std::vector{(Setter) {
                                        value::makeVList(valueName, value::jext::VPointer) + structMember.var.name,
                                        "MemorySegment.copy(" + structMember.var.name + ".pointer(), 0," + ptrName +
                                        ", " +
                                        std::to_string(structMember.offsetOfBit / 8) + ", Math.min(" +
                                        std::to_string(checkResultSize(structMember.var.byteSize)) + "," +
                                        structMember.var.name +
                                        ".pointer().byteSize()))"
                                }}
                        };
                    }
                    case value::method::copy_by_function_ptr_call: {
                        auto valueName = toCXTypeFunctionPtrName(element.type, analyser);
                        return {{(Getter) {
                                value::makePointer(valueName), "",
                                "FunctionUtils.makePointer(" + ptrName + ".asSlice(" +
                                std::to_string(structMember.offsetOfBit / 8) + ", " +
                                std::to_string(checkResultSize(structMember.var.byteSize)) + "))"}},
                                //setter
                                std::vector{(Setter) {
                                        value::makePointer(valueName) + " " + structMember.var.name,
                                        "MemorySegment.copy(" + structMember.var.name + ".pointer(), 0," + ptrName +
                                        ", " +
                                        std::to_string(structMember.offsetOfBit / 8) + ", Math.min(" +
                                        std::to_string(checkResultSize(structMember.var.byteSize)) + "," +
                                        structMember.var.name +
                                        ".pointer().byteSize()))"
                                }}
                        };
                    }
                    case value::method::copy_by_array_call:
                    case value::method::copy_by_ptr_copy_call: {
                        auto [name, depth] = functiongenerator::depthName(element.type, analyser);
                        depth++;
                        std::string jType;
                        std::string end;
                        for (int i = 0; i < depth; ++i) {
                            jType += "Pointer<";
                            end += ">";
                        }
                        return {std::vector{(Getter) {
                                jType + name + end, "",
                                "FunctionUtils.makePointer(" + ptrName + ".asSlice(" +
                                std::to_string(structMember.offsetOfBit / 8) + ", " +
                                std::to_string(checkResultSize(structMember.var.byteSize)) + "))"
                        }}, std::vector{(Setter) {
                                jType + name + end + " " + structMember.var.name,
                                "MemorySegment.copy(" + structMember.var.name + ".pointer(), 0," + ptrName + ", " +
                                std::to_string(structMember.offsetOfBit / 8) + ", Math.min(" +
                                std::to_string(checkResultSize(structMember.var.byteSize)) + "," +
                                structMember.var.name +
                                ".pointer().byteSize()))"
                        }}};
                    }
                    case value::method::copy_by_ptr_dest_copy_call:
                    case value::method::copy_by_ext_int128_call:
                    case value::method::copy_by_ext_long_double_call: {
                        std::string name;
                        if (value::method::copy_method_2_ext_type(element.copy).type != value::jext::type_other) {
                            name = value::method::copy_method_2_ext_type(element.copy).native_wrapper;
                        } else {
                            name = toCXTypeName(element.type, analyser);;
                        }
                        std::string paraType = NList + "<" + name + ">";
                        return {std::vector{(Getter) {
                                paraType, "",
                                name + ".list(FunctionUtils.makePointer(" + ptrName + ".asSlice(" +
                                std::to_string(structMember.offsetOfBit / 8) + "," +//offset
                                std::to_string(checkResultSize(structMember.var.byteSize)) +//size
                                ")))"
                        }}, std::vector{(Setter) {
                                paraType + " " + structMember.var.name,
                                "MemorySegment.copy(" + structMember.var.name + ".pointer(), 0," + ptrName + ", " +
                                std::to_string(structMember.offsetOfBit / 8) + ", Math.min(" +
                                std::to_string(checkResultSize(structMember.var.byteSize)) + "," +
                                structMember.var.name +
                                ".pointer().byteSize()))"}}
                        };
                    }
                    case value::method::copy_error:
                    case value::method::copy_void:
                    case value::method::copy_target_void:
                    case value::method::copy_internal_function_proto: {
                        assertAppend(0,
                                     "meet copy_error || copy_internal_function_proto || copy_target_void || copy_void" +
                                     toStringWithoutConst(copyResult.type))
                    };
                }
                assertAppend(0, "should not reach here");
            }
            case value::method::copy_error:
            case value::method::copy_void:
            case value::method::copy_internal_function_proto:
            case value::method::copy_target_void: {
                assertAppend(0, "meet copy_error || copy_internal_function_proto || copy_target_void || copy_void" +
                                toStringWithoutConst(copyResult.type))
            };
        }
        assertAppend(0, "should not reach here");
    }

    const auto JMethods = {"clone", "toString", "finalize", "hashCode", "getClass", "notify", "wait",
                           "pointer", "reinterpretSize"};

    // Define a function that checks if a string contains any of the elements in the vector
    bool containsJMethod(const std::string &s) {
        return std::any_of(JMethods.begin(), JMethods.end(), [s](auto e) {
            return s == e;
        }) || std::any_of(JAVA_KEY_WORDS.begin(), JAVA_KEY_WORDS.end(), [s](auto e) {
            return s == e;
        });
    }

    std::string
    StructGeneratorUtils::defaultStructMemberName(const StructDeclaration &declaration, const Analyser &analyser,
                                                  const StructMember &member) {

        std::string string = member.var.name;
        if (containsJMethod(string)) {
            return string + "$";
        }
        return string;
    }
}