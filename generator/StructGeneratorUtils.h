//
// Created by snownf on 23-11-9.
//

#ifndef JAVABINDGEN_STRUCTGENERATORUTILS_H
#define JAVABINDGEN_STRUCTGENERATORUTILS_H

#include <string>
#include <sstream>
#include <vector>
#include "Value.h"

#define NEXT_LINE  << std::endl
#define END_LINE std::endl
namespace jbindgen {

    class StructGeneratorUtils {
    public:
        static std::vector<Setter>
        defaultStructDecodeSetter(const jbindgen::StructMember &structMember, void *pUserdata) {

            return {};
        };

        static std::vector<Getter>
        defaultStructDecodeGetter(const jbindgen::StructMember &structMember, void *pUserdata) {

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
