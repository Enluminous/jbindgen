//
// Created by snownf on 23-11-7.
//

#ifndef JAVABINDGEN_MEMBER_H
#define JAVABINDGEN_MEMBER_H

#include "Utils.h"

namespace jbendgen {
    class Member {
    public:
        const jbindgen::Typed type;
        const int64_t offsetOfBit;

        explicit Member(jbindgen::Typed type, int64_t offsetOfBit);

        friend std::ostream &operator<<(std::ostream &stream, const Member &member) {
            stream << "Struct Member Info:  " << member.type << " offsetOfBit: " << member.offsetOfBit;
            return stream;
        }
    };

}
#endif //JAVABINDGEN_MEMBER_H
