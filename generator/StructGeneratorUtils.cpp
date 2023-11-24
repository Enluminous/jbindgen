//
// Created by snownf on 23-11-9.
//

#include "StructGeneratorUtils.h"
#include "StructGenerator.h"

namespace jbindgen {
    using namespace value::jbasic;

    std::string
    StructGeneratorUtils::makeCore(const std::string &imported, const std::string &packageName,
                                   const std::string &structName, long byteSize,
                                   const std::string &toString, const std::string &getter_setter) {
        {
            std::stringstream ss;
            ss << "package " << packageName << ";" << END_LINE
               NEXT_LINE
               << imported << END_LINE
               NEXT_LINE
               << "public final class " << structName << " implements Struct {" << END_LINE
               << "    public static final MemoryLayout MEMORY_LAYOUT = MemoryLayout.structLayout(MemoryLayout.paddingLayout("
               << byteSize << "));" << END_LINE
               << "    public static final long BYTE_SIZE = " << byteSize << ";" << END_LINE
               NEXT_LINE
               << "    public static NativeList<" << structName << "> list(MemorySegment ptr) {" << END_LINE
               << "        return new NativeList<>(ptr, " << structName << "::new, BYTE_SIZE);" << END_LINE
               << "    }" << END_LINE
               NEXT_LINE
               << "    public static NativeList<" << structName << "> list(MemorySegment ptr, long length) {"
               << END_LINE
               << "        return new NativeList<>(ptr, length, " << structName << "::new, BYTE_SIZE);" << END_LINE
               << "    }" << END_LINE
               NEXT_LINE
               << "    public static NativeList<" << structName
               << "> list(MemorySegment ptr, long length, Arena arena, Consumer<MemorySegment> cleanup) {" <<
               END_LINE
               << "        return new NativeList<>(ptr, length, arena, cleanup, " << structName <<
               "::new, BYTE_SIZE);"
               << END_LINE
               << "    }" << END_LINE
               NEXT_LINE
               << "    public static NativeList<" << structName << "> list(Arena arena, long length) {" << END_LINE
               << "        return new NativeList<>(arena, length, " << structName << "::new, BYTE_SIZE);" <<
               END_LINE
               << "    }" << END_LINE
               NEXT_LINE
               << "    private final MemorySegment ptr;" << END_LINE
               NEXT_LINE
               << "    public " << structName << "(MemorySegment ptr) {" << END_LINE
               << "        this.ptr = ptr;" << END_LINE
               << "    }" << END_LINE
               NEXT_LINE
               << "    public " << structName <<
               "(MemorySegment ptr, Arena arena, Consumer<MemorySegment> cleanup) {"
               << END_LINE
               << "        this.ptr = ptr.reinterpret(BYTE_SIZE, arena, cleanup);" << END_LINE
               << "    }" << END_LINE
               NEXT_LINE
               << "    public " << structName << "(Arena arena) {" << END_LINE
               << "        ptr = arena.allocate(BYTE_SIZE);" << END_LINE
               << "    }" << END_LINE
               NEXT_LINE
               << "    public " << structName << " fill(int value) {" << END_LINE
               << "        ptr.fill((byte) value);" << END_LINE
               << "        return this;" << END_LINE
               << "    }" << END_LINE
               NEXT_LINE
               << "    @Override" << END_LINE
               << "    public MemorySegment pointer() {" << END_LINE
               << "        return ptr;" << END_LINE
               << "    }" << END_LINE
               NEXT_LINE
               << "    public long byteSize() {" << END_LINE
               << "        return ptr.byteSize();" << END_LINE
               << "    }" << END_LINE
               NEXT_LINE
               << getter_setter << END_LINE
               NEXT_LINE
               << toString << END_LINE
               << "}" << END_LINE;
            return ss.str();
        }
    }

    int64_t getArrayLength(CXType type) {
        if (type.kind == CXType_Elaborated) {
            auto declared = clang_getCursorType(clang_getTypeDeclaration(type));
            return getArrayLength(declared);
        }
        return clang_getNumElements(type);
    }

    std::string toPointerName(const VarDeclare &declare) {
        const auto &pointee_type = toPointeeType(declare.type, declare.cursor);
        const auto &extra = declare.extra;
        std::string typeName = toStringWithoutConst(pointee_type);
        if (extra.has_value()) {
            typeName = std::any_cast<std::string>(extra);
        }
        return typeName;
    }

    std::string toArrayName(const VarDeclare &declare) {
        auto name = toStringWithoutConst(clang_getArrayElementType(declare.type));
        if (clang_Cursor_isAnonymous(
                clang_getTypeDeclaration(clang_getArrayElementType(declare.type)))) {
            assert(declare.extra.has_value());
            name = std::any_cast<std::string>(declare.extra);
        }
        return name;
    }

    std::string toVarDeclareString(const VarDeclare &varDeclare) {
        auto name = toStringWithoutConst(varDeclare.type);
        if (clang_Cursor_isAnonymous(clang_getTypeDeclaration(varDeclare.type))) {
            assert(varDeclare.extra.has_value());
            name = any_cast<std::string>(varDeclare.extra);
        }
        return name;
    }

    int32_t getPointeeOrArrayDepth(CXType type) {
        if (type.kind == CXType_Pointer || type.kind == CXType_BlockPointer) {
            return getPointeeOrArrayDepth(clang_getPointeeType(type)) + 1;
        }
        if (type.kind == CXType_Elaborated) {
            auto declared = clang_getCursorType(clang_getTypeDeclaration(type));
            return getPointeeOrArrayDepth(declared);
        }
        if (type.kind == CXType_VariableArray || type.kind == CXType_IncompleteArray ||
            type.kind == CXType_ConstantArray || type.kind == CXType_DependentSizedArray) {
            auto ele = clang_getArrayElementType(type);
            return getPointeeOrArrayDepth(ele) + 1;
        }
        return 0;
    }

    CXType toPointeeType(CXType type, CXCursor c) {
        if (type.kind == CXType_Elaborated) {
            auto declared = clang_getCursorType(c);
            return toPointeeType(declared, clang_getTypeDeclaration(declared));
        }
        return clang_getPointeeType(type);
    }

    std::string toDeepPointerName(const VarDeclare &declare) {
        auto deepType = toDeepPointeeType(declare.type, declare.cursor);
        return toStringWithoutConst(deepType);
    }

    CXType toDeepPointeeType(CXType type, CXCursor c) {
        auto pointee = toPointeeType(type, c);
        if (pointee.kind == CXType_BlockPointer || pointee.kind == CXType_Pointer) {
            return toPointeeType(type, clang_getTypeDeclaration(type));
        }
        return type;
    }

    std::vector<Setter>
    StructGeneratorUtils::defaultStructDecodeSetter(const StructMember &structMember, const std::string &ptrName,
                                                    void *pUserdata) {
        return get<1>(defaultStructDecodeShared(structMember, ptrName));
    }

    std::vector<Getter> StructGeneratorUtils::defaultStructDecodeGetter(const jbindgen::StructMember &structMember,
                                                                        const std::string &ptrName, void *pUserdata) {
        return get<0>(defaultStructDecodeShared(structMember, ptrName));
    }

    std::tuple<std::vector<Getter>, std::vector<Setter>>
    StructGeneratorUtils::defaultStructDecodeShared(const StructMember &structMember, const std::string &ptrName) {
        auto encode = value::method::typeCopy(structMember.var.type, structMember.var.cursor);
        switch (encode) {
            case value::method::copy_by_set_j_int_call:
            case value::method::copy_by_set_j_long_call:
            case value::method::copy_by_set_j_float_call:
            case value::method::copy_by_set_j_double_call:
            case value::method::copy_by_set_j_char_call:
            case value::method::copy_by_set_j_short_call:
            case value::method::copy_by_set_j_byte_call:
            case value::method::copy_by_set_j_bool_call: {
                const value::jbasic::FFMType &ffmType = copy_method_2_ffm_type(encode);
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
            case value::method::copy_by_value_j_char_call:
            case value::method::copy_by_value_j_short_call:
            case value::method::copy_by_value_j_byte_call:
            case value::method::copy_by_value_j_bool_call: {
                const value::jbasic::FFMType &ffmType = copy_method_2_ffm_type(encode);
                assert(ffmType.type != value::jbasic::type_other);
                return {std::vector{(Getter) {
                        toStringWithoutConst(structMember.var.type), "",
                        "new " + toStringWithoutConst(structMember.var.type) + "(" +
                        ptrName + ".asSlice(" +
                        std::to_string(structMember.offsetOfBit / 8) + "," +//offset
                        std::to_string(structMember.var.byteSize) +//size
                        "))"}}, std::vector{(Setter) {
                        toStringWithoutConst(structMember.var.type) + " " + structMember.var.name,
                        ptrName + ".set(" + ffmType.value_layout() + ", " +
                        std::to_string(structMember.offsetOfBit / 8) + ", " +
                        structMember.var.name +
                        ".value())"
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
                        std::to_string(structMember.var.byteSize) + "))"
                }}, std::vector{(Setter) {
                        ext.native_wrapper + " " + structMember.var.name,
                        //copy dest for ext type
                        "MemorySegment.copy(" + structMember.var.name + ", 0," + ptrName + ", " +
                        std::to_string(structMember.offsetOfBit / 8) + ", Math.min(" +
                        std::to_string(structMember.var.byteSize) + "," + structMember.var.name +
                        ".byteSize()))"
                }}
                };
            }
            case value::method::copy_by_set_memory_segment_call:
                return {std::vector{(Getter) {
                        "Pointer<?>", "",
                        "() -> " + ptrName + ".get(ValueLayout.ADDRESS," +
                        std::to_string(structMember.offsetOfBit / 8) + ")"
                }
                }, std::vector{(Setter) {
                        "Pointer<?> " + structMember.var.name,
                        ptrName + ".set(ValueLayout.ADDRESS, " +
                        std::to_string(structMember.offsetOfBit / 8) + ", " //offset
                        + structMember.var.name + ".pointer()" + //value
                        ")"
                }
                }};
            case value::method::copy_by_value_memory_segment_call:
                return {std::vector{(Getter) {
                        "Value<MemorySegment>", "",
                        "() -> " + ptrName + ".get(ValueLayout.ADDRESS," +
                        std::to_string(structMember.offsetOfBit / 8) + ")"
                }}, std::vector{(Setter) {
                        "Value<MemorySegment> " + structMember.var.name,
                        ptrName + ".set(ValueLayout.ADDRESS, " +
                        std::to_string(structMember.offsetOfBit / 8) + ", " //offset
                        + structMember.var.name + ".value()" + //value
                        ")"
                }
                }};

            case value::method::copy_by_ptr_copy_call: {
                auto pointee = toPointeeType(structMember.var.type, structMember.var.cursor);
                auto copy = value::method::typeCopy(pointee, clang_getTypeDeclaration(pointee));
                if (copy == value::method::copy_by_set_j_byte_call) {//maybe a String
                    std::vector<Setter> setters;
                    std::vector<Getter> getters;
                    setters.emplace_back((Setter) {
                            JString + " " + structMember.var.name,
                            ptrName + ".set(ValueLayout.ADDRESS, " +
                            std::to_string(structMember.offsetOfBit / 8) + ", " //offset
                            + structMember.var.name + ".pointer()" + //value
                            ")"
                    });
                    setters.emplace_back((Setter) {
                            Byte.native_wrapper() + " " + structMember.var.name,
                            ptrName + ".set(ValueLayout.ADDRESS, " +
                            std::to_string(structMember.offsetOfBit / 8) + ", " //offset
                            + structMember.var.name + ".pointer()" + //value
                            ")"
                    });
                    getters.emplace_back((Getter) {
                            "Pointer<" + Byte.native_wrapper() + ">", "",
                            "() -> " + ptrName + ".get(ValueLayout.ADDRESS," +
                            std::to_string(structMember.offsetOfBit / 8) + ")"
                    });
                    getters.emplace_back((Getter) {
                            NativeValue + "<" + Byte.native_wrapper() + ">", "long length",
                            Byte.native_wrapper() + ".list(" + ptrName + ".get(ValueLayout.ADDRESS," +
                            std::to_string(structMember.offsetOfBit / 8) + "), length)"
                    });
                    return {getters, setters};
                }
                const std::string &pointerName = toPointerName(structMember.var);
                Getter ptrGetter = (Getter) {
                        "Pointer<" + pointerName + ">", "",
                        "() -> " + ptrName + ".get(ValueLayout.ADDRESS," +
                        std::to_string(structMember.offsetOfBit / 8) + ")"
                };
                Getter nativeArrayGetter = (Getter) {
                        NativeArray + "<" + pointerName + ">", "long length",
                        pointerName + ".list(" + ptrName + ".get(ValueLayout.ADDRESS," +
                        std::to_string(structMember.offsetOfBit / 8) + "), length)"};
                Getter nativeValueGetter = (Getter) {
                        NativeValue + "<" + pointerName + ">", "long length",
                        pointerName + ".list(" + ptrName + ".get(ValueLayout.ADDRESS," +
                        std::to_string(structMember.offsetOfBit / 8) + "), length)"};

                if (copy_method_2_ffm_type(copy).type != type_other) {
                    if (copy_method_is_value(copy)) {
                        return {{nativeValueGetter, ptrGetter},
                                std::vector{(Setter) {
                                        NativeValue + "<" + pointerName + "> " + structMember.var.name,
                                        ptrName + ".set(ValueLayout.ADDRESS, " +
                                        std::to_string(structMember.offsetOfBit / 8) + ", " //offset
                                        + structMember.var.name + ".pointer()" + //value
                                        ")"
                                }}
                        };
                    } else {
                        return std::tuple{std::vector{ptrGetter, nativeArrayGetter},
                                          std::vector{(Setter) {
                                                  NativeArray + "<" + pointerName + "> " +
                                                  structMember.var.name,
                                                  ptrName + ".set(ValueLayout.ADDRESS, " +
                                                  std::to_string(structMember.offsetOfBit / 8) + ", " //offset
                                                  + structMember.var.name + ".pointer()" + //value
                                                  ")"
                                          }, (Setter) {
                                                  pointerName + " " + structMember.var.name,
                                                  ptrName + ".set(ValueLayout.ADDRESS, " +
                                                  std::to_string(structMember.offsetOfBit / 8) + ", " //offset
                                                  + structMember.var.name + ".pointer()" + //value
                                                  ")"
                                          },}};
                    }
                }
                int32_t depth = getPointeeOrArrayDepth(structMember.var.type);
                if (depth < 2) {
                    return std::tuple{std::vector{ptrGetter, nativeArrayGetter},
                                      std::vector{(Setter) {
                                              NativeArray + "<" + pointerName + "> " + structMember.var.name,
                                              ptrName + ".set(ValueLayout.ADDRESS, " +
                                              std::to_string(structMember.offsetOfBit / 8) + ", " //offset
                                              + structMember.var.name + ".pointer()" + //value
                                              ")"
                                      }, (Setter) {
                                              pointerName + " " + structMember.var.name,
                                              ptrName + ".set(ValueLayout.ADDRESS, " +
                                              std::to_string(structMember.offsetOfBit / 8) + ", " //offset
                                              + structMember.var.name + ".pointer()" + //value
                                              ")"
                                      }, (Setter) {
                                              "Pointer<" + pointerName + "> " + structMember.var.name,
                                              ptrName + ".set(ValueLayout.ADDRESS, " +
                                              std::to_string(structMember.offsetOfBit / 8) + ", " //offset
                                              + structMember.var.name + ".pointer()" + //value
                                              ")"
                                      }
                                      }};
                }
                std::string name = toDeepPointerName(structMember.var);
                auto deepType = toDeepPointeeType(structMember.var.type, structMember.var.cursor);
                std::string jType;
                std::string end;
                for (int i = 0; i < depth; ++i) {
                    jType += "Pointer<";
                    end += ">";
                }

                auto deepCopy = value::method::typeCopy(deepType, clang_getTypeDeclaration(deepType));
                const value::jbasic::FFMType &elementFFM = copy_method_2_ffm_type(deepCopy);
                if (elementFFM.type != value::jbasic::type_other &&
                    !value::method::copy_method_is_value(deepCopy)) {
                    return std::tuple{std::vector{(Getter) {
                            jType + elementFFM.native_wrapper() + end, "",
                            "() -> " + ptrName + ".get(ValueLayout.ADDRESS," +
                            std::to_string(structMember.offsetOfBit / 8) + ")"
                    }}, std::vector{(Setter) {
                            jType + elementFFM.native_wrapper() + end + " " + structMember.var.name,
                            ptrName + ".set(ValueLayout.ADDRESS, " +
                            std::to_string(structMember.offsetOfBit / 8) + ", " //offset
                            + structMember.var.name + ".pointer()" + //value
                            ")"
                    }}};
                }
                return std::tuple{std::vector{(Getter) {
                        jType + toStringWithoutConst(deepType) + end, "",
                        "() -> " + ptrName + ".get(ValueLayout.ADDRESS," +
                        std::to_string(structMember.offsetOfBit / 8) + ")"
                }}, std::vector{(Setter) {
                        jType + toStringWithoutConst(deepType) + end + " " + structMember.var.name,
                        ptrName + ".set(ValueLayout.ADDRESS, " +
                        std::to_string(structMember.offsetOfBit / 8) + ", " //offset
                        + structMember.var.name + ".pointer()" + //value
                        ")"
                }}
                };
            }

            case value::method::copy_by_ptr_dest_copy_call: {
                const std::string &varName = toVarDeclareString(structMember.var);
                return {std::vector{(Getter) {
                        varName, "",
                        "new " + varName + "(" + ptrName + ".get(ValueLayout.ADDRESS," +
                        std::to_string(structMember.offsetOfBit / 8) + "))"
                }}, std::vector{(Setter) {
                        varName + " " + structMember.var.name,
                        "MemorySegment.copy(" + structMember.var.name + ", 0," + ptrName + ", " +
                        std::to_string(structMember.offsetOfBit / 8) + ", Math.min(" +
                        std::to_string(structMember.var.byteSize) + "," + structMember.var.name + ".byteSize()))"
                }
                }};
            }
            case value::method::copy_by_array_call: {
                if (getPointeeOrArrayDepth(structMember.var.type) > 1) {
                    std::string jType;
                    std::string end;
                    for (int i = 0; i < getPointeeOrArrayDepth(structMember.var.type); ++i) {
                        jType += "Pointer<";
                        end += ">";
                    }
                    auto type = toDeepPointeeType(structMember.var.type,structMember.var.cursor);
                    auto copy = value::method::typeCopy(type, clang_getTypeDeclaration(type));
                    const value::jbasic::FFMType &elementFFM = copy_method_2_ffm_type(copy);
                    if (elementFFM.type != value::jbasic::type_other && !
                            value::method::copy_method_is_value(copy)) {
                        return {std::vector{
                                (Getter) {
                                        jType + elementFFM.native_wrapper() + end, "",
                                        "() -> " + ptrName + ".get(ValueLayout.ADDRESS," +
                                        std::to_string(structMember.offsetOfBit / 8) + ")"
                                }}, std::vector{
                                (Setter) {
                                        jType + elementFFM.native_wrapper()
                                        + end + " " + structMember.var.name,
                                        "MemorySegment.copy(" + structMember.var.name + ".pointer(), 0," + ptrName +
                                        ", " +
                                        std::to_string(structMember.offsetOfBit / 8) + ", Math.min(" +
                                        std::to_string(structMember.var.byteSize) + "," + structMember.var.name +
                                        ".byteSize()))"
                                }}};
                    }
                    return std::tuple{std::vector{(Getter) {
                            jType + toStringWithoutConst(type) + end, "",
                            "() -> " + ptrName + ".get(ValueLayout.ADDRESS," +
                            std::to_string(structMember.offsetOfBit / 8) + ")"
                    }}, std::vector{
                            (Setter) {
                                    jType + toStringWithoutConst(type) + end + " " + structMember.var.name,
                                    "MemorySegment.copy(" + structMember.var.name + ".pointer(), 0," + ptrName + ", " +
                                    std::to_string(structMember.offsetOfBit / 8) + ", Math.min(" +
                                    std::to_string(structMember.var.byteSize) + "," + structMember.var.name +
                                    ".byteSize()))"
                            }}};
                }
                //normal array
                auto element = value::method::typeCopy(clang_getArrayElementType(structMember.var.type),
                                                       clang_getTypeDeclaration(
                                                               clang_getArrayElementType(structMember.var.type)));
                auto name = toArrayName(structMember.var);
                const value::jbasic::FFMType &elementFFM = copy_method_2_ffm_type(element);
                std::string paraType;
                if (elementFFM.type != value::jbasic::type_other) {
                    paraType = NativeArray + "<" + elementFFM.native_wrapper() + ">";
                } else {
                    if (copy_method_is_value(element)) {
                        paraType = NativeValue + "<" + name + ">";
                    } else {
                        paraType = NativeArray + "<" + name + ">";
                    }
                }
                return std::tuple{std::vector{(Getter) {
                        paraType, "",
                        paraType + ".list(" + ptrName + ".asSlice(" +
                        std::to_string(structMember.offsetOfBit / 8) + "," +//offset
                        std::to_string(structMember.var.byteSize) +//size
                        "))"
                }}, std::vector{(Setter) {
                        paraType + " " + structMember.var.name,
                        "MemorySegment.copy(" + structMember.var.name + ".pointer(), 0," + ptrName + ", " +
                        std::to_string(structMember.offsetOfBit / 8) + ", Math.min(" +
                        std::to_string(structMember.var.byteSize) + "," + structMember.var.name + ".byteSize()))"}}
                };
            }
            case value::method::copy_error:
            case value::method::copy_void:
            case value::method::copy_internal_function_proto:
                assert(0);
        }
        assert(0);
    }
}