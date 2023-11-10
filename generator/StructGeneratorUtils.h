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

#define NEXT_LINE  << std::endl
#define END_LINE std::endl
namespace jbindgen {

    class StructGeneratorUtils {
    public:
        static std::vector<Setter>
        defaultStructDecodeSetter(const jbindgen::StructMember &structMember,
                                  const std::string &ptrName, void *pUserdata) {
            auto *analyser = reinterpret_cast<Analyser *>(pUserdata);
            const CXString &spelling = clang_getTypeSpelling(structMember.type.type);
            auto name = toString(spelling);
            Setter setter;
            setter.parameterString = "";
            setter.creator =
                    "new " + name + "(" + ptrName + ".get(ValueLayout.ADDRESS," +
                    std::to_string(structMember.offsetOfBit / 8) + ")";
            return {setter};
        };

        static std::vector<Getter>
        defaultStructDecodeGetter(const jbindgen::StructMember &structMember,
                                  const std::string &ptrName, void *pUserdata) {

            return {};
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
