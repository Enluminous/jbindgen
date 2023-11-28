//
// Created by nettal on 23-11-7.
//

#ifndef JBINDGEN_ANALYSERUTILS_H
#define JBINDGEN_ANALYSERUTILS_H

#include <any>
#include <clang-c/Index.h>
#include <string>
#include <ostream>

namespace jbindgen {
    constexpr bool DEBUG_LOG = true;
    constexpr bool WARNING = true;
    constexpr const char* NO_NAME = "#NO_NAME#";
    constexpr const char* NO_COMMIT = "#NO_COMMIT#";

    std::string toString(const CXString&s);

    std::string toStringIfNullptr(const CXString&s);

    std::string toStringWithoutConst(const CXType&t);

    std::string getCommit(CXCursor cursor);

    class Named {
    public:
        virtual ~Named() = default;

        virtual std::string getName() = 0;
    };

    class VarDeclare {
    public:
        const std::string name;
        const CXType type;
        const int64_t byteSize;
        const std::string commit;
        const CXCursor cursor;
        const std::any extra;

        VarDeclare(std::string name, CXType type, int64_t size, std::string commit, CXCursor cxCursor);

        VarDeclare(std::string name, CXType type, int64_t size, std::string commit, CXCursor cxCursor, std::any extra);

        friend std::ostream& operator<<(std::ostream&stream, const VarDeclare&typed);
    };
}

#endif //JBINDGEN_ANALYSERUTILS_H
