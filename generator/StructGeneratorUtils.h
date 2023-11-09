//
// Created by snownf on 23-11-9.
//

#ifndef JAVABINDGEN_STRUCTGENERATORUTILS_H
#define JAVABINDGEN_STRUCTGENERATORUTILS_H

#include <string>
#include <sstream>

#define NEXT_LINE  << std::endl
#define END_LINE std::endl

class StructGeneratorUtils {
    static std::string
    makeCore(const std::string &imported, const std::string &packageName, const std::string &structName, long byteSize,
             const std::string &toString,
             const std::string &getter_setter);
}

};


#endif //JAVABINDGEN_STRUCTGENERATORUTILS_H
