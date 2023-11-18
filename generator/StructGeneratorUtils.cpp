//
// Created by snownf on 23-11-9.
//

#include "StructGeneratorUtils.h"
#include "StructGenerator.h"

namespace jbindgen {
    using namespace value::jbasic;

    std::vector<Getter> StructGeneratorUtils::defaultStructDecodeGetter(const jbindgen::StructMember &structMember,
                                                                        const std::string &ptrName, void *pUserdata) {
        auto encode = value::method::typeEncode(structMember.var.type);
        const FFMType &ffmType = encode_method_2_ffm_type(encode);
        if (ffmType.type != type_other) {
            if (ffmType.type == j_void) {
                assert(0);
            }
            return {
                    (Getter) {
                            ffmType.primitive(), "",
                            ptrName + ".get(" + ffmType.value_layout() + ", " +
                            std::to_string(structMember.offsetOfBit / 8) +
                            ")"
                    }
            };
        }
        auto ext = value::method::encode_method_2_ext_type(encode);
        if (ext.type != value::jext::type_other) {
            return {
                    (Getter) {
                            ext.native_wrapper, "",
                            "new " + ext.native_wrapper + "(" + ptrName + ".get(" + ffmType.value_layout() + ", " +
                            std::to_string(structMember.offsetOfBit / 8) +
                            "))"
                    }
            };
        }
        switch (encode) {
            case value::method::encode_by_object_slice_call: {
                return {
                        (Getter) {
                                toVarDeclareString(structMember.var), "",
                                "new " + toVarDeclareString(structMember.var) + "(" + ptrName + ".asSlice(" +
                                std::to_string(structMember.offsetOfBit / 8) + ", " +
                                std::to_string(structMember.var.size) + "))"
                        }
                };
            }
            case value::method::encode_by_get_memory_segment_call: {
                return {
                        (Getter) {
                                "Pointer<?>", "",
                                "() -> " + ptrName + ".get(ValueLayout.ADDRESS," +
                                std::to_string(structMember.offsetOfBit / 8) + ")"
                        }
                };
            }
            case value::method::encode_by_object_ptr_call: {
                //typedef value also contains
                //obj ptr maybe an array,so just return Pointer<T>
                const auto &pointerName1 = toPointerName(structMember.var);
                return {
                        (Getter) {
                                "Pointer<" + pointerName1 + ">", "",
                                "() -> " + ptrName + ".get(ValueLayout.ADDRESS," +
                                std::to_string(structMember.offsetOfBit / 8) + ")"
                        }
                };
            }
            case value::method::encode_by_array_slice_call: {
                auto element = value::method::typeCopy(clang_getArrayElementType(structMember.var.type),
                                                       clang_getTypeDeclaration(
                                                               clang_getArrayElementType(structMember.var.type)));
                auto name = toArrayName(structMember.var);
                const FFMType &elementFFM = copy_method_2_ffm_type(element);
                std::string resultType;
                if (elementFFM.type != type_other) {
                    resultType = NativeArray + "<" + elementFFM.native_wrapper() + ">";
                } else {
                    if (copy_method_is_value(element)) {
                        resultType = NativeValue + "<" + name + ">";
                    } else {
                        resultType = NativeArray + "<" + name + ">";
                    }
                }
                return {
                        (Getter) {
                                resultType, "",
                                name + ".list(" + ptrName + ".asSlice(" +
                                std::to_string(structMember.offsetOfBit / 8) + ", " +
                                std::to_string(structMember.var.size) + "))"
                        }
                };
            }
            default: {
                std::cout << "unhandled encode :" << encode << std::endl;
                assert(0);
            }
        }
        assert(0);
    }

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

    CXType toPointeeType(CXType type) {
        if (type.kind == CXType_Elaborated) {
            auto declared = clang_getCursorType(clang_getTypeDeclaration(type));
            return toPointeeType(declared);
        }
        if (type.kind == CXType_Typedef) {
            auto ori = clang_getTypedefDeclUnderlyingType(clang_getTypeDeclaration(type));
            return toPointeeType(ori);
        }
        return clang_getPointeeType(type);
    }

    int64_t getArrayLength(CXType type) {
        if (type.kind == CXType_Elaborated) {
            auto declared = clang_getCursorType(clang_getTypeDeclaration(type));
            return getArrayLength(declared);
        }
        if (type.kind == CXType_Typedef) {
            auto ori = clang_getTypedefDeclUnderlyingType(clang_getTypeDeclaration(type));
            return getArrayLength(ori);
        }
        return clang_getNumElements(type);
    }

    std::string toPointerName(const VarDeclare &declare) {
        const auto &pointee_type = toPointeeType(declare.type);
        const auto &extra = declare.extra;
        std::string typeName = toString(pointee_type);
        if (extra.has_value()) {
            typeName = std::any_cast<std::string>(extra);
        }
        return typeName;
    }

    std::string toArrayName(const VarDeclare &declare) {
        auto name = toString(clang_getArrayElementType(declare.type));
        if (clang_Cursor_isAnonymous(
                clang_getTypeDeclaration(clang_getArrayElementType(declare.type)))) {
            assert(declare.extra.has_value());
            name = std::any_cast<std::string>(declare.extra);
        }
        return name;
    }

    std::string toVarDeclareString(const VarDeclare &varDeclare) {
        auto name = toString(varDeclare.type);
        if (clang_Cursor_isAnonymous(clang_getTypeDeclaration(varDeclare.type))) {
            assert(varDeclare.extra.has_value());
            name = any_cast<std::string>(varDeclare.extra);
        }
        return name;
    }

    std::vector<Setter>
    StructGeneratorUtils::defaultStructDecodeSetter(const StructMember &structMember, const std::string &ptrName,
                                                    void *pUserdata) {
        if (DEBUG_LOG) {
            unsigned line;
            unsigned column;
            CXFile file;
            unsigned offset;
            clang_getSpellingLocation(clang_getCursorLocation(structMember.var.cursor), &file, &line, &column,
                                      &offset);
            std::cout << "defaultStructDecodeSetter processing: " << toString(clang_getFileName(file)) << ":"
                      << line
                      << ":" << column << std::endl << std::flush;
        }
        auto encode = value::method::typeCopy(structMember.var.type, structMember.var.cursor);
        {

            const value::jbasic::FFMType &ffmType = copy_method_2_ffm_type(encode);
            if (ffmType.type != value::jbasic::type_other) {
                if (ffmType.type == value::jbasic::j_void) {
                    assert(0);
                }
                if (value::method::copy_method_is_value(encode)) {
                    //value based
                    return {
                            (Setter) {
                                    toString(structMember.var.type) + " " + structMember.var.name,
                                    ptrName + ".set(" + ffmType.value_layout() + ", " +
                                    std::to_string(structMember.offsetOfBit / 8) + ", " + structMember.var.name +
                                    ".value())"
                            }
                    };
                } //primitive
                return {
                        (Setter) {
                                ffmType.primitive() + " " + structMember.var.name,
                                ptrName + ".set(" + ffmType.value_layout() + ", " +
                                std::to_string(structMember.offsetOfBit / 8) + ", " + structMember.var.name +
                                ")"
                        }
                };
            }
            auto ext = value::method::copy_method_2_ext_type(encode);
            if (ext.type != value::jext::type_other) {
                return {
                        (Setter) {
                                ext.native_wrapper + " " + structMember.var.name,
                                //copy dest for ext type
                                "MemorySegment.copy(" + structMember.var.name + ", 0," + ptrName + ", " +
                                std::to_string(structMember.offsetOfBit / 8) + ", Math.min(" +
                                std::to_string(structMember.var.size) + "," + structMember.var.name + ".byteSize()))"
                        }
                };
            }
        }
        switch (encode) {
            case value::method::copy_by_set_memory_segment_call:
                return {
                        (Setter) {
                                "Pointer<?> " + structMember.var.name,
                                ptrName + ".set(ValueLayout.ADDRESS, " +
                                std::to_string(structMember.offsetOfBit / 8) + ", " //offset
                                + structMember.var.name + ".pointer()" + //value
                                ")"
                        }
                };
            case value::method::copy_by_value_memory_segment_call:
                return {
                        (Setter) {
                                "Value<MemorySegment> " + structMember.var.name,
                                ptrName + ".set(ValueLayout.ADDRESS, " +
                                std::to_string(structMember.offsetOfBit / 8) + ", " //offset
                                + structMember.var.name + ".value()" + //value
                                ")"
                        }
                };

            case value::method::copy_by_ptr_copy_call: {
                auto pointee = toPointeeType(structMember.var.type);
                auto copy = value::method::typeCopy(pointee, clang_getTypeDeclaration(pointee));
                if (copy == value::method::copy_by_set_j_byte_call) {//maybe a String
                    std::vector<Setter> setters;
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
                    return setters;
                }
                const std::string &pointerName = toPointerName(structMember.var);
                if (copy_method_2_ffm_type(copy).type != type_other) {
                    if (copy_method_is_value(copy)) {
                        return {
                                (Setter) {
                                        NativeValue + "<" + pointerName + "> " + structMember.var.name,
                                        ptrName + ".set(ValueLayout.ADDRESS, " +
                                        std::to_string(structMember.offsetOfBit / 8) + ", " //offset
                                        + structMember.var.name + ".pointer()" + //value
                                        ")"
                                }};
                    } else {
                        return {
                                (Setter) {
                                        NativeArray + "<" + pointerName + "> " + structMember.var.name,
                                        ptrName + ".set(ValueLayout.ADDRESS, " +
                                        std::to_string(structMember.offsetOfBit / 8) + ", " //offset
                                        + structMember.var.name + ".pointer()" + //value
                                        ")"
                                },
                                (Setter) {
                                        pointerName + " " + structMember.var.name,
                                        ptrName + ".set(ValueLayout.ADDRESS, " +
                                        std::to_string(structMember.offsetOfBit / 8) + ", " //offset
                                        + structMember.var.name + ".pointer()" + //value
                                        ")"
                                },};
                    }
                }
                return {
                        (Setter) {
                                NativeArray + "<" + pointerName + "> " + structMember.var.name,
                                ptrName + ".set(ValueLayout.ADDRESS, " +
                                std::to_string(structMember.offsetOfBit / 8) + ", " //offset
                                + structMember.var.name + ".pointer()" + //value
                                ")"
                        },
                        (Setter) {
                                pointerName + " " + structMember.var.name,
                                ptrName + ".set(ValueLayout.ADDRESS, " +
                                std::to_string(structMember.offsetOfBit / 8) + ", " //offset
                                + structMember.var.name + ".pointer()" + //value
                                ")"
                        },
                        (Setter) {
                                "Pointer<" + pointerName + "> " + structMember.var.name,
                                ptrName + ".set(ValueLayout.ADDRESS, " +
                                std::to_string(structMember.offsetOfBit / 8) + ", " //offset
                                + structMember.var.name + ".pointer()" + //value
                                ")"
                        }
                };
            }
            case value::method::copy_by_ptr_dest_copy_call:
                return {
                        (Setter) {
                                toVarDeclareString(structMember.var) + " " + structMember.var.name,
                                "MemorySegment.copy(" + structMember.var.name + ", 0," + ptrName + ", " +
                                std::to_string(structMember.offsetOfBit / 8) + ", Math.min(" +
                                std::to_string(structMember.var.size) + "," + structMember.var.name +
                                ".byteSize()))"
                        }
                };
            case value::method::copy_by_array_call: {
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
                return {
                        (Setter) {
                                paraType + " " + structMember.var.name,
                                "MemorySegment.copy(" + structMember.var.name + ".pointer(), 0," + ptrName + ", " +
                                std::to_string(structMember.offsetOfBit / 8) + ", Math.min(" +
                                std::to_string(structMember.var.size) + "," + structMember.var.name +
                                ".byteSize()))"
                        }
                };
            }
            default: {
                assert(0);
            }
        }
        assert(0);
    }
}
