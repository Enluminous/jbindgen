//
// Created by nettal on 23-11-7.
//

#ifndef JAVABINDGEN_ANALYSERUTILS_H
#define JAVABINDGEN_ANALYSERUTILS_H

#include <clang-c/Index.h>
#include <string>
#include <ostream>

namespace jbindgen {
    constexpr bool DEBUG_LOG = true;
    constexpr const char *NO_NAME = "#NO_NAME#";
    constexpr const char *NO_COMMIT = "#NO_COMMIT#";

    std::string toString(const CXString &s);

    std::string toString(const CXType &t);

    std::string getCommit(CXCursor cursor);

    class Typed {
    public:
        const std::string name;
        const CXType type;
        const int64_t size;
        const std::string commit;
        Typed(std::string name, CXType type, int64_t size,std::string commit);

        friend std::ostream &operator<<(std::ostream &stream, const Typed &typed);
    };
}

#endif //JAVABINDGEN_ANALYSERUTILS_H
