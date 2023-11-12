//
// Created by snownf on 23-11-9.
//

#include "StructGeneratorUtils.h"

namespace jbindgen {
    using namespace value::jbasic;
    std::vector<Getter> StructGeneratorUtils::defaultStructDecodeGetter(const jbindgen::StructMember& structMember,
        const std::string& ptrName, void* pUserdata) {
        auto encode = value::method::typeEncode(structMember.var.type);
        const FFMType &ffmType = encode_method_2_ffm_type(encode);
        if (ffmType.type != type_other) {
            if (ffmType.type == j_void) {
                assert(0);
            }
            return {
                (Getter){
                    ffmType.primitive, "",
                    ptrName + ".get(" + ffmType.value_layout + ", " +
                    std::to_string(structMember.offsetOfBit / 8) +
                    ")"
                }
            };
        }
        switch (encode) {
            case value::method::encode_by_object_slice: {
                return {
                    (Getter){
                        structMember.var.name, "",
                        ptrName + ".asSlice(" +
                        std::to_string(structMember.offsetOfBit / 8) + ", " +
                        std::to_string(structMember.var.size) + ")"
                    }
                };
            }
            case value::method::encode_by_get_memory_segment_call: {
                return {
                    (Getter){
                        "Pointer<" + structMember.var.name + ">", "",
                        "() -> " + ptrName + ".get(ValueLayout.ADDRESS," +
                        std::to_string(structMember.offsetOfBit / 8)
                    }
                };
            }
            case value::method::encode_by_object_ptr: {
                //typedef value also contains
                //obj ptr maybe a array,so just return Pointer<T>
                return {
                    (Getter){
                        "Pointer<" + structMember.var.name + ">", "",
                        "() -> " + ptrName + ".get(ValueLayout.ADDRESS," +
                        std::to_string(structMember.offsetOfBit / 8)
                    }
                };
            }
            case value::method::encode_by_array_slice: {
                auto element = value::method::typeCopy(clang_getArrayElementType(structMember.var.type),
                                                       clang_getTypeDeclaration(
                                                           clang_getArrayElementType(structMember.var.type)));
                auto name = toString(clang_getArrayElementType(structMember.var.type));
                const FFMType&elementFFM = copy_method_2_ffm_type(element);
                std::string resultType;
                if (elementFFM.type != type_other) {
                    resultType = NativeArray + "<" + elementFFM.native_wrapper + ">";
                }
                else {
                    if (copy_method_is_value(element)) {
                        resultType = NativeValue + "<" + name + ">";
                    }
                    else {
                        resultType = NativeArray + "<" + name + ">";
                    }
                }
                return {
                    (Getter){
                        resultType, "",
                        name + ".list(" + ptrName + ".asSlice(" +
                        std::to_string(structMember.offsetOfBit / 8) + ", " +
                        std::to_string(structMember.var.size) + "))"
                    }
                };
            }
            default: {
                assert(0);
            }
        }
        return {};
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
               << "> list(MemorySegment ptr, long length, Arena arena, Consumer<MemorySegment> cleanup) {" << END_LINE
               << "        return new NativeList<>(ptr, length, arena, cleanup, " << structName << "::new, BYTE_SIZE);"
               << END_LINE
               << "    }" << END_LINE
               NEXT_LINE
               << "    public static NativeList<" << structName << "> list(Arena arena, long length) {" << END_LINE
               << "        return new NativeList<>(arena, length, " << structName << "::new, BYTE_SIZE);" << END_LINE
               << "    }" << END_LINE
               NEXT_LINE
               << "    private final MemorySegment ptr;" << END_LINE
               NEXT_LINE
               << "    public " << structName << "(MemorySegment ptr) {" << END_LINE
               << "        this.ptr = ptr;" << END_LINE
               << "    }" << END_LINE
               NEXT_LINE
               << "    public " << structName << "(MemorySegment ptr, Arena arena, Consumer<MemorySegment> cleanup) {"
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
               << "    return this;" << END_LINE
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
}