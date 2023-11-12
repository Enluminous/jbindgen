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
            return {};
        };

        static std::vector<Getter>
        defaultStructDecodeGetter(const jbindgen::StructMember&structMember,
                                  const std::string&ptrName, void* pUserdata) {
            auto encode = value::typeEncode(structMember.var.type);
            const value::FFMType&ffmType = value::encode_method_2_ffm_type(encode);
            if (ffmType.type != value::type_other) {
                if (ffmType.type == value::j_void) {
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
                case value::encode_by_object_slice: {
                    return {
                        (Getter){
                            structMember.var.name, "",
                            ptrName + ".asSlice(" +
                            std::to_string(structMember.offsetOfBit / 8) + ", " +
                            std::to_string(structMember.var.size) + ")"
                        }
                    };
                }
                case value::encode_by_object_ptr: {//typedef value also contains
                    //obj ptr maybe a array,so just return Pointer<T>
                    return {
                        (Getter){
                            "Pointer<" + structMember.var.name + ">", "",
                            "() -> " + ptrName + ".get(" + value::MemorySegment.value_layout + "," +
                            std::to_string(structMember.offsetOfBit / 8)
                        }
                    };
                }
                case value::encode_by_array_slice: {
                    auto element = value::typeCopy(clang_getArrayElementType(structMember.var.type),
                                                   clang_getTypeDeclaration(
                                                       clang_getArrayElementType(structMember.var.type)));
                    auto name = toString(clang_getArrayElementType(structMember.var.type));
                    const value::FFMType&elementFFM = value::copy_method_2_ffm_type(element);
                    std::string resultType;
                    if (elementFFM.type != value::type_other) {
                        resultType = NativeArray + "<" + elementFFM.native_wrapper + ">";
                    }
                    else {
                        if (value::copy_method_is_value(element)) {
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
        };

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
