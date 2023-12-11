//
// Created by snownf on 23-11-15.
//

#include <iostream>
#include <cassert>
#include "FunctionGeneratorUtils.h"
#include "StructGeneratorUtils.h"
#include "Value.h"

namespace jbindgen::functiongenerator {
    //  j_type , fd , need allocator
    std::tuple<std::string, std::string, bool> processDirectCallType(const VarDeclare &varDeclare) {
        auto copyMethod = value::method::typeCopy(varDeclare.type);
        switch (copyMethod) {
            case value::method::copy_by_primitive_j_int_call:
            case value::method::copy_by_primitive_j_long_call:
#if NATIVE_UNSUPPORTED
            case value::method::copy_by_primitive_j_bool_call:
            case value::method::copy_by_primitive_j_char_call:
#endif
            case value::method::copy_by_primitive_j_float_call:
            case value::method::copy_by_primitive_j_double_call:
            case value::method::copy_by_primitive_j_short_call:
            case value::method::copy_by_primitive_j_byte_call: {
                auto ffm = value::method::copy_method_2_native_type(copyMethod);
                assert(ffm.type != value::jbasic::type_other);
                return {ffm.primitive(), ffm.value_layout(), false};
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
                auto value = value::method::copy_method_2_value_type(copyMethod);
                assert(value.type != value::jbasic::type_other);
                return {value.primitive(), value.value_layout(), false};
            }
            case value::method::copy_by_value_memory_segment_call: {
                auto value = value::jext::VPointer;
                return {value.primitive(), value.value_layout(), false};
            }
            case value::method::copy_by_function_ptr_call:
            case value::method::copy_by_array_call: {
                auto len = getArrayLength(value::method::typeCopyWithResultType(varDeclare.type).type);
                if (len == -1) {
                    return {value::jext::Pointer.primitive(), value::jext::Pointer.value_layout(), false};
                }
                return {value::jext::Pointer.primitive(), generateFakeValueLayout(varDeclare.byteSize), true};
            }
            case value::method::copy_by_ext_int128_call:
            case value::method::copy_by_ext_long_double_call:
            case value::method::copy_by_ptr_dest_copy_call:
                return {value::jext::Pointer.primitive(), generateFakeValueLayout(varDeclare.byteSize), true};
            case value::method::copy_by_ptr_copy_call:
                return {value::jext::Pointer.primitive(), value::jext::Pointer.value_layout(), false};
            case value::method::copy_error:
            case value::method::copy_void:
            case value::method::copy_target_void:
            case value::method::copy_internal_function_proto:
                assert(0);
        }
        assert(0);
    }

    //  j parameters , fds , invokes
    std::tuple<std::vector<std::string>, std::vector<std::string>, std::vector<std::string>>
    makeParameter(const jbindgen::FunctionSymbolDeclaration &declare) {
        std::vector<std::string> jParameters;
        std::vector<std::string> fds;
        std::vector<std::string> invokes;
        int i = 0;
        for (const auto &item: declare.paras) {
            //j_type fd
            auto pre = processDirectCallType(item);
            std::string paraName = item.name;
            if (std::equal(item.name.begin(), item.name.end(), NO_NAME)) {
                paraName = makeUnnamedParaNamed(i);
            }
            jParameters.emplace_back(get<0>(pre) + " " + paraName);
            fds.emplace_back(get<1>(pre));
            invokes.emplace_back(paraName);
            i++;
        }
        return {jParameters, fds, invokes};
    }

    std::vector<FunctionWrapperInfo>
    makeWrappers(const FunctionSymbolDeclaration &declaration, const Analyser &analyser);

    FunctionInfo
    defaultMakeFunctionInfo(const jbindgen::FunctionSymbolDeclaration *declaration, const Analyser &analyser) {
        FunctionInfo info;
        info.functionName = declaration->getName();
        //parameter
        auto parameters = makeParameter(*declaration);
        info.jParameters = get<0>(parameters);
        info.parameterDescriptors = get<1>(parameters);
        info.invokeParameters = get<2>(parameters);
        //result
        info.hasResult = declaration->ret.type.kind != CXType_Void;
        info.needAllocator = false;
        if (info.hasResult) {
            auto ret = processDirectCallType(declaration->ret);
            info.jResult = get<0>(ret);
            info.resultDescriptor = get<1>(ret);
            info.needAllocator = get<2>(ret);
        }
        info.wrappers = makeWrappers(*declaration, analyser);
        return info;
    }

    std::function<std::string(std::string constructorStr)>
    callNew(const std::string &clazz) {
        return [clazz](auto str) { return "new " + clazz + "(" + str + ")"; };
    }

    std::function<std::string(std::string constructorStr)>
    callList(const std::string &clazz) {
        return [clazz](auto str) { return clazz + ".list(" + str + ")"; };
    }

    std::function<std::string(std::string constructorStr)> callLambda() {
        return [](auto str) { return "() ->(" + str + ")"; };
    }

    wrapper callPointerLambda(const std::string &name) {
        return {value::makePointer(name), ".pointer()", callLambda()};
    }

    wrapper callPointerFunctionLambda(const std::string &name) {
        return {value::makePointer(name), ".pointer(arena)",
                [name](auto str) { return name + ".ofPointer(() ->(" + str + "))"; }};
    }

    std::pair<std::string, int> depthName(CXType type, const Analyser &analyser) {
        auto result = value::method::typeCopyWithResultType(type);
        int depth = 0;
        std::string name;
        while (1) {
            if (depth > 102400)
                throw std::runtime_error(
                        "loop over 102400 at std::pair<std::string, int> depthName(CXType type, const Analyser &analyser)");
            result = value::method::typeCopyWithResultType(result.type);
            switch (result.copy) {
                case value::method::copy_by_primitive_j_byte_call:
                case value::method::copy_by_primitive_j_int_call:
#if NATIVE_UNSUPPORTED
                case value::method::copy_by_primitive_j_bool_call:
                case value::method::copy_by_primitive_j_char_call:
#endif
                case value::method::copy_by_primitive_j_long_call:
                case value::method::copy_by_primitive_j_float_call:
                case value::method::copy_by_primitive_j_double_call:
                case value::method::copy_by_primitive_j_short_call: {
                    //primitive type
                    const value::jbasic::NativeType &pointeeType = value::method::copy_method_2_native_type(
                            result.copy);
                    assert(pointeeType.type != value::jbasic::type_other);
                    auto value = value::method::native_type_2_value_type(pointeeType);
                    name = value.wrapper();
                    break;
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
                case value::method::copy_by_value_j_byte_call:
                case value::method::copy_by_ptr_dest_copy_call: {
                    name = toCXTypeDeclName(analyser, result.type);
                    break;
                }
                case value::method::copy_by_value_memory_segment_call: {
                    name = toCXTypeDeclName(analyser, result.type);
                    break;
                }
                case value::method::copy_by_ext_int128_call:
                case value::method::copy_by_ext_long_double_call: {
                    //ext type
                    auto ext = value::method::copy_method_2_ext_type(result.copy);
                    assert(ext.type != value::jext::EXT_OTHER.type);
                    name = ext.native_wrapper;
                    break;
                }
                case value::method::copy_by_function_ptr_call: {
                    name = toCXTypeFunctionPtrName(result.type, analyser);
                    break;
                }
                case value::method::copy_by_array_call: {
                    result.type = clang_getArrayElementType(result.type);
                    depth++;
                    continue;
                }
                case value::method::copy_by_ptr_copy_call: {
                    result.type = toPointeeType(result.type);
                    depth++;
                    continue;
                }
                case value::method::copy_target_void: {
                    name = toCXTypeName(result.type, analyser);
                    break;
                }
                case value::method::copy_void: {
                    name = "?";
                    break;
                }
                case value::method::copy_internal_function_proto:
                case value::method::copy_error: {
                    assert(0);
                }
            }
            break;
        }
        return {name, depth};
    }

    static std::vector<wrapper> visitDeepType(const CXType &declare, const Analyser &analyser) {
        auto [name, depth] = depthName(declare, analyser);
        assert(!name.empty());
        depth++;
        std::string jType;
        std::string end;
        for (int i = 0; i < depth; ++i) {
            jType += "Pointer<";
            end += ">";
        }
        return {(wrapper) {jType + name + end, ".pointer()", callLambda()}};
    }

    std::vector<wrapper> processWrapperCallType(const VarDeclare &declare, const Analyser &analyser) {
        std::vector<wrapper> optional;
        auto copy = value::method::typeCopyWithResultType(declare.type);
        switch (copy.copy) {
            case value::method::copy_by_function_ptr_call: {
                auto functionName = toCXTypeFunctionPtrName(copy.type, analyser);
                optional.emplace_back(callPointerFunctionLambda(functionName));
                break;
            }
            case value::method::copy_by_ptr_dest_copy_call: {
                auto typeName = toCXTypeDeclName(analyser, copy.type);
                optional.emplace_back((wrapper) {typeName, ".pointer()", callNew(typeName)});
                break;
            }
            case value::method::copy_by_ptr_copy_call: {
                auto pointeeCopy = value::method::typeCopyWithResultType(toPointeeType(copy.type));
                switch (pointeeCopy.copy) {
                    case value::method::copy_by_primitive_j_byte_call:
#if NATIVE_UNSUPPORTED
                    case value::method::copy_by_primitive_j_bool_call:
                    case value::method::copy_by_primitive_j_char_call:
#endif
                    case value::method::copy_by_primitive_j_int_call:
                    case value::method::copy_by_primitive_j_long_call:
                    case value::method::copy_by_primitive_j_float_call:
                    case value::method::copy_by_primitive_j_double_call:
                    case value::method::copy_by_primitive_j_short_call: {
                        //primitive type
                        const value::jbasic::NativeType &pointeeType = copy_method_2_native_type(pointeeCopy.copy);
                        assert(pointeeType.type != value::jbasic::type_other);
                        auto value = value::method::native_type_2_value_type(pointeeType);
                        optional.emplace_back(callPointerLambda(value.wrapper()));
                        break;
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
                        //value based
                        const std::string &pointeeName = toCXTypeDeclName(analyser, pointeeCopy.type);
                        optional.emplace_back(callPointerLambda(pointeeName));
                        break;
                    }
                    case value::method::copy_by_value_memory_segment_call: {
                        //value based
                        const std::string &pointeeName = toCXTypeDeclName(analyser, pointeeCopy.type);
                        optional.emplace_back(callPointerLambda(pointeeName));
                        break;
                    }
                    case value::method::copy_by_function_ptr_call: {
                        //FunctionProto**
                        auto functionName = toCXTypeFunctionPtrName(pointeeCopy.type, analyser);
                        optional.emplace_back(callPointerFunctionLambda(value::makePointer(functionName)));
                        break;
                    }
                        //struct ptr liked
                    case value::method::copy_by_ptr_dest_copy_call: {
                        const std::string &pointeeName = toCXTypeDeclName(analyser, pointeeCopy.type);
                        optional.emplace_back(callPointerLambda(pointeeName));
                        break;
                    }
                    case value::method::copy_by_ext_int128_call:
                    case value::method::copy_by_ext_long_double_call: {
                        //ext type
                        auto ext = copy_method_2_ext_type(pointeeCopy.copy);
                        assert(ext.type != value::jext::EXT_OTHER.type);
                        optional.emplace_back(callPointerLambda(ext.native_wrapper));
                        break;
                    }
                    case value::method::copy_by_array_call:
                    case value::method::copy_by_ptr_copy_call: {
                        std::vector<wrapper> deep = visitDeepType(pointeeCopy.type, analyser);
                        for (const auto &item: deep) {
                            optional.emplace_back(item);
                        }
                        break;
                    }
                    case value::method::copy_target_void: {
                        optional.emplace_back(callPointerLambda(toCXTypeName(pointeeCopy.type, analyser)));
                        break;
                    }
                    case value::method::copy_internal_function_proto:
                    case value::method::copy_error:
                        assert(0);
                    case value::method::copy_void: {
                        optional.emplace_back(callPointerLambda("?"));
                        break;
                    }
                }
                break;
            }
                assert(0);
            case value::method::copy_by_array_call: {
                auto elementCopy = value::method::typeCopyWithResultType(clang_getArrayElementType(copy.type));
                switch (elementCopy.copy) {
                    case value::method::copy_by_primitive_j_int_call:
#if NATIVE_UNSUPPORTED
                    case value::method::copy_by_primitive_j_bool_call:
                    case value::method::copy_by_primitive_j_char_call:
#endif
                    case value::method::copy_by_primitive_j_long_call:
                    case value::method::copy_by_primitive_j_float_call:
                    case value::method::copy_by_primitive_j_double_call:
                    case value::method::copy_by_primitive_j_short_call: {
                        const value::jbasic::NativeType &nativeType = copy_method_2_native_type(elementCopy.copy);
                        auto value = value::method::native_type_2_value_type(nativeType);
                        optional.emplace_back((wrapper) {
                                value::makeVList(value),
                                ".pointer()", callList(nativeType.wrapper())});
                        break;
                    }
                    case value::method::copy_by_primitive_j_byte_call: {
                        optional.emplace_back(
                                (wrapper) {value::jext::String.wrapper(), ".pointer()",
                                           callNew(value::jext::String.wrapper())});
                        optional.emplace_back(
                                (wrapper) {value::makeVList(value::jbasic::VByte), ".pointer()",
                                           callList(value::jbasic::Byte.wrapper())});
                        break;
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
                        auto value = copy_method_2_value_type(elementCopy.copy);
                        const std::string &valueName = toCXTypeDeclName(analyser, elementCopy.type);
                        optional.emplace_back((wrapper) {
                                value::makeVList(valueName, value), ".pointer()",
                                callList(valueName)});
                        break;
                    }
                    case value::method::copy_by_value_memory_segment_call: {
                        const std::string &valueName = toCXTypeDeclName(analyser, elementCopy.type);
                        if (isTypedefFunction(elementCopy.type)) {
                            optional.emplace_back((wrapper) {callPointerLambda(valueName)});
                            break;
                        }
                        auto value = value::jext::VPointer;
                        optional.emplace_back((wrapper) {
                                value::makeVList(valueName, value), ".pointer()",
                                callList(valueName)});
                        break;
                    }
                    case value::method::copy_by_function_ptr_call: {
                        //array have function ptr
                        const std::string &funcPtr = toCXTypeFunctionPtrName(elementCopy.type, analyser);
                        optional.emplace_back((wrapper) {
                                value::makeVList(funcPtr, value::jext::VPointer), ".pointer()",
                                callList(funcPtr)});
                        break;
                    }
                    case value::method::copy_by_ptr_dest_copy_call: {
                        //other type
                        optional.emplace_back((wrapper) {
                                NList + "<" + toCXTypeDeclName(analyser, elementCopy.type) + ">",
                                ".pointer()"
                        });
                        break;
                    }
                    case value::method::copy_by_array_call:
                    case value::method::copy_by_ptr_copy_call: {
                        std::vector<wrapper> deep = visitDeepType(elementCopy.type, analyser);
                        for (const auto &item: deep) {
                            optional.emplace_back(item);
                        }
                        break;
                    }
                    case value::method::copy_by_ext_int128_call:
                    case value::method::copy_by_ext_long_double_call: {
                        //ext type
                        auto ext = copy_method_2_ext_type(elementCopy.copy);
                        assert (ext.type != value::jext::EXT_OTHER.type);
                        optional.emplace_back((wrapper) {
                                NList + "<" + ext.native_wrapper + ">",
                                ".pointer()", callList(ext.native_wrapper)
                        });
                        break;
                    }
                    case value::method::copy_error:
                    case value::method::copy_void:
                    case value::method::copy_target_void:
                    case value::method::copy_internal_function_proto:
                        assert(0);
                }
                break;
            }
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
                optional.emplace_back((wrapper) {copy_method_2_native_type(copy.copy).primitive(), "",
                                                 [](auto s) { return s; }});
                break;
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
                auto typeName = toCXTypeName(copy.type, analyser);
                optional.emplace_back((wrapper) {typeName, ".value()", callNew(typeName)});
            }
                break;
            case value::method::copy_by_value_memory_segment_call: {
                auto typeName = toCXTypeName(copy.type, analyser);
                if (isTypedefFunction(copy.type)) {
                    optional.emplace_back((wrapper) {value::makeValue(typeName,value::jext::VPointer),".value()",callNew(typeName)});
                    break;
                }
                optional.emplace_back((wrapper) {typeName, ".value()", callNew(typeName)});
                break;
            }
            case value::method::copy_by_ext_int128_call:
            case value::method::copy_by_ext_long_double_call:
                optional.emplace_back((wrapper) {
                        value::method::copy_method_2_ext_type(copy.copy).native_wrapper, ".pointer()",
                        callNew(value::method::copy_method_2_ext_type(copy.copy).native_wrapper)});
                break;
            case value::method::copy_error:
            case value::method::copy_void:
            case value::method::copy_internal_function_proto:
            case value::method::copy_target_void:
                assert(0);
                break;
        }
        assert(!optional.empty());
        return optional;
    }

    static void
    generateWrap(const std::vector<std::vector<std::string>> &elem, std::vector<std::vector<std::string> *> &paras) {
        if (elem.empty()) {
            return;
        }
        auto iter = elem[0].begin();
        auto s = *iter;
        paras[0]->emplace_back(s);
        auto batch = paras.size() / elem[0].size();
        decltype(batch) lastBatch = 0;
        for (int i = 1; i <= paras.size(); i++) {
            if (i % batch == 0) {
                auto subElem = std::vector(elem.begin() + 1, elem.end());
                auto subParas = std::vector(paras.begin() + lastBatch, paras.begin() + i);
                generateWrap(subElem, subParas);
                iter++;
                if (iter == elem[0].end())
                    break;
                s = *iter;
                lastBatch = i;
            }
            paras[i]->emplace_back(s);
        }
    }

    std::string makeName(const VarDeclare &varDeclare, int i) {
        const auto &item = varDeclare;
        if (std::equal(item.name.begin(), item.name.end(), NO_NAME)) {
            return makeUnnamedParaNamed(i);
        }
        return item.name;
    }

    std::vector<FunctionWrapperInfo>
    makeWrappers(const FunctionSymbolDeclaration &declaration, const Analyser &analyser) {
        std::vector<std::vector<std::string>> jOptions;
        std::vector<std::vector<std::string>> decodeOptions;
        std::vector<std::vector<std::string>> encodeOptions;
        std::vector<std::vector<std::string> *> jParameters;
        std::vector<std::vector<std::string> *> decodeParameters;
        std::vector<std::vector<std::string> *> encodeParameters;
        size_t parameterCount = 1;
        for (int ij = 0; ij < declaration.paras.size(); ++ij) {
            auto varName = makeName(declaration.paras[ij], ij);
            auto para = processWrapperCallType(declaration.paras[ij], analyser);
            assert(!para.empty());
            parameterCount *= para.size();
            std::vector<std::string> jOption;
            std::vector<std::string> decodeOption;
            std::vector<std::string> encodeOption;
            for (auto &p: para) {
                jOption.emplace_back(p.type + " " + varName);
                decodeOption.emplace_back(varName + p.decode);
                encodeOption.emplace_back(p.getEncode(varName));
            }
            jOptions.emplace_back(jOption);
            decodeOptions.emplace_back(decodeOption);
            encodeOptions.emplace_back(encodeOption);
        }
        for (int j = 0; j < parameterCount; ++j) {
            jParameters.emplace_back(new std::vector<std::string>());
            decodeParameters.emplace_back(new std::vector<std::string>());
            encodeParameters.emplace_back(new std::vector<std::string>());
        }
        generateWrap(jOptions, jParameters);
        generateWrap(decodeOptions, decodeParameters);
        generateWrap(encodeOptions, encodeParameters);

        std::vector<FunctionWrapperInfo> wrappers;
        if (declaration.ret.type.kind == CXType_Void) {
            FunctionWrapperInfo info;
            info.wrapperName = declaration.getName();
            for (auto j = 0; j < jParameters.size(); ++j) {
                info.jParameters = *jParameters[j];
                info.decodeParameters = *decodeParameters[j];
                info.encodeParameters = *encodeParameters[j];
                //result is void
                wrappers.emplace_back(info);
            }
        } else {//has result
            auto ret = processWrapperCallType(declaration.ret, analyser);
            for (auto &item: ret) {
                FunctionWrapperInfo info;
                info.wrapperName = declaration.getName() + "$" + item.type;
                if (item.type.contains("<")) {//remove <> for wrapper name
                    info.wrapperName = declaration.getName() + "$"
                                       + item.type.substr(0, item.type.find_first_of('<'));
                }
                for (auto j = 0; j < jParameters.size(); ++j) {
                    info.jParameters = *jParameters[j];
                    info.decodeParameters = *decodeParameters[j];
                    info.encodeParameters = *encodeParameters[j];
                    info.wrappedResult = item.type;
                    info.makeResult = item.getEncode;
                    info.wrappedResultCall = item.decode;
                    wrappers.emplace_back(info);
                }
            }
        }
        for (const auto &item: jParameters) {
            delete item;
        }
        for (const auto &item: decodeParameters) {
            delete item;
        }
        for (const auto &item: encodeParameters) {
            delete item;
        }
        return wrappers;
    }

} // jbindgen