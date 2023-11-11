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
            int type = 0;
            isFFM_RawType(structMember.var.type, structMember.var.cursor, &type);
            if (type > 0) {//value based class,use java primitives is better than MemorySegment
                const CXString &spelling = clang_getTypeSpelling(structMember.var.type);
                auto name = toString(spelling);
                Getter getter;
                getter.parameterString = "";
                getter.creator =
                        "new " + name + "(" + ptrName + ".asSlice(" +
                        std::to_string(structMember.offsetOfBit / 8) + "," + std::to_string(structMember.var.size) +
                        ")";
                return {getter};
            } else if (type == 0) { //special
                throw std::runtime_error("member type is void");
            } else {

            }

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
