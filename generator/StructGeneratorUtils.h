//
// Created by snownf on 23-11-9.
//

#ifndef JAVABINDGEN_STRUCTGENERATORUTILS_H
#define JAVABINDGEN_STRUCTGENERATORUTILS_H

#include <string>
#include <sstream>
#include <vector>
#include <iostream>
#include <cassert>
#include "Value.h"
#include "../analyser/Analyser.h"
#include "GenUtils.h"

#define NEXT_LINE  << std::endl
#define END_LINE std::endl

namespace jbindgen {
    class StructGeneratorUtils {
    public:
        static std::vector<Setter>
        defaultStructDecodeSetter(const jbindgen::StructMember&structMember,
                                  const std::string&ptrName, void* pUserdata) {
            auto encode = value::method::typeCopy(structMember.var.type, structMember.var.cursor);
            const value::jbasic::FFMType&ffmType = copy_method_2_ffm_type(encode);
            if (ffmType.type != value::jbasic::type_other) {
                if (ffmType.type == value::jbasic::j_void) {
                    assert(0);
                }
                if (value::method::copy_method_is_value(encode)) {
                    //value based
                    return {
                        (Setter){
                            toString(structMember.var.type) + " " + structMember.var.name,
                            ptrName + ".set(" + ffmType.value_layout + ", " +
                            std::to_string(structMember.offsetOfBit / 8) + ", " + structMember.var.name + ".value())"
                        }
                    };
                } //primitive
                return {
                    (Setter){
                        ffmType.primitive + " " + structMember.var.name,
                        ptrName + ".set(" + ffmType.value_layout + ", " +
                        std::to_string(structMember.offsetOfBit / 8) + ", " + structMember.var.name +
                        ")"
                    }
                };
            }
            auto ext = value::method::copy_method_2_ext_type(encode);
            if (ext.type != value::jext::type_other) {
                return {
                    (Setter){
                        ext.native_wrapper+ " " + structMember.var.name,
                        //copy dest for ext type
                        "MemorySegment.copy(" + structMember.var.name + ", 0," + ptrName + ", " +
                        std::to_string(structMember.offsetOfBit / 8) + ", Math.min(" +
                        std::to_string(structMember.var.size) + "," + structMember.var.name + ".byteSize()))"
                    }
                };
            }
            switch (encode) {
                case value::method::copy_by_set_memory_segment_call:
                    return {
                        (Setter){
                            "Pointer<?> " + structMember.var.name,
                            ptrName + ".set(ValueLayout.ADDRESS, " +
                            std::to_string(structMember.offsetOfBit / 8) + ", " //offset
                            + structMember.var.name + ".pointer()" + //value
                            ")"
                        }
                    };
                case value::method::copy_by_value_memory_segment_call:
                    return {
                        (Setter){
                            toString(structMember.var.type) + " " + structMember.var.name,
                            ptrName + ".set(ValueLayout.ADDRESS, " +
                            std::to_string(structMember.offsetOfBit / 8) + ", " //offset
                            + structMember.var.name + ".value()" + //value
                            ")"
                        }
                    };

                case value::method::copy_by_ptr_copy_call:
                    return {
                        (Setter){
                            toString(structMember.var.type) + " " + structMember.var.name,
                            ptrName + ".set(ValueLayout.ADDRESS, " +
                            std::to_string(structMember.offsetOfBit / 8) + ", " //offset
                            + structMember.var.name + ".pointer()" + //value
                            ")"
                        }
                    };
                case value::method::copy_by_ptr_dest_copy_call:
                    return {
                        (Setter){
                            toString(structMember.var.type) + " " + structMember.var.name,
                            "MemorySegment.copy(" + structMember.var.name + ", 0," + ptrName + ", " +
                            std::to_string(structMember.offsetOfBit / 8) + ", Math.min(" +
                            std::to_string(structMember.var.size) + "," + structMember.var.name + ".byteSize()))"
                        }
                    };
                case value::method::copy_by_array_call: {
                    auto element = value::method::typeCopy(clang_getArrayElementType(structMember.var.type),
                                                           clang_getTypeDeclaration(
                                                               clang_getArrayElementType(structMember.var.type)));
                    auto name = toString(clang_getArrayElementType(structMember.var.type));
                    const value::jbasic::FFMType&elementFFM = copy_method_2_ffm_type(element);
                    std::string paraType;
                    if (elementFFM.type != value::jbasic::type_other) {
                        paraType = NativeArray + "<" + elementFFM.native_wrapper + ">";
                    }
                    else {
                        if (copy_method_is_value(element)) {
                            paraType = NativeValue + "<" + name + ">";
                        }
                        else {
                            paraType = NativeArray + "<" + name + ">";
                        }
                    }
                    return {
                        (Setter){
                            paraType + " " + structMember.var.name,
                            "MemorySegment.copy(" + structMember.var.name + ".pointer(), 0," + ptrName + ", " +
                            std::to_string(structMember.offsetOfBit / 8) + ", Math.min(" +
                            std::to_string(structMember.var.size) + "," + structMember.var.name + ".byteSize()))"
                        }
                    };
                }
                default: {
                    assert(0);
                }
            }
            return {};
        };

        static std::vector<Getter>
        defaultStructDecodeGetter(const jbindgen::StructMember&structMember,
                                  const std::string&ptrName, void* pUserdata);;

        static std::string defaultStructMemberRename(const std::string&name, void* pUserdata) {
            return name;
        };

        static std::string
        makeCore(const std::string&imported, const std::string&packageName, const std::string&structName,
                 long byteSize,
                 const std::string&toString,
                 const std::string&getter_setter);
    };
}


#endif //JAVABINDGEN_STRUCTGENERATORUTILS_H
