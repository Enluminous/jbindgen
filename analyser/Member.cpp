//
// Created by snownf on 23-11-7.
//

#include "Member.h"


jbendgen::Member::Member(jbindgen::Typed type, int64_t offsetOfBit) : type(std::move(type)), offsetOfBit(offsetOfBit) {
}
