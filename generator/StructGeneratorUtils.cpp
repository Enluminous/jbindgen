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
            std::string core = std::vformat(
                    "package {4};\n"
                    "\n"
                    "\n"
                    "{5}"
                    "\n"
                    "import java.lang.foreign.Arena;\n"
                    "import java.lang.foreign.MemoryLayout;\n"
                    "import java.lang.foreign.MemorySegment;\n"
                    "import java.lang.foreign.ValueLayout;\n"
                    "import java.util.function.Consumer;\n"
                    "\n"
                    "\n"
                    "public final class {0} implements Pointer<{0}> {{\n"
                    "    public static final MemoryLayout MEMORY_LAYOUT = {1};\n"
                    "    public static final long BYTE_SIZE = MEMORY_LAYOUT.byteSize();\n"
                    "\n"
                    "    public static NList<{0}> list(MemorySegment ptr) {{\n"
                    "        return new NList<>(ptr, {0}::new, BYTE_SIZE);\n"
                    "    }}\n"
                    "\n"
                    "    public static NList<{0}> list(MemorySegment ptr, long length) {{\n"
                    "        return new NList<>(ptr, length, {0}::new, BYTE_SIZE);\n"
                    "    }}\n"
                    "\n"
                    "    public static NList<{0}> list(MemorySegment ptr, long length, Arena arena, Consumer<MemorySegment> cleanup) {{\n"
                    "        return new NList<>(ptr, length, arena, cleanup, {0}::new, BYTE_SIZE);\n"
                    "    }}\n"
                    "\n"
                    "    public static NList<{0}> list(Arena arena, long length) {{\n"
                    "        return new NList<>(arena, length, {0}::new, BYTE_SIZE);\n"
                    "    }}\n"
                    "\n"
                    "    private final MemorySegment ptr;\n"
                    "\n"
                    "    public {0}(MemorySegment ptr) {{\n"
                    "        this.ptr = ptr;\n"
                    "    }}\n"
                    "\n"
                    "    public {0}(MemorySegment ptr, Arena arena, Consumer<MemorySegment> cleanup) {{\n"
                    "        this.ptr = ptr.reinterpret(BYTE_SIZE, arena, cleanup);\n"
                    "    }}\n"
                    "\n"
                    "    public {0}(Arena arena) {{\n"
                    "        ptr = arena.allocate(BYTE_SIZE);\n"
                    "    }}\n"
                    "\n"
                    "    public {0} fill(int value) {{\n"
                    "        ptr.fill((byte) value);\n"
                    "        return this;\n"
                    "    }}\n"
                    "\n"
                    "    @Override\n"
                    "    public MemorySegment pointer() {{\n"
                    "        return ptr;\n"
                    "    }}\n"
                    "\n"
                    "    public long byteSize() {{\n"
                    "        return ptr.byteSize();\n"
                    "    }}\n"
                    "\n"
                    "{2}\n"
                    "{3}\n"
                    "}}", std::make_format_args(structName, generateFakeValueLayout(byteSize), getter_setter,
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
        auto encode = value::method::typeCopy(structMember.var.type);
        switch (encode) {
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
                const value::jbasic::NativeType &ffmType = copy_method_2_native_type(encode);
                assert(ffmType.type != value::jbasic::type_other);
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
                auto value = copy_method_2_value_type(encode);
                assert(value.type != value::jbasic::type_other);
                auto name = toCXTypeName(value::method::typeCopyWithResultType(structMember.var.type).type,
                                         analyser);
                return {std::vector{(Getter) {
                        name, "",
                        "new " + name + "(" +
                        ptrName + ".asSlice(" +
                        std::to_string(structMember.offsetOfBit / 8) + "," +//offset
                        std::to_string(checkResultSize(structMember.var.byteSize)) +//size
                        "))"}}, std::vector{(Setter) {
                        //setter
                        name + " " + structMember.var.name,
                        ptrName + ".set(" + value.value_layout() + ", " +
                        std::to_string(structMember.offsetOfBit / 8) + ", " +
                        structMember.var.name + ".value())"
                }}
                };
            }
            case value::method::copy_by_ext_int128_call:
            case value::method::copy_by_ext_long_double_call: {
                auto ext = value::method::copy_method_2_ext_type(encode);
                assert(ext.type != value::jext::type_other);
                return {std::vector{(Getter) {
                        ext.native_wrapper, "",
                        "new " + ext.native_wrapper + "(" + ptrName + ".asSlice(" +
                        std::to_string(structMember.offsetOfBit / 8) + ", " +
                        std::to_string(checkResultSize(structMember.var.byteSize)) + "))"
                }}, std::vector{(Setter) {
                        //setter
                        ext.native_wrapper + " " + structMember.var.name,
                        //copy dest for ext type
                        "MemorySegment.copy(" + structMember.var.name + ", 0," + ptrName + ", " +
                        std::to_string(structMember.offsetOfBit / 8) + ", Math.min(" +
                        std::to_string(checkResultSize(structMember.var.byteSize)) + "," + structMember.var.name +
                        ".byteSize()))"
                }}
                };
            }
            case value::method::copy_by_value_memory_segment_call:
                return {std::vector{(Getter) {
                        "Value<MemorySegment>", "",
                        "() -> " + ptrName + ".get(ValueLayout.ADDRESS," +
                        std::to_string(structMember.offsetOfBit / 8) + ")"
                }}, std::vector{(Setter) {
                        //setter
                        "Value<MemorySegment> " + structMember.var.name,
                        ptrName + ".set(ValueLayout.ADDRESS, " +
                        std::to_string(structMember.offsetOfBit / 8) + ", " //offset
                        + structMember.var.name + ".value()" + //value
                        ")"
                }
                }};

            case value::method::copy_by_ptr_copy_call: {
                auto pointee = toPointeeType(value::method::typeCopyWithResultType(structMember.var.type).type);
                assert(pointee.kind != CXType_Invalid);
                auto copy = value::method::typeCopy(pointee);
                //special char*
                switch (copy) {
                    case value::method::copy_by_primitive_j_int_call:
                    case value::method::copy_by_primitive_j_long_call:
#if NATIVE_UNSUPPORTED
                    case value::method::copy_by_primitive_j_bool_call:
                    case value::method::copy_by_primitive_j_char_call:
#endif
                    case value::method::copy_by_primitive_j_float_call:
                    case value::method::copy_by_primitive_j_double_call:
                    case value::method::copy_by_primitive_j_short_call: {
                        auto native = copy_method_2_native_type(copy);
                        auto value = value::method::native_type_2_value_type(native);
                        Getter ptrGetter = (Getter) {
                                "Pointer<" + value.wrapper() + ">", "",
                                "() -> " + ptrName + ".get(ValueLayout.ADDRESS," +
                                std::to_string(structMember.offsetOfBit / 8) + ")"
                        };
                        return {{(Getter) {
                                value::makeVList(value),
                                "long length",
                                value.wrapper() + ".list(" + ptrName + ".get(ValueLayout.ADDRESS," +
                                std::to_string(structMember.offsetOfBit / 8) + "), length)"}, ptrGetter},
                                //setter
                                std::vector{(Setter) {
                                        value::makeVList(value)
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
                    case value::method::copy_by_primitive_j_byte_call: {
                        std::vector<Setter> setters;
                        std::vector<Getter> getters;
                        setters.emplace_back((Setter) {
                                value::jext::String.wrapper() + " " + structMember.var.name,
                                ptrName + ".set(ValueLayout.ADDRESS, " +
                                std::to_string(structMember.offsetOfBit / 8) + ", " //offset
                                + structMember.var.name + ".pointer()" + //value
                                ")"
                        });
                        setters.emplace_back((Setter) {
                                Byte.wrapper() + " " + structMember.var.name,
                                ptrName + ".set(ValueLayout.ADDRESS, " +
                                std::to_string(structMember.offsetOfBit / 8) + ", " //offset
                                + structMember.var.name + ".pointer()" + //value
                                ")"
                        });
                        //getter
                        getters.emplace_back((Getter) {
                                "Pointer<" + VByte.wrapper() + ">", "",
                                "() -> " + ptrName + ".get(ValueLayout.ADDRESS," +
                                std::to_string(structMember.offsetOfBit / 8) + ")"
                        });
                        getters.emplace_back((Getter) {
                                value::makeVList(value::jbasic::VByte), "long length",
                                Byte.wrapper() + ".list(" + ptrName + ".get(ValueLayout.ADDRESS," +
                                std::to_string(structMember.offsetOfBit / 8) + "), length)"
                        });
                        return {getters, setters};
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
                        auto value = copy_method_2_value_type(copy);
                        auto pointerTypeName = value.wrapper();
                        Getter ptrGetter = (Getter) {
                                "Pointer<" + pointerTypeName + ">", "",
                                "() -> " + ptrName + ".get(ValueLayout.ADDRESS," +
                                std::to_string(structMember.offsetOfBit / 8) + ")"
                        };
                        return {{(Getter) {
                                value::makeVList(pointerTypeName, value),
                                "long length",
                                pointerTypeName + ".list(" + ptrName + ".get(ValueLayout.ADDRESS," +
                                std::to_string(structMember.offsetOfBit / 8) + "), length)"}, ptrGetter},
                                //setter
                                std::vector{(Setter) {
                                        value::makeVList(pointerTypeName, value)
                                        + " " + structMember.var.name,
                                        ptrName + ".set(ValueLayout.ADDRESS, " +
                                        std::to_string(structMember.offsetOfBit / 8) + ", " //offset
                                        + structMember.var.name + ".pointer()" + //value
                                        ")"
                                }}
                        };
                    }
                    case value::method::copy_by_value_memory_segment_call: {
                        auto value = value::jext::VPointer;
                        auto pointerTypeName = toCXTypeName(pointee, analyser);
                        Getter ptrGetter = (Getter) {
                                "Pointer<" + pointerTypeName + ">", "",
                                "() -> " + ptrName + ".get(ValueLayout.ADDRESS," +
                                std::to_string(structMember.offsetOfBit / 8) + ")"
                        };
                        return {{(Getter) {
                                value::makeVList(pointerTypeName, value),
                                "long length",
                                pointerTypeName + ".list(" + ptrName + ".get(ValueLayout.ADDRESS," +
                                std::to_string(structMember.offsetOfBit / 8) + "), length)"}, ptrGetter},
                                //setter
                                std::vector{(Setter) {
                                        value::makeVList(pointerTypeName, value)
                                        + " " + structMember.var.name,
                                        ptrName + ".set(ValueLayout.ADDRESS, " +
                                        std::to_string(structMember.offsetOfBit / 8) + ", " //offset
                                        + structMember.var.name + ".pointer()" + //value
                                        ")"
                                }}
                        };
                    }
                    case value::method::copy_by_array_call:
                    case value::method::copy_by_ptr_copy_call: {
                        auto [name, depth] = functiongenerator::depthName(pointee, analyser);
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
                                                "() -> " + ptrName + ".get(ValueLayout.ADDRESS," +
                                                std::to_string(structMember.offsetOfBit / 8) + ")"
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
                        if (value::method::copy_method_2_ext_type(copy).type != value::jext::type_other) {
                            name = value::method::copy_method_2_ext_type(copy).native_wrapper;
                        } else {
                            name = toCXTypeName(pointee, analyser);
                        }
                        Getter nativeArrayGetter = (Getter) {
                                NList + "<" + name + ">", "long length",
                                name + ".list(" + ptrName + ".get(ValueLayout.ADDRESS," +
                                std::to_string(structMember.offsetOfBit / 8) + "), length)"};
                        Getter ptrGetter = (Getter) {
                                "Pointer<" + name + ">", "",
                                "() -> " + ptrName + ".get(ValueLayout.ADDRESS," +
                                std::to_string(structMember.offsetOfBit / 8) + ")"};
                        return std::tuple{std::vector{ptrGetter, nativeArrayGetter},
                                          std::vector{(Setter) {
                                                  NList + "<" + name + "> " + structMember.var.name,
                                                  ptrName + ".set(ValueLayout.ADDRESS, " +
                                                  std::to_string(structMember.offsetOfBit / 8) + ", " //offset
                                                  + structMember.var.name + ".pointer()" + //value
                                                  ")"
                                          }, (Setter) {
                                                  name + " " + structMember.var.name,
                                                  ptrName + ".set(ValueLayout.ADDRESS, " +
                                                  std::to_string(structMember.offsetOfBit / 8) + ", " //offset
                                                  + structMember.var.name + ".pointer()" + //value
                                                  ")"
                                          }}};
                    }
                    case value::method::copy_internal_function_proto: {
                        auto name = toCXTypeName(toPointeeType(
                                value::method::typeCopyWithResultType(structMember.var.type).type), analyser);
                        return std::tuple{
                                std::vector{
                                        (Getter) {
                                                value::makePointer(name), "",
                                                "() -> " + ptrName + ".get(ValueLayout.ADDRESS," +
                                                std::to_string(structMember.offsetOfBit / 8) + ")"
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
                    case value::method::copy_target_void: {
                        auto name = toCXTypeName(pointee, analyser);
                        return std::tuple{
                                std::vector{
                                        (Getter) {
                                                value::makePointer(name), "",
                                                "() -> " + ptrName + ".get(ValueLayout.ADDRESS," +
                                                std::to_string(structMember.offsetOfBit / 8) + ")"
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
                    case value::method::copy_error:
                        assert(0);
                    case value::method::copy_void: {
                        return {std::vector{(Getter) {
                                "Pointer<?>", "",
                                "() -> " + ptrName + ".get(ValueLayout.ADDRESS," +
                                std::to_string(structMember.offsetOfBit / 8) + ")"
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
                assert(0);
            }

            case value::method::copy_by_ptr_dest_copy_call: {
                //mush have declaration
                const std::string &varName = toCXTypeDeclName(analyser, structMember.var.type);
                return {std::vector{(Getter) {
                        varName, "",
                        "new " + varName + "(" + ptrName + ".get(ValueLayout.ADDRESS," +
                        std::to_string(structMember.offsetOfBit / 8) + "))"
                }}, std::vector{(Setter) {
                        varName + " " + structMember.var.name,
                        "MemorySegment.copy(" + structMember.var.name + ", 0," + ptrName + ", " +
                        std::to_string(structMember.offsetOfBit / 8) + ", Math.min(" +
                        std::to_string(checkResultSize(structMember.var.byteSize)) + "," + structMember.var.name +
                        ".byteSize()))"
                }
                }};
            }
            case value::method::copy_by_array_call: {
                auto array = value::method::typeCopyWithResultType(structMember.var.type);
                auto element = value::method::typeCopyWithResultType(clang_getArrayElementType(array.type));
                switch (element.copy) {
                    case value::method::copy_by_primitive_j_int_call:
#if NATIVE_UNSUPPORTED
                    case value::method::copy_by_primitive_j_bool_call:
                    case value::method::copy_by_primitive_j_char_call:
#endif
                    case value::method::copy_by_primitive_j_long_call:
                    case value::method::copy_by_primitive_j_float_call:
                    case value::method::copy_by_primitive_j_double_call:
                    case value::method::copy_by_primitive_j_short_call: {
                        auto native = value::method::copy_method_2_native_type(element.copy);
                        auto value = value::method::native_type_2_value_type(native);
                        return {{(Getter) {
                                value::makeVList(value), "",
                                value.wrapper() + ".list(" + ptrName + ".asSlice(" +
                                std::to_string(structMember.offsetOfBit / 8) + ", " +
                                std::to_string(checkResultSize(structMember.var.byteSize)) + "))"}},
                                //setter
                                std::vector{(Setter) {
                                        value::makeVList(value) + structMember.var.name,
                                        "MemorySegment.copy(" + structMember.var.name + ", 0," + ptrName + ", " +
                                        std::to_string(structMember.offsetOfBit / 8) + ", Math.min(" +
                                        std::to_string(checkResultSize(structMember.var.byteSize)) + "," +
                                        structMember.var.name +
                                        ".byteSize()))"
                                }}
                        };
                    }
                    case value::method::copy_by_primitive_j_byte_call: {
                        std::vector<Setter> setters;
                        std::vector<Getter> getters;
                        setters.emplace_back((Setter) {
                                value::jext::String.wrapper() + " " + structMember.var.name,
                                "MemorySegment.copy(" + structMember.var.name + ", 0," + ptrName + ", " +
                                std::to_string(structMember.offsetOfBit / 8) + ", Math.min(" +
                                std::to_string(checkResultSize(structMember.var.byteSize)) + "," +
                                structMember.var.name +
                                ".byteSize()))"
                        });
                        setters.emplace_back((Setter) {
                                value::makeVList(VByte) + structMember.var.name,
                                "MemorySegment.copy(" + structMember.var.name + ", 0," + ptrName + ", " +
                                std::to_string(structMember.offsetOfBit / 8) + ", Math.min(" +
                                std::to_string(checkResultSize(structMember.var.byteSize)) + "," +
                                structMember.var.name +
                                ".byteSize()))"
                        });
                        //getter
                        getters.emplace_back((Getter) {
                                value::makeVList(value::jbasic::VByte), "",
                                VByte.wrapper() + ".list(" + ptrName + ".asSlice(" +
                                std::to_string(structMember.offsetOfBit / 8) + ", " +
                                std::to_string(checkResultSize(structMember.var.byteSize)) + "))"
                        });
                        return {getters, setters};
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
                                value.wrapper() + ".list(" + ptrName + ".asSlice(" +
                                std::to_string(structMember.offsetOfBit / 8) + ", " +
                                std::to_string(checkResultSize(structMember.var.byteSize)) + "))"}},
                                //setter
                                std::vector{(Setter) {
                                        value::makeVList(valueName, value) + structMember.var.name,
                                        "MemorySegment.copy(" + structMember.var.name + ", 0," + ptrName + ", " +
                                        std::to_string(structMember.offsetOfBit / 8) + ", Math.min(" +
                                        std::to_string(checkResultSize(structMember.var.byteSize)) + "," +
                                        structMember.var.name +
                                        ".byteSize()))"
                                }}
                        };
                    }
                    case value::method::copy_by_value_memory_segment_call: {
                        auto value = value::jext::VPointer;
                        auto valueName = toCXTypeName(element.type, analyser);
                        return {{(Getter) {
                                value::makeVList(valueName, value), "",
                                value.wrapper() + ".list(" + ptrName + ".asSlice(" +
                                std::to_string(structMember.offsetOfBit / 8) + ", " +
                                std::to_string(checkResultSize(structMember.var.byteSize)) + "))"}},
                                //setter
                                std::vector{(Setter) {
                                        value::makeVList(valueName, value) + structMember.var.name,
                                        "MemorySegment.copy(" + structMember.var.name + ", 0," + ptrName + ", " +
                                        std::to_string(structMember.offsetOfBit / 8) + ", Math.min(" +
                                        std::to_string(checkResultSize(structMember.var.byteSize)) + "," +
                                        structMember.var.name +
                                        ".byteSize()))"
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
                                "() -> " + ptrName + ".asSlice(" +
                                std::to_string(structMember.offsetOfBit / 8) + ", " +
                                std::to_string(checkResultSize(structMember.var.byteSize)) + ")"
                        }}, std::vector{(Setter) {
                                jType + name + end + " " + structMember.var.name,
                                "MemorySegment.copy(" + structMember.var.name + ", 0," + ptrName + ", " +
                                std::to_string(structMember.offsetOfBit / 8) + ", Math.min(" +
                                std::to_string(checkResultSize(structMember.var.byteSize)) + "," +
                                structMember.var.name +
                                ".byteSize()))"
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
                                name + ".list(" + ptrName + ".asSlice(" +
                                std::to_string(structMember.offsetOfBit / 8) + "," +//offset
                                std::to_string(checkResultSize(structMember.var.byteSize)) +//size
                                "))"
                        }}, std::vector{(Setter) {
                                paraType + " " + structMember.var.name,
                                "MemorySegment.copy(" + structMember.var.name + ".pointer(), 0," + ptrName + ", " +
                                std::to_string(structMember.offsetOfBit / 8) + ", Math.min(" +
                                std::to_string(checkResultSize(structMember.var.byteSize)) + "," +
                                structMember.var.name +
                                ".byteSize()))"}}
                        };
                    }
                    case value::method::copy_error:
                    case value::method::copy_void:
                    case value::method::copy_target_void:
                    case value::method::copy_internal_function_proto:
                        assert(0);
                }
                assert(0);
            }
            case value::method::copy_error:
            case value::method::copy_void:
            case value::method::copy_internal_function_proto:
            case value::method::copy_target_void:
                assert(0);
        }
        assert(0);
    }

    const std::vector JMethods{"clone", "toString", "finalize", "hashCode", "getClass", "notify", "wait", "pointer"};

    // Define a function that checks if a string contains any of the elements in the vector
    bool containsJMethod(const std::string &s) {
        for (const auto &m: JMethods) {
            if (s.find(m) != std::string::npos) {
                return true;
            }
        }
        return false;
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