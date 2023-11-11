//
// Created by snownf on 23-11-9.
//

#ifndef JAVABINDGEN_STRUCTGENERATORUTILS_H
#define JAVABINDGEN_STRUCTGENERATORUTILS_H

#include <string>
#include <sstream>
#include <vector>
#include "Value.h"
#include "../analyser/Analyser.h"
#include "GenUtils.h"

#define NEXT_LINE  << std::endl
#define END_LINE std::endl
namespace jbindgen {

    class StructGeneratorUtils {
    public:
        static std::vector<Setter>
        defaultStructDecodeSetter(const jbindgen::StructMember &structMember,
                                  const std::string &ptrName, void *pUserdata) {
            return {};
        };

        static std::vector<Getter>
        defaultStructDecodeGetter(const jbindgen::StructMember &structMember,
                                  const std::string &ptrName, void *pUserdata) {
            auto *analyser = reinterpret_cast<Analyser *>(pUserdata);

            auto type = structMember.var.type;
            if (type.kind == CXType_Pointer || type.kind == CXType_BlockPointer) {
                auto ptr_type = clang_getPointeeType(type);
                toString(ptr_type);

            }
            const CXString &spelling = clang_getTypeSpelling(type);
            auto name = toString(spelling);

            Getter getter;
            getter.parameterString = "";
            getter.creator =
                    "new " + name + "(" + ptrName + ".get(ValueLayout.ADDRESS," +
                    std::to_string(structMember.offsetOfBit / 8) + ")";
            return {getter};
        };

        static std::string defaultStructMemberRename(const std::string &name, void *pUserdata) {

            return name;
        };

        static std::string
        makeCore(const std::string &imported, const std::string &packageName, const std::string &structName,
                 long byteSize,
                 const std::string &toString,
                 const std::string &getter_setter);
    };
}


#endif //JAVABINDGEN_STRUCTGENERATORUTILS_H
